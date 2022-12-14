package sk.qbsw.security.management.db.service;

import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AccountUnitGroupDao;
import sk.qbsw.security.core.dao.GroupDao;
import sk.qbsw.security.core.dao.UnitDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.management.base.service.AccountPermissionManagementServiceBase;
import sk.qbsw.security.management.service.AccountPermissionManagementService;

/**
 * Account permission management service implementation.
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @author farkas.roman
 * @version 2.0.0
 * @since 1.0.0
 */
public class AccountPermissionManagementServiceImpl extends AccountPermissionManagementServiceBase<Account> implements AccountPermissionManagementService
{
	/**
	 * Instantiates a new Account permission management service.
	 *
	 * @param accountDao the account dao
	 * @param groupDao the group dao
	 * @param unitDao the unit dao
	 * @param accountUnitGroupDao the account unit group dao
	 */
	public AccountPermissionManagementServiceImpl (AccountDao<Account> accountDao, GroupDao groupDao, UnitDao unitDao, AccountUnitGroupDao accountUnitGroupDao)
	{
		super(accountDao, groupDao, unitDao, accountUnitGroupDao);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void assignAccountToGroup (String login, String groupCode, String unitName) throws CBusinessException
	{
		super.assignAccountToGroupBase(login, groupCode, unitName);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void assignAccountToGroup (String login, String groupCode) throws CBusinessException
	{
		super.assignAccountToGroupBase(login, groupCode, null);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void unassignAccountFromGroup (String login, String groupCode, String unitName) throws CSecurityException
	{
		super.unassignAccountFromGroupBase(login, groupCode, unitName);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void unassignAccountFromGroup (String login, String groupCode) throws CSecurityException
	{
		super.unassignAccountFromGroupBase(login, groupCode, null);
	}
}
