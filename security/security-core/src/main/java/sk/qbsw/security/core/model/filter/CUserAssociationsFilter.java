package sk.qbsw.security.core.model.filter;

import sk.qbsw.security.core.model.domain.CGroup;
import sk.qbsw.security.core.model.domain.COrganization;
import sk.qbsw.security.core.model.domain.CRole;
import sk.qbsw.security.core.model.domain.CUser;

/**
 * The user associations filter.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.13.0
 */
public class CUserAssociationsFilter
{
	/** The organization. */
	private COrganization organization;

	/** The enabled. */
	private Boolean enabled;

	/** The group. */
	private CGroup group;

	/** The role. */
	private CRole role;

	/** The excluded user. */
	private CUser excludedUser;

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
	 * Gets the group.
	 *
	 * @return the group
	 */
	public CGroup getGroup ()
	{
		return group;
	}

	/**
	 * Sets the group.
	 *
	 * @param group the new group
	 */
	public void setGroup (CGroup group)
	{
		this.group = group;
	}

	/**
	 * Gets the role.
	 *
	 * @return the role
	 */
	public CRole getRole ()
	{
		return role;
	}

	/**
	 * Sets the role.
	 *
	 * @param role the new role
	 */
	public void setRole (CRole role)
	{
		this.role = role;
	}

	/**
	 * Gets the excluded user.
	 *
	 * @return the excluded user
	 */
	public CUser getExcludedUser ()
	{
		return excludedUser;
	}

	/**
	 * Sets the excluded user.
	 *
	 * @param excludedUser the new excluded user
	 */
	public void setExcludedUser (CUser excludedUser)
	{
		this.excludedUser = excludedUser;
	}
}
