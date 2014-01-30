/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.model.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * @version 1.6.0
 * @since 1.0
 */
@Entity
@Table (name = "t_group", schema = "sec")
public class CGroup implements Serializable, IEntity<Long>
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The pk id. */
	@Id
	@SequenceGenerator (name = "t_group_pkid_generator", sequenceName = "sec.t_group_pk_id_seq")
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "t_group_pkid_generator")
	@Column (name = "pk_id")
	private Long pkId;

	/** The code. */
	@Column (name = "code", unique = true)
	private String code;

	/** The flag system. */
	@Column (name = "flag_system")
	private Boolean flagSystem;

	/** The category. */
	@Column (name = "category", nullable = true)
	private String category;

	//bi-directional many-to-many association to CRole
	/** The roles. */
	@ManyToMany (mappedBy = "groups", fetch = FetchType.LAZY)
	private Set<CRole> roles;

	//bi-directional many-to-many association to CUser
	/** The users. */
	@ManyToMany (mappedBy = "groups", fetch = FetchType.LAZY)
	private Set<CUser> users;

	//bi-directional many-to-many association to CUnit
	/** The units. */
	@ManyToMany (mappedBy = "groups", fetch = FetchType.LAZY)
	private Set<CUnit> units;

	/**
	 * Instantiates a new c group.
	 */
	public CGroup ()
	{
	}

	/**
	 * Checks for role.
	 *
	 * @param roleToCheck the role to check
	 * @return true, if successful
	 */
	public boolean hasRole (CRole roleToCheck)
	{

		if (roleToCheck == null)
		{
			return true;
		}

		if (roles == null)
		{
			return false;
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

	/**
	 * Checks for unit.
	 *
	 * @param unitToCheck the unit to check
	 * @return true, if successful
	 */
	public boolean hasUnit (CUnit unitToCheck)
	{
		if (unitToCheck == null)
		{
			return true;
		}

		if (units == null)
		{
			return false;
		}

		for (CUnit unit : units)
		{
			if (unit.getName().equals(unitToCheck.getName()))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Gets the category.
	 *
	 * @return the category
	 */
	public String getCategory ()
	{
		return category;
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
	 * Gets the flag system.
	 *
	 * @return the flag system
	 */
	public Boolean getFlagSystem ()
	{
		return flagSystem;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.model.domain.IEntity#getId()
	 */
	@Override
	public Long getId ()
	{
		return getPkId();
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
	 * Gets the roles.
	 *
	 * @return the roles
	 */
	public Set<CRole> getRoles ()
	{
		return this.roles;
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
	 * Sets the category.
	 *
	 * @param category the category to set
	 */
	public void setCategory (String category)
	{
		this.category = category;
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
	 * Sets the flag system.
	 *
	 * @param flagSystem the new flag system
	 */
	public void setFlagSystem (Boolean flagSystem)
	{
		this.flagSystem = flagSystem;
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
	 * Sets the roles.
	 *
	 * @param roles the new roles
	 */
	public void setRoles (Set<CRole> roles)
	{
		this.roles = roles;
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
	 * Gets the units.
	 *
	 * @return the units
	 */
	public Set<CUnit> getUnits ()
	{
		return units;
	}

	/**
	 * Sets the units.
	 *
	 * @param units the new units
	 */
	public void setUnits (Set<CUnit> units)
	{
		this.units = units;
	}
}
