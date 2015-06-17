package ESystemParameters;

/**
 * The Enum ECoreSystemParameter.
 * 
 * @author Dalibor Rak
 * 
 * @version 1.13.1
 * @since 1.13.0
 */
public enum ECoreSystemParameter
{
	/** The token expire limit. */
	MASTER_TOKEN_EXPIRE_LIMIT ("MASTER_TOKEN_EXPIRE_LIMIT"),

	/** The master token change limit. */
	MASTER_TOKEN_CHANGE_LIMIT ("MASTER_TOKEN_CHANGE_LIMIT"),

	/** The authentication token expire limit. */
	AUTHENTICATION_TOKEN_EXPIRE_LIMIT ("AUTHENTICATION_TOKEN_EXPIRE_LIMIT"),

	/** The authentication token change limit. */
	AUTHENTICATION_TOKEN_CHANGE_LIMIT ("AUTHENTICATION_TOKEN_CHANGE_LIMIT");

	/** The parameter name. */
	private String parameterName;

	/**
	 * Instantiates a new e core system parameter.
	 *
	 * @param parameterName the parameter name
	 */
	private ECoreSystemParameter (String parameterName)
	{
		this.parameterName = parameterName;
	}

	/**
	 * Gets the parameter name.
	 *
	 * @return the parameter name
	 */
	public String getParameterName ()
	{
		return parameterName;
	}

}
