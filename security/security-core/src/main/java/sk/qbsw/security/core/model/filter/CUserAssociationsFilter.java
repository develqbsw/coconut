package sk.qbsw.security.core.model.filter;

import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Organization;
import sk.qbsw.security.core.model.domain.Role;
import sk.qbsw.security.core.model.domain.User;

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
	private Organization organization;

	/** The enabled. */
	private Boolean enabled;

	/** The group. */
	private Group group;

	/** The role. */
	private Role role;

	/** The excluded user. */
	private User excludedUser;

	/**
	 * Gets the organization.
	 *
	 * @return the organization
	 */
	public Organization getOrganization ()
	{
		return organization;
	}

	/**
	 * Sets the organization.
	 *
	 * @param organization the new organization
	 */
	public void setOrganization (Organization organization)
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
	public Group getGroup ()
	{
		return group;
	}

	/**
	 * Sets the group.
	 *
	 * @param group the new group
	 */
	public void setGroup (Group group)
	{
		this.group = group;
	}

	/**
	 * Gets the role.
	 *
	 * @return the role
	 */
	public Role getRole ()
	{
		return role;
	}

	/**
	 * Sets the role.
	 *
	 * @param role the new role
	 */
	public void setRole (Role role)
	{
		this.role = role;
	}

	/**
	 * Gets the excluded user.
	 *
	 * @return the excluded user
	 */
	public User getExcludedUser ()
	{
		return excludedUser;
	}

	/**
	 * Sets the excluded user.
	 *
	 * @param excludedUser the new excluded user
	 */
	public void setExcludedUser (User excludedUser)
	{
		this.excludedUser = excludedUser;
	}
}
