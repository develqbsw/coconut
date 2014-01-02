/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao;

import java.io.Serializable;

import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.core.security.model.domain.CUnit;

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
	 * Find by name.
	 *
	 * @param name the unit name
	 * @return the unit
	 */
	public CUnit findByName (String name);

	/**
	 * Find by name and return NULL if unit not exist - NOT exception.
	 *
	 * @param name the name
	 * @return the unit or null if unit does not exist
	 */
	public CUnit findByNameNull (String name);
}
