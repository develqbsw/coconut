/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.model.domain;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

/**
 * The Class CUnit.
 * 
 * @author Tomas Lauro
 * @version 1.9.1
 * @since 1.6.0
 */
@Entity
@Table (name = "t_unit", schema = "sec")
public class CUnit extends ASecurityChangeEntity<Long>
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The pk id. */
	@Id
	@SequenceGenerator (name = "t_unit_pkid_generator", sequenceName = "sec.t_unit_pk_id_seq")
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "t_unit_pkid_generator")
	@Column (name = "pk_id")
	private Long id;

	/** The name. */
	@Column (name = "name", unique = true, nullable = false)
	private String name;

	/** The organization. */
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_organization", nullable = false)
	@Expose
	private COrganization organization;

	// bi-directional many-to-many association to CUnit
	/** The units. */
	@ManyToMany (fetch = FetchType.LAZY)
	@JoinTable (schema = "sec", name = "t_x_group_unit", joinColumns = {@JoinColumn (name = "fk_unit")}, inverseJoinColumns = {@JoinColumn (name = "fk_group")})
	private Set<CGroup> groups;

	// bi-directional many-to-many association to cross entity to group and unit
	/** Set of cross entities. */
	@OneToMany (mappedBy = "unit", fetch = FetchType.LAZY)
	private Set<CXUserUnitGroup> xUserUnitGroups;

	/** User address */
	@ManyToOne (fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	@JoinColumn (name = "fk_address", nullable = true)
	private CAddress address;

	/**
	 * Instantiates a new unit.
	 */
	public CUnit ()
	{
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
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName ()
	{
		return name;
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
		return groups;
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
	 * @return the address
	 */
	public CAddress getAddress ()
	{
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress (CAddress address)
	{
		this.address = address;
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

		if ( (object instanceof CUnit) == false)
		{
			return false;
		}

		CUnit unit = (CUnit) object;
		return ((getId() != null && getId().equals(unit.getId())) || (getId() == null && unit.getId() == null))
			&& ((getName() != null && getName().equals(unit.getName())) || (getName() == null && unit.getName() == null));
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
		if (getName() != null)
		{
			result = 31 * result + getName().hashCode();
		}
		return result;
	}
}
