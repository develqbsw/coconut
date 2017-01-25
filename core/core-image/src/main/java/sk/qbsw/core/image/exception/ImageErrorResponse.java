package sk.qbsw.core.image.exception;

import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.base.exception.IErrorResponse;

/**
 * The image error code.
 *
 * @author Tomas Lauro
 * 
 * @version 1.17.0
 * @since 1.17.0
 */
public enum ImageErrorResponse implements IErrorResponse
{
	/** The system error. */
	SYSTEM_ERROR ("-1", "error.systemerror"),

	/** The invalid file type. */
	INVALID_FILE_TYPE ("1", "error.invalidfiletype"),

	/** The file operation error. */
	FILE_OPERATION_ERROR ("2", "error.fileoperationerror"),

	/** The invalid parameters. */
	INVALID_PARAMETERS ("3", "error.invalidparameters");

	/** The code. */
	private String code;

	/** The message key. */
	private String messageKey;

	/**
	 * Instantiates a new e pnp error code.
	 *
	 * @param errorCode the error code
	 * @param messageKey the message key
	 */
	private ImageErrorResponse (String errorCode, String messageKey)
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
