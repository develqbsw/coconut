/**
 * 
 */
package sk.qbsw.core.security.exception;

import sk.qbsw.core.base.exception.CBusinessException;

/**
 * Security exception
 *
 * @author Dalibor Rak
 * @version 1.6.0
 * @since 1.0.0
 */
@SuppressWarnings ("serial")
public class CSecurityException extends CBusinessException
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

	/**
	 * Instantiates a new c security exception.
	 *
	 * @param message the message of security exception
	 * @param errorCode the error code of the message
	 */
	public CSecurityException (String message, String errorCode)
	{
		super(message, errorCode);
	}
}
