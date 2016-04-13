/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.model.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;

import com.google.gson.annotations.Expose;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.security.service.CGroupService;
import sk.qbsw.core.security.service.CUserService;

/**
 * The Class CUser.
 * 
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @author Michal Lacko
 * 
 * @version 1.13.0
 * @since 1.0.0
 */
@Entity
@Table (name = "t_user", schema = "sec")
@FilterDef (name = "userDefaultUnitFilter")
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue ("user")
@DiscriminatorColumn (name = "d_type", discriminatorType = DiscriminatorType.STRING)
public class CUser extends ASecurityChangeEntity<Long>
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The pk id. */
	@Id
	@SequenceGenerator (name = "t_user_pkid_generator", sequenceName = "sec.t_user_pk_id_seq")
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "t_user_pkid_generator")
	@Column (name = "pk_id")
	@Expose
	private Long id;

	/** The flag enabled. */
	@Column (name = "c_flag_enabled")
	private Boolean flagEnabled;

	/** The login. */
	@Expose
	@Column (name = "c_login", unique = true)
	private String login;

	/** The name. */
	@Expose
	@Column (name = "c_name")
	private String name;

	/** The surname. */
	@Expose
	@Column (name = "c_surname")
	private String surname;

	/** The email. */
	@Expose
	@Column (name = "c_email")
	private String email;

	/** The degree. */
	@Expose
	@Column (name = "c_degree")
	private String degree;

	/** The working position. */
	@Expose
	@Column (name = "c_working_position")
	private String workingPosition;

	// bi-directional many-to-one association to COrganization
	/** The organization. */
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_organization", nullable = false)
	@Expose
	private COrganization organization;

	// bi-directional many-to-many association to cross entity to group and unit
	/** set of cross entities. */
	@OneToMany (mappedBy = "user", fetch = FetchType.LAZY)
	@Filter (name = "userDefaultUnitFilter", condition = "( (fk_unit = (select us.fk_default_unit from sec.t_user us where us.pk_id = fk_user)) or ( (select us.fk_default_unit from sec.t_user us where us.pk_id = fk_user) is null and fk_unit is null) )")
	private Set<CXUserUnitGroup> xUserUnitGroups;

	/** The default user's unit. */
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_default_unit", nullable = true)
	@Expose
	private CUnit defaultUnit;

	/** The authentication params. */
	@OneToMany (mappedBy = "user", fetch = FetchType.LAZY)
	private List<CAuthenticationParams> authenticationParams;

	/** The user type. */
	@Column (name = "c_type", nullable = true)
	@Enumerated (EnumType.STRING)
	private EUserType userType = EUserType.PERSON;

	/**  User address. */
	@ManyToOne (fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	@JoinColumn (name = "fk_address", nullable = true)
	private CAddress address;

	/**
	 * Instantiates a new c user.
	 */
	public CUser ()
	{
		xUserUnitGroups = new HashSet<CXUserUnitGroup>();
	}

	/**
	 * Gets the pk id.
	 * 
	 * @return the pk id
	 */
	@Override
	public Long getId ()
	{
		return this.id;
	}

	/**
	 * Sets the pk id.
	 * 
	 * @param id
	 *            the new pk id
	 */
	public void setId (Long id)
	{
		this.id = id;
	}

	/**
	 * Gets the flag enabled.
	 * 
	 * @return the flag enabled
	 */
	public Boolean getFlagEnabled ()
	{
		return this.flagEnabled;
	}

	/**
	 * Sets the flag enabled.
	 * 
	 * @param flagEnabled
	 *            the new flag enabled
	 */
	public void setFlagEnabled (Boolean flagEnabled)
	{
		this.flagEnabled = flagEnabled;
	}

	/**
	 * Gets the login.
	 * 
	 * @return the login
	 */
	public String getLogin ()
	{
		return this.login;
	}

	/**
	 * Sets the login.
	 * 
	 * @param login
	 *            the new login
	 */
	public void setLogin (String login)
	{
		this.login = login;
	}

	/**
	 * Gets the plain text password. If there is no plain text password, returns
	 * null.
	 * 
	 * @return the plain text password
	 */
	public String getPassword ()
	{
		if (getAuthenticationParams() != null)
		{
			return getAuthenticationParams().getPassword();
		}
		else
		{
			throw new CSystemException("The user has not a authentication params");
		}
	}
	/**
	 * Gets the Digest text password. If there is no Digest text password, returns
	 * null.
	 * 
	 * @return the hash text password
	 */
	public String getPasswordDigest ()
	{
		if (getAuthenticationParams() != null)
		{
			return getAuthenticationParams().getPasswordDigest();
		}
		else
		{
			throw new CSystemException("The user has not a authentication params");
		}
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName ()
	{
		return this.name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
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
		return this.surname;
	}

	/**
	 * Sets the surname.
	 * 
	 * @param surname
	 *            the new surname
	 */
	public void setSurname (String surname)
	{
		this.surname = surname;
	}

	/**
	 * Gets the organization.
	 * 
	 * @return the organization
	 */
	public COrganization getOrganization ()
	{
		return this.organization;
	}

	/**
	 * Sets the organization.
	 * 
	 * @param organization
	 *            the new organization
	 */
	public void setOrganization (COrganization organization)
	{
		this.organization = organization;
	}

	/**
	 * Gets the groups assigned to user.
	 * 
	 * If user has default unit, this method return groups for this unit.
	 * If user has not default unit, this method return groups with no unit assigned. 
	 * 
	 * @return the groups
	 */
	public Set<CGroup> getGroups ()
	{
		Set<CGroup> groups = new HashSet<CGroup>();

		for (CXUserUnitGroup xuug : xUserUnitGroups)
		{
			groups.add(xuug.getGroup());
		}

		return groups;
	}

	/**
	 * Do not use, returned set may by incomplete.
	 *
	 * @param unit the unit
	 * @return the groups in unit
	 * @deprecated misunderstanding of concept. use {@link CGroupService#getByUnitUser(CUnit, CUser)}}.
	 */
	@Deprecated
	public Set<CGroup> getGroupsInUnit (CUnit unit)
	{
		Set<CGroup> groups = new HashSet<CGroup>();

		for (CXUserUnitGroup xuug : xUserUnitGroups)
		{
			if (xuug.getUnit().getName().equals(unit.getName()))
			{
				groups.add(xuug.getGroup());
			}
		}

		return groups;
	}

	/**
	 * Adds the group.
	 *
	 * @param grp the grp
	 * @deprecated use {@link CUserService#setUserToGroup(CUser, CGroup)}
	 */
	@Deprecated
	public void addGroup (CGroup grp)
	{
		CXUserUnitGroup xuug = new CXUserUnitGroup();
		xuug.setGroup(grp);
		xuug.setUser(this);
		xUserUnitGroups.add(xuug);
	}

	/**
	 * Adds the group.
	 *
	 * @param grp the grp
	 * @param unt unit
	 * @deprecated use {@link CUserService#setUserToGroup(CUser, CGroup, CUnit)}
	 */
	@Deprecated
	public void addGroupUnit (CGroup grp, CUnit unt)
	{
		CXUserUnitGroup xuug = new CXUserUnitGroup();
		xuug.setGroup(grp);
		xuug.setUnit(unt);
		xuug.setUser(this);
		xUserUnitGroups.add(xuug);
	}

	/**
	 * bind groups with this user.
	 *
	 * @param groups the new groups
	 * @deprecated use {@link CUserService#setUserToGroup(CUser, CGroup)}
	 */
	@Deprecated
	public void setGroups (Set<CGroup> groups)
	{
		for (CGroup group : groups)
		{
			addGroup(group);
		}
	}

	/**
	 * bind groups with this user and unit.
	 *
	 * @param groups the groups
	 * @param unit the unit
	 * @deprecated use {@link CUserService#setUserToGroup(CUser, CGroup)}
	 */
	@Deprecated
	public void setGroupsUnit (Set<CGroup> groups, CUnit unit)
	{
		for (CGroup group : groups)
		{
			addGroupUnit(group, unit);
		}
	}

	/**
	 * Sets the main group.
	 * 
	 * @param group the new main group
	 */
	public void setMainGroup (CGroup group)
	{
		xUserUnitGroups.clear();
		addGroup(group);
	}

	/**
	 * Gets the main group.
	 * 
	 * @return the main group
	 */
	public CGroup getMainGroup ()
	{
		return xUserUnitGroups.iterator().next().getGroup();
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
	 * @param email
	 *            the new email
	 */
	public void setEmail (String email)
	{
		this.email = email;
	}

	/**
	 * Gets the degree.
	 *
	 * @return the degree
	 */
	public String getDegree ()
	{
		return degree;
	}

	/**
	 * Sets the degree.
	 *
	 * @param degree the new degree
	 */
	public void setDegree (String degree)
	{
		this.degree = degree;
	}

	/**
	 * Gets the working position.
	 *
	 * @return the working position
	 */
	public String getWorkingPosition ()
	{
		return workingPosition;
	}

	/**
	 * Sets the working position.
	 *
	 * @param workingPosition the new working position
	 */
	public void setWorkingPosition (String workingPosition)
	{
		this.workingPosition = workingPosition;
	}

	/**
	 * Gets the default unit.
	 * 
	 * @return the default unit
	 */
	public CUnit getDefaultUnit ()
	{
		return defaultUnit;
	}

	/**
	 * Sets the default unit.
	 * 
	 * @param defaultUnit
	 *            the new default unit
	 */
	public void setDefaultUnit (CUnit defaultUnit)
	{
		this.defaultUnit = defaultUnit;
	}

	/**
	 * Gets the authentication params.
	 * 
	 * @return the authentication params
	 */
	private CAuthenticationParams getAuthenticationParams ()
	{
		if (authenticationParams.size() == 1)
		{
			return authenticationParams.get(0);
		}
		else
		{
			return null;
		}
	}

	/**
	 * Authentication by digest.
	 * 
	 * @return autentication type
	 */
	public EAuthenticationType getAuthenticationType ()
	{
		if (getAuthenticationParams() != null)
		{
			return getAuthenticationParams().getAuthenticationType();
		}
		else
		{
			throw new CSystemException("The user has not a authentication params");
		}
	}

	public EPasswordType getPasswordType ()
	{
		if (getAuthenticationParams() != null)
		{
			return getAuthenticationParams().getPasswordType();
		}
		else
		{
			throw new CSystemException("The user has not a authentication params");
		}
	}

	/**
	 * Checks if the user has role.
	 * 
	 * @param role
	 *            needed role
	 * @return true / false
	 */
	public boolean hasRole (CRole role)
	{
		for (CGroup group : getGroups())
		{
			if (group.hasRole(role))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Export roles.
	 * 
	 * @return the list
	 */
	public List<String> exportRoles ()
	{
		List<String> retVal = new ArrayList<String>();

		for (CGroup group : getGroups())
		{
			Set<CRole> roles = group.getRoles();
			for (CRole cRole : roles)
			{
				retVal.add(cRole.getCode());
			}
		}

		return retVal;
	}

	/**
	 * Checks if the user is in defined unit.
	 * 
	 * @param unit
	 *            the unit
	 * @return true / false
	 */
	public boolean isInUnit (CUnit unit)
	{
		for (CGroup group : getGroups())
		{
			if (group.hasUnit(unit))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks if the user has a category and a role at same time.
	 *
	 * @param category            the needed category
	 * @param role the role
	 * @return true / false
	 */
	public boolean hasCategory (String category, CRole role)
	{
		for (CGroup group : getGroups())
		{
			if (group.hasCategory(category, role) == true)
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Gets the x user unit groups.
	 *
	 * @return the xUserUnitGroups
	 */
	public Set<CXUserUnitGroup> getxUserUnitGroups ()
	{
		return xUserUnitGroups;
	}

	/**
	 * Sets the x user unit groups.
	 *
	 * @param xUserUnitGroups            the xUserUnitGroups to set
	 */
	public void setxUserUnitGroups (Set<CXUserUnitGroup> xUserUnitGroups)
	{
		this.xUserUnitGroups = xUserUnitGroups;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public CAddress getAddress ()
	{
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address            the address to set
	 */
	public void setAddress (CAddress address)
	{
		this.address = address;
	}

	/**
	 * Gets the user type.
	 *
	 * @return the user type
	 */
	public EUserType getUserType ()
	{
		return userType;
	}

	/**
	 * Sets the user type.
	 *
	 * @param userType the new user type
	 */
	public void setUserType (EUserType userType)
	{
		this.userType = userType;
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

		if ( (object instanceof CUser) == false)
		{
			return false;
		}

		CUser user = (CUser) object;
		return ( (getId() != null && getId().equals(user.getId())) || (getId() == null && user.getId() == null)) && ( (getLogin() != null && getLogin().equals(user.getLogin())) || (getLogin() == null && user.getLogin() == null)) && ( (getName() != null && getName().equals(user.getName())) || (getName() == null && user.getName() == null)) && ( (getSurname() != null && getSurname().equals(user.getSurname())) || (getSurname() == null && user.getSurname() == null)) && ( (getEmail() != null && getEmail().equals(user.getEmail())) || (getEmail() == null && user.getEmail() == null));
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
		if (getLogin() != null)
		{
			result = 31 * result + getLogin().hashCode();
		}
		if (getName() != null)
		{
			result = 31 * result + getName().hashCode();
		}
		if (getSurname() != null)
		{
			result = 31 * result + getSurname().hashCode();
		}
		if (getEmail() != null)
		{
			result = 31 * result + getEmail().hashCode();
		}
		return result;
	}
}
