/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.base.exception;

/**
 * Business exception which should be handled by client UI
 *
 * @author Dalibor Rak
 * @version 1.4.0
 * @since 1.0.0
 */
@SuppressWarnings ("serial")
public class CBusinessException extends Exception
{
	private String errorCode;

	/**
	 * Instantiates a new c business exception.
	 *
	 * @param message the message
	 */
	public CBusinessException (String message)
	{
		super(message);
	}

	/**
	 * Instantiates a new c business exception.
	 *
	 * @param message the message
	 * @param errorCode the error code
	 */
	public CBusinessException (String message, String errorCode)
	{
		this(message);
		this.errorCode = errorCode;
	}

	/**
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	public String getErrorCode ()
	{
		return errorCode;
	}
}
