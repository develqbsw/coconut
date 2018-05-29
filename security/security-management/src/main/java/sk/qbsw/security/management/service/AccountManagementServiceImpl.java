package sk.qbsw.security.management.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.base.logging.annotation.CNotLogged;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AuthenticationParamsDao;
import sk.qbsw.security.core.dao.OrganizationDao;
import sk.qbsw.security.core.model.domain.*;
import sk.qbsw.security.core.model.filter.AccountAssociationsFilter;
import sk.qbsw.security.core.model.filter.AccountDetailFilter;
import sk.qbsw.security.core.model.order.*;

import javax.persistence.NoResultException;
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
public class AccountManagementServiceImpl extends AService implements AccountManagementService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountManagementServiceImpl.class);

	private final AccountDao accountDao;

	private final OrganizationDao organizationDao;

	private final AuthenticationParamsDao authenticationParamsDao;

	private final AccountCredentialManagementService authenticationService;

	/**
	 * Instantiates a new account management service.
	 *
	 * @param accountDao the account dao
	 * @param organizationDao the organization dao
	 * @param authenticationParamsDao the authentication params dao
	 * @param authenticationService the authentication service
	 */
	@Autowired
	public AccountManagementServiceImpl (AccountDao accountDao, OrganizationDao organizationDao, AuthenticationParamsDao authenticationParamsDao, AccountCredentialManagementService authenticationService)
	{
		this.accountDao = accountDao;
		this.organizationDao = organizationDao;
		this.authenticationParamsDao = authenticationParamsDao;
		this.authenticationService = authenticationService;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Account register (Account account, Organization organization) throws CSecurityException
	{
		try
		{
			// checks if account already exists
			accountDao.findOneByLogin(account.getLogin());
			throw new CSecurityException("Account with login " + account.getLogin() + " already exists.", ECoreErrorResponse.ACCOUNT_ALREADY_EXISTS);
		}
		catch (NoResultException nre)
		{
			LOGGER.debug("Login not used, can continue", nre);
		}

		if (account.getLogin() == null || organization == null || organization.getId() == null)
		{
			throw new CSecurityException(ECoreErrorResponse.ACCOUNT_NOT_ALL_PARAMETERS);
		}

		Organization currentOrganization;
		try
		{
			currentOrganization = organizationDao.findById(organization.getId());
		}
		catch (NoResultException nre)
		{
			LOGGER.debug("Organization not found", nre);
			throw new CSecurityException(ECoreErrorResponse.ORGANIZATION_NOT_VALID);
		}

		account.setOrganization(currentOrganization);
		Account createAccount = accountDao.update(account);

		// create and create empty authentication params
		AuthenticationParams authParams = new AuthenticationParams();
		authParams.setAccount(createAccount);
		authParams.setPassword(null);
		authParams.setPasswordDigest(null);
		authParams.setPin(null);
		authenticationParamsDao.update(authParams);

		return createAccount;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Account register (Account account, @CNotLogged @CNotAuditLogged String password, Organization organization) throws CSecurityException
	{
		// register new account with empty password
		Account createdAccount = register(account, organization);

		if (password == null)
		{
			throw new CSecurityException(ECoreErrorResponse.PASSWORD_MANDATORY);
		}

		// set password and create
		authenticationService.changeEncryptedPassword(createdAccount.getLogin(), password);

		return createdAccount;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Account read (Long id)
	{
		return accountDao.findById(id);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<Account> findAll ()
	{
		return accountDao.findAll();
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<Account> findByOrganization (Organization organization)
	{
		AccountAssociationsFilter filter = new AccountAssociationsFilter();
		filter.setOrganization(organization);

		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		return accountDao.findByAccountAssociationsFilter(filter, orderModel);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<Account> findAllExceptAccount (Organization organization, Group group, Account account)
	{
		AccountAssociationsFilter filter = new AccountAssociationsFilter();
		filter.setOrganization(organization);
		filter.setGroup(group);
		filter.setExcludedAccount(account);
		filter.setState(ActivityStates.ACTIVE);

		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		return accountDao.findByAccountAssociationsFilter(filter, orderModel);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<Account> findByOrganizationAndState (Organization organization, ActivityStates state)
	{
		AccountAssociationsFilter filter = new AccountAssociationsFilter();
		filter.setOrganization(organization);
		filter.setState(state);

		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		return accountDao.findByAccountAssociationsFilter(filter, orderModel);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<Account> findByOrganizationAndStateAndGroup (Organization organization, ActivityStates state, Group group)
	{
		AccountAssociationsFilter filter = new AccountAssociationsFilter();
		filter.setOrganization(organization);
		filter.setState(state);
		filter.setGroup(group);

		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		return accountDao.findByAccountAssociationsFilter(filter, orderModel);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<Account> findByOrganizationAndStateAndGroupOrderByOrganizationNameAndLogin (Organization organization, ActivityStates state, Group group)
	{
		AccountAssociationsFilter filter = new AccountAssociationsFilter();
		filter.setOrganization(organization);
		filter.setState(state);
		filter.setGroup(group);

		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(OrganizationOrderByAttributeSpecifiers.NAME, OrderSpecifiers.ASC));
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		return accountDao.findByAccountAssociationsFilter(filter, orderModel);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<Account> findByOrganizationAndRole (Organization organization, Role role)
	{
		AccountAssociationsFilter filter = new AccountAssociationsFilter();
		filter.setOrganization(organization);

		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		if (role != null)
		{
			filter.setRole(role);
		}

		return accountDao.findByAccountAssociationsFilter(filter, orderModel);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<Account> findByOrganizationAndLoginAndState (Organization organization, String login, ActivityStates state)
	{
		AccountDetailFilter filter = new AccountDetailFilter();
		filter.setLogin(login);
		filter.setState(state);
		filter.setOrganization(organization);

		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		return accountDao.findByAccountDetailFilter(filter, orderModel);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<Account> findByLoginAndState (String login, ActivityStates state)
	{
		AccountDetailFilter filter = new AccountDetailFilter();
		filter.setLogin(login);
		filter.setState(state);

		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		return accountDao.findByAccountDetailFilter(filter, orderModel);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<Account> findByLoginAndStateAndGroupPrefix (String login, ActivityStates state, String groupPrefix)
	{
		AccountDetailFilter filter = new AccountDetailFilter();
		filter.setLogin(login);
		filter.setState(state);
		filter.setGroupCodePrefix(groupPrefix);

		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		return accountDao.findByAccountDetailFilter(filter, orderModel);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<Account> findByEmail (String email)
	{
		AccountDetailFilter filter = new AccountDetailFilter();
		filter.setEmail(email);

		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		return accountDao.findByAccountDetailFilter(filter, orderModel);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Account findByByLogin (String login) throws CSecurityException
	{
		return accountDao.findOneByLogin(login);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public boolean existsByLogin (String login)
	{
		try
		{
			accountDao.findOneByLogin(login);
			return true;
		}
		catch (NoResultException | CSecurityException e)
		{
			LOGGER.debug("Account does not exist", e);
			return false;
		}
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public boolean existsByPin (String pin)
	{
		try
		{
			List<Account> users = accountDao.findByPinCode(pin);

			return !users.isEmpty();

		}
		catch (CBusinessException e)
		{
			LOGGER.debug("User with pin not found", e);
			// the user with pin null does not exists

			return false;
		}
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Account update (Account account)
	{
		return accountDao.update(account);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Account activate (Long id)
	{
		Account currentAccount = accountDao.findById(id);
		currentAccount.setState(ActivityStates.ACTIVE);

		return currentAccount;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Account inactivate (Long id)
	{
		Account currentAccount = accountDao.findById(id);
		currentAccount.setState(ActivityStates.INACTIVE);

		return currentAccount;
	}
}
