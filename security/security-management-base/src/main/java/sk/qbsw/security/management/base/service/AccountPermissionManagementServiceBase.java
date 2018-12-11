package sk.qbsw.security.management.base.service;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AccountUnitGroupDao;
import sk.qbsw.security.core.dao.GroupDao;
import sk.qbsw.security.core.dao.UnitDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.AccountUnitGroup;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Unit;

import java.util.List;
import java.util.Set;

/**
 * Account permission management service base.
 *
 * @param <A> the type parameter
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @author farkas.roman
 * @version 2.1.0
 * @since 1.0.0
 */
public abstract class AccountPermissionManagementServiceBase<A extends Account>extends AService
{
	/**
	 * The Account dao.
	 */
	protected final AccountDao<A> accountDao;

	/**
	 * The Group dao.
	 */
	protected final GroupDao groupDao;

	/**
	 * The Unit dao.
	 */
	protected final UnitDao unitDao;

	/**
	 * The Account unit group dao.
	 */
	protected final AccountUnitGroupDao accountUnitGroupDao;

	/**
	 * Instantiates a new Account permission management service.
	 *
	 * @param accountDao the account dao
	 * @param groupDao the group dao
	 * @param unitDao the unit dao
	 * @param accountUnitGroupDao the account unit group dao
	 */
	public AccountPermissionManagementServiceBase (AccountDao<A> accountDao, GroupDao groupDao, UnitDao unitDao, AccountUnitGroupDao accountUnitGroupDao)
	{
		this.accountDao = accountDao;
		this.groupDao = groupDao;
		this.unitDao = unitDao;
		this.accountUnitGroupDao = accountUnitGroupDao;
	}

	/**
	 * Assign account to group.
	 *
	 * @param login the login
	 * @param groupCode the group code
	 * @param unitName the unit name
	 * @throws CBusinessException the c business exception
	 */
	protected void assignAccountToGroupBase (String login, String groupCode, String unitName) throws CBusinessException
	{
		if (login == null || groupCode == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		// read persisted objects
		A account = accountDao.findOneByLogin(login);
		Group group = groupDao.findOneByCode(groupCode);
		Unit unit = null;

		if (unitName != null)
		{
			unit = unitDao.findOneByName(unitName);
		}

		assignAccountToGroup(account, group, unit);
	}

	/**
	 * Assign account to group.
	 *
	 * @param login the login
	 * @param groupCode the group code
	 * @throws CBusinessException the c business exception
	 */
	protected void assignAccountToGroupBase (String login, String groupCode) throws CBusinessException
	{
		assignAccountToGroupBase(login, groupCode, null);
	}

	private void assignAccountToGroup (A account, Group group, Unit unit) throws CBusinessException
	{
		// validates input objects
		if (!Account.isKnown(account) || !Group.isKnown(group))
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		if (unit != null && unit.getId() == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		boolean canBeAdded = canBeGroupUnitAssignedToAccount(account, group, unit);
		if (canBeAdded)
		{
			AccountUnitGroup accountUnitGroup = new AccountUnitGroup();
			accountUnitGroup.setAccount(account);
			accountUnitGroup.setGroup(group);
			accountUnitGroup.setUnit(unit);
			accountUnitGroupDao.update(accountUnitGroup);
		}
		else
		{
			throw new CBusinessException("The group " + group.getCode() + " cannot be set to account: it's excluded or already has been set to account.");
		}
	}

	private boolean canBeGroupUnitAssignedToAccount (A account, Group group, Unit unit)
	{
		// find all groups assigned to account
		List<AccountUnitGroup> accountUnitGroups = accountUnitGroupDao.findByAccountIdAndUnitAndGroup(account.getId(), unit, null);

		for (AccountUnitGroup accountUnitGroup : accountUnitGroups)
		{
			if (group.equals(accountUnitGroup.getGroup()))
			{
				// if is group already added
				return false;
			}

			// if added group is excluded by group which account already have then group cannot be added
			Set<Group> excludedGroups = accountUnitGroup.getGroup().getExcludedGroups();
			if (excludedGroups != null && excludedGroups.contains(group))
			{
				return false;
			}
		}
		// if is not combination account group unit already added
		// or if existing group assigned to account not exclude added group
		return true;
	}

	/**
	 * Unassign account from group.
	 *
	 * @param login the login
	 * @param groupCode the group code
	 * @param unitName the unit name
	 * @throws CSecurityException the c security exception
	 */
	protected void unassignAccountFromGroupBase (String login, String groupCode, String unitName) throws CSecurityException
	{
		if (login == null || groupCode == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		// read persisted objects
		A account = accountDao.findOneByLogin(login);
		Group group = groupDao.findOneByCode(groupCode);
		Unit unit = null;

		if (unitName != null)
		{
			unit = unitDao.findOneByName(unitName);
		}

		unassignAccountFromGroup(account, group, unit);

	}

	/**
	 * Unassign account from group.
	 *
	 * @param login the login
	 * @param groupCode the group code
	 * @throws CSecurityException the c security exception
	 */
	protected void unassignAccountFromGroupBase (String login, String groupCode) throws CSecurityException
	{
		unassignAccountFromGroupBase(login, groupCode, null);
	}

	private void unassignAccountFromGroup (A account, Group group, Unit unit) throws CSecurityException
	{
		// validates input objects
		if (!Account.isKnown(account) || !Group.isKnown(group))
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		if (unit != null && unit.getId() == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		removeAccountUnitGroup(account, group, unit);
	}

	private void removeAccountUnitGroup (A account, Group group, Unit unit)
	{
		// find account <-> group mapping records - the list should contains only one record, but the method handles the case if not
		List<AccountUnitGroup> accountUnitGroups = accountUnitGroupDao.findByAccountIdAndUnitAndGroup(account.getId(), unit, group);

		// delete records
		for (AccountUnitGroup accountUnitGroup : accountUnitGroups)
		{
			account.getAccountUnitGroups().removeIf(r -> r.getId().equals(accountUnitGroup.getId()));
			accountUnitGroupDao.remove(accountUnitGroup);
		}
	}
}
