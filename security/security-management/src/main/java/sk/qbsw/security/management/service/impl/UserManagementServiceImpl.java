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
import sk.qbsw.security.core.dao.UserDao;
import sk.qbsw.security.core.model.domain.CAddress;
import sk.qbsw.security.core.model.domain.CAuthenticationParams;
import sk.qbsw.security.core.model.domain.CGroup;
import sk.qbsw.security.core.model.domain.COrganization;
import sk.qbsw.security.core.model.domain.CRole;
import sk.qbsw.security.core.model.domain.CUser;
import sk.qbsw.security.core.model.filter.CUserAssociationsFilter;
import sk.qbsw.security.core.model.filter.CUserDetailFilter;
import sk.qbsw.security.core.model.order.COrderModel;
import sk.qbsw.security.core.model.order.COrderSpecification;
import sk.qbsw.security.core.model.order.EOrderSpecifier;
import sk.qbsw.security.core.model.order.EOrganizationOrderByAttributeSpecifier;
import sk.qbsw.security.core.model.order.EUserOrderByAttributeSpecifier;
import sk.qbsw.security.core.model.order.IOrderByAttributeSpecifier;
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
	private UserDao userDao;

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
	 * @see sk.qbsw.security.core.core.management.service.ISecurityService#disableUser(sk.qbsw.security.core.core.model.domain.CUser)
	 */
	@Override
	@Transactional (readOnly = false)
	public void disableUser (CUser user)
	{
		CUser toModify = userDao.findById(user.getId());
		toModify.setFlagEnabled(Boolean.FALSE);
		userDao.update(toModify);
	}

	/**
	 * Enable user.
	 *
	 * @param user the user
	 * @see sk.qbsw.security.core.core.management.service.ISecurityService#enableUser(sk.qbsw.security.core.core.model.domain.CUser)
	 */
	@Override
	@Transactional (readOnly = false)
	public void enableUser (CUser user)
	{
		CUser toModify = userDao.findById(user.getId());
		toModify.setFlagEnabled(Boolean.TRUE);
		userDao.update(toModify);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#get(java.lang.Long)
	 */
	@Override
	@Transactional (readOnly = true)
	public CUser get (Long id)
	{
		return userDao.findById(id);
	}

	/**
	 * Gets the all users.
	 *
	 * @param organization the organization
	 * @return the all users
	 * @see sk.qbsw.security.core.core.management.service.ISecurityService#getAllUsers(sk.qbsw.security.core.core.model.domain.COrganization)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<CUser> getAllUsers (COrganization organization)
	{
		CUserAssociationsFilter filter = new CUserAssociationsFilter();
		filter.setOrganization(organization);

		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.LOGIN, EOrderSpecifier.ASC));

		return userDao.findByUserAssociationsFilter(filter, orderModel);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#getOtherActiveUsers(sk.qbsw.security.core.core.model.domain.COrganization, sk.qbsw.security.core.core.model.domain.CGroup, sk.qbsw.security.core.core.model.domain.CUser)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<CUser> getOtherActiveUsers (COrganization organization, CGroup group, CUser user)
	{
		CUserAssociationsFilter filter = new CUserAssociationsFilter();
		filter.setOrganization(organization);
		filter.setGroup(group);
		filter.setExcludedUser(user);
		filter.setEnabled(true);

		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.LOGIN, EOrderSpecifier.ASC));

		return userDao.findByUserAssociationsFilter(filter, orderModel);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#getUserByLogin(java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public CUser getUserByLogin (String login) throws CSecurityException
	{
		return userDao.findOneByLogin(login);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#getUserForModification(java.lang.Long)
	 */
	@Override
	@Transactional (readOnly = true)
	public CUser getUserForModification (Long id)
	{
		return userDao.findForModification(id);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.stavbyveduci.management.service.IUserService#getUsers()
	 */
	@Override
	@Transactional (readOnly = true)
	public List<CUser> getUsers ()
	{
		return userDao.findAll();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#getUsers(sk.qbsw.security.core.core.model.domain.COrganization, java.lang.Boolean)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<CUser> getUsers (COrganization organization, Boolean enabled)
	{
		CUserAssociationsFilter filter = new CUserAssociationsFilter();
		filter.setOrganization(organization);
		filter.setEnabled(enabled);

		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.LOGIN, EOrderSpecifier.ASC));

		return userDao.findByUserAssociationsFilter(filter, orderModel);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#getUsersOrderByOrganization(sk.qbsw.security.core.core.model.domain.COrganization, java.lang.Boolean, sk.qbsw.security.core.core.model.domain.CGroup)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<CUser> getUsersOrderByOrganization (COrganization organization, Boolean enabled, CGroup group)
	{
		CUserAssociationsFilter filter = new CUserAssociationsFilter();
		filter.setOrganization(organization);
		filter.setEnabled(enabled);
		filter.setGroup(group);

		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EOrganizationOrderByAttributeSpecifier.NAME, EOrderSpecifier.ASC));
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.LOGIN, EOrderSpecifier.ASC));

		return userDao.findByUserAssociationsFilter(filter, orderModel);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#getUsers(sk.qbsw.security.core.core.model.domain.COrganization, java.lang.Boolean, sk.qbsw.security.core.core.model.domain.CGroup)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<CUser> getUsers (COrganization organization, Boolean enabled, CGroup group)
	{
		CUserAssociationsFilter filter = new CUserAssociationsFilter();
		filter.setOrganization(organization);
		filter.setEnabled(enabled);
		filter.setGroup(group);

		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.LOGIN, EOrderSpecifier.ASC));

		return userDao.findByUserAssociationsFilter(filter, orderModel);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.stavbyveduci.management.service.IUserService#getUsers(sk.qbsw.winnetou.security.model.COrganization)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<CUser> getUsers (COrganization organization, CRole role)
	{
		if (role != null)
		{
			CUserAssociationsFilter filter = new CUserAssociationsFilter();
			filter.setOrganization(organization);
			filter.setRole(role);

			COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<>();
			orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.LOGIN, EOrderSpecifier.ASC));

			return userDao.findByUserAssociationsFilter(filter, orderModel);
		}
		else
		{
			CUserAssociationsFilter filter = new CUserAssociationsFilter();
			filter.setOrganization(organization);

			COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<>();
			orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.LOGIN, EOrderSpecifier.ASC));

			return userDao.findByUserAssociationsFilter(filter, orderModel);
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#getUsers(java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<CUser> getUsers (String name, String surname, String login, Boolean enabled)
	{
		CUserDetailFilter filter = new CUserDetailFilter();
		filter.setName(name);
		filter.setSurname(surname);
		filter.setLogin(login);
		filter.setEnabled(enabled);

		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.LOGIN, EOrderSpecifier.ASC));

		return userDao.findByUserDetailFilter(filter, orderModel);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#getUsers(java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean, sk.qbsw.security.core.core.model.domain.COrganization)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<CUser> getUsers (String name, String surname, String login, Boolean enabled, COrganization organization)
	{
		CUserDetailFilter filter = new CUserDetailFilter();
		filter.setName(name);
		filter.setSurname(surname);
		filter.setLogin(login);
		filter.setEnabled(enabled);
		filter.setOrganization(organization);

		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.LOGIN, EOrderSpecifier.ASC));

		return userDao.findByUserDetailFilter(filter, orderModel);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#getUsers(java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<CUser> getUsers (String name, String surname, String login, Boolean enabled, String groupPrefix)
	{
		CUserDetailFilter filter = new CUserDetailFilter();
		filter.setName(name);
		filter.setSurname(surname);
		filter.setLogin(login);
		filter.setEnabled(enabled);
		filter.setGroupCodePrefix(groupPrefix);

		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.LOGIN, EOrderSpecifier.ASC));

		return userDao.findByUserDetailFilter(filter, orderModel);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#getUsers(java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<CUser> getUsers (String email)
	{
		CUserDetailFilter filter = new CUserDetailFilter();
		filter.setEmail(email);

		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.LOGIN, EOrderSpecifier.ASC));

		return userDao.findByUserDetailFilter(filter, orderModel);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#registerNewUser(sk.qbsw.security.core.core.model.domain.CUser, sk.qbsw.security.core.core.model.domain.COrganization)
	 */
	@Override
	@Transactional (readOnly = false)
	public void registerNewUser (CUser user, @CNotLogged @CNotAuditLogged String password, COrganization organization) throws CSecurityException
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
	public void registerNewUser (CUser user, COrganization organization) throws CSecurityException
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

		COrganization redOrg;
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
		CAuthenticationParams authParams = new CAuthenticationParams();
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
	public void updateUser (CUser user)
	{
		userDao.update(user);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#setAddress(sk.qbsw.security.core.core.model.domain.CUser, sk.qbsw.security.core.core.model.domain.CAddress)
	 */
	@Override
	@Transactional
	public void setAddress (CUser user, CAddress address)
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
