package sk.qbsw.security.oauth.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The organization data.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.18.2
 */
public class OrganizationData implements Serializable
{
	private static final long serialVersionUID = 6368009909934050540L;

	@NotNull
	private Long id;

	private String name;

	private String code;

	/**
	 * Instantiates a new Organization data.
	 */
	public OrganizationData ()
	{
	}

	/**
	 * Instantiates a new Organization data.
	 *
	 * @param id the id
	 * @param name the name
	 * @param code the code
	 */
	public OrganizationData (Long id, String name, String code)
	{
		this.id = id;
		this.name = name;
		this.code = code;
	}

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	public Long getId ()
	{
		return id;
	}

	/**
	 * Sets id.
	 *
	 * @param id the id
	 */
	public void setId (Long id)
	{
		this.id = id;
	}

	/**
	 * Gets name.
	 *
	 * @return the name
	 */
	public String getName ()
	{
		return name;
	}

	/**
	 * Sets name.
	 *
	 * @param name the name
	 */
	public void setName (String name)
	{
		this.name = name;
	}

	/**
	 * Gets code.
	 *
	 * @return the code
	 */
	public String getCode ()
	{
		return code;
	}

	/**
	 * Sets code.
	 *
	 * @param code the code
	 */
	public void setCode (String code)
	{
		this.code = code;
	}
}
