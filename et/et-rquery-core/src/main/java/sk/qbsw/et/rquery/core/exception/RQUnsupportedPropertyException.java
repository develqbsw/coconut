package sk.qbsw.et.rquery.core.exception;

/**
 * The request query unsupported prperty exception.
 *
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
public class RQUnsupportedPropertyException extends RQSystemException
{
	private static final RQError ERROR = RQError.UNSUPPORTED_PROPERTY;

	private static final long serialVersionUID = 5338004915595517460L;

	/**
	 * Instantiates a new Rq unsupported property exception.
	 *
	 * @param message the message
	 */
	public RQUnsupportedPropertyException (String message)
	{
		super(message, ERROR);
	}

	/**
	 * Instantiates a new Rq unsupported property exception.
	 */
	public RQUnsupportedPropertyException ()
	{
		super(ERROR);
	}
}
