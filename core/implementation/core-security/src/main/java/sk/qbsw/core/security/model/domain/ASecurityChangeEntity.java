package sk.qbsw.core.security.model.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * Abstract entity which add changeDateTime value which is date and time of last change
 * changeDateTime is automatically set when is entity updated.
 *
 * @param <T> the generic type
 *
 * @author Michal Lacko
 * @author Tomas Lauro
 * 
 * @version 1.12.0
 * @since 1.8.0
 */
@MappedSuperclass
public abstract class ASecurityChangeEntity<T> implements Serializable, IEntity<T>
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** date and time of last entity change. */
	@Column (name = "c_change_date_time", nullable = false)
	@Type (type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime changeDateTime;

	/** The user who changed the entity last time. */
	@OneToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_changed_by")
	private CUser changedBy;

	/** The operation id. */
	@Column (name = "c_operation_id")
	private Long operationId;

	/**
	 * Instantiates a new a security change entity.
	 */
	public ASecurityChangeEntity ()
	{
		super();
	}

	/**
	 * On create.
	 */
	@PrePersist
	protected void onCreate ()
	{
		this.changeDateTime = new DateTime();
	}

	/**
	 * On update.
	 */
	@PreUpdate
	protected void onUpdate ()
	{
		this.changeDateTime = new DateTime();
	}

	/**
	 * Sets the change date time.
	 *
	 * @param changeDateTime the new change date time
	 */
	protected void setChangeDateTime (DateTime changeDateTime)
	{
		this.changeDateTime = changeDateTime;
	}

	/**
	 * Gets the change date time.
	 *
	 * @return the change date time
	 */
	public DateTime getChangeDateTime ()
	{
		return this.changeDateTime;
	}

	/**
	 * Update changeDateTime to current time.
	 */
	public void updateChangeDateTime ()
	{
		this.changeDateTime = new DateTime();
	}

	/**
	 * Gets the changed by.
	 *
	 * @return the changed by
	 */
	public CUser getChangedBy ()
	{
		return changedBy;
	}

	/**
	 * Sets the changed by.
	 *
	 * @param changedBy the new changed by
	 */
	public void setChangedBy (CUser changedBy)
	{
		this.changedBy = changedBy;
	}

	/**
	 * Gets the operation id.
	 *
	 * @return the operation id
	 */
	public Long getOperationId ()
	{
		return operationId;
	}

	/**
	 * Sets the operation id.
	 *
	 * @param operationId the new operation id
	 */
	public void setOperationId (Long operationId)
	{
		this.operationId = operationId;
	}
}
