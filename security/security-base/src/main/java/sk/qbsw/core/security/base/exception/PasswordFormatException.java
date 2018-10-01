package sk.qbsw.core.security.base.exception;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;

/**
 * Incorrect password format exception - the password doesn't match pattern.
 *
 * @author Tomas Lauro
 * @version 1.7.2
 * @since 1.7.2
 */
public class PasswordFormatException extends CSecurityException
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new c password format exception.
	 *
	 * @param message the message
	 */
	public PasswordFormatException (String message)
	{
		super(message, ECoreErrorResponse.PASSWORD_INVALID_FORMAT);
	}
	
	/**
	 * Instantiates a new password format exception.
	 *
	 * @param message the message
	 */
	public PasswordFormatException (String message, ECoreErrorResponse errorResponse)
	{
		super(message, errorResponse);
	}
}
