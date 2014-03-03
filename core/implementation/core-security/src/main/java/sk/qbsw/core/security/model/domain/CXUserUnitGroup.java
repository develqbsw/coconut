/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.model.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * Cross entity for user unit and group.
 *
 * @author farkas.roman
 * @version 1.6.4
 * @since 1.6.4
 */
@Entity
@Table (name = "t_x_group_user", schema = "sec")
public class CXUserUnitGroup implements Serializable, IEntity<Long>
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The pk id. */
	@Id
	@SequenceGenerator (name = "t_x_group_user_pkid_generator", sequenceName = "sec.t_x_group_user_pk_id_seq")
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "t_x_group_user_pkid_generator")
	@Column (name = "pk_id")
	private Long pkId;

	/** The user. */
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_user", nullable = false)
	private CUser user;

	/** The unit. */
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_unit", nullable = true)
	private CUnit unit;

	/** The group. */
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_group", nullable = false)
	private CGroup group;

	/**
	 * Instantiates a new c group.
	 */
	public CXUserUnitGroup ()
	{
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.model.domain.IEntity#getId()
	 */
	@Override
	public Long getId ()
	{
		return pkId;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public CUser getUser ()
	{
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the user to set
	 */
	public void setUser (CUser user)
	{
		this.user = user;
	}

	/**
	 * Gets the unit.
	 *
	 * @return the unit
	 */
	public CUnit getUnit ()
	{
		return unit;
	}

	/**
	 * Sets the unit.
	 *
	 * @param unit the unit to set
	 */
	public void setUnit (CUnit unit)
	{
		this.unit = unit;
	}

	/**
	 * Gets the group.
	 *
	 * @return the group
	 */
	public CGroup getGroup ()
	{
		return group;
	}

	/**
	 * Sets the group.
	 *
	 * @param group the group to set
	 */
	public void setGroup (CGroup group)
	{
		this.group = group;
	}
}
