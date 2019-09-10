package sk.qbsw.core.filesystem.exception;

import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.base.exception.IErrorResponse;

/**
 * The enum Filesystem error.
 *
 * @author Tomas Leken
 * @version 2.3.0
 * @since 2.3.0
 */
public enum FilesystemError implements IErrorResponse
{
	/**
	 * File not found filesystem error.
	 */
	FILE_NOT_FOUND ("10", "error.file_notfound");

	/** The code. */
	private String code;

	/** The message key. */
	private String messageKey;

	FilesystemError (String errorCode, String messageKey)
	{
		this.code = errorCode;
		this.messageKey = messageKey;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.base.exception.IError#getErrorCode()
	 */
	@Override
	public String getCode ()
	{
		return code;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.base.exception.IError#getMessage()
	 */
	@Override
	public String getMessageKey ()
	{
		return messageKey;
	}

	/**
	 * Gets the by code.
	 *
	 * @param code the code
	 * @return the by code
	 */
	public static ECoreErrorResponse getByCode (String code)
	{
		for (ECoreErrorResponse en : ECoreErrorResponse.values())
		{
			if (en.getCode().equals(code))
			{
				return en;
			}
		}
		return null;
	}
}
