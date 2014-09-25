package sk.qbsw.core.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.security.dao.IAddressDao;
import sk.qbsw.core.security.dao.IGroupDao;
import sk.qbsw.core.security.dao.IOrganizationDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.model.domain.CAddress;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * Service for organization management.
 *
 * @author Tomas Leken
 * @author Tomas Lauro
 * 
 * @version 1.11.5
 * @since 1.0.0
 */
@Service ("cOrganizationService")
public class COrganizationService implements IOrganizationService
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

	/** The address dao. */
	@Autowired
	private IAddressDao addressDao;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IOrganizationService#registerNewOrganization(sk.qbsw.core.security.model.domain.COrganization, sk.qbsw.core.security.model.domain.CUser, java.lang.String)
	 */
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

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IOrganizationService#registerNewOrganization(sk.qbsw.core.security.model.domain.COrganization, sk.qbsw.core.security.model.domain.CUser)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IOrganizationService#getOrganizationByNameNull(java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public COrganization getOrganizationByNameNull (String name)
	{
		return organizationDao.findByNameNull(name);
	}

	//TODO: REWRITE
	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IOrganizationService#getOrganizationByGPS(java.lang.Float, java.lang.Float)
	 */
	@Override
	@Deprecated
	@Transactional (readOnly = true)
	public COrganization getOrganizationByGPS (Float longitude, Float latitude)
	{
		return organizationDao.findById(2L);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IOrganizationService#getOrganizations()
	 */
	@Override
	@Transactional (readOnly = true)
	public List<COrganization> getOrganizations ()
	{
		return organizationDao.findAll();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IOrganizationService#getOrganizations(java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<COrganization> getOrganizations (String name)
	{
		return organizationDao.findAllByName(name);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IOrganizationService#updateOrganization(sk.qbsw.core.security.model.domain.COrganization)
	 */
	@Override
	@Transactional
	public void updateOrganization (COrganization organization)
	{
		organizationDao.save(organization);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IOrganizationService#getOrganizationById(java.lang.Long)
	 */
	@Override
	@Transactional (readOnly = true)
	public COrganization getOrganizationById (Long id)
	{
		return organizationDao.findById(id);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IOrganizationService#setAddress(sk.qbsw.core.security.model.domain.COrganization, sk.qbsw.core.security.model.domain.CAddress)
	 */
	@Override
	@Transactional
	public void setAddress (COrganization organization, CAddress address)
	{
		//set address to unit
		organization.setAddress(address);

		//save entities
		addressDao.save(address);
		organizationDao.save(organization);

	}
}
