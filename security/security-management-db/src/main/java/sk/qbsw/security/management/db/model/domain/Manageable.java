package sk.qbsw.security.management.db.model.domain;

import sk.qbsw.security.core.model.domain.User;

import java.time.OffsetDateTime;

/**
 * The manageable interface.
 *
 * @author Michal Slezák
 * @version 2.5.0
 * @since 2.5.0
 */
public interface Manageable
{
	/**
	 * Get updated.
	 *
	 * @return the offset date time
	 */
	OffsetDateTime getUpdated();

	/**
	 * Set updated.
	 *
	 * @param offsetDateTime the offset date time
	 */
	void setUpdated(OffsetDateTime offsetDateTime);

	/**
	 * Get updated by.
	 *
	 * @return the user
	 */
	User getUpdatedBy();

	/**
	 * Set updated by.
	 *
	 * @param user the user
	 */
	void setUpdatedBy(User user);
}
