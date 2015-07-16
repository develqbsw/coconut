package sk.qbsw.core.base.exception;

/**
 * Enum represents default error operation result by code & message pair.
 * 
 * @author Michal Lacko
 * @author Tomas Lauro
 * 
 * @version 1.13.1
 * @since 1.8.0
 *
 */
public enum ECoreErrorResponse implements IErrorResponse
{
	// SYSTEM
	/** The system error. */
	SYSTEM_ERROR ("9001", "error.systemerror"),

	/** The access denied. */
	ACCESS_DENIED ("9002", "error.accessdenied"),

	/** The pin wrong. */
	PIN_WRONG ("9003", "error.wrongpin"),

	// BUSINESS
	/** The user already exists. */
	USER_ALREADY_EXISTS ("10002", "error.security.loginused"),

	/** The user not all parameters. */
	USER_NOT_ALL_PARAMETERS ("10003", "error.security.user.notallfields"),

	/** The organization not valid. */
	ORGANIZATION_NOT_VALID ("10004", "error.security.organization.notvalid"),

	/** The password mandatory. */
	PASSWORD_MANDATORY ("10005", "error.security.password.mandatory"),

	/** The password change denied. */
	PASSWORD_CHANGE_DENIED ("10006", "error.security.changepassworddenied"),

	/** The missing mandatory parameters. */
	MISSING_MANDATORY_PARAMETERS ("10007", "error.security.missingmandatoryparameters"),

	/** The master token already exists. */
	MASTER_TOKEN_ALREADY_EXISTS ("10008", "error.security.oauth.mastertokenexists"),

	/** The master token not found. */
	MASTER_TOKEN_NOT_FOUND ("10009", "error.security.oauth.mastertokennotfound"),

	/** The master token invalidated. */
	MASTER_TOKEN_INVALIDATED ("10010", "error.security.oauth.mastertokeninvalidated"),

	/** The authentication token already exists. */
	AUTHENTICATION_TOKEN_ALREADY_EXISTS ("10011", "error.security.oauth.authenticationtokenexists"),

	/** The authentication token not found. */
	AUTHENTICATION_TOKEN_NOT_FOUND ("10012", "error.security.oauth.authenticationtokennotfound"),

	/** The authentication token invalidated. */
	AUTHENTICATION_TOKEN_INVALIDATED ("10013", "error.security.oauth.authenticationtokeninvalidated"),

	/** The user not found. */
	USER_NOT_FOUND ("10014", "error.security.usernotfound");

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
	private ECoreErrorResponse (String errorCode, String messageKey)
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
	public String toString ()
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
