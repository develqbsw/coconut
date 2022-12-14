package sk.qbsw.core.base.exception;

/**
 * Enum represents default error operation result by code & message pair.
 * 
 * @author Michal Lacko
 * @author Tomas Lauro
 * @author Michal Slezák
 * @version 2.5.0
 * @since 1.8.0
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

	/** The unsupported authentication token. */
	UNSUPPORTED_AUTHENTICATION_TOKEN ("9004", "error.unsupportedauthenticationtoken"),

	ILLEGAL_STATE ("9005", "error.illegalstate"),

	ILLEGAL_ARGUMENT ("9006", "error.illegalargument"),

	INVALID_DATA ("9007", "error.invaliddata"),

	// BUSINESS
	/** The user already exists. */
	ACCOUNT_ALREADY_EXISTS ("10002", "error.security.loginused"),

	/** The user not all parameters. */
	ACCOUNT_NOT_ALL_PARAMETERS ("10003", "error.security.user.notallfields"),

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
	ACCOUNT_NOT_FOUND ("10014", "error.security.usernotfound"),
	
	/** The password incorrect format. */
	PASSWORD_INVALID_FORMAT ("10015", "error.security.invalidpasswordformat"),

	/** The group not found. */
	GROUP_NOT_FOUND ("10016", "error.security.groupnotfound"),

	/** The role not found. */
	ROLE_NOT_FOUND ("10017", "error.security.rolenotfound"),

	/** The unit not found. */
	UNIT_NOT_FOUND ("10018", "error.security.unitnotfound"),

	/** The user not found. */
	USER_NOT_FOUND ("10019", "error.security.usernotfound"),
	;

	/**
	 * code of the error. This is shot code represent error in application
	 */
	private String code;

	/** this is description of error. */
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

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.core.base.exception.IError#getErrorCode()
	 */
	public String getCode ()
	{
		return code;
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.core.base.exception.IError#getMessage()
	 */
	public String getMessageKey ()
	{
		return messageKey;
	}

	/*
	 * (non-Javadoc)
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
