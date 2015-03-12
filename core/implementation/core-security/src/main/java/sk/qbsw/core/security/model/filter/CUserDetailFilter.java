package sk.qbsw.core.security.model.filter;

import sk.qbsw.core.security.model.domain.COrganization;

/**
 * User details filter.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.13.0
 */
public class CUserDetailFilter
{
	/** The name. */
	private String name;

	/** The surname. */
	private String surname;

	/** The login. */
	private String login;

	/** The email. */
	private String email;

	/** The enabled. */
	private Boolean enabled;

	/** The group code prefix. */
	private String groupCodePrefix;

	/** The organization. */
	private COrganization organization;

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName ()
	{
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName (String name)
	{
		this.name = name;
	}

	/**
	 * Gets the surname.
	 *
	 * @return the surname
	 */
	public String getSurname ()
	{
		return surname;
	}

	/**
	 * Sets the surname.
	 *
	 * @param surname the new surname
	 */
	public void setSurname (String surname)
	{
		this.surname = surname;
	}

	/**
	 * Gets the login.
	 *
	 * @return the login
	 */
	public String getLogin ()
	{
		return login;
	}

	/**
	 * Sets the login.
	 *
	 * @param login the new login
	 */
	public void setLogin (String login)
	{
		this.login = login;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail ()
	{
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail (String email)
	{
		this.email = email;
	}

	/**
	 * Gets the enabled.
	 *
	 * @return the enabled
	 */
	public Boolean getEnabled ()
	{
		return enabled;
	}

	/**
	 * Sets the enabled.
	 *
	 * @param enabled the new enabled
	 */
	public void setEnabled (Boolean enabled)
	{
		this.enabled = enabled;
	}

	/**
	 * Gets the group code prefix.
	 *
	 * @return the group code prefix
	 */
	public String getGroupCodePrefix ()
	{
		return groupCodePrefix;
	}

	/**
	 * Sets the group code prefix.
	 *
	 * @param groupCodePrefix the new group code prefix
	 */
	public void setGroupCodePrefix (String groupCodePrefix)
	{
		this.groupCodePrefix = groupCodePrefix;
	}

	/**
	 * Gets the organization.
	 *
	 * @return the organization
	 */
	public COrganization getOrganization ()
	{
		return organization;
	}

	/**
	 * Sets the organization.
	 *
	 * @param organization the new organization
	 */
	public void setOrganization (COrganization organization)
	{
		this.organization = organization;
	}
}
