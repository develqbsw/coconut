package sk.qbsw.integration.mailchimp.client.exception;

/**
 * The mailchimp exception.
 *
 * @author Tomas Lauro
 *
 * @version 1.17.0
 * @since 1.17.0
 */
public class MailchimpTooManyRequestsException extends MailchimpException
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 9145930776811867048L;

	/**
	 * Instantiates a new mailchimp too many requests exception.
	 *
	 * @param message the message
	 */
	public MailchimpTooManyRequestsException (String message)
	{
		super(message);
	}

	/**
	 * Instantiates a new mailchimp too many requests exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public MailchimpTooManyRequestsException (String message, Throwable cause)
	{
		super(message, cause);
	}
}
