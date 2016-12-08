package sk.qbsw.et.browser.core.exception;

import sk.qbsw.core.base.exception.IErrorResponse;

/**
 * The browser errors.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.16.0
 *
 */
public enum EBrwError implements IErrorResponse
{
	UNDEFINED_ENTITY_MAPPING ("11000", "error.brw.core.undefinedentitymapping"),

	UNDEFINED_BROWSER_MAPPING ("11001", "error.brw.core.undefinedbrowsermapping"),

	UNDEFINED_CLASS ("11002", "error.brw.core.undefinedclass");

	/**
	 * code of the error. This is shot code represent error in application
	 */
	private String code;

	/**  this is description of error. */
	private String messageKey;

	/**
	 * Instantiates a new e error.
	 *
	 * @param errorCode the error code
	 * @param messageKey the message key
	 */
	private EBrwError (String errorCode, String messageKey)
	{
		this.code = errorCode;
		this.messageKey = messageKey;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.base.exception.IError#getErrorCode()
	 */
	public String getCode ()
	{
		return code;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.base.exception.IError#getMessage()
	 */
	public String getMessageKey ()
	{
		return messageKey;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString ()
	{
		StringBuilder buffer = new StringBuilder();
		buffer.append(code);
		if (buffer.length() > 0 && messageKey != null && messageKey.length() > 0)
		{
			buffer.append(" : ");
		}

		buffer.append(messageKey);
		return buffer.toString();
	}

	/**
	 * Gets the by code.
	 *
	 * @param code the code
	 * @return the by code
	 */
	public static EBrwError getByCode (String code)
	{
		for (EBrwError en : EBrwError.values())
		{
			if (en.getCode().equals(code))
			{
				return en;
			}
		}
		return null;
	}
}
