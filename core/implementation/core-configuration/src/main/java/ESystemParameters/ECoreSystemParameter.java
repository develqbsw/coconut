package ESystemParameters;

/**
 * The Enum ECoreSystemParameter.
 * 
 * @author Dalibor Rak
 * @version 1.13.0
 * @since 1.13.0
 */
public enum ECoreSystemParameter {

	/** The token expire limit. */
	TOKEN_EXPIRE_LIMIT("TOKEN_EXPIRE_LIMIT"),
	TOKEN_CHANGE_LIMIT("TOKEN_CHANGE_LIMIT");

	/** The parameter name. */
	private String parameterName;

	/**
	 * Instantiates a new e core system parameter.
	 *
	 * @param parameterName the parameter name
	 */
	private ECoreSystemParameter(String parameterName) {
		this.parameterName = parameterName;
	}

	/**
	 * Gets the parameter name.
	 *
	 * @return the parameter name
	 */
	public String getParameterName() {
		return parameterName;
	}

}
