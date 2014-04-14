/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.model.domain;

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

// TODO: Auto-generated Javadoc
/**
 * The Class CRole.
 *
 * @author Dalibor Rak
 * @version 1.2.1
 * @since 1.0
 */
@Entity
@Table (name = "t_role", schema = "sec")
public class CRole extends ASecurityChangeEntity<Long>
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The pk id. */
	@Id
	@SequenceGenerator (name = "t_role_pkid_generator", sequenceName = "sec.t_role_pk_id_seq")
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "t_role_pkid_generator")
	@Column (name = "pk_id")
	private Long id;

	/** The code. */
	@Column (name = "code", unique = true)
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
	 * Instantiates a new c role.
	 *
	 * @param code the code
	 */
	public CRole (String code)
	{
		this.setCode(code);
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

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.model.domain.IEntity#getId()
	 */
	@Override
	public Long getId ()
	{
		return this.id;
	}

}
