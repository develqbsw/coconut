package sk.qbsw.core.persistence.dao;

import java.util.List;

import sk.qbsw.core.persistence.model.CFilterParameter;
import sk.qbsw.core.persistence.model.COrderParameter;
import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * The CRUD list dao interface.
 *
 * @param <PK> the primary key type
 * @param <T> the entity type
 */
public interface ICrudListDao<PK, T extends IEntity<PK>>
{
	/**
	 * Gets the CRUD list.
	 *
	 * @param wheres the wheres
	 * @param orderSpecifiers the order specifiers
	 * @param from the from
	 * @param count the count
	 * @return the CRUD list
	 */
	public List<T> getCRUDList (final List<? extends CFilterParameter> wheres, List<COrderParameter> orderSpecifiers, Long from, Long count);

	/**
	 * Gets the CRUD count.
	 *
	 * @param wheres the wheres
	 * @return the CRUD count
	 */
	public long getCRUDCount (final List<? extends CFilterParameter> wheres);
}
