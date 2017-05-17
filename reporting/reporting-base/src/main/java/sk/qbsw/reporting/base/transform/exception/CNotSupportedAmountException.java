package sk.qbsw.reporting.base.transform.exception;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.IErrorResponse;

/**
 * The amount given in the process is out of range - too big.
 *
 * @author Tomas Lauro
 * 
 * @version 1.10.2
 * @since 1.10.2
 */
@SuppressWarnings("serial")
public class CNotSupportedAmountException extends CBusinessException
{

	/**
	 * Instantiates a new not supported amount.
	 *
	 * @param message the message
	 */
	public CNotSupportedAmountException(String message)
	{
		super(message);
	}

	/**
	 * Instantiates a new not supported amount.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public CNotSupportedAmountException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Instantiates a new not supported amount.
	 *
	 * @param message the message
	 * @param error the error
	 */
	public CNotSupportedAmountException(String message, IErrorResponse error)
	{
		super(message, error);
	}

	/**
	 * Instantiates a new not supported amount.
	 *
	 * @param message the message
	 * @param error the error
	 * @param cause the cause
	 */
	public CNotSupportedAmountException(String message, Throwable cause, IErrorResponse error)
	{
		super(message, cause, error);
	}
}
