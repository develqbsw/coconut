package sk.qbsw.et.rquery.core.exception;

/**
 * The request query unsupported exception.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public class RQUnsupportedOperatorException extends RQBusinessException
{
	private static final long serialVersionUID = 1481917257208646420L;

	private static final RQError ERROR = RQError.UNSUPPORTED_OPERATOR;

	/**
	 * Instantiates a new Rq unsupported operator exception.
	 *
	 * @param message the message
	 */
	public RQUnsupportedOperatorException (String message)
	{
		super(message, ERROR);
	}

	/**
	 * Instantiates a new Rq unsupported operator exception.
	 */
	public RQUnsupportedOperatorException ()
	{
		super(ERROR);
	}

	/**
	 * Instantiates a new Rq unsupported operator exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public RQUnsupportedOperatorException (String message, Throwable cause)
	{
		super(message, cause, ERROR);
	}
}
