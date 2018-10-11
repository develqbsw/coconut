package sk.qbsw.security.management.base.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.core.security.base.model.AccountInputData;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AuthenticationParamsDao;
import sk.qbsw.security.core.dao.OrganizationDao;
import sk.qbsw.security.core.dao.UserDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.AuthenticationParams;
import sk.qbsw.security.core.model.domain.Organization;
import sk.qbsw.security.core.model.filter.AccountDetailFilter;
import sk.qbsw.security.core.model.order.AccountOrderByAttributeSpecifiers;
import sk.qbsw.security.core.model.order.OrderModel;
import sk.qbsw.security.core.model.order.OrderSpecification;
import sk.qbsw.security.core.model.order.OrderSpecifiers;
import sk.qbsw.security.core.service.mapper.AccountInputDataMapper;
import sk.qbsw.security.core.service.mapper.AccountOutputDataMapper;
import sk.qbsw.security.management.service.AccountCredentialManagementService;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The account management service.
 *
 * @param <I> the account input type
 * @param <O> the account output type
 * @param <A> the account type
 * @author Dalibor Rak
 * @author Tomas Leken
 * @author Michal Lacko
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.0.0
 */
public abstract class AccountManagementServiceBase<I extends AccountInputData, O extends AccountData, A extends Account>extends AService
{
	/**
	 * The constant LOGGER.
	 */
	protected static final Logger LOGGER = LoggerFactory.getLogger(AccountManagementServiceBase.class);

	/**
	 * The Account dao.
	 */
	protected final AccountDao<A> accountDao;

	/**
	 * The User dao.
	 */
	protected final UserDao userDao;

	/**
	 * The Organization dao.
	 */
	protected final OrganizationDao organizationDao;

	/**
	 * The Authentication params dao.
	 */
	protected final AuthenticationParamsDao authenticationParamsDao;

	/**
	 * The Authentication service.
	 */
	protected final AccountCredentialManagementService authenticationService;

	/**
	 * The Account account input data mapper.
	 */
	protected final AccountInputDataMapper<I, A> accountAccountInputDataMapper;

	/**
	 * The Account output data mapper.
	 */
	protected final AccountOutputDataMapper<O, A> accountOutputDataMapper;

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
	 */
	public AccountManagementServiceBase (AccountDao<A> accountDao, UserDao userDao, OrganizationDao organizationDao, AuthenticationParamsDao authenticationParamsDao, AccountCredentialManagementService authenticationService, AccountInputDataMapper<I, A> accountAccountInputDataMapper, AccountOutputDataMapper<O, A> accountOutputDataMapper)
	{
		this.accountDao = accountDao;
		this.userDao = userDao;
		this.organizationDao = organizationDao;
		this.authenticationParamsDao = authenticationParamsDao;
		this.authenticationService = authenticationService;
		this.accountAccountInputDataMapper = accountAccountInputDataMapper;
		this.accountOutputDataMapper = accountOutputDataMapper;
	}

	/**
	 * Register base o.
	 *
	 * @param accountInputData the account input data
	 * @param password the password
	 * @return the o
	 * @throws CSecurityException the c security exception
	 */
	protected O registerBase (I accountInputData, String password) throws CSecurityException
	{
		// register new account with empty password
		O accountData = registerBase(accountInputData);

		if (password == null)
		{
			throw new CSecurityException(ECoreErrorResponse.PASSWORD_MANDATORY);
		}

		// set password and create
		authenticationService.changeEncryptedPassword(accountData.getLogin(), password);

		return accountData;
	}

	/**
	 * Register base o.
	 *
	 * @param accountInputData the account input data
	 * @return the o
	 * @throws CSecurityException the c security exception
	 */
	protected O registerBase (I accountInputData) throws CSecurityException
	{
		validateRegisterInput(accountInputData);

		// map account
		A account = accountAccountInputDataMapper.mapToAccount(accountInputData);

		if (account.getUser() != null && account.getUser().getId() == null)
		{
			account.setUser(userDao.update(account.getUser()));
		}

		account = accountDao.update(account);

		// create and create empty authentication params
		AuthenticationParams authParams = new AuthenticationParams();
		authParams.setAccount(account);
		authParams.setPassword(null);
		authParams.setPasswordDigest(null);
		authParams.setPin(null);
		authenticationParamsDao.update(authParams);

		return accountOutputDataMapper.mapToAccountOutputData(account);
	}

	/**
	 * Validate register input.
	 *
	 * @param accountInputData the account input data
	 * @throws CSecurityException the c security exception
	 */
	protected void validateRegisterInput (I accountInputData) throws CSecurityException
	{
		// validate account
		try
		{
			// checks if account already exists
			accountDao.findOneByLogin(accountInputData.getLogin());
			throw new CSecurityException("Account with login " + accountInputData.getLogin() + " already exists.", ECoreErrorResponse.ACCOUNT_ALREADY_EXISTS);
		}
		catch (NoResultException nre)
		{
			LOGGER.debug("Login not used, can continue", nre);
		}

		if (accountInputData.getLogin() == null || accountInputData.getOrganizationId() == null)
		{
			throw new CSecurityException(ECoreErrorResponse.ACCOUNT_NOT_ALL_PARAMETERS);
		}

		Organization currentOrganization = organizationDao.findById(accountInputData.getOrganizationId());
		if (currentOrganization == null)
		{
			LOGGER.debug("Organization with code {} not found", accountInputData.getOrganizationId());
			throw new CSecurityException(ECoreErrorResponse.ORGANIZATION_NOT_VALID);
		}
	}

