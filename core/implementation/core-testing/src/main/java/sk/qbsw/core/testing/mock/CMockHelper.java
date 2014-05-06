package sk.qbsw.core.testing.mock;

import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;

/**
 * The helper for mocking.
 *
 * @author Tomas Lauro
 * 
 * @version 1.9.0
 * @since 1.9.0
 */
@Component ("mockHelper")
public class CMockHelper
{
	/**
	 * Unwrap spring proxy object.
	 *
	 * @param <T> the generic type
	 * @param object the object
	 * @return the unwrapped object
	 * @throws Exception the exception
	 */
	@SuppressWarnings ("unchecked")
	public <T>T unwrapSpringProxyObject (T object) throws Exception
	{
		if (AopUtils.isAopProxy(object) && object instanceof Advised)
		{
			return (T) ((Advised) object).getTargetSource().getTarget();
		}
		else
		{
			throw new Exception("The object is not a aop advise proxy object");
		}
	}
}
