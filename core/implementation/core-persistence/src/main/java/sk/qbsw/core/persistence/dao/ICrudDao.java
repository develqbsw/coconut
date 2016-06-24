package sk.qbsw.core.persistence.dao;

import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * The crud dao interface.
 *
 * @param <PK> the primary key
 * @param <T> the entity type
 */
public interface ICrudDao<PK, T extends IEntity<PK>>extends IDao
{
	/**
	 * Creates the.
	 *
	 * @param e the e
	 * @return the pk
	 */
	PK create (T e);

	/**
	 * Returns object associated to input identifier or null, if
	 * object not found.
	 *
	 * @param id input identifier
	 * @return return object
	 */
	T read (PK id);

	/**
	 * Updates.
	 *
	 * @param e the e
	 * @return the t
	 */
	T update (T e);

	/**
	 * Removes entity from persistent space.
	 *
	 * @param object input entity
	 */
	void remove (T object);

	/**
	 * Invalidate.
	 *
	 * @param e the e
	 */
	void invalidate (T e);

	/**
	 * Validate.
	 *
	 * @param e the e
	 */
	void validate (T e);
}
