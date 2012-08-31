package sk.qbsw.core.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * Service for user management
 * 
 * @author Tomas Leken
 * @author Michal Lacko
 * @version 1.0.0
 * @since 1.0.0
 * 
 */
@Service ("cUserService")
public class CUserService implements IUserService
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

	@Transactional (readOnly = true)
	public CUser getUserByLogin (String login)
	{
		return userDao.findByLogin(login);
	}

	@Transactional (readOnly = true)
	public CUser getUserByLoginNull (String login)
	{
		return userDao.findByLoginNull(login);
	}

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


	@Transactional (readOnly = true)
	public List<CUser> getUsers (COrganization organization, Boolean enabled, CGroup group)
	{

		return userDao.findAllUsers(organization, enabled, group);
	}

	@Transactional
	public void updateUser (CUser user)
	{
		userDao.merge(user);
	}

	@Transactional (readOnly = true)
	public CUser get (Long id)
	{
		return userDao.findById(id);
	}

	@Transactional
	public void registerNewUser (CUser user, COrganization organization)
	{
		user.setOrganization(organization);
		userDao.persit(user);
	}

	public List<CUser> getOtherActiveUsers (COrganization organization, CGroup group, CUser user)
	{
		return userDao.getOtherActiveUsers(organization, group, user);
	}

}
