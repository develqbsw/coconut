package sk.qbsw.security.organization.simple.management.db.service;

import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.core.security.base.model.AccountDataTypes;
import sk.qbsw.core.security.base.model.AccountInputData;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AuthenticationParamsDao;
import sk.qbsw.security.core.dao.OrganizationDao;
import sk.qbsw.security.core.dao.UserDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.AccountInputDataMapper;
import sk.qbsw.security.core.service.mapper.AccountOutputDataMapper;
import sk.qbsw.security.management.service.AccountCredentialManagementService;
import sk.qbsw.security.organization.simple.management.SPOAccountManagementService;

import java.util.List;

/**
 * The simple organization account management service.
 *
 * @author Tomas Lauro
 * @author Tomas Leken
 * @version 2.2.0
 * @since 2.0.0
 */
public class SPOAccountManagementServiceImpl extends SPOAccountManagementServiceBase<AccountInputData, AccountData, Account> implements SPOAccountManagementService<AccountInputData, AccountData>
{
	/**
	 * Instantiates a new Simple organization account management service.
	 *
	 * @param accountDao the account dao
	 * @param organizationDao the organization dao
	 * @param authenticationParamsDao the authentication params dao
	 * @param authenticationService the authentication service
	 * @param accountAccountInputDataMapper the account account input data mapper
	 * @param accountOutputDataMapper the account output data mapper
	 */
	public SPOAccountManagementServiceImpl (AccountDao<Account> accountDao, UserDao userDao, OrganizationDao organizationDao, AuthenticationParamsDao authenticationParamsDao, AccountCredentialManagementService authenticationService, AccountInputDataMapper<AccountInputData, Account> accountAccountInputDataMapper, AccountOutputDataMapper<AccountData, Account> accountOutputDataMapper)
	{
		super(accountDao, userDao, organizationDao, authenticationParamsDao, authenticationService, accountAccountInputDataMapper, accountOutputDataMapper);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public AccountData register (AccountInputData accountInputData, String password) throws CBusinessException
	{
		return super.registerBase(accountInputData, password);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public AccountData register (AccountInputData accountInputData) throws CBusinessException
	{
		return super.registerBase(accountInputData);
	}

	@Override
	protected void validateRegisterInput (AccountInputData accountInputData) throws CSecurityException
	{
		super.validateRegisterInput(accountInputData);

		if (accountInputData.getUser() == null)
		{
			throw new CSecurityException(ECoreErrorResponse.ACCOUNT_NOT_ALL_PARAMETERS);
		}
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

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public AccountData findOneByOrganizationCodeAndLoginAndState (String organizationCode, String login, ActivityStates state)
	{
		return super.findOneByOrganizationCodeAndLoginAndStateBase(organizationCode, login, state);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<AccountData> findByOrganizationCode (String organizationCode)
	{
		return super.findByOrganizationCodeBase(organizationCode);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<AccountData> findByOrganizationCodeAndGroupCodeAndNotLogin (String organizationCode, String groupCode, String login)
	{
		return super.findByOrganizationCodeAndGroupCodeAndNotLoginBase(organizationCode, groupCode, login);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<AccountData> findByOrganizationCodeAndState (String organizationCode, ActivityStates state)
	{
		return super.findByOrganizationCodeAndStateBase(organizationCode, state);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<AccountData> findByOrganizationCodeAndStateAndGroupCode (String organizationCode, ActivityStates state, String groupCode)
	{
		return super.findByOrganizationCodeAndStateAndGroupCodeBase(organizationCode, state, groupCode);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<AccountData> findByOrganizationCodeAndStateAndGroupCodeOrderByOrganizationNameAndLogin (String organizationCode, ActivityStates state, String groupCode)
	{
		return super.findByOrganizationCodeAndStateAndGroupCodeOrderByOrganizationNameAndLoginBase(organizationCode, state, groupCode);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<AccountData> findByOrganizationCodeAndRoleCode (String organizationCode, String roleCode)
	{
		return super.findByOrganizationCodeAndRoleCodeBase(organizationCode, roleCode);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<AccountData> findByTypeAndOrganizationCode (AccountDataTypes type, String organizationCode)
	{
		return super.findByTypeAndOrganizationCode(type, organizationCode);
	}
}
