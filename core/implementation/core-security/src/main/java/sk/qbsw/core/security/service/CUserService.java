package sk.qbsw.core.security.service;

import java.util.List;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * Service for user management.
 *
 * @author Tomas Leken
 * @author Michal Lacko
 * @version 1.0.0
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

	/* (non-Javadoc)
	 * @see sk.qbsw.stavbyveduci.management.service.IUserService#getUsers()
	 */
	@Transactional (readOnly = true)
	public List<CUser> getUsers ()
	{
		return userDao.findAllUsers();
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
	 * @see sk.qbsw.core.security.service.IUserService#getUserByLogin(java.lang.String)
	 */
	@Transactional (readOnly = true)
	public CUser getUserByLogin (String login)
	{
		return userDao.findByLogin(login);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getUserByLoginNull(java.lang.String)
	 */
	@Transactional (readOnly = true)
	public CUser getUserByLoginNull (String login)
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
	 * @see sk.qbsw.core.security.service.IUserService#getUsers(sk.qbsw.core.security.model.domain.COrganization, java.lang.Boolean, sk.qbsw.core.security.model.domain.CGroup)
	 */
	@Transactional (readOnly = true)
	public List<CUser> getUsers (COrganization organization, Boolean enabled, CGroup group)
	{

		return userDao.findAllUsers(organization, enabled, group);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#updateUser(sk.qbsw.core.security.model.domain.CUser)
	 */
	@Transactional
	public void updateUser (CUser user)
	{
		userDao.merge(user);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#get(java.lang.Long)
	 */
	@Transactional (readOnly = true)
	public CUser get (Long id)
	{
		return userDao.findById(id);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#registerNewUser(sk.qbsw.core.security.model.domain.CUser, sk.qbsw.core.security.model.domain.COrganization)
	 */
	@Transactional (readOnly = false)
	public void registerNewUser (CUser user, COrganization organization)
	{
		user.setPasswordDigest(generatePasswordDigest(user.getPassword()));
		user.setPassword(null);
		user.setOrganization(organization);
		userDao.persit(user);
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
	 * @see sk.qbsw.core.security.service.IUserService#changePassword(sk.qbsw.core.security.model.domain.CUser, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = false)
	public void changePassword (CUser user, String password)
	{
		CUser userToSave = userDao.findById(user.getPkId());
		userToSave.setPassword(null);

		userToSave.setPasswordDigest(generatePasswordDigest(password));

		userDao.persit(userToSave);
	}

	/**
	 * Generate password digest.
	 *
	 * @param password the password
	 * @return the string
	 */
	private String generatePasswordDigest (String password)
	{
		ConfigurablePasswordEncryptor passwordEncryptor2 = new ConfigurablePasswordEncryptor();
		passwordEncryptor2.setAlgorithm("SHA-1");
		passwordEncryptor2.setPlainDigest(true);
		return passwordEncryptor2.encryptPassword(password);
	}
}
