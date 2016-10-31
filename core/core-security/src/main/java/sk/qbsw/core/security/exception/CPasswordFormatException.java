package sk.qbsw.core.security.exception;

import sk.qbsw.core.base.exception.CSecurityException;

/**
 * Incorrect password format exception - the password doesn't match pattern.
 *
 * @author Tomas Lauro
 * @version 1.7.2
 * @since 1.7.2
 */
public class CPasswordFormatException extends CSecurityException
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new c password format exception.
	 *
	 * @param message the message
	 */
	public CPasswordFormatException (String message)
	{
		super(message);
	}
}
