package sk.qbsw.core.base.exception;

/**
 * Enum represents default error operation result by code & message pair.
 * 
 * @author Michal Lacko
 * @version 1.13.0
 * @since 1.8.0
 *
 */
public enum ECoreErrorResponse implements IErrorResponse
{

	// SYSTEM
	SYSTEM_ERROR("9001", "error.systemerror"),
	ACCESS_DENIED("9002", "error.accessdenied"),
	PIN_WRONG("9003", "error.wrongpin"),
	// BUSINESS
	USER_ALREADY_EXISTS("10002", "error.security.loginused"),
	USER_NOT_ALL_PARAMETERS("10003", "error.security.user.notallfields"),
	ORGANIZATION_NOT_VALID("10004", "error.security.organization.notvalid"),
	PASSWORD_MANDATORY("10005", "error.security.password.mandatory"),
	PASSWORD_CHANGE_DENIED("10006", "error.security.changepassworddenied");

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
	private ECoreErrorResponse(String errorCode, String messageKey)
	{
		this.code = errorCode;
		this.messageKey = messageKey;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.base.exception.IError#getErrorCode()
	 */
	public String getCode()
	{
		return code;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.base.exception.IError#getMessage()
	 */
	public String getMessageKey()
	{
		return messageKey;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	public String toString()
	{
		StringBuffer buffer = new StringBuffer();
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
	public static ECoreErrorResponse getByCode(String code) {
		for (ECoreErrorResponse en : ECoreErrorResponse.values()) {
			if (en.getCode().equals(code)) {
				return en;
			}
		}
		return null;
	}
}
