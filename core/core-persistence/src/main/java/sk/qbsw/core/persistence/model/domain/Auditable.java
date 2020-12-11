package sk.qbsw.core.persistence.model.domain;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

/**
 * The auditable.
 *
 * @param <U> the generic type
 * @param <P> the generic type
 *
 * @author Tomas Leken
 * @version 2.6.0
 * @since 2.6.0
 */
@MappedSuperclass
@EntityListeners (AuditingEntityListener.class)
public abstract class Auditable<U, P> extends AEntity<P> {

	private static final long serialVersionUID = -70093079091434576L;

	/**
	 * The Created.
	 */
	@CreatedDate
	@NotNull
	@Column (name = "c_created", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	private OffsetDateTime created;

	/**
	 * The Created by.
	 */
	@NotNull
	@CreatedBy
	@Column (name = "c_created_by")
	private U createdBy;

	/**
	 * The Updated.
	 */
	@LastModifiedDate
	@NotNull
	@Column (name = "c_updated", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	private OffsetDateTime updated;

	/**
	 * The Updated by.
	 */
	@NotNull
	@LastModifiedBy
	@Column (name = "c_updated_by")
	private U updatedBy;

	/**
	 * Gets created.
	 *
	 * @return the created
	 */
	public OffsetDateTime getCreated() {
		return created;
	}

	/**
	 * Sets created.
	 *
	 * @param created the created
	 */
	public void setCreated(OffsetDateTime created) {
		this.created = created;
	}

	/**
	 * Gets created by.
	 *
	 * @return the created by
	 */
	public U getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets created by.
	 *
	 * @param createdBy the created by
	 */
	public void setCreatedBy(U createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets updated.
	 *
	 * @return the updated
	 */
	public OffsetDateTime getUpdated() {
		return updated;
	}

	/**
	 * Sets updated.
	 *
	 * @param updated the updated
	 */
	public void setUpdated(OffsetDateTime updated) {
		this.updated = updated;
	}

	/**
	 * Gets updated by.
	 *
	 * @return the updated by
	 */
	public U getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * Sets updated by.
	 *
	 * @param updatedBy the updated by
	 */
	public void setUpdatedBy(U updatedBy) {
		this.updatedBy = updatedBy;
	}
}
