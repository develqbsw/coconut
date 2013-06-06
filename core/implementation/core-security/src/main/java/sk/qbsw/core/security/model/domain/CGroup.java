/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.model.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * The Class CGroup.
 *
 * @author Dalibor Rak
 * @version 1.2.1
 * @since 1.0
 */
@Entity
@Table (name = "t_group", schema = "sec")
public class CGroup implements Serializable, IEntity
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The pk id. */
	@Id
	@SequenceGenerator (name = "T_GROUP_PKID_GENERATOR", sequenceName = "SEC.T_GROUP_PK_ID_SEQ")
	@GeneratedValue (strategy = GenerationType.AUTO, generator = "T_GROUP_PKID_GENERATOR")
	@Column (name = "pk_id")
	private Long pkId;

	/** The code. */
	private String code;

	/** The flag system. */
	@Column (name = "flag_system")
	private Boolean flagSystem;

	//bi-directional many-to-many association to CRole
	/** The roles. */
	@ManyToMany (mappedBy = "groups")
	private Set<CRole> roles;

	//bi-directional many-to-many association to CUser
	/** The users. */
	@ManyToMany (mappedBy = "groups")
	private Set<CUser> users;

	/**
	 * Instantiates a new c group.
	 */
	public CGroup ()
	{
	}

	/**
	 * Gets the pk id.
	 *
	 * @return the pk id
	 */
	public Long getPkId ()
	{
		return this.pkId;
	}

	/**
	 * Sets the pk id.
	 *
	 * @param pkId the new pk id
	 */
	public void setPkId (Long pkId)
	{
		this.pkId = pkId;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode ()
	{
		return this.code;
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
	 * Gets the roles.
	 *
	 * @return the roles
	 */
	public Set<CRole> getRoles ()
	{
		return this.roles;
	}

	/**
	 * Sets the roles.
	 *
	 * @param roles the new roles
	 */
	public void setRoles (Set<CRole> roles)
	{
		this.roles = roles;
	}

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	public Set<CUser> getUsers ()
	{
		return this.users;
	}

	/**
	 * Sets the users.
	 *
	 * @param users the new users
	 */
	public void setUsers (Set<CUser> users)
	{
		this.users = users;
	}

	/**
	 * Gets the flag system.
	 *
	 * @return the flag system
	 */
	public Boolean getFlagSystem ()
	{
		return flagSystem;
	}

	/**
	 * Sets the flag system.
	 *
	 * @param flagSystem the new flag system
	 */
	public void setFlagSystem (Boolean flagSystem)
	{
		this.flagSystem = flagSystem;
	}

	public boolean hasRole (CRole roleToCheck)
	{

		if (roleToCheck == null)
		{
			return true;
		}

		for (CRole role : roles)
		{
			if (role.getCode().equals(roleToCheck.getCode()))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public Long getId ()
	{
		return getPkId();
	}
}
