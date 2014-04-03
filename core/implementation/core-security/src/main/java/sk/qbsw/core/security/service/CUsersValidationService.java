package sk.qbsw.core.security.service;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 * @version 1.7.2
 * @since 1.0.0
 */
@Service ("cUsersValidationService")
public class CUsersValidationService implements IUsersValidationService
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
		Boolean exists = false;

		COrganization organizationOld = organizationDao.findByNameNull(organization.getName());
		if (organizationOld != null && ! (organizationOld.getId().equals(organization.getId())))
		{
			exists = true;
		}

		return exists;
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
		Boolean exists = false;

		COrganization organizationOld = organizationDao.findByNameNull(name);
		if (organizationOld != null)
		{
			exists = true;
		}

		return exists;
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
		CAuthenticationParams oldUserAuthParams = authParamsDao.findByUserId(userOld.getId());

		CUser user = userDao.findByPinNull(oldUserAuthParams.getPin());
		if (user != null)
		{
			if (! (userOld.getId().equals(user.getId())))
			{
				exists = true;
			}
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

		CUser user = userDao.findByPinNull(pin);
		if (user != null)
		{
			exists = true;
		}

		return exists;
	}
}
