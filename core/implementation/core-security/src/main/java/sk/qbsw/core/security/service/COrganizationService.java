package sk.qbsw.core.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.security.dao.IAddressDao;
import sk.qbsw.core.security.dao.IGroupDao;
import sk.qbsw.core.security.dao.IOrganizationDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.model.domain.CAddress;
import sk.qbsw.core.security.model.domain.CLicense;
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
@Service("cOrganizationService")
public class COrganizationService extends AService implements IOrganizationService
{

	/** The license generator. */
	@Autowired
	private ILicenseGenerator licenseGenerator;

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
	public void registerNewOrganization(COrganization organization, CUser user, String group)
	{
		organization.setFlagEnabled(true);
		organizationDao.update(organization);

		user.addGroup(groupDao.findByCode(group).get(0));
		user.setFlagEnabled(true);
		userDao.update(user);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IOrganizationService#registerNewOrganization(sk.qbsw.core.security.model.domain.COrganization, sk.qbsw.core.security.model.domain.CUser)
	 */
	@Override
	@Transactional
	public void registerNewOrganization(COrganization organization, CUser user)
	{
		organization.setFlagEnabled(true);
		organizationDao.update(organization);

		user.addGroup(groupDao.findByCode("ADMINISTRATOR").get(0));
		user.setOrganization(organization);
		user.setFlagEnabled(true);
		userDao.update(user);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IOrganizationService#getOrganizationByNameNull(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public COrganization getOrganizationByNameNull(String name)
	{
		return organizationDao.findByNameNull(name);
	}

	//TODO: REWRITE
	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IOrganizationService#getOrganizationByGPS(java.lang.Float, java.lang.Float)
	 */
	@Override
	@Deprecated
	@Transactional(readOnly = true)
	public COrganization getOrganizationByGPS(Float longitude, Float latitude)
	{
		return organizationDao.findById(2L);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IOrganizationService#getOrganizations()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<COrganization> getOrganizations()
	{
		return organizationDao.findAll();
	}

	/**
	 * Disable organization.
	 *
	 * @param org the org
	 * @see sk.qbsw.core.security.service.ISecurityService#disableOrganization(sk.qbsw.core.security.model.domain.COrganization)
	 */
	@Override
	@Transactional(readOnly = false)
	public void disableOrganization(COrganization org)
	{
		COrganization toModify = organizationDao.findById(org.getId());
		toModify.setFlagEnabled(Boolean.FALSE);
		organizationDao.update(toModify);
	}

	/**
	 * Enable organization.
	 *
	 * @param org the org
	 * @see sk.qbsw.core.security.service.ISecurityService#enableOrganization(sk.qbsw.core.security.model.domain.COrganization)
	 */
	@Override
	@Transactional(readOnly = false)
	public void enableOrganization(COrganization org)
	{
		COrganization toModify = organizationDao.findById(org.getId());
		toModify.setFlagEnabled(Boolean.TRUE);
		organizationDao.update(toModify);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IOrganizationService#getOrganizations(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<COrganization> getOrganizations(String name)
	{
		return organizationDao.findAllByName(name);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IOrganizationService#updateOrganization(sk.qbsw.core.security.model.domain.COrganization)
	 */
	@Override
	@Transactional
	public void updateOrganization(COrganization organization)
	{
		organizationDao.update(organization);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IOrganizationService#getOrganizationById(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	public COrganization getOrganizationById(Long id)
	{
		return organizationDao.findById(id);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IOrganizationService#setAddress(sk.qbsw.core.security.model.domain.COrganization, sk.qbsw.core.security.model.domain.CAddress)
	 */
	@Override
	@Transactional
	public void setAddress(COrganization organization, CAddress address)
	{
		//set address to unit
		organization.setAddress(address);

		//save entities
		addressDao.update(address);
		organizationDao.update(organization);

	}

	/**
	 * Register organization.
	 *
	 * @param org the org
	 * @param manager the manager
	 * @see sk.qbsw.core.security.service.ISecurityService#registerOrganization(sk.qbsw.core.security.model.domain.COrganization)
	 */
	@Override
	@Transactional(readOnly = false)
	public void registerOrganization(COrganization org, CUser manager)
	{
		// modify organization
		org.setFlagEnabled(Boolean.TRUE);

		// create license
		CLicense<?> license = licenseGenerator.generateFreeLicence(1);
		license.setOrganization(org);
		org.addLicence(license);

		// create new User
		manager.setFlagEnabled(Boolean.TRUE);
		manager.setName(org.getName());
		manager.setSurname("");
		manager.setMainGroup(groupDao.findById(IGroupDao.ID_ORG_ADMIN));
		manager.setOrganization(org);
		org.addUser(manager);

		// save game
		organizationDao.update(org);
	}

	/**
	 * Checks if is org name free.
	 *
	 * @param name the name
	 * @param id the pk id
	 * @return true, if is org name free
	 * @see sk.qbsw.core.security.service.ISecurityService#isOrgNameFree(java.lang.String, java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	public boolean isOrgNameFree(String name, Long id)
	{
		COrganization organization = organizationDao.findByNameNull(name);

		if (organization != null)
		{
			return organization.getId().equals(id);
		}
		return true;
	}
}
