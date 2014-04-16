package sk.qbsw.core.security.service;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.security.dao.IAddressDao;
import sk.qbsw.core.security.dao.IAuthenticationParamsDao;
import sk.qbsw.core.security.dao.IOrganizationDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.domain.CAddress;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * Service for user management.
 *
 * @author Dalibor Rak
 * @author Tomas Leken
 * @author Michal Lacko
 * @author Tomas Lauro
 * @version 1.8.0
 * @since 1.0.0
 */
@Service ("cUserService")
public class CUserService implements IUserService
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The user dao. */
	@Autowired
	private IUserDao userDao;

	/** The organization dao. */
	@Autowired
	private IOrganizationDao organizationDao;

	/** The authentication params dao. */
	@Autowired
	private IAuthenticationParamsDao authenticationParamsDao;

	/** The address dao */
	@Autowired
	private IAddressDao addressDao;

	/** The authentication service. */
	@Autowired
	private IAuthenticationService authenticationService;

	/**
	 * Disable user.
	 *
	 * @param user the user
	 * @see sk.qbsw.core.security.service.ISecurityService#disableUser(sk.qbsw.core.security.model.domain.CUser)
	 */
	@Transactional (readOnly = false)
	public void disableUser (CUser user)
	{
		CUser toModify = userDao.findById(user.getId());
		toModify.setFlagEnabled(Boolean.FALSE);
		userDao.save(toModify);
	}

	/**
	 * Enable user.
	 *
	 * @param user the user
	 * @see sk.qbsw.core.security.service.ISecurityService#enableUser(sk.qbsw.core.security.model.domain.CUser)
	 */
	@Transactional (readOnly = false)
	public void enableUser (CUser user)
	{
		CUser toModify = userDao.findById(user.getId());
		toModify.setFlagEnabled(Boolean.TRUE);
		userDao.save(toModify);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#get(java.lang.Long)
	 */
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
	 * @see sk.qbsw.core.security.service.ISecurityService#getAllUsers(sk.qbsw.core.security.model.domain.COrganization)
	 */
	@Transactional (readOnly = true)
	public List<CUser> getAllUsers (COrganization organization)
	{
		return userDao.findAllUsers(organization);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getOtherActiveUsers(sk.qbsw.core.security.model.domain.COrganization, sk.qbsw.core.security.model.domain.CGroup, sk.qbsw.core.security.model.domain.CUser)
	 */
	@Transactional (readOnly = true)
	public List<CUser> getOtherActiveUsers (COrganization organization, CGroup group, CUser user)
	{
		return userDao.getOtherActiveUsers(organization, group, user);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getUserByLogin(java.lang.String)
	 */
	@Transactional (readOnly = true)
	public CUser getUserByLogin (String login)
	{
		return userDao.findByLogin(login);
	}


	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getUserByPin(java.lang.String)
	 */
	@Transactional (readOnly = true)
	public CUser getUserByPin (String pin)
	{
		CUser user = userDao.findByPinNull(pin);
		if (user.getFlagEnabled())
		{
			return user;
		}
		else
		{
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getUserForModification(java.lang.Long)
	 */
	@Transactional (readOnly = true)
	public CUser getUserForModification (Long id)
	{
		return userDao.findForModification(id);
	}


	/* (non-Javadoc)
	 * @see sk.qbsw.stavbyveduci.management.service.IUserService#getUsers()
	 */
	@Transactional (readOnly = true)
	public List<CUser> getUsers ()
	{
		return userDao.findAllUsers();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getUsers(sk.qbsw.core.security.model.domain.COrganization, java.lang.Boolean)
	 */
	@Transactional (readOnly = true)
	public List<CUser> getUsers (COrganization organization, Boolean enabled)
	{
		return userDao.findAllUsers(organization, enabled);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getUsersOrderByOrganization(sk.qbsw.core.security.model.domain.COrganization, java.lang.Boolean, sk.qbsw.core.security.model.domain.CGroup)
	 */
	@Transactional (readOnly = true)
	public List<CUser> getUsersOrderByOrganization (COrganization organization, Boolean enabled, CGroup group)
	{
		return userDao.findAllUsersOrderByOrganization(organization, enabled, group);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getUsers(sk.qbsw.core.security.model.domain.COrganization, java.lang.Boolean, sk.qbsw.core.security.model.domain.CGroup)
	 */
	@Transactional (readOnly = true)
	public List<CUser> getUsers (COrganization organization, Boolean enabled, CGroup group)
	{
		return userDao.findAllUsers(organization, enabled, group);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.stavbyveduci.management.service.IUserService#getUsers(sk.qbsw.winnetou.security.model.COrganization)
	 */
	@Transactional (readOnly = true)
	public List<CUser> getUsers (COrganization organization, CRole role)
	{
		if (role != null)
		{
			return userDao.findAllUsersByRole(organization, role);
		}
		else
		{
			return userDao.findAllUsers(organization);
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getUsers(java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean)
	 */
	@Transactional (readOnly = true)
	public List<CUser> getUsers (String name, String surname, String login, Boolean enabled)
	{
		return userDao.findAllUsers(name, surname, login, enabled);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getUsers(java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean, java.lang.String)
	 */
	@Transactional (readOnly = true)
	public List<CUser> getUsers (String name, String surname, String login, Boolean enabled, String groupPrefix)
	{
		return userDao.findAllUsers(name, surname, login, enabled, groupPrefix);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getUsers(java.lang.String)
	 */
	@Transactional (readOnly = true)
	public List<CUser> getUsers (String email)
	{
		return userDao.findAllUsers(email);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#registerNewUser(sk.qbsw.core.security.model.domain.CUser, sk.qbsw.core.security.model.domain.COrganization)
	 */
	@Transactional (readOnly = false)
	public void registerNewUser (CUser user, String password, COrganization organization) throws CSecurityException
	{
		try
		{
			//checks if user already exists
			userDao.findByLogin(user.getLogin());
			throw new CSecurityException("User with login " + user.getLogin() + " already exists", "error.security.loginused");
		}
		catch (NoResultException nre)
		{
			//do nothing
		}

		if (user.getLogin() == null || password == null || organization == null || organization.getName() == null)
		{
			throw new CSecurityException("Not enough parameter to create user. The login and plain text password are required", "error.security.loginused");
		}

		try
		{
			organization = organizationDao.findByName(organization.getName());
		}
		catch (NoResultException nre)
		{
			throw new CSecurityException("Security exception", "error.security.invalidOrganization");
		}

		user.setOrganization(organization);
		userDao.save(user);

		//set password and save
		authenticationService.changeEncryptedPassword(user.getLogin(), password);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#updateUser(sk.qbsw.core.security.model.domain.CUser)
	 */
	@Transactional
	public void updateUser (CUser user)
	{
		userDao.save(user);
	}

	@Transactional
	public void setAddress (CUser user, CAddress address)
	{
		//set address to user
		user.setAddress(address);

		//save entities
		addressDao.save(address);
		userDao.save(user);

	}
}
