package sk.qbsw.core.security.base.exception;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;

/**
 * The Class AccountDisabledException.
 * 
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.7.0
 */
@SuppressWarnings ("serial")
public class AccountDisabledException extends CSecurityException
{
	
	/**
	 * Instantiates a new account disabled exception.
	 *
	 * @param message the message
	 */
	public AccountDisabledException (String message)
	{
		super(message, ECoreErrorResponse.ACCESS_DENIED);
	}
	
	/**
	 * Instantiates a new account disabled exception.
	 *
	 * @param message the message
	 * @param errorResponse the error response
	 */
	public AccountDisabledException (String message, Throwable errorResponse)
	{
		super(message, errorResponse);
	}
}
