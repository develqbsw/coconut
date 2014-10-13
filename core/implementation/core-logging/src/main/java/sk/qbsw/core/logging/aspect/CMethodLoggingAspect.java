package sk.qbsw.core.logging.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import sk.qbsw.core.base.logging.annotation.CLogged;

/**
 * Aspect to logging on methods, log method name, parameters name and values which come to method
 * 
 * @author Marián Oravec
 * @author Michal Lacko
 * @since 1.8.0
 * @version 1.8.0
 */
@Aspect
public class CMethodLoggingAspect extends AMethodLoggingAspect
{

	@Pointcut (value = "execution(* *(..))")
	public void anyMethod ()
	{
	}

	@Pointcut ("@within(sk.qbsw.core.base.logging.annotation.CLogged)")
	public void loggedClass ()
	{
	}

	/**
	 * logging every method which have annotation auditLogged and don't have annotation CNotAuditLogged
	 */
	@Around ("anyMethod() && @annotation(logged) && !@annotation(sk.qbsw.core.base.logging.annotation.CNotLogged)")
	public Object loggedMethodPointcut (ProceedingJoinPoint pjp, CLogged logged) throws Throwable
	{
		return this.doBasicLogging(pjp, logged);
	}

	/**
	 * logging every method where class have annotation CAuditLogged and method don't have annotation CNotAuditLogged
	 */
	@Around ("anyMethod() && loggedClass() && !@annotation(sk.qbsw.core.base.logging.annotation.CNotLogged)")
	public Object loggedClassPointcut (ProceedingJoinPoint pjp) throws Throwable
	{
		return this.doBasicLogging(pjp, pjp.getTarget().getClass().getAnnotation(CLogged.class));
	}

}
