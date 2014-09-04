/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.model.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The Class CGroup.
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.9.1
 * @since 1.0.0
 */
@Entity
@Table (name = "t_group", schema = "sec")
public class CGroup extends ASecurityChangeEntity<Long>
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The pk id. */
	@Id
	@SequenceGenerator (name = "t_group_pkid_generator", sequenceName = "sec.t_group_pk_id_seq")
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "t_group_pkid_generator")
	@Column (name = "pk_id")
	private Long id;

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

	//bi-directional many-to-many association to CGroup
	//this association excludes group in other words if user have one group then can't have groups which are associated with this group through this association
	/** Excluded groups. */
	@ManyToMany (fetch = FetchType.LAZY)
	@JoinTable (schema = "sec", name = "t_x_group_group", joinColumns = {@JoinColumn (name = "fk_group")}, inverseJoinColumns = {@JoinColumn (name = "fk_excluded_group")})
	private Set<CGroup> excludedGroups;
	
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
	public void setUsers (Set<CUser> users)
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
	 * @param id the new pk id
	 */
	public void setId (Long id)
	{
		this.id = id;
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

	/**
	 * @return the excludedGroups
	 */
	public Set<CGroup> getExcludedGroups ()
	{
		return excludedGroups;
	}

	/**
	 * @param excludedGroups the excludedGroups to set
	 */
	public void setExcludedGroups (Set<CGroup> excludedGroups)
	{
		this.excludedGroups = excludedGroups;
	}

	/**
	 * @return the id
	 */
	public Long getId ()
	{
		return this.id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals (Object object)
	{
		if (object == this)
		{
			return true;
		}

		if ( (object instanceof CGroup) == false)
		{
			return false;
		}

		CGroup group = (CGroup) object;
		return ((getId() != null && getId().equals(group.getId())) || (getId() == null && group.getId() == null))
			&& ((getCode() != null && getCode().equals(group.getCode())) || (getCode() == null && group.getCode() == null))
			&& ((getCategory() != null && getCategory().equals(group.getCategory())) || (getCategory() == null && group.getCategory() == null));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode ()
	{
		int result = 17;
		if (getId() != null)
		{
			result = 31 * result + getId().hashCode();
		}
		if (getCode() != null)
		{
			result = 31 * result + getCode().hashCode();
		}
		if (getCategory() != null)
		{
			result = 31 * result + getCategory().hashCode();
		}
		return result;
	}
}
