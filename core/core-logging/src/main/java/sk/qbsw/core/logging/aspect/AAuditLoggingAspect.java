package sk.qbsw.core.logging.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.base.exception.IErrorResponse;
import sk.qbsw.core.base.logging.annotation.CAuditLogged;
import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.base.threadlocal.CThreadLocalStorage;
import sk.qbsw.core.logging.aspect.param.AParameter;
import sk.qbsw.core.logging.model.domain.EOperationResult;
import sk.qbsw.core.logging.service.IAuditLogService;

/**
 * Abstract class for logging to share common methods for audit log. This
 * logging aspect provide common functionality for logging to database.
 * 
 * @author Michal Lacko
 * @version 1.8.0
 * @since 1.11.6
 *
 */
public abstract class AAuditLoggingAspect extends ALoggingAspect
{

	@Autowired
	private IAuditLogService auditLogService;

	/**
	 * holder to audit logging only on highest level. Because when is logged
	 * method proceed that aspect around is called again and again recursively
	 * to lowest level. For example when is service method, on which is
	 * annotation CAuditLogged call then method is logged, but if this method
	 * call another method that method is not logged.
	 */
	static final ThreadLocal<Boolean> LOCK_HOLDER = new ThreadLocal<Boolean>();

	public AAuditLoggingAspect()
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
	public Object doBasicLogging(ProceedingJoinPoint pjp, CAuditLogged auditLogged) throws Throwable
	{
		final Object result;
		final Boolean locked = LOCK_HOLDER.get();

		// avoid recursively logging logging only on top level where is
		// annotation used
		if (Boolean.TRUE.equals(locked))
		{
			result = pjp.proceed();
		}
		else
		{
			LOCK_HOLDER.set(true);

			final Class<? extends Object> targetClass = pjp.getTarget().getClass();
			final MethodSignature signature = (MethodSignature) pjp.getSignature();
			final CMethodRepresentation methodRepresentation = this.getMethodRepresentation(targetClass, signature, auditLogged.logResult());

			final List<AParameter> loggedArguments = getArguments(pjp, methodRepresentation);

			final String actionName = getActionName(auditLogged, targetClass, signature.getMethod());

			try
			{
				// call method
				result = pjp.proceed();
			}
			
			catch (final CBusinessException e)
			{
				// get error description
				IErrorResponse error = e.getError();
				String errorMessage = null;

				if (error != null)
				{
					errorMessage = error.toString();
				}

				// log when warning
				this.doLog(actionName, EOperationResult.WARNING, errorMessage, loggedArguments);

				throw e;
			} catch (final Exception e)
			{
				// log when error
				this.doLog(actionName, EOperationResult.ERROR, ECoreErrorResponse.SYSTEM_ERROR.toString(), loggedArguments);

				throw e;
			} finally
			{
				LOCK_HOLDER.remove();
			}

			this.doLog(actionName, EOperationResult.OK, null, loggedArguments);
		}

		return result;
	}
	
	public Object processNotLogged(ProceedingJoinPoint pjp,CNotAuditLogged notAuditLogged)throws Throwable
	{
		final Object result;
		if(notAuditLogged!=null){
			if(notAuditLogged.blockTree()){
				try{
					LOCK_HOLDER.set(true);
					result = pjp.proceed();
				}catch (final Exception e)
				{
					throw e;
				}
				finally
				{
					LOCK_HOLDER.remove();
				}
			}else{
				result = pjp.proceed();
			}
		}else{
			result = pjp.proceed();
		}
		return result;
	}

	/**
	 * proceed logging
	 * 
	 * @param operationCode
	 *            name of action which is logged
	 * @param operationResult
	 *            result code of operation
	 * @param resultDescription
	 *            description of operation
	 * @param params
	 *            parameters which come to method
	 */
	protected void doLog(String operationCode, EOperationResult operationResult, String resultDescription, Object... params)
	{
		auditLogService.doLog(operationCode, Arrays.asList(params), operationResult, resultDescription, CThreadLocalStorage.getUniqueRequestIdValue());
	}

	@Override
	protected boolean checkParameterLogging(final Annotation[] parameterAnnotations)
	{
		boolean logParameter = true;
		// if is parameter annotated with CNotLogged annotation the is their
		// content not logged
		for (final Annotation parameterAnnotation : parameterAnnotations)
		{
			if (parameterAnnotation instanceof CNotAuditLogged)
			{
				logParameter = false;
			}
		}
		return logParameter;
	}

	/**
	 * get name of the business action which is logged
	 * @param auditLogged audit logged annotation
	 * @param clazz - class where is audit log action logged
	 * @param method - representation of method, which was called
	 * @return action name which is logged
	 */
	protected String getActionName(CAuditLogged auditLogged, Class<? extends Object> clazz, Method method)
	{
		return auditLogged.actionName();
	}

}
