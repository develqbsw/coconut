package sk.qbsw.core.persistence.dao;

import java.util.List;

import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * General DAO interface for entities
 * 
 * @author rosenberg
 * @since 1.0
 * @version 1.0
 * 
 * @param <T>
 *            required entity class
 */
public interface IEntityDao<T extends IEntity> {
	/**
	 * Save new or update an existing entity
	 * 
	 * @param object
	 *            input entity
	 */
	void save(T object);

	/**
	 * Removes entity from persistent space
	 * 
	 * @param object
	 *            input entity
	 */
	void remove(T object);

	/**
	 * Finds and returns all entities
	 * 
	 * @return list of entities
	 */
	List<T> findAll();

	/**
	 * Returns object associated to input identifier or throws an exception, if
	 * object not found
	 * 
	 * @param id
	 *            input identifier
	 * @return return object
	 */
	T findById(Long id);
	
	/**
	 * Flushes all
	 */
	void flush();
}
