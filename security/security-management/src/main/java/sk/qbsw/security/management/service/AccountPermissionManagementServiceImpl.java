package sk.qbsw.security.management.service;

import org.springframework.transaction.annotation.Transactional;
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
 * Authentication service.
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @author farkas.roman
 * @version 1.19.0
 * @since 1.0.0
 */
public class AccountPermissionManagementServiceImpl extends AService implements AccountPermissionManagementService
{
	private final AccountDao accountDao;

	private final GroupDao groupDao;

	private final UnitDao unitDao;

	private final AccountUnitGroupDao accountUnitGroupDao;

	/**
	 * Instantiates a new Account permission management service.
	 *
	 * @param accountDao the account dao
	 * @param groupDao the group dao
	 * @param unitDao the unit dao
	 * @param accountUnitGroupDao the account unit group dao
	 */
	public AccountPermissionManagementServiceImpl (AccountDao accountDao, GroupDao groupDao, UnitDao unitDao, AccountUnitGroupDao accountUnitGroupDao)
	{
		this.accountDao = accountDao;
		this.groupDao = groupDao;
		this.unitDao = unitDao;
		this.accountUnitGroupDao = accountUnitGroupDao;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void setAccountToGroup (Long accountId, Long groupId, Long unitId) throws CBusinessException
	{
		if (accountId == null || groupId == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		// read persisted objects
		Account account = accountDao.findById(accountId);
		Group group = groupDao.findById(groupId);
		Unit unit = null;

		if (unitId != null)
		{
			unit = unitDao.findById(unitId);
		}

		// call method with logic
		if (unit != null)
		{
			setAccountToGroup(account, group, unit);
		}
		else
		{
			setAccountToGroup(account, group);
		}

	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void setAccountToGroup (Long accountId, Long groupId) throws CBusinessException
	{
		setAccountToGroup(accountId, groupId, null);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void setAccountToGroup (Account account, Group group, Unit unit) throws CBusinessException
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

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void setAccountToGroup (Account account, Group group) throws CBusinessException
	{
		setAccountToGroup(account, group, null);
	}

	private boolean canBeGroupUnitAssignedToAccount (Account account, Group group, Unit unit)
	{
		// find all groups assigned to account
		List<AccountUnitGroup> accountUnitGroups = accountUnitGroupDao.findByAccountAndUnitAndGroup(account, unit, null);

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

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void unsetAccountFromGroup (Long accountId, Long groupId, Long unitId) throws CSecurityException
	{
		if (accountId == null || groupId == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		// read persisted objects
		Account account = accountDao.findById(accountId);
		Group group = groupDao.findById(groupId);
		Unit unit = null;

		if (unitId != null)
		{
			unit = unitDao.findById(unitId);
		}

		// call method with logic
		if (unit != null)
		{
			unsetAccountFromGroup(account, group, unit);
		}
		else
		{
			unsetAccountFromGroup(account, group);
		}
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void unsetAccountFromGroup (Long accountId, Long groupId) throws CSecurityException
	{
		unsetAccountFromGroup(accountId, groupId, null);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void unsetAccountFromGroup (Account account, Group group, Unit unit) throws CSecurityException
	{
		// validates input objects
		if (account == null || account.getId() == null || group == null || group.getId() == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		if (unit != null && unit.getId() == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		removeAccountUnitGroup(account, group, unit);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void unsetAccountFromGroup (Account account, Group group) throws CSecurityException
	{
		unsetAccountFromGroup(account, group, null);
	}

	private void removeAccountUnitGroup (Account account, Group group, Unit unit)
	{
		// find account <-> group mapping records - the list should contains only one record, but the method handles the case if not
		List<AccountUnitGroup> accountUnitGroups = accountUnitGroupDao.findByAccountAndUnitAndGroup(account, unit, group);

		// delete records
		for (AccountUnitGroup accountUnitGroup : accountUnitGroups)
		{
			account.getAccountUnitGroups().removeIf(r -> r.getId().equals(accountUnitGroup.getId()));
			accountUnitGroupDao.remove(accountUnitGroup);
		}
	}
}
