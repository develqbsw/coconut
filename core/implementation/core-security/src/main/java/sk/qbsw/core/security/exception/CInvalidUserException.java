package sk.qbsw.core.security.exception;

/**
 * The invalid user exception.
 *
 * @author Tomas Lauro
 * @version 1.7.0
 * @since 1.7.0
 */
@SuppressWarnings ("serial")
public class CInvalidUserException extends CSecurityException
{
	/**
	 * Instantiates a new invalid user exception.
	 *
	 * @param message the message
	 */
	public CInvalidUserException (String message)
	{
		super(message);
	}
}
