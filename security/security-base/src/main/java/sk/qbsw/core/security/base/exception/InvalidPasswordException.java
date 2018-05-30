package sk.qbsw.core.security.base.exception;

import sk.qbsw.core.base.exception.CSecurityException;

/**
 * Invalid password exception.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.12.2
 */
@SuppressWarnings ({"serial"})
public class InvalidPasswordException extends CSecurityException
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
