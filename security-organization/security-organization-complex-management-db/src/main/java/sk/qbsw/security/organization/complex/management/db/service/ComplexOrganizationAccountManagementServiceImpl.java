package sk.qbsw.security.organization.complex.management.db.service;

import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AuthenticationParamsDao;
import sk.qbsw.security.core.dao.OrganizationDao;
import sk.qbsw.security.core.service.mapper.AccountInputDataMapper;
import sk.qbsw.security.core.service.mapper.AccountOutputDataMapper;
import sk.qbsw.security.management.base.service.AccountManagementServiceBase;
import sk.qbsw.security.management.service.AccountCredentialManagementService;
import sk.qbsw.security.organization.complex.base.model.ComplexOrganizationAccountData;
import sk.qbsw.security.organization.complex.base.model.ComplexOrganizationAccountInputData;
import sk.qbsw.security.organization.complex.core.model.domain.UserAccount;
import sk.qbsw.security.organization.complex.management.service.ComplexOrganizationAccountManagementService;

import java.util.List;

/**
 * The simple organization account management service.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class ComplexOrganizationAccountManagementServiceImpl extends AccountManagementServiceBase<ComplexOrganizationAccountInputData, ComplexOrganizationAccountData, UserAccount> implements ComplexOrganizationAccountManagementService<ComplexOrganizationAccountInputData, ComplexOrganizationAccountData>
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
	public ComplexOrganizationAccountManagementServiceImpl (AccountDao<UserAccount> accountDao, OrganizationDao organizationDao, AuthenticationParamsDao authenticationParamsDao, AccountCredentialManagementService authenticationService, AccountInputDataMapper<ComplexOrganizationAccountInputData, UserAccount> accountAccountInputDataMapper, AccountOutputDataMapper<ComplexOrganizationAccountData, UserAccount> accountOutputDataMapper)
	{
		super(accountDao, organizationDao, authenticationParamsDao, authenticationService, accountAccountInputDataMapper, accountOutputDataMapper);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public ComplexOrganizationAccountData register (ComplexOrganizationAccountInputData accountInputData, String password) throws CSecurityException
	{
		return super.registerBase(accountInputData, password);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public ComplexOrganizationAccountData register (ComplexOrganizationAccountInputData accountInputData) throws CSecurityException
	{
		return super.registerBase(accountInputData);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public ComplexOrganizationAccountData findOneById (Long id)
	{
		return super.findOneByIdBase(id);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public ComplexOrganizationAccountData findOneByLogin (String login) throws CSecurityException
	{
		return super.findOneByLoginBase(login);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public ComplexOrganizationAccountData findOneByUid (String uid)
	{
		return super.findOneByUidBase(uid);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public ComplexOrganizationAccountData findOneByLoginAndState (String login, ActivityStates state)
	{
		return super.findOneByLoginAndStateBase(login, state);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public ComplexOrganizationAccountData findOneByLoginAndStateAndGroupCodePrefix (String login, ActivityStates state, String groupCodePrefix)
	{
		return super.findOneByLoginAndStateAndGroupCodePrefixBase(login, state, groupCodePrefix);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<ComplexOrganizationAccountData> findByEmail (String email)
	{
		return super.findByEmailBase(email);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<ComplexOrganizationAccountData> findAll ()
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
	public ComplexOrganizationAccountData update (ComplexOrganizationAccountInputData accountInputData)
	{
		return super.updateBase(accountInputData);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public ComplexOrganizationAccountData activate (Long id)
	{
		return super.activateBase(id);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public ComplexOrganizationAccountData inactivate (Long id)
	{
		return super.inactivateBase(id);
	}
}
