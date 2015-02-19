package sk.qbsw.core.security.service;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.security.dao.IAuthenticationParamsDao;
import sk.qbsw.core.security.dao.IGroupDao;
import sk.qbsw.core.security.dao.IOrganizationDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.model.domain.CAuthenticationParams;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * Service for validation users.
 *
 * @author Tomas Leken
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.0.0
 */
@Service ("cUsersValidationService")
public class CUsersValidationService extends AService implements IUsersValidationService
{
	/** The organization dao. */
	@Autowired
	private IOrganizationDao organizationDao;

	/** The user dao. */
	@Autowired
	private IUserDao userDao;

	/** The group dao. */
	@Autowired
	private IGroupDao groupDao;

	/** The auth params dao. */
	@Autowired
	private IAuthenticationParamsDao authParamsDao;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUsersValidationService#isOrganizationExists(sk.qbsw.core.security.model.domain.COrganization)
	 */
	@Override
	public Boolean isOrganizationExists (COrganization organization)
	{
		//checks if there is an organization with id exists
		if (organization.getId() != null)
		{
			COrganization persistedOrganization = organizationDao.findById(organization.getId());
			if (persistedOrganization != null)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			//checks if there is an organization with given name
			return isOrganizationExists(organization.getName());
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUsersValidationService#isUserExists(sk.qbsw.core.security.model.domain.CUser)
	 */
	@Override
	public Boolean isUserExists (CUser user)
	{
		Boolean exists = false;
		CUser userOld;

		try
		{
			userOld = userDao.findByLogin(user.getLogin());
		}
		catch (NoResultException nre)
		{
			userOld = null;
		}

		if (userOld != null && ! (userOld.getId().equals(user.getId())))
		{
			exists = true;
		}

		return exists;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUsersValidationService#leastOneAdmin(sk.qbsw.core.security.model.domain.CUser, sk.qbsw.core.security.model.domain.COrganization, java.lang.String)
	 */
	public Boolean leastOneAdmin (CUser user, COrganization organization, String group)
	{

		Boolean leastOneAdmin = false;

		CGroup adminGroup = groupDao.findByCode(group).get(0);

		List<CUser> users = userDao.getOtherActiveUsers(organization, adminGroup, user);

		if (users.isEmpty() && (!user.getGroups().iterator().next().getCode().equals(adminGroup.getCode()) || !user.getFlagEnabled()))
		{
			leastOneAdmin = true;
		}

		return leastOneAdmin;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUsersValidationService#isOrganizationExists(java.lang.String)
	 */
	@Override
	public Boolean isOrganizationExists (String name)
	{
		if (name != null)
		{
			List<COrganization> persistedOrganizations = organizationDao.findByName(name);
			if (persistedOrganizations != null && persistedOrganizations.isEmpty() == false)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUsersValidationService#isUserExists(java.lang.String)
	 */
	@Override
	public Boolean isUserExists (String login)
	{

		Boolean exists = false;

		CUser userOld;

		try
		{
			userOld = userDao.findByLogin(login);
		}
		catch (NoResultException nre)
		{
			userOld = null;
		}

		if (userOld != null)
		{
			exists = true;
		}

		return exists;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUsersValidationService#isUserExistsPin(sk.qbsw.core.security.model.domain.CUser)
	 */
	@Override
	public Boolean isUserExistsPin (CUser userOld)
	{
		Boolean exists = false;
		CAuthenticationParams oldUserAuthParams = authParamsDao.findOneByUserId(userOld.getId());

		try
		{
			List<CUser> users = userDao.findByPin(oldUserAuthParams.getPin());

			//if the users is not empty - any user has assigned given pin
			if (users != null && users.isEmpty() == false)
			{
				exists = true;
			}
		}
		catch (CBusinessException e)
		{
			//the user with pin null does not exists
		}

		return exists;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUsersValidationService#isUserExistsPin(java.lang.String)
	 */
	@Override
	public Boolean isUserExistsPin (String pin)
	{
		Boolean exists = false;

		try
		{
			List<CUser> users = userDao.findByPin(pin);

			if (users != null && users.isEmpty() == false)
			{
				exists = true;
			}
		}
		catch (CBusinessException e)
		{
			//the user with pin null does not exists
		}

		return exists;
	}
}
