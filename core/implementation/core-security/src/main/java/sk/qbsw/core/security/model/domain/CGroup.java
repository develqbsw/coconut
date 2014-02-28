/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.model.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	/** Cross entity to user and unit. */
	@OneToMany (mappedBy = "group", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<CXUserUnitGroup> xUserUnitGroups;

	//bi-directional many-to-many association to CUnit
	/** The units. */
	@ManyToMany (mappedBy = "groups", fetch = FetchType.LAZY)
	private Set<CUnit> units;

	/** Categories separator. */
	@Transient
	private final String CATEGORIES_SEPARATOR = ";";

	/**
	 * Instantiates a new c group.
	 */
	public CGroup ()
	{
		xUserUnitGroups = new HashSet<CXUserUnitGroup>();
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
	 * Checks if the group has a category and a role at same time.
	 *
	 * @param category the needed category
	 * @param role the role
	 * @return true / false
	 */
	public boolean hasCategory (String category, CRole role)
	{
		if (this.category != null && hasRole(role) == true)
		{
			String[] categories = this.category.split(CATEGORIES_SEPARATOR);
			for (String cat : categories)
			{
				if (cat.equals(category) == true)
				{
					return true;
				}
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
		HashSet<CUser> users = new HashSet<CUser>();
		
		for (CXUserUnitGroup xuug : xUserUnitGroups)
		{	
			users.add(xuug.getUser());
		}
		return users;
	}
	
	/**
	 * bind users with this group
	 * @param users
	 */
	public void setUsers(Set<CUser> users)
	{
		for (CUser user : users)
		{
			addUser(user);
		}
	}
	
	/**
	 * Adds the user.
	 * 
	 * @param user
	 */
	public void addUser (CUser user)
	{
		CXUserUnitGroup xuug = new CXUserUnitGroup();
		xuug.setGroup(this);
		xuug.setUser(user);
		xUserUnitGroups.add(xuug);
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
