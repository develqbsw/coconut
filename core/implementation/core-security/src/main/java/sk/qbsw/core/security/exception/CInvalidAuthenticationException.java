package sk.qbsw.core.security.exception;

import sk.qbsw.core.base.exception.CSecurityException;

/**
 * Invalid authentication exception. The user authentication parameters have invalid date.
 *
 * @author Tomas Lauro
 * 
 * @version 1.12.2
 * @since 1.12.2
 */
@SuppressWarnings ({"serial"})
public class CInvalidAuthenticationException extends CSecurityException
{
	/**
	 * Instantiates a new c invalid authentication exception.
	 *
	 * @param message the message
	 */
	public CInvalidAuthenticationException (String message)
	{
		super(message);
	}

}
