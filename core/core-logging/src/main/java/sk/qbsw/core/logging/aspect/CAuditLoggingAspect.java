package sk.qbsw.core.logging.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import sk.qbsw.core.base.logging.annotation.CAuditLogged;
import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;

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
 * @author Peter Bozik
 * @since 1.8.0
 * @version 1.12.4
 */
@Aspect
public class CAuditLoggingAspect extends AAuditLoggingAspect
{

	@Pointcut (value = "execution(* *(..))")
	public void anyMethod ()
	{
	}

	@Pointcut ("@within(sk.qbsw.core.base.logging.annotation.CAuditLogged)")
	public void loggedClass ()
	{
	}
	
	@Pointcut ("@within(sk.qbsw.core.base.logging.annotation.CNotAuditLogged)")
	public void notLoggedClass ()
	{
	}

	/**
	 * logging every method which have annotation auditLogged and don't have annotation CNotAuditLogged
	 */
	@Around ("anyMethod() && @annotation(auditLogged) && !@annotation(sk.qbsw.core.base.logging.annotation.CNotAuditLogged)")
	public Object loggedMethodPointcut (ProceedingJoinPoint pjp, CAuditLogged auditLogged) throws Throwable
	{
		return this.doBasicLogging(pjp, auditLogged);
	}
	
	/**
	 * process every method which have annotation {@link CNotAuditLogged} and don't have annotation {@link CAuditLogged}
	 */
	@Around ("anyMethod() && @annotation(notAauditLogged) && !@annotation(sk.qbsw.core.base.logging.annotation.CAuditLogged)")
	public Object loggedMethodPointcut (ProceedingJoinPoint pjp, CNotAuditLogged notAauditLogged) throws Throwable
	{
		return this.processNotLogged(pjp,notAauditLogged);
	}

	/**
	 * logging every method where class have annotation CAuditLogged and method don't have annotation CNotAuditLogged
	 */
	@Around ("anyMethod() && loggedClass() && !@annotation(sk.qbsw.core.base.logging.annotation.CNotAuditLogged) && !@annotation(sk.qbsw.core.base.logging.annotation.CAuditLogged)")
	public Object loggedClassPointcut (ProceedingJoinPoint pjp) throws Throwable
	{
		return this.doBasicLogging(pjp, pjp.getTarget().getClass().getAnnotation(CAuditLogged.class));
	}
	
	/**
	 * process every method where class have annotation {@link CNotAuditLogged} and method don't have annotation {@link CAuditLogged}
	 */
	@Around ("anyMethod() && notLoggedClass() && !@annotation(sk.qbsw.core.base.logging.annotation.CNotAuditLogged) && !@annotation(sk.qbsw.core.base.logging.annotation.CAuditLogged)")
	public Object loggedClassPointcutNotLogged (ProceedingJoinPoint pjp) throws Throwable
	{
		return this.processNotLogged(pjp, pjp.getTarget().getClass().getAnnotation(CNotAuditLogged.class));
	}

}
