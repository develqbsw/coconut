package sk.qbsw.et.mailchimp.client.exception;

import sk.qbsw.core.base.exception.CSystemException;

/**
 * The mailchimp exception.
 *
 * @author Tomas Lauro
 *
 * @version 1.17.0
 * @since 1.17.0
 */
public class MailchimpException extends CSystemException
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1925411414526663088L;

	/**
	 * Instantiates a new mailchimp exception.
	 *
	 * @param message the message
	 */
	public MailchimpException (String message)
	{
		super(message);
	}

	/**
	 * Instantiates a new mailchimp exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public MailchimpException (String message, Throwable cause)
	{
		super(message, cause);
	}
}
