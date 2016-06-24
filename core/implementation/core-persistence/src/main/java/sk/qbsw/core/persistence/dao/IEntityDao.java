package sk.qbsw.core.persistence.dao;

import java.util.List;

import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * General DAO interface for entities.
 *
 * @param <PK> Primary Key of the entity type
 * @param <T> Entity Class type
 * 
 * @author Rosenberg
 * @author Rak
 * @author Tomas Lauro
 * 
 * @version 1.6.0
 * @since 1.0.0
 */
public interface IEntityDao<PK, T extends IEntity<PK>>extends IDao, ICrudDao<PK, T>
{
	/**
	 * Finds and returns all entities.
	 *
	 * @return list of entities
	 */
	List<T> findAll ();

	/**
	 * Returns object associated to input identifier or null, if
	 * object not found.
	 *
	 * @param id input identifier
	 * @return return object
	 */
	T findById (PK id);

	/**
	 * Finds by ids.
	 *
	 * @param ids the List of entity ids
	 * @return the list
	 */
	List<T> findById (List<PK> ids);
}
