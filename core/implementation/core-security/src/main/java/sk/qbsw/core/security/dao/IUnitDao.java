/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * The Interface IUnitDao.
 *
 * @author Tomas Lauro
 * @version 1.6.0
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
	 * find all units by user
	 * 
	 * @param user
	 * @return
	 */
	List<CUnit> findAllByUser (CUser user);
}
