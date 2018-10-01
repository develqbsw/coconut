package sk.qbsw.core.security.base.exception;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;

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
		super(message, ECoreErrorResponse.ACCESS_DENIED);
	}

	/**
	 * Instantiates a new invalid password exception.
	 *
	 * @param message the message
	 * @param errorResponse the error response
	 */
	public InvalidPasswordException (String message, ECoreErrorResponse errorResponse)
	{
		super(message, errorResponse);
	}
}
