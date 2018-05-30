package sk.qbsw.security.management.service;

import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.security.core.dao.AccountUnitGroupDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.AccountUnitGroup;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Unit;

import java.util.ArrayList;
import java.util.List;

/**
 * The account unit group implementation.
 *
 * @author farkas.roman
 * @version 1.19.0
 * @since 1.7.0
 */
public class AccountUnitGroupServiceImpl extends AService implements AccountUnitGroupService
{
	private final AccountUnitGroupDao accountUnitGroupDao;

	/**
	 * Instantiates a new Account unit group service.
	 *
	 * @param accountUnitGroupDao the account unit group dao
	 */
	public AccountUnitGroupServiceImpl (AccountUnitGroupDao accountUnitGroupDao)
	{
		this.accountUnitGroupDao = accountUnitGroupDao;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public AccountUnitGroup create (AccountUnitGroup accountUnitGroup)
	{
		return accountUnitGroupDao.update(accountUnitGroup);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<AccountUnitGroup> create (List<AccountUnitGroup> accountUnitGroups)
	{
		List<AccountUnitGroup> createdAccountUnitGroups = new ArrayList<>();
		for (AccountUnitGroup accountUnitGroup : accountUnitGroups)
		{
			createdAccountUnitGroups.add(accountUnitGroupDao.update(accountUnitGroup));
		}

		return createdAccountUnitGroups;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<AccountUnitGroup> findByAccount (Account account)
	{
		return findByAccountAndUnitAndGroup(account, null, null);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<AccountUnitGroup> findByAccountAndUnitAndGroup (Account account, Unit unit, Group group)
	{
		return accountUnitGroupDao.findByAccountAndUnitAndGroup(account, unit, group);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void delete (AccountUnitGroup accountUnitGroup)
	{
		if (accountUnitGroup == null || accountUnitGroup.getId() == null)
		{
			throw new IllegalArgumentException("input object to delete cannot be null and must have id");
		}

		accountUnitGroupDao.remove(accountUnitGroupDao.findById(accountUnitGroup.getId()));
	}
}
