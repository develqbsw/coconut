package sk.qbsw.security.authentication.test.performance;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import sk.qbsw.security.authentication.test.performance.task.LoginTask;

/**
 * The login performance test. Measures the time of login for specified login iterations and threads count.
 *
 * @author Tomas Lauro
 * 
 * @version 1.11.4
 * @since 1.7.2
 */
public class LoginPerformanceTest
{
	/** The Constant LOGIN_COUNT. */
	private static final int LOGIN_COUNT = 5000;

	/** The Constant THREADS_COUNT. */
	private static final int THREADS_COUNT = 10;

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main (String[] args)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("./spring/test-context.xml");
		ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("loginTaskExecutor");

		System.out.println("-------------------------------------------------");
		System.out.println("| Database login performance test begins \t|");
		System.out.println("-------------------------------------------------");
		float databasePerformanceTestTime = testLogin(context, taskExecutor, "databaseLoginTask");

		System.out.println("-------------------------------------------------");
		System.out.println("| Login iterations: " + LOGIN_COUNT + "\t\t\t\t|");
		System.out.println("| Threads count   : " + THREADS_COUNT + "\t\t\t\t|");
		System.out.println("|-----------------------------------------------|");
		System.out.println("| The database test takes " + databasePerformanceTestTime + " seconds \t|");
		System.out.println("-------------------------------------------------");

		//shutdown executor
		taskExecutor.shutdown();
		System.exit(0);
	}

	/**
	 * Test login.
	 *
	 * @param context the context
	 * @param taskExecutor the task executor
	 * @param loginBeanId the login bean id
	 * @return the float
	 */
	private static float testLogin (ApplicationContext context, ThreadPoolTaskExecutor taskExecutor, String loginBeanId)
	{
		//create login tasks
		List<LoginTask> loginTasks = new ArrayList<LoginTask>();
		for (int i = 0; i < THREADS_COUNT; i++)
		{
			LoginTask loginTask = (LoginTask) context.getBean(loginBeanId);
			loginTask.init("Login task " + i, LOGIN_COUNT);
			loginTasks.add(loginTask);
		}

		//take the begin time
		long start = System.currentTimeMillis();

		//run task
		for (LoginTask loginTask : loginTasks)
		{
			taskExecutor.execute(loginTask);
		}

		//wait unit finished
		for (;;)
		{
			int count = taskExecutor.getActiveCount();
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			if (count == 0)
			{
				break;
			}
		}

		//elapsed time in seconds
		return (System.currentTimeMillis() - start) / 1000F;
	}
}
