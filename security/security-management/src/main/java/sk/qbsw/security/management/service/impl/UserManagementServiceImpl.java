package sk.qbsw.security.management.service.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.base.logging.annotation.CNotLogged;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.security.core.dao.AddressDao;
import sk.qbsw.security.core.dao.AuthenticationParamsDao;
import sk.qbsw.security.core.dao.OrganizationDao;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.model.domain.*;
import sk.qbsw.security.core.model.domain.Address;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.filter.AccountAssociationsFilter;
import sk.qbsw.security.core.model.filter.AccountDetailFilter;
import sk.qbsw.security.core.model.order.OrderModel;
import sk.qbsw.security.core.model.order.OrderSpecification;
import sk.qbsw.security.core.model.order.OrderSpecifiers;
import sk.qbsw.security.core.model.order.OrganizationOrderByAttributeSpecifiers;
import sk.qbsw.security.core.model.order.UserOrderByAttributeSpecifiers;
import sk.qbsw.security.core.model.order.OrderByAttributeSpecifier;
import sk.qbsw.security.management.service.UserCredentialManagementService;
import sk.qbsw.security.management.service.UserManagementService;

/**
 * Service for user management.
 *
 * @author Dalibor Rak
 * @author Tomas Leken
 * @author Michal Lacko
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.0.0
 */
@Service ("userManagementService")
public class UserManagementServiceImpl extends AService implements UserManagementService
{
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(UserManagementServiceImpl.class);

	/** The user dao. */
	@Autowired
	private AccountDao userDao;

	/** The organization dao. */
	@Autowired
	private OrganizationDao organizationDao;

	/** The authentication params dao. */
	@Autowired
	private AuthenticationParamsDao authenticationParamsDao;

	/**  The address dao. */
	@Autowired
	private AddressDao addressDao;

	/** The authentication service. */
	@Autowired
	private UserCredentialManagementService authenticationService;

	/**
	 * Disable user.
	 *
	 * @param user the user
	 * @see sk.qbsw.security.core.core.management.service.ISecurityService#disableUser(sk.qbsw.security.core.User.model.domain.CUser)
	 */
	@Override
	@Transactional (readOnly = false)
	public void disableUser (Account user)
	{
		Account toModify = userDao.findById(user.getId());
		toModify.setFlagEnabled(Boolean.FALSE);
		userDao.update(toModify);
	}

