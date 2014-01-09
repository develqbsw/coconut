/**
 * 
 */
package sk.qbsw.core.security.dao;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.CUnit;

/**
 * The Interface IGroupDao.
 *
 * @author rosenberg
 * @author lacko
 * @version 1.0
 * @since 1.0
 */
public interface IGroupDao extends Serializable, IEntityDao<Long, CGroup>
{

	/** The I d_ or g_ admin. */
	public static Long ID_ORG_ADMIN = 2l;

	/** The I d_ sy s_ admin. */
	public static Long ID_SYS_ADMIN = 1l;

	/**
	 * Find all by flag system.
	 *
	 * @param flagSystem the flag system
	 * @return the list
	 */
	public List<CGroup> findAllByFlagSystem (boolean flagSystem);
	
	/**
	 * Find groups by code
	 *
	 * @return the list
	 */
	public List<CGroup> findByCode(String code);
	
	/**
	 * Find groups by code and unit. The roles and units for group are fetched.
	 * 
	 * @param code the code of requested group
	 * @param unit the unit of group
	 *
	 * @return the list of groups
	 */
	public List<CGroup> findByCode(String code, CUnit unit);
}
