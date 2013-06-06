package sk.qbsw.core.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.security.dao.IGroupDao;
import sk.qbsw.core.security.dao.IOrganizationDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * Service for organization management
 * 
 * @author Tomas Leken
 * @version 1.0.0
 * @since 1.0.0
 * O
 */
@Service ("cOrganizationService")
public class COrganizationService implements IOrganizationService
{

	@Autowired
	private IOrganizationDao organizationDao;

	@Autowired
	private IUserDao userDao;

	@Autowired
	private IGroupDao groupDao;

	@Override
	@Transactional
	public void registerNewOrganization (COrganization organization, CUser user, String group)
	{
		organization.setFlagEnabled(true);
		organizationDao.save(organization);

		user.addGroup(groupDao.findByCode(group).get(0));
		user.setFlagEnabled(true);
		userDao.save(user);
	}

	@Transactional
	public void registerNewOrganization (COrganization organization, CUser user)
	{

		organization.setFlagEnabled(true);
		organizationDao.save(organization);

		user.addGroup(groupDao.findByCode("ADMINISTRATOR").get(0));
		user.setOrganization(organization);
		user.setFlagEnabled(true);
		userDao.save(user);
	}

	@Transactional (readOnly = true)
	public COrganization getOrganizationByNameNull (String name)
	{
		return organizationDao.findByNameNull(name);
	}

	//TODO: REWRITE
	@Transactional (readOnly = true)
	public COrganization getOrganizationByGPS (Float longitude, Float latitude)
	{
		return organizationDao.findById(2L);
	}

	@Override
	@Transactional (readOnly = true)
	public List<COrganization> getOrganizations ()
	{
		return organizationDao.findAll();
	}

	@Override
	@Transactional
	public void updateOrganization (COrganization organization)
	{
		organizationDao.save(organization);
	}

	@Override
	public COrganization getOrganizationById (Long id)
	{
		return organizationDao.findById(id);
	}

}
