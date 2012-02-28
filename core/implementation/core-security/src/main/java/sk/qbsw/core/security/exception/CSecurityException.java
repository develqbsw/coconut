/**
 * 
 */
package sk.qbsw.core.security.exception;

/**
 * The Class CSecurityException.
 *
 * @author Dalibor Rak
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings ("serial")
public class CSecurityException extends Exception
{

	/**
	 * Instantiates a new c security exception.
	 *
	 * @param message the message
	 */
	public CSecurityException (String message)
	{
		super(message);
	}
}
