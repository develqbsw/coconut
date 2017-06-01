package sk.qbsw.security.core.model.domain;

import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.Type;

import sk.qbsw.core.persistence.model.domain.AEntity;

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
public abstract class BaseSecurityChangeEntity<T>extends AEntity<T> implements Serializable
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** date and time of last entity change. */
	@Column (name = "c_change_date_time", nullable = false)
	@Type (type = "org.hibernate.type.OffsetDateTimeType")
	private OffsetDateTime changeDateTime;

	/** The user who changed the entity last time. */
	@OneToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_changed_by")
	private User changedBy;

	/** The operation id. */
	@Column (name = "c_operation_id")
	private Long operationId;

	/**
	 * Instantiates a new a security change entity.
	 */
	public BaseSecurityChangeEntity ()
	{
		super();
	}

	/**
	 * On create.
	 */
	@PrePersist
	protected void onCreate ()
	{
		this.changeDateTime = OffsetDateTime.now();
	}

	/**
	 * On update.
	 */
	@PreUpdate
	protected void onUpdate ()
	{
		this.changeDateTime = OffsetDateTime.now();
	}

	/**
	 * Sets the change date time.
	 *
	 * @param changeDateTime the new change date time
	 */
	protected void setChangeDateTime (OffsetDateTime changeDateTime)
	{
		this.changeDateTime = changeDateTime;
	}

	/**
	 * Gets the change date time.
	 *
	 * @return the change date time
	 */
	public OffsetDateTime getChangeDateTime ()
	{
		return this.changeDateTime;
	}

	/**
	 * Update changeDateTime to current time.
	 */
	public void updateChangeDateTime ()
	{
		this.changeDateTime = OffsetDateTime.now();
	}

	/**
	 * Gets the changed by.
	 *
	 * @return the changed by
	 */
	public User getChangedBy ()
	{
		return changedBy;
	}

	/**
	 * Sets the changed by.
	 *
	 * @param changedBy the new changed by
	 */
	public void setChangedBy (User changedBy)
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
