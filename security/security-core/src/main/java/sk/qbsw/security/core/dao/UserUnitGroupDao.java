package sk.qbsw.security.core.dao;

import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Unit;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.core.model.domain.UserUnitGroup;

import java.util.List;

/**
 * DAO for cross entities between user, unit and group
 * 
 * @author farkas.roman
 * @author Tomas Lauro
 * @version 1.13.0
 * @since 1.7.0
 */
public interface UserUnitGroupDao extends IEntityDao<Long, UserUnitGroup>
{
	/**
	 * Find by user and unit and group.
	 *
	 * @param user the user (optional)
	 * @param unit the unit (optional)
	 * @param group the group (optional)
	 * @return the list of records
	 */
	List<UserUnitGroup> findByUserAndUnitAndGroup (User user, Unit unit, Group group);
}
