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
 * @version 1.10.2
 * @since 1.0.0
 */
@SuppressWarnings ("serial")
public class CBusinessException extends Exception
{
	/** The error. */
	private IError error;

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
	 * @param cause the cause
	 */
	public CBusinessException (String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Instantiates a new c business exception.
	 *
	 * @param message the message
	 * @param errorCode the error code
	 * @deprecated use CBusinessException(String message, IError error) instead
	 */
	@Deprecated
	public CBusinessException (String message, String errorCode)
	{
		super(message);
		this.error = new CError(errorCode, message);
	}

	/**
	 * Instantiates a new c business exception.
	 *
	 * @param message the message
	 * @param error the error
	 */
	public CBusinessException (String message, IError error)
	{
		super(message);
		this.error = error;
	}

	/**
	 * Instantiates a new c business exception.
	 *
	 * @param message the message
	 * @param error the error
	 * @param cause the cause
	 */
	public CBusinessException (String message, IError error, Throwable cause)
	{
		super(message, cause);
		this.error = error;
	}

	/**
	 * Gets the error code.
	 *
	 * @return the error code or null if there is no error code
	 */
	public String getErrorCode ()
	{
		return error == null ? null : error.getErrorCode();
	}

	/**
	 * Gets the error with code and description.
	 *
	 * @return the error
	 */
	public IError getError ()
	{
		return this.error;
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString ()
	{
		String parentToString = super.toString();

		if (error == null)
		{
			return parentToString;
		}
		else
		{
			return parentToString + ", code: " + error.getErrorCode();
		}
	}

	/**
	 * class only for keep backwards compatibility.
	 *
	 * @author Michal Lacko
	 * @version 1.8.0
	 * @since 1.8.0
	 */
	@Deprecated
	private class CError implements IError
	{

		/** error code which represent error. */
		private String errorCode;

		/** message which represent error description. */
		private String message;

		/**
		 * Instantiates a new c error.
		 *
		 * @param errorCode the error code
		 * @param message the message
		 */
		public CError (String errorCode, String message)
		{
			super();
			this.errorCode = errorCode;
			this.message = message;
		}

		/* (non-Javadoc)
		 * @see sk.qbsw.core.base.exception.IError#getErrorCode()
		 */
		public String getErrorCode ()
		{
			return this.errorCode;
		}

		/* (non-Javadoc)
		 * @see sk.qbsw.core.base.exception.IError#getMessage()
		 */
		public String getMessage ()
		{
			return this.message;
		}
	}



}
