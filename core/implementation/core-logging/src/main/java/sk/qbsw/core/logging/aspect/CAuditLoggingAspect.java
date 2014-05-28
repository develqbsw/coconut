package sk.qbsw.core.logging.aspect;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.EError;
import sk.qbsw.core.base.exception.IError;
import sk.qbsw.core.logging.aspect.annotation.CAuditLogged;
import sk.qbsw.core.logging.aspect.annotation.CNotAuditLogged;
import sk.qbsw.core.logging.aspect.param.AParameter;
import sk.qbsw.core.logging.aspect.param.AParameterFactory;
import sk.qbsw.core.logging.model.domain.EOperationResult;
import sk.qbsw.core.logging.service.IAuditLogService;

/**
 * Aspect for audit logging on methods, log audit action, method name, parameters name and values which come to method
 * Audit logging only on highest level. For example when is service method, on which is annotation CAuditLogged call then method is logged, but if this method call another method that method is not logged.
 * 
 * Logging can be turned on two level 
 * turn on method logging on class level: log all methods for class which have {@link CAuditLogged} annotation and method haven't {@link CNotAuditLogged} annotation
 * turn on method logging on method level: log method which have {@link CAuditLogged}
 *
 * If is parameter annotated with {@link CNotAuditLogged} then parameter isn't logged
 * 
 * @author Michal Lacko
 * @author Marián Oravec
 * @since 1.8.0
 * @version 1.8.0
 */
@Aspect
public class CAuditLoggingAspect extends ALoggingAspect
{

	@Autowired
	private IAuditLogService auditLogService;

	/**
	 * holder to audit logging only on highest level. Because when is logged method proceed that aspect around is called again and again recursively to lowest level.
	 * For example when is service method, on which is annotation CAuditLogged call then method is logged, but if this method call another method that method is not logged. 
	 */
	private static final ThreadLocal<Boolean> LOCK_HOLDER = new ThreadLocal<Boolean>();

	@Pointcut (value = "execution(* *(..))")
	public void anyMethod ()
	{
	}

	@Pointcut ("@within(sk.qbsw.core.logging.aspect.annotation.CAuditLogged)")
	public void loggedClass ()
	{
	}

	/**
	 * logging every method which have annotation auditLogged and don't have annotation CNotAuditLogged
	 */
	@Around ("anyMethod() && @annotation(auditLogged) && !@annotation(sk.qbsw.core.logging.aspect.annotation.CNotAuditLogged)")
	public Object loggedMethodPointcut (ProceedingJoinPoint pjp, CAuditLogged auditLogged) throws Throwable
	{
		return this.doBasicLogging(pjp, auditLogged);
	}

	/**
	 * logging every method where class have annotation CAuditLogged and method don't have annotation CNotAuditLogged
	 */
	@Around ("anyMethod() && loggedClass() && !@annotation(sk.qbsw.core.logging.aspect.annotation.CNotAuditLogged)")
	public Object loggedClassPointcut (ProceedingJoinPoint pjp) throws Throwable
	{
		return this.doBasicLogging(pjp, pjp.getTarget().getClass().getAnnotation(CAuditLogged.class));
	}

	/**
	 * log method name and their parameters 
	 * @param pjp - Aspect join point 
	 * @param auditLogged - annotation which include action name
	 * @return result from called method
	 * @throws Throwable when method fail, or method not found, etc.
	 */
	public Object doBasicLogging (ProceedingJoinPoint pjp, CAuditLogged auditLogged) throws Throwable
	{
		final Object result;
		final Boolean locked = LOCK_HOLDER.get();

		//avoid recursively logging logging only on top level where is annotation used 
		if (Boolean.TRUE.equals(locked))
		{
			result = pjp.proceed();
		}
		else
		{
			LOCK_HOLDER.set(true);

			final String actionName = auditLogged.actionName();
			final Class<? extends Object> targetClass = pjp.getTarget().getClass();
			final MethodSignature signature = (MethodSignature) pjp.getSignature();
			final CMethodRepresentation methodRepresentation = this.getMethodRepresentation(targetClass, signature, auditLogged.logResult());

			final List<AParameterFactory> parameterFactories = methodRepresentation.getParameterFactories();
			final List<AParameter> loggedArguments = new ArrayList<AParameter>(parameterFactories.size());
			final Object[] arguments = pjp.getArgs();

			int parameterIndex = 0;

			//prepare arguments to log
			for (final AParameterFactory pf : parameterFactories)
			{
				loggedArguments.add(pf.getInstance(arguments[parameterIndex]));
				parameterIndex++;
			}

			try
			{
				//call method
				result = pjp.proceed();
			}

			catch (final CBusinessException e)
			{
				//get error description
				IError error = e.getError();
				String errorMessage = null;

				if (error != null)
				{
					errorMessage = error.toString();
				}

				//log when warning
				this.doLog(actionName, EOperationResult.WARNING, errorMessage, loggedArguments);

				throw e;
			}
			catch (final Exception e)
			{
				//log when error
				this.doLog(actionName, EOperationResult.ERROR, EError.SYSTEM_ERROR.toString(), loggedArguments);

				throw e;
			}
			finally
			{
				LOCK_HOLDER.remove();
			}

			this.doLog(actionName, EOperationResult.OK, null, loggedArguments);
		}

		return result;
	}

	/**
	 * proceed logging
	 * @param operationCode name of action which is logged
	 * @param operationResult result code of operation
	 * @param resultDescription description of operation
	 * @param params parameters which come to method
	 */
	protected void doLog (String operationCode, EOperationResult operationResult, String resultDescription, Object... params)
	{

		auditLogService.doLog(operationCode, Arrays.asList(params), operationResult, resultDescription);

	}

	@Override
	protected boolean checkParameterLogging (final Annotation[] parameterAnnotations)
	{
		boolean logParameter = true;
		//if is parameter annotated with CNotLogged annotation the is their content not logged
		for (final Annotation parameterAnnotation : parameterAnnotations)
		{
			if (parameterAnnotation instanceof CNotAuditLogged)
			{
				logParameter = false;
			}
		}
		return logParameter;
	}

}
