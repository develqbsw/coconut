package sk.qbsw.security.organization.complex.management.db.service;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.core.security.base.model.AccountInputData;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AuthenticationParamsDao;
import sk.qbsw.security.core.dao.OrganizationDao;
import sk.qbsw.security.core.dao.UserDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.core.service.mapper.AccountInputDataMapper;
import sk.qbsw.security.core.service.mapper.AccountOutputDataMapper;
import sk.qbsw.security.management.base.service.AccountManagementServiceBase;
import sk.qbsw.security.management.service.AccountCredentialManagementService;
import sk.qbsw.security.management.service.AccountManagementService;
import sk.qbsw.security.organization.complex.base.model.CXOUserInputData;
import sk.qbsw.security.organization.complex.core.dao.CXOUnitDao;
import sk.qbsw.security.organization.complex.core.model.domain.CXOUnit;
import sk.qbsw.security.organization.complex.core.model.domain.CXOUser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The complex organization account management service.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class CXOAccountManagementServiceImpl extends AccountManagementServiceBase<AccountInputData, AccountData, Account> implements AccountManagementService<AccountInputData, AccountData>
{
	private final CXOUnitDao unitDao;

	/**
	 * Instantiates a new account management service.
	 *
	 * @param accountDao the account dao
	 * @param userDao the user dao
	 * @param organizationDao the organization dao
	 * @param authenticationParamsDao the authentication params dao
	 * @param authenticationService the authentication service
	 * @param accountAccountInputDataMapper the account account input data mapper
	 * @param accountOutputDataMapper the account output data mapper
	 * @param unitDao the unit dao
	 */
	public CXOAccountManagementServiceImpl (AccountDao<Account> accountDao, UserDao userDao, OrganizationDao organizationDao, AuthenticationParamsDao authenticationParamsDao, AccountCredentialManagementService authenticationService, AccountInputDataMapper<AccountInputData, Account> accountAccountInputDataMapper, AccountOutputDataMapper<AccountData, Account> accountOutputDataMapper, CXOUnitDao unitDao)
	{
		super(accountDao, userDao, organizationDao, authenticationParamsDao, authenticationService, accountAccountInputDataMapper, accountOutputDataMapper);
		this.unitDao = unitDao;
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
	protected void validateRegisterInput (AccountInputData accountInputData) throws CSecurityException
	{
		super.validateRegisterInput(accountInputData);

		if (! (accountInputData.getUser() instanceof CXOUserInputData) || CollectionUtils.isEmpty( ((CXOUserInputData) accountInputData.getUser()).getUnits()))
		{
			throw new CSecurityException(ECoreErrorResponse.ACCOUNT_NOT_ALL_PARAMETERS);
		}
	}

	@Override
	protected void setRegisterUserAssociations (User user) throws CBusinessException
	{
		super.setRegisterUserAssociations(user);

		CXOUser cxoUser = (CXOUser) user;
		Set<CXOUnit> units = new HashSet<>();

		for (CXOUnit inputUnit : cxoUser.getUnits())
		{
			units.add(unitDao.findOneByCode(inputUnit.getCode()));
		}

		cxoUser.setUnits(units);
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
