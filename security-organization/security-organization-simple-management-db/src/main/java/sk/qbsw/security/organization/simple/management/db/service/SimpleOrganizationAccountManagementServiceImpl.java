package sk.qbsw.security.organization.simple.management.db.service;

import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AuthenticationParamsDao;
import sk.qbsw.security.core.dao.OrganizationDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.AccountInputDataMapper;
import sk.qbsw.security.core.service.mapper.AccountOutputDataMapper;
import sk.qbsw.security.management.service.AccountCredentialManagementService;
import sk.qbsw.security.organization.simple.base.model.SimpleOrganizationAccountData;
import sk.qbsw.security.organization.simple.base.model.SimpleOrganizationAccountInputData;
import sk.qbsw.security.organization.simple.management.SimpleOrganizationAccountManagementService;

import java.util.List;

/**
 * The simple organization account management service.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class SimpleOrganizationAccountManagementServiceImpl extends SimpleOrganizationAccountManagementServiceBase<SimpleOrganizationAccountInputData, SimpleOrganizationAccountData, Account> implements SimpleOrganizationAccountManagementService<SimpleOrganizationAccountInputData, SimpleOrganizationAccountData>
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
	public SimpleOrganizationAccountManagementServiceImpl (AccountDao<Account> accountDao, OrganizationDao organizationDao, AuthenticationParamsDao authenticationParamsDao, AccountCredentialManagementService authenticationService, AccountInputDataMapper<SimpleOrganizationAccountInputData, Account> accountAccountInputDataMapper, AccountOutputDataMapper<SimpleOrganizationAccountData, Account> accountOutputDataMapper)
	{
		super(accountDao, organizationDao, authenticationParamsDao, authenticationService, accountAccountInputDataMapper, accountOutputDataMapper);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public SimpleOrganizationAccountData register (SimpleOrganizationAccountInputData accountInputData, String password) throws CSecurityException
	{
		return super.registerBase(accountInputData, password);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public SimpleOrganizationAccountData register (SimpleOrganizationAccountInputData accountInputData) throws CSecurityException
	{
		return super.registerBase(accountInputData);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public SimpleOrganizationAccountData findOneById (Long id)
	{
		return super.findOneByIdBase(id);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public SimpleOrganizationAccountData findOneByLogin (String login) throws CSecurityException
	{
		return super.findOneByLoginBase(login);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public SimpleOrganizationAccountData findOneByUid (String uid)
	{
		return super.findOneByUidBase(uid);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public SimpleOrganizationAccountData findOneByLoginAndState (String login, ActivityStates state)
	{
		return super.findOneByLoginAndStateBase(login, state);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public SimpleOrganizationAccountData findOneByLoginAndStateAndGroupCodePrefix (String login, ActivityStates state, String groupCodePrefix)
	{
		return super.findOneByLoginAndStateAndGroupCodePrefixBase(login, state, groupCodePrefix);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<SimpleOrganizationAccountData> findByEmail (String email)
	{
		return super.findByEmailBase(email);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<SimpleOrganizationAccountData> findAll ()
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
	public SimpleOrganizationAccountData update (SimpleOrganizationAccountInputData accountInputData)
	{
		return super.updateBase(accountInputData);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public SimpleOrganizationAccountData activate (Long id)
	{
		return super.activateBase(id);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public SimpleOrganizationAccountData inactivate (Long id)
	{
		return super.inactivateBase(id);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public SimpleOrganizationAccountData findOneByOrganizationCodeAndLoginAndState (String organizationCode, String login, ActivityStates state)
	{
		return super.findOneByOrganizationCodeAndLoginAndStateBase(organizationCode, login, state);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<SimpleOrganizationAccountData> findByOrganizationCode (String organizationCode)
	{
		return super.findByOrganizationCodeBase(organizationCode);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<SimpleOrganizationAccountData> findByOrganizationCodeAndGroupCodeAndNotLogin (String organizationCode, String groupCode, String login)
	{
		return super.findByOrganizationCodeAndGroupCodeAndNotLoginBase(organizationCode, groupCode, login);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<SimpleOrganizationAccountData> findByOrganizationCodeAndState (String organizationCode, ActivityStates state)
	{
		return super.findByOrganizationCodeAndStateBase(organizationCode, state);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<SimpleOrganizationAccountData> findByOrganizationCodeAndStateAndGroupCode (String organizationCode, ActivityStates state, String groupCode)
	{
		return super.findByOrganizationCodeAndStateAndGroupCodeBase(organizationCode, state, groupCode);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<SimpleOrganizationAccountData> findByOrganizationCodeAndStateAndGroupCodeOrderByOrganizationNameAndLogin (String organizationCode, ActivityStates state, String groupCode)
	{
		return super.findByOrganizationCodeAndStateAndGroupCodeOrderByOrganizationNameAndLoginBase(organizationCode, state, groupCode);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<SimpleOrganizationAccountData> findByOrganizationCodeAndRoleCode (String organizationCode, String roleCode)
	{
		return super.findByOrganizationCodeAndRoleCodeBase(organizationCode, roleCode);
	}
}
