package sk.qbsw.et.rquery.core.exception;

/**
 * The request query unsupported exception.
 *
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
public class RQUnsupportedOperatorException extends RQSystemException
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
}
