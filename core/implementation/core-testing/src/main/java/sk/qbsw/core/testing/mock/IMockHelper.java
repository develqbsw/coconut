package sk.qbsw.core.testing.mock;

/**
 * The helper for mocking.
 *
 * @author Tomas Lauro
 * 
 * @version 1.9.0
 * @since 1.9.0
 */
public interface IMockHelper
{
	/**
	 * Unwrap spring proxy object.
	 *
	 * @param <T> the generic type
	 * @param object the object
	 * @return the unwrapped object
	 * @throws Exception the exception
	 */
	public <T>T unwrapSpringProxyObject (T object) throws Exception;
}
