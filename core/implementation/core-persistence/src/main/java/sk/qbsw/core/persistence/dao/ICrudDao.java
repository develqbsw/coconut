package sk.qbsw.core.persistence.dao;

import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * The Interface ICrudDao.
 *
 * @param <PK> the generic type
 * @param <T> the generic type
 */
public interface ICrudDao<PK, T extends IEntity<PK>> extends IDao {

	/**
	 * Creates the.
	 *
	 * @param e the e
	 * @return the pk
	 */
	public PK create(T e);

	/**
	 * Returns object associated to input identifier or null, if
	 * object not found.
	 *
	 * @param id input identifier
	 * @return return object
	 */
	public T read(PK id);

	/**
	 * Updates.
	 *
	 * @param e the e
	 * @return the t
	 */
	public T update(T e);

	/**
	 * Removes entity from persistent space.
	 *
	 * @param object input entity
	 */
	public void remove(T object);

	/**
	 * Invalidate.
	 *
	 * @param e the e
	 */
	public void invalidate(T e);

	/**
	 * Validate.
	 *
	 * @param e the e
	 */
	public void validate(T e);

}
