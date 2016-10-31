package sk.qbsw.core.security.exception;

/**
 * Invalid password exception.
 *
 * @author Tomas Lauro
 * 
 * @version 1.12.2
 * @since 1.12.2
 */
@SuppressWarnings ({"serial", "deprecation"})
public class CInvalidPasswordException extends CWrongPasswordException
{
	/**
	 * Instantiates a new invalid password exception.
	 *
	 * @param message the message
	 */
	public CInvalidPasswordException (String message)
	{
		super(message);
	}

}
