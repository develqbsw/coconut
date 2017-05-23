/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.model.domain;

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

/**
 * Cross entity for user unit and group.
 *
 * @author farkas.roman
 * @author Tomas Lauro
 * @version 1.9.1
 * @since 1.6.4
 */
@Entity
@Table (name = "t_x_group_user", schema = "sec")
public class CXUserUnitGroup extends ASecurityChangeEntity<Long>
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The pk id. */
	@Id
	@SequenceGenerator (name = "t_x_group_user_pkid_generator", sequenceName = "sec.t_x_group_user_pk_id_seq")
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "t_x_group_user_pkid_generator")
	@Column (name = "pk_id")
	private Long id;

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
		return this.id;
	}
	
	/**
	 * Sets id
	 * 
	 * @param id
	 */
	public void setId (Long id)
	{
		this.id = id;
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals (Object obj)
	{
		if (obj == this)
		{
			return true;
		}

		if ( (obj instanceof CXUserUnitGroup) == false)
		{
			return false;
		}

		CXUserUnitGroup userGroupUnitRecord = (CXUserUnitGroup) obj;
		return ((getId() != null && getId().equals(userGroupUnitRecord.getId())) || (getId() == null && userGroupUnitRecord.getId() == null))
			&& ((getUser() != null && getUser().equals(userGroupUnitRecord.getUser())) || (getUser() == null && userGroupUnitRecord.getUser() == null))
			&& ((getUnit() != null && getUnit().equals(userGroupUnitRecord.getUnit())) || (getUnit() == null && userGroupUnitRecord.getUnit() == null))
			&& ((getGroup() != null && getGroup().equals(userGroupUnitRecord.getGroup())) || (getGroup() == null && userGroupUnitRecord.getGroup() == null));
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
		if (getUser() != null)
		{
			result = 31 * result + getUser().hashCode();
		}
		if (getUnit() != null)
		{
			result = 31 * result + getUnit().hashCode();
		}
		if (getGroup() != null)
		{
			result = 31 * result + getGroup().hashCode();
		}
		return result;
	}
}
