package sk.qbsw.core.security.base.exception;

/**
 * Invalid password exception.
 *
 * @author Tomas Lauro
 * 
 * @version 1.12.2
 * @since 1.12.2
 */
@SuppressWarnings ({"serial", "deprecation"})
public class InvalidPasswordException extends WrongPasswordException
{
	/**
	 * Instantiates a new invalid password exception.
	 *
	 * @param message the message
	 */
	public InvalidPasswordException (String message)
	{
		super(message);
	}

}
