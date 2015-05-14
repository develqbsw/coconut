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
@SuppressWarnings("serial")
public class CSystemException extends RuntimeException
{

	/** The error. */
	private IErrorResponse error;

	/**
	 * Instantiates a new c system exception.
	 *
	 * @param message the message
	 * @param th the th
	 */
	public CSystemException(String message, Throwable th)
	{
		super(message, th);
	}

	/**
	 * Instantiates a new c system exception.
	 *
	 * @param message the message
	 */
	public CSystemException(String message)
	{
		super(message);
	}

	/**
	 * Instantiates a new c system exception.
	 *
	 * @param error the error
	 */
	public CSystemException(IErrorResponse error)
	{
		super();
		this.error = error;
	}

	/**
	 * Instantiates a new c system exception.
	 *
	 * @param th the th
	 * @param error the error
	 */
	public CSystemException(Throwable th, IErrorResponse error)
	{
		super(th);
		this.error = error;
	}

	/**
	 * Instantiates a new c system exception.
	 *
	 * @param message the message
	 * @param error the error
	 */
	public CSystemException(String message, IErrorResponse error)
	{
		super(message);
		this.error = error;
	}

	/**
	 * Gets the error code.
	 *
	 * @return the error code or null if there is no error code
	 */
	public String getErrorCode()
	{
		return error == null ? null : error.getCode();
	}

	/**
	 * Gets the error with code and description.
	 *
	 * @return the error
	 */
	public IErrorResponse getError()
	{
		return this.error;
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString()
	{
		String parentToString = super.toString();

		if (error == null)
		{
			return parentToString;
		}
		else
		{
			return parentToString + ", code: " + error.getCode();
		}
	}
}
