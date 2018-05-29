package sk.qbsw.core.security.base.exception;

import sk.qbsw.core.base.exception.CSecurityException;

/**
 * The invalid account exception.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.7.0
 */
@SuppressWarnings ("serial")
public class InvalidAccountException extends CSecurityException
{
	/**
	 * Instantiates a new invalid user exception.
	 *
	 * @param message the message
	 */
	public InvalidAccountException (String message)
	{
		super(message);
	}
}
