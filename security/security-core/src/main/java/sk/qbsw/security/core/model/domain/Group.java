/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.security.core.model.domain;

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
 * The Class Group.
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.9.1
 * @since 1.0.0
 */
@Entity
@Table (name = "t_group", schema = "sec")
public class Group extends BaseSecurityChangeEntity<Long>
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
	@Column (name = "c_code", unique = true)
	private String code;

	/** The flag system. */
	@Column (name = "c_flag_system")
	private Boolean flagSystem;

	/** The category. */
	@Column (name = "c_category", nullable = true)
	private String category;

	//bi-directional many-to-many association to Role
	/** The roles. */
	@ManyToMany (mappedBy = "groups", fetch = FetchType.LAZY)
	private Set<Role> roles;

	//bi-directional many-to-many association to User
	/** Cross entity to user and unit. */
	@OneToMany (mappedBy = "group", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<UserUnitGroup> xUserUnitGroups;

	//bi-directional many-to-many association to Unit
	/** The units. */
	@ManyToMany (mappedBy = "groups", fetch = FetchType.LAZY)
	private Set<Unit> units;

	//bi-directional many-to-many association to Group
	//this association excludes group in other words if user have one group then can't have groups which are associated with this group through this association
	/** Excluded groups. */
	@ManyToMany (fetch = FetchType.LAZY)
	@JoinTable (schema = "sec", name = "t_x_group_group", joinColumns = {@JoinColumn (name = "fk_group")}, inverseJoinColumns = {@JoinColumn (name = "fk_excluded_group")})
	private Set<Group> excludedGroups;
	
	/** Categories separator. */
	@Transient
	private final String CATEGORIES_SEPARATOR = ";";

	/**
	 * Instantiates a new c group.
	 */
	public Group ()
	{
		xUserUnitGroups = new HashSet<UserUnitGroup>();
	}

	/**
	 * Checks for role.
	 *
	 * @param roleToCheck the role to check
	 * @return true, if successful
	 */
	public boolean hasRole (Role roleToCheck)
	{

		if (roleToCheck == null)
		{
			return true;
		}

		if (roles == null)
		{
			return false;
		}

		for (Role role : roles)
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
	public boolean hasUnit (Unit unitToCheck)
	{
		if (unitToCheck == null)
		{
			return true;
		}

		if (units == null)
		{
			return false;
		}

		for (Unit unit : units)
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
	public boolean hasCategory (String category, Role role)
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
	public Set<Role> getRoles ()
	{
		return this.roles;
	}

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	public Set<User> getUsers ()
	{
		HashSet<User> users = new HashSet<User>();

		for (UserUnitGroup xuug : xUserUnitGroups)
		{
			users.add(xuug.getUser());
		}
		return users;
	}

	/**
	 * bind users with this group
	 * @param users
	 */
	public void setUsers (Set<User> users)
	{
		for (User user : users)
		{
			addUser(user);
		}
	}

	/**
	 * Adds the user.
	 * 
	 * @param user
	 */
	public void addUser (User user)
	{
		UserUnitGroup xuug = new UserUnitGroup();
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
	public void setRoles (Set<Role> roles)
	{
		this.roles = roles;
	}

	/**
	 * Gets the units.
	 *
	 * @return the units
	 */
	public Set<Unit> getUnits ()
	{
		return units;
	}

	/**
	 * Sets the units.
	 *
	 * @param units the new units
	 */
	public void setUnits (Set<Unit> units)
	{
		this.units = units;
	}

	/**
	 * @return the excludedGroups
	 */
	public Set<Group> getExcludedGroups ()
	{
		return excludedGroups;
	}

	/**
	 * @param excludedGroups the excludedGroups to set
	 */
	public void setExcludedGroups (Set<Group> excludedGroups)
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

		if ( (object instanceof Group) == false)
		{
			return false;
		}

		Group group = (Group) object;
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
