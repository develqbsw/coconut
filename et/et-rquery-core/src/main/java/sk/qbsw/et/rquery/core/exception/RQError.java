package sk.qbsw.et.rquery.core.exception;

import sk.qbsw.core.base.exception.IErrorResponse;

/**
 * The rq errors.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public enum RQError implements IErrorResponse
{
	/**
	 * Undefined entity mapping rq error.
	 */
	UNDEFINED_ENTITY_MAPPING ("11000", "error.rquery.core.undefined_entity_mapping"),

	/**
	 * Unsupported operator rq error.
	 */
	UNSUPPORTED_OPERATOR ("11001", "error.rquery.core.unsupported_operator"),

	/**
	 * Unsupported property rq error.
	 */
	UNSUPPORTED_PROPERTY ("11002", "error.rquery.core.unsupported_property");

	private String code;

	private String messageKey;

	RQError (String errorCode, String messageKey)
	{
		this.code = errorCode;
		this.messageKey = messageKey;
	}

	public String getCode ()
	{
		return code;
	}

	public String getMessageKey ()
	{
		return messageKey;
	}

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
	public static RQError getByCode (String code)
	{
		for (RQError en : RQError.values())
		{
			if (en.getCode().equals(code))
			{
				return en;
			}
		}
		return null;
	}
}
