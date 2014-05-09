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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;


/**
 * The Class COrganization.
 *
 * @author Dalibor Rak
 * @version 1.2.1
 * @since 1.0
 */
@Entity
@Table (name = "t_organization", schema = "sec")
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue ("organization")
@DiscriminatorColumn (name = "type", discriminatorType = DiscriminatorType.STRING)
public class COrganization extends ASecurityChangeEntity<Long>
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The email. */
	private String email;

	/** Phone. */
	private String phone;

	/** The flag enabled. */
	@Column (name = "flag_enabled")
	private Boolean flagEnabled;

	//bi-directional many-to-one association to CLicence
	/** The licences. */
	@OneToMany (mappedBy = "organization", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	//@Cascade ({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	@OrderBy (value = "id")
	private Set<CLicense<?>> licences;

	/** The name. */
	@Expose
	@Column (name = "name", unique = true)
	private String name;

	/**
	 * shopper code
	 */
	private String code;

	/** The pk id. */
	@Id
	@SequenceGenerator (name = "t_organization_pkid_generator", sequenceName = "sec.t_organization_pk_id_seq")
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "t_organization_pkid_generator")
	@Column (name = "pk_id")
	@Expose
	@Access (AccessType.PROPERTY)
	private Long id;

	//bi-directional many-to-one association to CUser
	/** The users. */
	@OneToMany (mappedBy = "organization", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	//@Cascade ({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private List<CUser> users;

	/** Organization address */
	@ManyToOne (fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	@JoinColumn (name = "fk_address", nullable = true)
	private CAddress address;


	/**
	 * Instantiates a new c organization.
	 */
	public COrganization ()
	{
		this.licences = new HashSet<CLicense<?>>();
		this.users = new ArrayList<CUser>();
	}

	/**
	 * Instantiates a new c organization.
	 *
	 * @param id the pk id
	 */
	public COrganization (Long id)
	{
		this.id = id;
	}

	/**
	 * Adds the licence.
	 *
	 * @param licence the licence
	 */
	public void addLicence (CLicense<?> licence)
	{
		this.licences.add(licence);
	}

	/**
	 * Adds the user.
	 *
	 * @param user the user
	 */
	public void addUser (CUser user)
	{
		this.users.add(user);
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail ()
	{
		return this.email;
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
	 * Gets the licences.
	 *
	 * @return the licences
	 */
	public Set<CLicense<?>> getLicences ()
	{
		return this.licences;
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
	 * Returns shooper code
	 * @return string
	 */
	public String getCode ()
	{
		return code;
	}

	/**
	 * Sets shopper code
	 * @param code
	 */
	public void setCode (String code)
	{
		this.code = code;
	}

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	public List<CUser> getUsers ()
	{
		return this.users;
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
	 * Sets the flag enabled.
	 *
	 * @param flagEnabled the new flag enabled
	 */
	public void setFlagEnabled (Boolean flagEnabled)
	{
		this.flagEnabled = flagEnabled;
	}

	/**
	 * Sets the licences.
	 *
	 * @param licences the new licences
	 */
	public void setLicences (Set<CLicense<?>> licences)
	{
		this.licences = licences;
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
	 * Sets the pk id.
	 *
	 * @param id the new pk id
	 */
	public void setId (Long id)
	{
		this.id = id;
	}

	/**
	 * Sets the users.
	 *
	 * @param users the new users
	 */
	public void setUsers (List<CUser> users)
	{
		this.users = users;
	}

	/**
	 * Gets the phone.
	 *
	 * @return the phone
	 */
	public String getPhone ()
	{
		return phone;
	}

	/**
	 * Sets the phone.
	 *
	 * @param phone the new phone
	 */
	public void setPhone (String phone)
	{
		this.phone = phone;
	}

	/**
	 * Gets the main license.
	 *
	 * @return the main license
	 */
	public CLicense<?> getMainLicense ()
	{
		// the most valuable license
		CLicense<?> actualLicense = null;
		for (CLicense<?> license : getLicences())
		{
			if (license != null && license.validateLicense())
			{
				// no license or actual license priority is higher
				if (actualLicense == null || actualLicense.getPriority().compareTo(license.getPriority()) < 0)
				{
					actualLicense = license;
				}
			}
		}
		return actualLicense;
	}

	@Override
	public Long getId ()
	{
		return this.id;
	}

	/**
	 * @return the address
	 */
	public CAddress getAddress ()
	{
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress (CAddress address)
	{
		this.address = address;
	}

}
