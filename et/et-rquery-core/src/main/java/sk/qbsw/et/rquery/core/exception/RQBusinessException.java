package sk.qbsw.et.rquery.core.exception;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.IErrorResponse;

/**
 * The request query business exception.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public class RQBusinessException extends CBusinessException
{
	private static final long serialVersionUID = -7462592358468477819L;

	/**
	 * Instantiates a new Rq business exception.
	 *
	 * @param message the message
	 */
	public RQBusinessException (String message)
	{
		super(message);
	}

	/**
	 * Instantiates a new Rq business exception.
	 *
	 * @param error the error
	 */
	public RQBusinessException (IErrorResponse error)
	{
		super(error);
	}

	/**
	 * Instantiates a new Rq business exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public RQBusinessException (String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Instantiates a new Rq business exception.
	 *
	 * @param message the message
	 * @param error the error
	 */
	public RQBusinessException (String message, IErrorResponse error)
	{
		super(message, error);
	}

	/**
	 * Instantiates a new Rq business exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 * @param error the error
	 */
	public RQBusinessException (String message, Throwable cause, IErrorResponse error)
	{
		super(message, cause, error);
	}
}
