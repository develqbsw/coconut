package sk.qbsw.et.browser.core.exception;

/**
 * The undefined variable mapping exception.
 *
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public class CBrwUndefinedVariableMappingException extends CBrwBusinessException
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7329606376468938876L;

	/** The Constant ERROR. */
	private static final EBrwError ERROR = EBrwError.UNDEFINED_VARIABLE_MAPPING;

	/**
	 * Instantiates a new c brw undefined variable mapping exception.
	 *
	 * @param message the message
	 */
	public CBrwUndefinedVariableMappingException (String message)
	{
		super(message, ERROR);
	}

	/**
	 * Instantiates a new c brw undefined variable mapping exception.
	 */
	public CBrwUndefinedVariableMappingException ()
	{
		super(ERROR);
	}

	/**
	 * Instantiates a new c brw undefined variable mapping exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public CBrwUndefinedVariableMappingException (String message, Throwable cause)
	{
		super(message, cause, ERROR);
	}
}
