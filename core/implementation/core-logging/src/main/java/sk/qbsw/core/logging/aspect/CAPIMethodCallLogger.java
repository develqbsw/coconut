package sk.qbsw.core.logging.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * Aspect logger for API method call.
 * 
 * @author Tomas Lauro
 * @version 0.1.0
 * @since 0.1.0
 */
@Aspect
public class CAPIMethodCallLogger
{	
	/** Indicate if aspect logs messages. */
	@Value ("${aspect.logging}")
	private Boolean logging;

	/**
	 * Poincut.
	 *
	 * @param request the request
	 */
	@Pointcut ("execution(* *(String, ..)) && args(request, *)")
	public void request (String request)
	{
	}

	/**
	 * Log at the beginning of the request.
	 *
	 * @param joinPoint the join point
	 * @param request the request
	 */
	@Before ("request(request)")
	public void logBeginning (JoinPoint joinPoint, String request)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Begining of the API method ").append(joinPoint.getSignature().getName()).append(" with request: ").append(request);

		log(joinPoint.getTarget().getClass(), builder.toString());
	}

	/**
	 * Log end of the request.
	 *
	 * @param joinPoint the join point
	 * @param request the request
	 */
	@AfterReturning ("request(request)")
	public void logEnd (JoinPoint joinPoint, String request)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("End of the API method ").append(joinPoint.getSignature().getName());

		log(joinPoint.getTarget().getClass(), builder.toString());
	}

	/**
	 * Log debug message.
	 *
	 * @param <T> the class used as logger name
	 * @param loggerName the logger name
	 * @param log the log message
	 */
	private <T>void log (Class<T> loggerName, String log)
	{
		if (logging == true)
		{
			LoggerFactory.getLogger(loggerName).debug(log);
		}
	}
}
