package sk.qbsw.core.testing.mock;

import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.AopConfigException;
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
public class CMockHelper implements IMockHelper
{
	/* (non-Javadoc)
	 * @see sk.qbsw.core.testing.mock.IMockHelper#unwrapSpringProxyObject(java.lang.Object)
	 */
	@SuppressWarnings ("unchecked")
	public <T> T unwrapSpringProxyObject (T object) throws AopConfigException
	{
		try
		{

			if (AopUtils.isAopProxy(object) && object instanceof Advised)
			{
				return (T) ((Advised) object).getTargetSource().getTarget();
			}
		}
		catch (Exception e)
		{
			throw new AopConfigException("The object is not a aop advise proxy object", e);
		}
		throw new AopConfigException("The object is not a aop advise proxy object");
	}
}
