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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * The Class CRole.
 *
 * @author Dalibor Rak
 * @version 1.2.1
 * @since 1.0
 */
@Entity
@Table (name = "t_role", schema = "sec")
public class CRole implements Serializable, IEntity
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The pk id. */
	@Id
	@SequenceGenerator (name = "T_ROLE_PKID_GENERATOR", sequenceName = "SEC.T_ROLE_PK_ID_SEQ")
	@GeneratedValue (strategy = GenerationType.AUTO, generator = "T_ROLE_PKID_GENERATOR")
	@Column (name = "pk_id")
	private Long pkId;

	/** The code. */
	private String code;

	//bi-directional many-to-many association to CGroup
	/** The groups. */
	@ManyToMany (fetch = FetchType.LAZY)
	@JoinTable (schema = "sec", name = "t_x_group_role", joinColumns = {@JoinColumn (name = "fk_role")}, inverseJoinColumns = {@JoinColumn (name = "fk_group")})
	private Set<CGroup> groups;

	/**
	 * Instantiates a new c role.
	 */
	public CRole ()
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
	 * @param groups the new groups
	 */
	public void setGroups (Set<CGroup> groups)
	{
		this.groups = groups;
	}
	
	@Override
	public Long getId ()
	{
		return getPkId();
	}

}
