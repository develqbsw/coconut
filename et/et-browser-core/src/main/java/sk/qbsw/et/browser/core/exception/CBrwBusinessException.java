package sk.qbsw.et.browser.core.exception;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.IErrorResponse;

/**
 * The browser business exception.
 *
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public class CBrwBusinessException extends CBusinessException
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7462592358468477819L;

	/**
	 * Instantiates a new c brw business exception.
	 *
	 * @param message the message
	 */
	public CBrwBusinessException (String message)
	{
		super(message);
	}

	/**
	 * Instantiates a new c brw business exception.
	 *
	 * @param error the error
	 */
	public CBrwBusinessException (IErrorResponse error)
	{
		super(error);
	}

	/**
	 * Instantiates a new c brw business exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public CBrwBusinessException (String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Instantiates a new c brw business exception.
	 *
	 * @param message the message
	 * @param error the error
	 */
	public CBrwBusinessException (String message, IErrorResponse error)
	{
		super(message, error);
	}

	/**
	 * Instantiates a new c brw business exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 * @param error the error
	 */
	public CBrwBusinessException (String message, Throwable cause, IErrorResponse error)
	{
		super(message, cause, error);
	}
}
