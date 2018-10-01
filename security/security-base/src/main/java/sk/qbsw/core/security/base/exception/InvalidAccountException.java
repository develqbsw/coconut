package sk.qbsw.core.security.base.exception;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;

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
		super(message, ECoreErrorResponse.ACCESS_DENIED);
	}
	
	/**
	 * Instantiates a new invalid account exception.
	 *
	 * @param message the message
	 * @param errorResponse the error response
	 */
	public InvalidAccountException (String message, Throwable errorResponse)
	{
		super(message, errorResponse);
	}
}
