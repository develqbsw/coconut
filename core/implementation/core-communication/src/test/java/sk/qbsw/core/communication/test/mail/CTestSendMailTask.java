package sk.qbsw.core.communication.test.mail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.communication.mail.job.CSendMailTask;

/**
 * The test for sending mail task.
 * 
 * @author Tomas Lauro
 * @version 1.9.0
 * @since 1.9.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@TransactionConfiguration (transactionManager = "transactionManager", defaultRollback = true)
public class CTestSendMailTask
{
	/** The mail sender. */
	@Autowired
	protected JavaMailSender mailSender;

	/** The send mail task. */
	@Autowired
	private CSendMailTask sendMailTask;

	/**
	 * Inits the test.
	 */
	@Before
	public void init ()
	{
		// setting mail sender parameters
		((org.springframework.mail.javamail.JavaMailSenderImpl) mailSender).setHost("195.168.42.242");
		((org.springframework.mail.javamail.JavaMailSenderImpl) mailSender).setPort(25);
	}

	@Test
	@Transactional
	@Rollback (true)
	public void testRun () throws InterruptedException
	{
		sendMailTask.run();
	}
}
