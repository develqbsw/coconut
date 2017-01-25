package sk.qbsw.core.image.exception;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.base.exception.IErrorResponse;

/**
 * The image system exception. 
 *
 * @author Tomas Lauro
 * 
 * @version 1.17.0
 * @since 1.17.0
 */
public class ImageException extends CSystemException
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1618086606340507801L;

	/**
	 * Instantiates a new image exception.
	 *
	 * @param message the message
	 */
	public ImageException (String message)
	{
		super(message);
	}

	/**
	 * Instantiates a new image exception.
	 *
	 * @param error the error
	 */
	public ImageException (IErrorResponse error)
	{
		super(error);
	}

	/**
	 * Instantiates a new image exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public ImageException (String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Instantiates a new image exception.
	 *
	 * @param message the message
	 * @param error the error
	 */
	public ImageException (String message, IErrorResponse error)
	{
		super(message, error);
	}
}
