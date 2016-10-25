package sk.qbsw.core.persistence.model.domain;

import java.time.OffsetDateTime;

/**
 * The updateable entity.
 *
 * @version 1.16.0
 * @since 1.16.0
 * 
 * @param <PK> the primary key type
 */
public interface IUpdateableEntity<PK>extends IEntity<PK>
{
	/**
	 * Gets the updated.
	 *
	 * @return the updated
	 */
	OffsetDateTime getUpdated ();
}
