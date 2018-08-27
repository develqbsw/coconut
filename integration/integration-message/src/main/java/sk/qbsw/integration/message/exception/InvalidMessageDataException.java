package sk.qbsw.integration.message.exception;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.IErrorResponse;

/**
 * The invalid message data exception.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class InvalidMessageDataException extends CBusinessException
{
	/**
	 * Instantiates a new Invalid message data exception.
	 *
	 * @param message the message
	 */
	public InvalidMessageDataException (String message)
	{
		super(message);
	}

	/**
	 * Instantiates a new Invalid message data exception.
	 *
	 * @param error the error
	 */
	public InvalidMessageDataException (IErrorResponse error)
	{
		super(error);
	}

	/**
	 * Instantiates a new Invalid message data exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public InvalidMessageDataException (String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Instantiates a new Invalid message data exception.
	 *
	 * @param message the message
	 * @param error the error
	 */
	public InvalidMessageDataException (String message, IErrorResponse error)
	{
		super(message, error);
	}

	/**
	 * Instantiates a new Invalid message data exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 * @param error the error
	 */
	public InvalidMessageDataException (String message, Throwable cause, IErrorResponse error)
	{
		super(message, cause, error);
	}
}
