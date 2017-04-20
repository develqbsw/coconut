package sk.qbsw.core.security.dao;

import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.domain.CXUserUnitGroup;

import java.util.List;

/**
 * DAO for cross entities between user, unit and group
 * 
 * @author farkas.roman
 * @author Tomas Lauro
 * @version 1.13.0
 * @since 1.7.0
 */
public interface IXUserUnitGroupDao extends IEntityDao<Long, CXUserUnitGroup>
{
	/**
	 * Find by user and unit and group.
	 *
	 * @param user the user (optional)
	 * @param unit the unit (optional)
	 * @param group the group (optional)
	 * @return the list of records
	 */
	List<CXUserUnitGroup> findByUserAndUnitAndGroup (CUser user, CUnit unit, CGroup group);
}
