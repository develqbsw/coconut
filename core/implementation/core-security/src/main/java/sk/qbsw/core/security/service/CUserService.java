package sk.qbsw.core.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.service.signature.IPasswordDigester;

/**
 * Service for user management.
 *
 * @author Dalibor Rak
 * @author Tomas Leken
 * @author Michal Lacko
 * @version 1.3.0
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
	
	/** Password digester **/
	@Autowired
	private IPasswordDigester digester;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#changePassword(sk.qbsw.core.security.model.domain.CUser, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = false)
	public void changePassword (CUser user, String password)
	{
		CUser userToSave = userDao.findById(user.getPkId());
		userToSave.setPassword(null);
		userToSave.setPasswordDigest(digester.generateDigest(user.getPassword()));

		userDao.persit(userToSave);
	}

	/**
	 * Disable user.
	 *
	 * @param user the user
	 * @see sk.qbsw.core.security.service.ISecurityService#disableUser(sk.qbsw.core.security.model.domain.CUser)
	 */
	@Transactional (readOnly = false)
	public void disableUser (CUser user)
	{
		CUser toModify = userDao.findById(user.getPkId());
		toModify.setFlagEnabled(Boolean.FALSE);
		userDao.persit(toModify);
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
		CUser toModify = userDao.findById(user.getPkId());
		toModify.setFlagEnabled(Boolean.TRUE);
		userDao.persit(toModify);
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

	/**
	 * Gets the user for modification including related groups.
	 *
	 * @param pkId the pk id
	 * @return the user for modification
	 * @see sk.qbsw.core.security.service.ISecurityService#getUserForModification(java.lang.Long)
	 */
	@Transactional (readOnly = true)
	public CUser getUserForModification (Long pkId)
	{
		return userDao.findForModification(pkId);
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
	 * @see sk.qbsw.core.security.service.IUserService#registerNewUser(sk.qbsw.core.security.model.domain.CUser, sk.qbsw.core.security.model.domain.COrganization)
	 */
	@Transactional (readOnly = false)
	public void registerNewUser (CUser user, COrganization organization)
	{
		user.setPasswordDigest(digester.generateDigest(user.getPassword()));
		user.setPassword(null);
		user.setOrganization(organization);
		userDao.persit(user);
	}

	/**
	 * Renew password.
	 *
	 * @param login the login
	 * @param email the email
	 * @param password the password
	 * @throws CSecurityException the c security exception
	 * @see sk.qbsw.core.security.service.ISecurityService#renewPassword(java.lang.String, java.lang.String)
	 */
	@Transactional (readOnly = false)
	public void renewPassword (String login, String email, String password) throws CSecurityException
	{
		CUser user = userDao.findByLogin(login);

		if (user == null)
		{
			throw new CSecurityException("error.security.changepassworddenied");
		}

		if (!email.equals(user.getEmail()))
		{
			throw new CSecurityException("error.security.changepassworddenied");
		}

		user.setPasswordDigest(digester.generateDigest(user.getPassword()));
		user.setPassword(null);
		userDao.persit(user);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#updateUser(sk.qbsw.core.security.model.domain.CUser)
	 */
	@Transactional
	public void updateUser (CUser user)
	{
		userDao.merge(user);
	}


}
