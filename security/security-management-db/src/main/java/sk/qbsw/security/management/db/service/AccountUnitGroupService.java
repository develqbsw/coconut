package sk.qbsw.security.management.db.service;

import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.AccountUnitGroup;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Unit;

import java.util.List;

/**
 * The account unit group service.
 *
 * @author farkas.roman
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.7.0
 */
public interface AccountUnitGroupService
{
	/**
	 * Create.
	 *
	 * @param accountUnitGroup the account unit group
	 */
	AccountUnitGroup create (AccountUnitGroup accountUnitGroup);

	/**
	 * Create.
	 *
	 * @param accountUnitGroups the account unit groups
	 */
	List<AccountUnitGroup> create (List<AccountUnitGroup> accountUnitGroups);

	/**
	 * Find by account list.
	 *
	 * @param account the account
	 * @return the list
	 */
	List<AccountUnitGroup> findByAccount (Account account);

	/**
	 * Find by account and unit and group list.
	 *
	 * @param account the account
	 * @param unit the unit
	 * @param group the group
	 * @return the list
	 */
	List<AccountUnitGroup> findByAccountAndUnitAndGroup (Account account, Unit unit, Group group);

	/**
	 * Delete.
	 *
	 * @param accountUnitGroup the account unit group
	 */
	void delete (AccountUnitGroup accountUnitGroup);
}
