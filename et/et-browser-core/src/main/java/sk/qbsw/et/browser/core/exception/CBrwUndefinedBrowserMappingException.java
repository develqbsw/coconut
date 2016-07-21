package sk.qbsw.et.browser.core.exception;

/**
 * The undefined browser mapping exception.
 *
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public class CBrwUndefinedBrowserMappingException extends CBrwBusinessException
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7329606376468938876L;

	/** The Constant ERROR. */
	private static final EBrwError ERROR = EBrwError.UNDEFINED_BROWSER_MAPPING;

	/**
	 * Instantiates a new c brw undefined browser mapping exception.
	 *
	 * @param message the message
	 */
	public CBrwUndefinedBrowserMappingException (String message)
	{
		super(message, ERROR);
	}

	/**
	 * Instantiates a new c brw undefined browser mapping exception.
	 */
	public CBrwUndefinedBrowserMappingException ()
	{
		super(ERROR);
	}

	/**
	 * Instantiates a new c brw undefined browser mapping exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public CBrwUndefinedBrowserMappingException (String message, Throwable cause)
	{
		super(message, cause, ERROR);
	}
}
