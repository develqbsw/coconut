/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.base.exception;

/**
 * Business exception which should be handled by client UI.
 *
 * @author Dalibor Rak
 * @author Michal Lacko
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.0.0
 */
@SuppressWarnings("serial")
public class CBusinessException extends Exception
{

	/** The error. */
	private final IErrorResponse error;

	/**
	 * Instantiates a new c business exception.
	 *
	 * @param message the message
	 */
	public CBusinessException(String message)
	{
		super(message);
		this.error = null;
	}

	/**
	 * Instantiates a new c business exception.
	 *
	 * @param message the message
	 */
	public CBusinessException(IErrorResponse error)
	{
		super();
		this.error = error;
	}

	/**
	 * Instantiates a new c business exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public CBusinessException(String message, Throwable cause)
	{
		super(message, cause);
		this.error = null;
	}

	/**
	 * Instantiates a new c business exception.
	 *
	 * @param message the message
	 * @param error the error
	 */
	public CBusinessException(String message, IErrorResponse error)
	{
		super(message);
		this.error = error;
	}

	/**
	 * Instantiates a new c business exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 * @param error the error
	 */
	public CBusinessException(String message, Throwable cause, IErrorResponse error)
	{
		super(message, cause);
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
