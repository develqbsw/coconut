package sk.qbsw.integration.mail.exception;

import sk.qbsw.core.base.exception.CSystemException;

/**
 * The sending or receiving mails exception. 
 *
 * @author Tomas Lauro
 * 
 * @version 1.9.0
 * @since 1.9.0
 */
@SuppressWarnings ("serial")
public class CCommunicationException extends CSystemException
{
	/**
	 * Instantiates a new communication exception.
	 *
	 * @param message the message
	 * @param th the th
	 */
	public CCommunicationException (String message, Throwable th)
	{
		super(message, th);
	}

	/**
	 * Instantiates a new communication exception.
	 *
	 * @param message the message
	 */
	public CCommunicationException (String message)
	{
		super(message);
	}
}