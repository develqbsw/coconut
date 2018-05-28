package sk.qbsw.security.core.dao;

import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.AccountUnitGroup;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Unit;

import java.util.List;

/**
 * DAO for cross entities between user, unit and group
 *
 * @author farkas.roman
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.7.0
 */
public interface AccountUnitGroupDao extends IEntityDao<Long, AccountUnitGroup>
{
	/**
	 * Find by user and unit and group.
	 *
	 * @param account the account
	 * @param unit the unit (optional)
	 * @param group the group (optional)
	 * @return the list of records
	 */
	List<AccountUnitGroup> findByAccountAndUnitAndGroup (Account account, Unit unit, Group group);
}
