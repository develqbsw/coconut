/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.model.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import sk.qbsw.core.persistence.model.domain.IEntity;

import com.google.gson.annotations.Expose;

/**
 * The Class CUser.
 * 
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.6.0
 * @since 1.0
 */
@Entity
@Table (name = "t_user", schema = "sec")
public class CUser implements Serializable, IEntity<Long>
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The pk id. */
	@Id
	@SequenceGenerator (name = "t_user_pkid_generator", sequenceName = "sec.t_user_pk_id_seq")
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "t_user_pkid_generator")
	@Column (name = "pk_id")
	@Expose
	private Long pkId;

	/** The flag enabled. */
	@Column (name = "flag_enabled")
	private Boolean flagEnabled;

	/** The login. */
	@Expose
	private String login;

	/** The name. */
	@Expose
	private String name;

	/** The password. */
	private String password;

	/** Password digest. */
	private String passwordDigest;

	/** The surname. */
	@Expose
	private String surname;

	/** The email. */
	@Expose
	private String email;

	// bi-directional many-to-one association to COrganization
	/** The organization. */
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_organization", nullable = false)
	@Expose
	private COrganization organization;

	// bi-directional many-to-many association to CGroup
	/** The groups. */
	@ManyToMany (fetch = FetchType.LAZY)
	@JoinTable (schema = "sec", name = "t_x_group_user", joinColumns = {@JoinColumn (name = "fk_user")}, inverseJoinColumns = {@JoinColumn (name = "fk_group")})
	private Set<CGroup> groups;

	/** The PIN code. */
	@Expose
	private String pin;

	/** The default user's unit. */
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_default_unit", nullable = true)
	@Expose
	private CUnit defaultUnit;

	/** The user type. */
	@Column (name = "type", nullable = true)
	@Enumerated (EnumType.STRING)
	private EUserType userType;

	/**
	 * Instantiates a new c user.
	 */
	public CUser ()
	{
		groups = new HashSet<CGroup>();
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
	 * Sets the pk id.
	 * 
	 * @param pkId
	 *            the new pk id
	 */
	public void setPkId (Long pkId)
	{
		this.pkId = pkId;
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
	 * Gets the password digest.
	 * 
	 * @return the password digest
	 */
	public String getPasswordDigest ()
	{
		return passwordDigest;
	}

	/**
	 * Sets the password digest.
	 * 
	 * @param passwordDigest
	 *            the new password digest
	 */
	public void setPasswordDigest (String passwordDigest)
	{
		this.passwordDigest = passwordDigest;
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
	 * Gets the password.
	 * 
	 * @return the password
	 */
	public String getPassword ()
	{
		return this.password;
	}

	/**
	 * Sets the password.
	 * 
	 * @param password
	 *            the new password
	 */
	public void setPassword (String password)
	{
		this.password = password;
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
	 * Gets the groups.
	 * 
	 * @return the groups
	 */
	public Set<CGroup> getGroups ()
	{
		return this.groups;
	}

	/**
	 * Sets the groups.
	 * 
	 * @param groups
	 *            the new groups
	 */
	public void setGroups (Set<CGroup> groups)
	{
		this.groups = groups;
	}

	/**
	 * Adds the group.
	 * 
	 * @param grp
	 *            the grp
	 */
	public void addGroup (CGroup grp)
	{
		groups.add(grp);
	}

	/**
	 * Sets the main group.
	 * 
	 * @param group
	 *            the new main group
	 */
	public void setMainGroup (CGroup group)
	{
		this.groups.clear();
		this.groups.add(group);
	}

	/**
	 * Gets the main group.
	 * 
	 * @return the main group
	 */
	public CGroup getMainGroup ()
	{
		return groups.iterator().next();
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
	 * Gets the pin.
	 * 
	 * @return the pin
	 */
	public String getPin ()
	{
		return pin;
	}

	/**
	 * Sets the pin.
	 * 
	 * @param pin
	 *            the new pin
	 */
	public void setPin (String pin)
	{
		this.pin = pin;
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
	 * @param defaultUnit the new default unit
	 */
	public void setDefaultUnit (CUnit defaultUnit)
	{
		this.defaultUnit = defaultUnit;
	}

	/**
	 * Authentication by digest.
	 *
	 * @return autentication type
	 */
	public EAuthenticationType authenticationType ()
	{
		return passwordDigest != null ? EAuthenticationType.BY_PASSWORD_DIGEST : EAuthenticationType.BY_PASSWORD;
	}

	/**
	 * Checks if the user has role.
	 *
	 * @param role needed role
	 * @return true / false
	 */
	public boolean hasRole (CRole role)
	{
		for (CGroup group : groups)
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

		for (CGroup group : groups)
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
	 * @param unit the unit
	 * @return true / false
	 */
	public boolean isInUnit (CUnit unit)
	{
		for (CGroup group : groups)
		{
			if (group.hasUnit(unit))
			{
				return true;
			}
		}

		return false;
	}
	
	/**
	 * Checks if the user has a category.
	 *
	 * @param category the needed category
	 * @return true / false
	 */
	public boolean hasCategory (String category)
	{
		for (CGroup group : groups)
		{
			if (group.getCategory() != null && group.getCategory().equals(category))
			{
				return true;
			}
		}

		return false;
	}
}
