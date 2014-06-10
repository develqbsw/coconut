package sk.qbsw.core.base.exception;

// TODO: Auto-generated Javadoc
/**
 * Enum represents default error operation result by code & message pair.
 * 
 * @author Michal Lacko
 * @version 1.8.0
 * @since 1.8.0
 *
 */
public enum EError implements IError
{
	/** The system error. */
	SYSTEM_ERROR ("ERR-001", "SYSTEM_ERROR");

	/**
	 * code of the error. This is shot code represent error in application
	 */
	private String errorCode;

	/**  this is description of error. */
	private String message;

	/**
	 * Instantiates a new e error.
	 *
	 * @param errorCode the error code
	 * @param message the message
	 */
	private EError (String errorCode, String message)
	{
		this.errorCode = errorCode;
		this.message = message;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.base.exception.IError#getErrorCode()
	 */
	public String getErrorCode ()
	{
		return errorCode;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.base.exception.IError#getMessage()
	 */
	public String getMessage ()
	{
		return message;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	public String toString ()
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append(errorCode);
		if (buffer.length() > 0 && message != null && message.length() > 0)
		{
			buffer.append(" : ");
		}

		buffer.append(message);
		return buffer.toString();
	}

}
