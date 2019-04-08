package sk.qbsw.et.rquery.core.exception;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.base.exception.IErrorResponse;

/**
 * The request query system exception.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public class RQSystemException extends CSystemException
{
	private static final long serialVersionUID = 2433331534008696731L;

	/**
	 * Instantiates a new Rq system exception.
	 *
	 * @param message the message
	 */
	public RQSystemException (String message)
	{
		super(message);
	}

	/**
	 * Instantiates a new Rq system exception.
	 *
	 * @param error the error
	 */
	public RQSystemException (IErrorResponse error)
	{
		super(error);
	}

	/**
	 * Instantiates a new Rq system exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public RQSystemException (String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Instantiates a new Rq system exception.
	 *
	 * @param message the message
	 * @param error the error
	 */
	public RQSystemException (String message, IErrorResponse error)
	{
		super(message, error);
	}
}
