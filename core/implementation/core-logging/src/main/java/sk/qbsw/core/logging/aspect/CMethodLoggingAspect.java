package sk.qbsw.core.logging.aspect;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sk.qbsw.core.logging.aspect.annotation.CLogged;
import sk.qbsw.core.logging.aspect.param.AParameter;
import sk.qbsw.core.logging.aspect.param.AParameterFactory;
import sk.qbsw.core.logging.aspect.param.CNotLoggedParameter;

/**
 * Aspect to logging on methods, log method name, parameters name and values which come to method
 * 
 * @author Marián Oravec
 * @author Michal Lacko
 * @since 1.8.0
 * @version 1.8.0
 */
@Aspect
public class CMethodLoggingAspect extends ALoggingAspect
{

	@Pointcut (value = "execution(* *(..))")
	public void anyMethod ()
	{
	}

	@Pointcut ("@within(sk.qbsw.core.logging.aspect.annotation.CLogged)")
	public void loggedClass ()
	{
	}

	/**
	 * logging every method which have annotation auditLogged and don't have annotation CNotAuditLogged
	 */
	@Around ("anyMethod() && @annotation(logged) && !@annotation(sk.qbsw.core.logging.aspect.annotation.CNotLogged)")
	public Object loggedMethodPointcut (ProceedingJoinPoint pjp, CLogged logged) throws Throwable
	{
		return this.doBasicLogging(pjp, logged);
	}

	/**
	 * logging every method where class have annotation CAuditLogged and method don't have annotation CNotAuditLogged
	 */
	@Around ("anyMethod() && loggedClass() && !@annotation(sk.qbsw.core.logging.aspect.annotation.CNotLogged)")
	public Object loggedClassPointcut (ProceedingJoinPoint pjp) throws Throwable
	{
		return this.doBasicLogging(pjp, pjp.getTarget().getClass().getAnnotation(CLogged.class));
	}

	/**
	 * log method name and their parameters 
	 * @param pjp - Aspect join point 
	 * @param auditLogged - annotation which include action name
	 * @return result from called method
	 * @throws Throwable when method fail, or method not found, etc.
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
			final String methodSignature = methodRepresentation.getMethodSignature();
			final List<AParameterFactory> parameterFactories = methodRepresentation.getParameterFactories();
			final List<AParameter> loggedArguments = new ArrayList<AParameter>(parameterFactories.size());
			final Object[] arguments = pjp.getArgs();

			int parameterIndex = 0;

			for (final AParameterFactory pf : parameterFactories)
			{
				loggedArguments.add(pf.getInstance(arguments[parameterIndex]));
				parameterIndex++;
			}

			//log method name with arguments which come to method
			this.doLog(logger, level, "Entering method {} with arguments {}.", methodSignature, loggedArguments);

			final long start = System.currentTimeMillis();

			try
			{
				//run method
				result = pjp.proceed();
			}
			catch (final Exception e)
			{
				final long duration = System.currentTimeMillis() - start;

				//log exception
				this.doLog(logger, level, "Exception of class '{}' with message '{}' was thrown in method {}. Duration: {} ms.", e.getClass().getName(), e.getMessage(), methodSignature, duration);

				throw e;
			}

			final long duration = System.currentTimeMillis() - start;
			final Object loggedResult = methodRepresentation.isLogResult() ? result : CNotLoggedParameter.NOT_LOGGED_VALUE;

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
	 * log to logger for required level
	 * @param logger - logger which log message
	 * @param level - logger level to which is message logged
	 * @param message - message which is logged
	 * @param params - parameters which is logged
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
	 * @param logger logger which tells us if is required level enabled 
	 * @param level requested level for which is this method logged
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

}
