package sk.qbsw.security.management.db.service;

import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.core.security.base.model.AccountInputData;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AuthenticationParamsDao;
import sk.qbsw.security.core.dao.OrganizationDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.AccountInputDataMapper;
import sk.qbsw.security.core.service.mapper.AccountOutputDataMapper;
import sk.qbsw.security.management.base.service.AccountManagementServiceBase;
import sk.qbsw.security.management.service.AccountCredentialManagementService;
import sk.qbsw.security.management.service.AccountManagementService;

import java.util.List;

/**
 * The account management service.
 *
 * @author Dalibor Rak
 * @author Tomas Leken
 * @author Michal Lacko
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.0.0
 */
public class AccountManagementServiceImpl extends AccountManagementServiceBase<AccountInputData, AccountData, Account> implements AccountManagementService<AccountInputData, AccountData>
{
	/**
	 * Instantiates a new account management service.
	 *
	 * @param accountDao the account dao
	 * @param organizationDao the organization dao
	 * @param authenticationParamsDao the authentication params dao
	 * @param authenticationService the authentication service
	 * @param accountAccountInputDataMapper the account account input data mapper
	 * @param accountOutputDataMapper the account output data mapper
	 */
	public AccountManagementServiceImpl (AccountDao<Account> accountDao, OrganizationDao organizationDao, AuthenticationParamsDao authenticationParamsDao, AccountCredentialManagementService authenticationService, AccountInputDataMapper<AccountInputData, Account> accountAccountInputDataMapper, AccountOutputDataMapper<AccountData, Account> accountOutputDataMapper)
	{
		super(accountDao, organizationDao, authenticationParamsDao, authenticationService, accountAccountInputDataMapper, accountOutputDataMapper);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public AccountData register (AccountInputData accountInputData, String password) throws CSecurityException
	{
		return super.registerBase(accountInputData, password);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public AccountData register (AccountInputData accountInputData) throws CSecurityException
	{
		return super.registerBase(accountInputData);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public AccountData findOneById (Long id)
	{
		return super.findOneByIdBase(id);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public AccountData findOneByLogin (String login) throws CSecurityException
	{
		return super.findOneByLoginBase(login);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public AccountData findOneByUid (String uid)
	{
		return super.findOneByUidBase(uid);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public AccountData findOneByLoginAndState (String login, ActivityStates state)
	{
		return super.findOneByLoginAndStateBase(login, state);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public AccountData findOneByLoginAndStateAndGroupCodePrefix (String login, ActivityStates state, String groupCodePrefix)
	{
		return super.findOneByLoginAndStateAndGroupCodePrefixBase(login, state, groupCodePrefix);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<AccountData> findByEmail (String email)
	{
		return super.findByEmailBase(email);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<AccountData> findAll ()
	{
		return super.findAllBase();
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public boolean existsByLogin (String login)
	{
		return super.existsByLoginBase(login);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public boolean existsByPin (String pin)
	{
		return super.existsByPinBase(pin);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public AccountData update (AccountInputData accountInputData)
	{
		return super.updateBase(accountInputData);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public AccountData activate (Long id)
	{
		return super.activateBase(id);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public AccountData inactivate (Long id)
	{
		return super.inactivateBase(id);
	}
}
