package sk.qbsw.core.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sk.qbsw.core.security.dao.IGroupDao;
import sk.qbsw.core.security.dao.IOrganizationDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * Service for validation users
 * 
 * @author Tomas Leken
 *
 */
@Service ("cUsersValidationService")
public class CUsersValidationService implements IUsersValidationService
{

	@Autowired
	private IOrganizationDao organizationDao;

	@Autowired
	private IUserDao userDao;

	@Autowired
	private IGroupDao groupDao;

	@Override
	public Boolean isOrganizationExists (COrganization organization)
	{
		Boolean exists = false;

		COrganization organizationOld = organizationDao.findByNameNull(organization.getName());
		if (organizationOld != null && ! (organizationOld.getPkId().equals(organization.getPkId())))
		{
			exists = true;
		}

		return exists;
	}

	@Override
	public Boolean isUserExists (CUser user)
	{

		Boolean exists = false;

		CUser userOld = userDao.findByLoginNull(user.getLogin());
		if (userOld != null && ! (userOld.getPkId().equals(user.getPkId())))
		{
			exists = true;
		}

		return exists;
	}

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

	@Override
	public Boolean isUserExists (String login)
	{

		Boolean exists = false;

		CUser userOld = userDao.findByLoginNull(login);
		if (userOld != null)
		{
			exists = true;
		}

		return exists;
	}

	@Override
	public Boolean isUserExistsPin(CUser userOld) {
		Boolean exists = false;

		CUser user = userDao.findByPinNull(userOld.getPin());
		if (user != null) {
			if (!(userOld.getPkId().equals(user.getPkId()))) {
				exists = true;
			}
		}

		return exists;
	}
	
	@Override
	public Boolean isUserExistsPin(String pin) {
		Boolean exists = false;

		CUser user = userDao.findByPinNull(pin);
		if (user != null) {
			exists = true;
		}

		return exists;
	}
}
