/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.core.security.model.domain.CUnit;

/**
 * The Interface IUnitDao.
 *
 * @author Tomas Lauro
 * @version 1.7.1
 * @since 1.6.0
 */
public interface IUnitDao extends Serializable, IEntityDao<Long, CUnit>
{
	/**
	 * Find by name. Returns null if there is no result or more than 1 result.
	 *
	 * @param name the unit name
	 * @return the unit or null if there is no result or more than 1 result
	 */
	public CUnit findByName (String name);

	/**
	 * Find all units by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	public List<CUnit> findAll (Long userId);
}
