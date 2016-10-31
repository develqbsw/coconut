package sk.qbsw.core.api.model.response;

import javax.validation.constraints.NotNull;

/**
 * The response error.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public class CResponseError
{
	/** The code. */
	@NotNull
	private String code;

	/** The code. */
	private String description;

	/**
	 * Instantiates a new c api error.
	 */
	public CResponseError ()
	{
	}

	/**
	 * Instantiates a new c response error.
	 *
	 * @param code the code
	 */
	public CResponseError (String code)
	{
		this.code = code;
	}

	/**
	 * Instantiates a new c api error.
	 *
	 * @param code the code
	 * @param description the description
	 */
	public CResponseError (String code, String description)
	{
		this.code = code;
		this.description = description;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode ()
	{
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode (String code)
	{
		this.code = code;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription ()
	{
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription (String description)
	{
		this.description = description;
	}
}