	/**
	 * Enable user.
	 *
	 * @param user the user
	 * @see sk.qbsw.security.core.core.management.service.ISecurityService#enableUser(sk.qbsw.security.core.User.model.domain.CUser)
	 */
	@Override
	@Transactional (readOnly = false)
	public void enableUser (Account user)
	{
		Account toModify = userDao.findById(user.getId());
		toModify.setFlagEnabled(Boolean.TRUE);
		userDao.update(toModify);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#get(java.lang.Long)
	 */
	@Override
	@Transactional (readOnly = true)
	public Account get (Long id)
	{
		return userDao.findById(id);
	}

	/**
	 * Gets the all users.
	 *
	 * @param organization the organization
	 * @return the all users
	 * @see sk.qbsw.security.core.core.management.service.ISecurityService#getAllUsers(sk.qbsw.security.core.Organization.model.domain.COrganization)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<Account> getAllUsers (Organization organization)
	{
		AccountAssociationsFilter filter = new AccountAssociationsFilter();
		filter.setOrganization(organization);

		OrderModel<UserOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<OrderByAttributeSpecifier>(UserOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		return userDao.findByUserAssociationsFilter(filter, orderModel);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#getOtherActiveUsers(sk.qbsw.security.core.core.model.domain.COrganization, sk.qbsw.security.core.core.model.domain.CGroup, sk.qbsw.security.core.core.model.domain.CUser)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<Account> getOtherActiveUsers (Organization organization, Group group, Account user)
	{
		AccountAssociationsFilter filter = new AccountAssociationsFilter();
		filter.setOrganization(organization);
		filter.setGroup(group);
		filter.setExcludedUser(user);
		filter.setEnabled(true);

		OrderModel<UserOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<OrderByAttributeSpecifier>(UserOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		return userDao.findByUserAssociationsFilter(filter, orderModel);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#getUserByLogin(java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public Account getUserByLogin (String login) throws CSecurityException
	{
		return userDao.findOneByLogin(login);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#getUserForModification(java.lang.Long)
	 */
	@Override
	@Transactional (readOnly = true)
	public Account getUserForModification (Long id)
	{
		return userDao.findForModification(id);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.stavbyveduci.management.service.IUserService#getUsers()
	 */
	@Override
	@Transactional (readOnly = true)
	public List<Account> getUsers ()
	{
		return userDao.findAll();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#getUsers(sk.qbsw.security.core.core.model.domain.COrganization, java.lang.Boolean)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<Account> getUsers (Organization organization, Boolean enabled)
	{
		AccountAssociationsFilter filter = new AccountAssociationsFilter();
		filter.setOrganization(organization);
		filter.setEnabled(enabled);

		OrderModel<UserOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<OrderByAttributeSpecifier>(UserOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		return userDao.findByUserAssociationsFilter(filter, orderModel);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#getUsersOrderByOrganization(sk.qbsw.security.core.core.model.domain.COrganization, java.lang.Boolean, sk.qbsw.security.core.core.model.domain.CGroup)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<Account> getUsersOrderByOrganization (Organization organization, Boolean enabled, Group group)
	{
		AccountAssociationsFilter filter = new AccountAssociationsFilter();
		filter.setOrganization(organization);
		filter.setEnabled(enabled);
		filter.setGroup(group);

		OrderModel<UserOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<OrderByAttributeSpecifier>(OrganizationOrderByAttributeSpecifiers.NAME, OrderSpecifiers.ASC));
		orderModel.getOrderSpecification().add(new OrderSpecification<OrderByAttributeSpecifier>(UserOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		return userDao.findByUserAssociationsFilter(filter, orderModel);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#getUsers(sk.qbsw.security.core.core.model.domain.COrganization, java.lang.Boolean, sk.qbsw.security.core.core.model.domain.CGroup)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<Account> getUsers (Organization organization, Boolean enabled, Group group)
	{
		AccountAssociationsFilter filter = new AccountAssociationsFilter();
		filter.setOrganization(organization);
		filter.setEnabled(enabled);
		filter.setGroup(group);

		OrderModel<UserOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<OrderByAttributeSpecifier>(UserOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		return userDao.findByUserAssociationsFilter(filter, orderModel);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.stavbyveduci.management.service.IUserService#getUsers(sk.qbsw.winnetou.security.model.COrganization)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<Account> getUsers (Organization organization, Role role)
	{
		if (role != null)
		{
			AccountAssociationsFilter filter = new AccountAssociationsFilter();
			filter.setOrganization(organization);
			filter.setRole(role);

			OrderModel<UserOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
			orderModel.getOrderSpecification().add(new OrderSpecification<OrderByAttributeSpecifier>(UserOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

			return userDao.findByUserAssociationsFilter(filter, orderModel);
		}
		else
		{
			AccountAssociationsFilter filter = new AccountAssociationsFilter();
			filter.setOrganization(organization);

			OrderModel<UserOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
			orderModel.getOrderSpecification().add(new OrderSpecification<OrderByAttributeSpecifier>(UserOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

			return userDao.findByUserAssociationsFilter(filter, orderModel);
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#getUsers(java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<Account> getUsers (String name, String surname, String login, Boolean enabled)
	{
		AccountDetailFilter filter = new AccountDetailFilter();
		filter.setName(name);
		filter.setSurname(surname);
		filter.setLogin(login);
		filter.setEnabled(enabled);

		OrderModel<UserOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<OrderByAttributeSpecifier>(UserOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		return userDao.findByUserDetailFilter(filter, orderModel);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#getUsers(java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean, sk.qbsw.security.core.core.model.domain.COrganization)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<Account> getUsers (String name, String surname, String login, Boolean enabled, Organization organization)
	{
		AccountDetailFilter filter = new AccountDetailFilter();
		filter.setName(name);
		filter.setSurname(surname);
		filter.setLogin(login);
		filter.setEnabled(enabled);
		filter.setOrganization(organization);

		OrderModel<UserOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<OrderByAttributeSpecifier>(UserOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		return userDao.findByUserDetailFilter(filter, orderModel);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#getUsers(java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<Account> getUsers (String name, String surname, String login, Boolean enabled, String groupPrefix)
	{
		AccountDetailFilter filter = new AccountDetailFilter();
		filter.setName(name);
		filter.setSurname(surname);
		filter.setLogin(login);
		filter.setEnabled(enabled);
		filter.setGroupCodePrefix(groupPrefix);

		OrderModel<UserOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<OrderByAttributeSpecifier>(UserOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		return userDao.findByUserDetailFilter(filter, orderModel);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#getUsers(java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<Account> getUsers (String email)
	{
		AccountDetailFilter filter = new AccountDetailFilter();
		filter.setEmail(email);

		OrderModel<UserOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<OrderByAttributeSpecifier>(UserOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		return userDao.findByUserDetailFilter(filter, orderModel);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#registerNewUser(sk.qbsw.security.core.core.model.domain.CUser, sk.qbsw.security.core.core.model.domain.COrganization)
	 */
	@Override
	@Transactional (readOnly = false)
	public void registerNewUser (Account user, @CNotLogged @CNotAuditLogged String password, Organization organization) throws CSecurityException
	{
		//register new user with empty password
		registerNewUser(user, organization);

		if (password == null)
		{
			throw new CSecurityException(ECoreErrorResponse.PASSWORD_MANDATORY);
		}

		//set password and save
		authenticationService.changeEncryptedPassword(user.getLogin(), password);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#registerNewUser(sk.qbsw.security.core.core.model.domain.CUser, sk.qbsw.security.core.core.model.domain.COrganization)
	 */
	@Override
	@Transactional (readOnly = false)
	public void registerNewUser (Account user, Organization organization) throws CSecurityException
	{
		try
		{
			//checks if user already exists
			userDao.findOneByLogin(user.getLogin());
			throw new CSecurityException("User with login " + user.getLogin() + " already exists.", ECoreErrorResponse.USER_ALREADY_EXISTS);
		}
		catch (NoResultException nre)
		{
			LOGGER.debug("Login not used, can continue", nre);
		}

		if (user.getLogin() == null || organization == null || organization.getId() == null)
		{
			throw new CSecurityException(ECoreErrorResponse.USER_NOT_ALL_PARAMETERS);
		}

		Organization redOrg;
		try
		{
			redOrg = organizationDao.findById(organization.getId());
		}
		catch (NoResultException nre)
		{
			LOGGER.debug("Organization not found", nre);
			throw new CSecurityException(ECoreErrorResponse.ORGANIZATION_NOT_VALID);
		}

		user.setOrganization(redOrg);
		userDao.update(user);

		//create and save empty authentication params
		AuthenticationParams authParams = new AuthenticationParams();
		authParams.setUser(user);
		authParams.setPassword(null);
		authParams.setPasswordDigest(null);
		authParams.setPin(null);
		authenticationParamsDao.update(authParams);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#updateUser(sk.qbsw.security.core.core.model.domain.CUser)
	 */
	@Override
	@Transactional (readOnly = false)
	public void updateUser (Account user)
	{
		userDao.update(user);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#setAddress(sk.qbsw.security.core.core.model.domain.CUser, sk.qbsw.security.core.core.model.domain.CAddress)
	 */
	@Override
	@Transactional
	public void setAddress (Account user, Address address)
	{
		//set address to user
		user.setAddress(address);

		//save entities
		addressDao.update(address);
		userDao.update(user);

	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#checksUserExist(java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public boolean checksUserExist (String login)
	{
		try
		{
			userDao.findOneByLogin(login);
			return true;
		}
		catch (NoResultException | CSecurityException e)
		{
			LOGGER.debug("User does not exist", e);
			return false;
		}
	}

}