	/**
	 * Find one by id base o.
	 *
	 * @param id the id
	 * @return the o
	 */
	protected O findOneByIdBase (Long id)
	{
		return accountOutputDataMapper.mapToAccountOutputData(accountDao.findById(id));
	}

	/**
	 * Find one by login base o.
	 *
	 * @param login the login
	 * @return the o
	 * @throws CSecurityException the c security exception
	 */
	protected O findOneByLoginBase (String login) throws CSecurityException
	{
		return accountOutputDataMapper.mapToAccountOutputData(accountDao.findOneByLogin(login));
	}

	/**
	 * Find one by uid base o.
	 *
	 * @param uid the uid
	 * @return the o
	 */
	protected O findOneByUidBase (String uid)
	{
		return accountOutputDataMapper.mapToAccountOutputData(accountDao.findByUid(uid));
	}

	/**
	 * Find one by login and state base o.
	 *
	 * @param login the login
	 * @param state the state
	 * @return the o
	 */
	protected O findOneByLoginAndStateBase (String login, ActivityStates state)
	{
		AccountDetailFilter filter = new AccountDetailFilter();
		filter.setLogin(login);
		filter.setState(state);

		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		return accountDao.findByAccountDetailFilter(filter, orderModel).stream().map(accountOutputDataMapper::mapToAccountOutputData).findFirst().orElseThrow(NoResultException::new);
	}

	/**
	 * Find one by login and state and group code prefix base o.
	 *
	 * @param login the login
	 * @param state the state
	 * @param groupCodePrefix the group code prefix
	 * @return the o
	 */
	protected O findOneByLoginAndStateAndGroupCodePrefixBase (String login, ActivityStates state, String groupCodePrefix)
	{
		AccountDetailFilter filter = new AccountDetailFilter();
		filter.setLogin(login);
		filter.setState(state);
		filter.setGroupCodePrefix(groupCodePrefix);

		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		return accountDao.findByAccountDetailFilter(filter, orderModel).stream().map(accountOutputDataMapper::mapToAccountOutputData).findFirst().orElseThrow(NoResultException::new);
	}

	/**
	 * Find by email base list.
	 *
	 * @param email the email
	 * @return the list
	 */
	protected List<O> findByEmailBase (String email)
	{
		AccountDetailFilter filter = new AccountDetailFilter();
		filter.setEmail(email);

		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		return accountDao.findByAccountDetailFilter(filter, orderModel).stream().map(accountOutputDataMapper::mapToAccountOutputData).collect(Collectors.toList());
	}

	/**
	 * Find all base list.
	 *
	 * @return the list
	 */
	protected List<O> findAllBase ()
	{
		return accountDao.findAll().stream().map(accountOutputDataMapper::mapToAccountOutputData).collect(Collectors.toList());
	}

	/**
	 * Exists by login base boolean.
	 *
	 * @param login the login
	 * @return the boolean
	 */
	protected boolean existsByLoginBase (String login)
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

	/**
	 * Exists by pin base boolean.
	 *
	 * @param pin the pin
	 * @return the boolean
	 */
	protected boolean existsByPinBase (String pin)
	{
		try
		{
			List<A> users = accountDao.findByPinCode(pin);

			return !users.isEmpty();

		}
		catch (CBusinessException e)
		{
			LOGGER.debug("User with pin not found", e);
			// the user with pin null does not exists

			return false;
		}
	}

	/**
	 * Update base o.
	 *
	 * @param accountInputData the account input data
	 * @return the o
	 */
	protected O updateBase (I accountInputData)
	{
		A currentAccount = accountDao.findById(accountInputData.getId());

		A account = accountAccountInputDataMapper.mapToAccount(accountInputData);
		account.setAccountUnitGroups(currentAccount.getAccountUnitGroups());
		account.setDefaultUnit(currentAccount.getDefaultUnit());
		account.setState(currentAccount.getState());

		return accountOutputDataMapper.mapToAccountOutputData(accountDao.update(account));
	}

	/**
	 * Activate base o.
	 *
	 * @param id the id
	 * @return the o
	 */
	protected O activateBase (Long id)
	{
		A currentAccount = accountDao.findById(id);
		currentAccount.setState(ActivityStates.ACTIVE);

		return accountOutputDataMapper.mapToAccountOutputData(currentAccount);
	}

	/**
	 * Inactivate base o.
	 *
	 * @param id the id
	 * @return the o
	 */
	protected O inactivateBase (Long id)
	{
		A currentAccount = accountDao.findById(id);
		currentAccount.setState(ActivityStates.INACTIVE);

		return accountOutputDataMapper.mapToAccountOutputData(currentAccount);
	}
}
