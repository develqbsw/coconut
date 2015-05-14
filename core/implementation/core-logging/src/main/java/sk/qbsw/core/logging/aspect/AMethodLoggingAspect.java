package sk.qbsw.core.logging.aspect;

import java.lang.annotation.Annotation;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sk.qbsw.core.base.logging.ELoggingLevel;
import sk.qbsw.core.base.logging.annotation.CLogged;
import sk.qbsw.core.base.logging.annotation.CNotLogged;
import sk.qbsw.core.logging.aspect.param.AParameter;
import sk.qbsw.core.logging.aspect.param.CNotLoggedParameter;

/**
 * Abstract class for logging to share common methods for method logging. This
 * logging aspect provide common functionality for logging to slf4j.
 * 
 * @author Michal Lacko
 * @version 1.8.0
 * @since 1.11.6
 *
 */
public abstract class AMethodLoggingAspect extends ALoggingAspect
{

	public AMethodLoggingAspect ()
	{
		super();
	}

	/**
	 * log method name and their parameters
	 * 
	 * @param pjp
	 *            - Aspect join point
	 * @param auditLogged
	 *            - annotation which include action name
	 * @return result from called method
	 * @throws Throwable
	 *             when method fail, or method not found, etc.
	 */
	public Object doBasicLogging (ProceedingJoinPoint pjp, CLogged logged) throws Throwable
	{
		final Object result;
		final ELoggingLevel level = logged.level();
		final Class<? extends Object> targetClass = pjp.getTarget().getClass();
		final Logger logger = LoggerFactory.getLogger(targetClass);
		final boolean levelEnabled = this.isLevelEnabled(logger, level);

		if (levelEnabled)
		{
			final MethodSignature signature = (MethodSignature) pjp.getSignature();
			final CMethodRepresentation methodRepresentation = getMethodRepresentation(targetClass, signature, logged.logResult());
			final String methodSignature = getMethodSignature(methodRepresentation);
			final List<AParameter> loggedArguments = getArguments(pjp, methodRepresentation);

			// log method name with arguments which come to method
			this.doLog(logger, level, "Entering method {} with arguments {}.", methodSignature, loggedArguments);

			final long start = System.currentTimeMillis();

			try
			{
				// run method
				result = pjp.proceed();
			}
			catch (final Exception e)
			{
				final long duration = getDuration(start);

				// log exception
				this.doLog(logger, level, "Exception of class '{}' with message '{}' was thrown in method {}. Duration: {} ms.", e.getClass().getName(), e.getMessage(), methodSignature, duration);

				throw e;
			}

			final long duration = getDuration(start);
			final Object loggedResult = getLoggedResult(result, methodRepresentation);

			this.doLog(logger, level, "Exiting method {} with result {}. Duration: {} ms.", methodSignature, loggedResult, duration);
		}
		else
		{
			/*
			 * Logging for this level is not enabled. Just call target method.
			 */
			result = pjp.proceed();
		}

		return result;
	}

	/**
	 * get duration of method execution
	 * this method is called after method proceed
	 * @param start - time when method execution start 
	 * @return time between method start and now
	 */
	protected long getDuration (final long start)
	{
		return System.currentTimeMillis() - start;
	}

	/**
	 * get result of method call
	 * @param result result of method
	 * @param methodRepresentation - representation of method. Contains method signature and parameters
	 * @return result which goes to log
	 */
	protected Object getLoggedResult (final Object result, final CMethodRepresentation methodRepresentation)
	{
		return methodRepresentation.isLogResult() ? result : CNotLoggedParameter.NOT_LOGGED_VALUE;
	}

	/**
	 * get signature of method
	 * @param methodRepresentation representation o method
	 * @return return string representation of method signature
	 */
	protected String getMethodSignature (final CMethodRepresentation methodRepresentation)
	{
		return methodRepresentation.getMethodSignature();
	}

	/**
	 * log to logger for required level
	 * 
	 * @param logger
	 *            - logger which log message
	 * @param level
	 *            - logger level to which is message logged
	 * @param message
	 *            - message which is logged
	 * @param params
	 *            - parameters which is logged
	 */
	protected void doLog (Logger logger, ELoggingLevel level, String message, Object... params)
	{
		switch (level)
		{
			case TRACE:
				logger.trace(message, params);
				break;

			case DEBUG:
				logger.debug(message, params);
				break;

			case INFO:
				logger.info(message, params);
				break;

			case WARN:
				logger.warn(message, params);
				break;

			case ERROR:
				logger.error(message, params);
				break;

			default:
				throw new IllegalStateException("Not supported ELoggingLevel: " + level);
		}
	}

	/**
	 * detect if is required logger level enabled
	 * 
	 * @param logger
	 *            logger which tells us if is required level enabled
	 * @param level
	 *            requested level for which is this method logged
	 * @return true if is logged level enabled, false otherwise
	 */
	protected boolean isLevelEnabled (Logger logger, ELoggingLevel level)
	{
		final boolean result;

		switch (level)
		{
			case TRACE:
				result = logger.isTraceEnabled();
				break;

			case DEBUG:
				result = logger.isDebugEnabled();
				break;

			case INFO:
				result = logger.isInfoEnabled();
				break;

			case WARN:
				result = logger.isWarnEnabled();
				break;

			case ERROR:
				result = logger.isErrorEnabled();
				break;

			default:
				throw new IllegalStateException("Not supported ELoggingLevel: " + level);
		}

		return result;
	}

	@Override
	protected boolean checkParameterLogging (final Annotation[] parameterAnnotations)
	{
		boolean logParameter = true;
		// if is parameter annotated with CNotLogged annotation the is their
		// content not logged
		for (final Annotation parameterAnnotation : parameterAnnotations)
		{
			if (parameterAnnotation instanceof CNotLogged)
			{
				logParameter = false;
			}
		}
		return logParameter;
	}

}
