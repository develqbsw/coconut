package sk.qbsw.security.management.db.service;

import sk.qbsw.security.core.model.domain.AccountUnitGroup;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Unit;

import java.util.List;

/**
 * The account unit group service.
 *
 * @author farkas.roman
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 1.7.0
 */
public interface AccountUnitGroupService
{
	/**
	 * Create.
	 *
	 * @param accountUnitGroup the account unit group
	 * @return the account unit group
	 */
	AccountUnitGroup create (AccountUnitGroup accountUnitGroup);

	/**
	 * Create.
	 *
	 * @param accountUnitGroups the account unit groups
	 * @return the list
	 */
	List<AccountUnitGroup> create (List<AccountUnitGroup> accountUnitGroups);

	/**
	 * Find by account list.
	 *
	 * @param accountId the account id
	 * @return the list
	 */
	List<AccountUnitGroup> findByAccountId (Long accountId);

	/**
	 * Find by account and unit and group list.
	 *
	 * @param accountId the account id
	 * @param unit the unit
	 * @param group the group
	 * @return the list
	 */
	List<AccountUnitGroup> findByAccountIdAndUnitAndGroup (Long accountId, Unit unit, Group group);

	/**
	 * Delete.
	 *
	 * @param accountUnitGroup the account unit group
	 */
	void delete (AccountUnitGroup accountUnitGroup);
}
