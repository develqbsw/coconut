/**
 * 
 */
package sk.qbsw.core.security.dao;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * The Interface IRoleDao.
 *
 * @author rosenberg
 * @version 1.0
 * @since 1.0
 */
public interface IRoleDao extends Serializable, IEntityDao<Long, CRole>
{

	/**
	 * Find all by user.
	 *
	 * @param user the user
	 * @return the list
	 */
	public List<CRole> findAllByUser (CUser user);

	/**
	 * Find by code.
	 *
	 * @param user the user
	 * @return the list
	 */
	public List<CRole> findByCode (String code);

	/**
	 * Find by unit and code. Returns null if there is no result or more than 1 result.
	 *
	 * @param unit the unit
	 * @param code the code
	 * @return the unit or null if there is no result or more than 1 result
	 */
	public CRole findByUnitAndCode (CUnit unit, String code);
}
