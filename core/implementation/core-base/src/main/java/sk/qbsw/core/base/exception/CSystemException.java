/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.base.exception;

/**
 * System Exception for runtime exception handling.
 *
 * @author Dalibor Rak
 * @version 1.4.0
 * @since 1.0.0
 */
@SuppressWarnings ("serial")
public class CSystemException extends RuntimeException
{

	/**
	 * Instantiates a new c system exception.
	 *
	 * @param message the message
	 * @param th the th
	 */
	public CSystemException (String message, Throwable th)
	{
		super(message, th);
	}

	/**
	 * Instantiates a new c system exception.
	 *
	 * @param message the message
	 */
	public CSystemException (String message)
	{
		super(message);
	}
}
