package sk.qbsw.security.management.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.service.AService;
import sk.qbsw.security.core.dao.AddressDao;
import sk.qbsw.security.core.dao.GroupDao;
import sk.qbsw.security.core.dao.OrganizationDao;
import sk.qbsw.security.core.dao.UserDao;
import sk.qbsw.security.core.model.domain.Address;
import sk.qbsw.security.core.model.domain.License;
import sk.qbsw.security.core.model.domain.Organization;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.core.service.LicenseGenerator;
import sk.qbsw.security.management.service.OrganizationService;

/**
 * Service for organization management.
 *
 * @author Tomas Leken
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.0.0
 */
@Service("cOrganizationService")
public class OrganizationServiceImpl extends AService implements OrganizationService
{

	/** The license generator. */
	@Autowired
	private LicenseGenerator licenseGenerator;

	/** The organization dao. */
	@Autowired
	private OrganizationDao organizationDao;

	/** The user dao. */
	@Autowired
	private UserDao userDao;

	/** The group dao. */
	@Autowired
	private GroupDao groupDao;

	/** The address dao. */
	@Autowired
	private AddressDao addressDao;

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IOrganizationService#registerNewOrganization(sk.qbsw.security.core.core.model.domain.COrganization, sk.qbsw.security.core.core.model.domain.CUser, java.lang.String)
	 */
	@Override
	@Transactional
	public void registerNewOrganization(Organization organization, User user, String group)
	{
		organization.setFlagEnabled(true);
		organizationDao.update(organization);

		user.addGroup(groupDao.findByCode(group).get(0));
		user.setFlagEnabled(true);
		userDao.update(user);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IOrganizationService#registerNewOrganization(sk.qbsw.security.core.core.model.domain.COrganization, sk.qbsw.security.core.core.model.domain.CUser)
	 */
	@Override
	@Transactional
	public void registerNewOrganization(Organization organization, User user)
	{
		organization.setFlagEnabled(true);
		organizationDao.update(organization);

		user.addGroup(groupDao.findByCode("ADMINISTRATOR").get(0));
		user.setOrganization(organization);
		user.setFlagEnabled(true);
		userDao.update(user);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IOrganizationService#getOrganizationByNameNull(java.lang.String)
	 */
	@Deprecated
	@Override
	@Transactional(readOnly = true)
	public Organization getOrganizationByNameNull(String name)
	{
		return organizationDao.findOneByName(name);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IOrganizationService#getOrganizationByName(java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<Organization> getOrganizationByName (String name)
	{
		return organizationDao.findByName(name);
	}

	//TODO: REWRITE
	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IOrganizationService#getOrganizationByGPS(java.lang.Float, java.lang.Float)
	 */
	@Override
	@Deprecated
	@Transactional(readOnly = true)
	public Organization getOrganizationByGPS(Float longitude, Float latitude)
	{
		return organizationDao.findById(2L);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IOrganizationService#getOrganizations()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Organization> getOrganizations()
	{
		return organizationDao.findAll();
	}

	/**
	 * Disable organization.
	 *
	 * @param org the org
	 * @see sk.qbsw.security.core.core.management.service.ISecurityService#disableOrganization(sk.qbsw.security.core.Organization.model.domain.COrganization)
	 */
	@Override
	@Transactional(readOnly = false)
	public void disableOrganization(Organization org)
	{
		Organization toModify = organizationDao.findById(org.getId());
		toModify.setFlagEnabled(Boolean.FALSE);
		organizationDao.update(toModify);
	}

	/**
	 * Enable organization.
	 *
	 * @param org the org
	 * @see sk.qbsw.security.core.core.management.service.ISecurityService#enableOrganization(sk.qbsw.security.core.Organization.model.domain.COrganization)
	 */
	@Override
	@Transactional(readOnly = false)
	public void enableOrganization(Organization org)
	{
		Organization toModify = organizationDao.findById(org.getId());
		toModify.setFlagEnabled(Boolean.TRUE);
		organizationDao.update(toModify);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IOrganizationService#getOrganizations(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Organization> getOrganizations(String name)
	{
		return organizationDao.findByName(name);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IOrganizationService#updateOrganization(sk.qbsw.security.core.core.model.domain.COrganization)
	 */
	@Override
	@Transactional
	public void updateOrganization(Organization organization)
	{
		organizationDao.update(organization);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IOrganizationService#getOrganizationById(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	public Organization getOrganizationById(Long id)
	{
		return organizationDao.findById(id);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IOrganizationService#setAddress(sk.qbsw.security.core.core.model.domain.COrganization, sk.qbsw.security.core.core.model.domain.CAddress)
	 */
	@Override
	@Transactional
	public void setAddress(Organization organization, Address address)
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
	 * @see sk.qbsw.security.core.core.management.service.ISecurityService#registerOrganization(sk.qbsw.security.core.Organization.model.domain.COrganization)
	 */
	@Override
	@Transactional(readOnly = false)
	public void registerOrganization(Organization org, User manager)
	{
		// modify organization
		org.setFlagEnabled(Boolean.TRUE);

		// create license
		License<?> license = licenseGenerator.generateFreeLicence(1);
		license.setOrganization(org);
		org.addLicence(license);

		// create new User
		manager.setFlagEnabled(Boolean.TRUE);
		manager.setName(org.getName());
		manager.setSurname("");
		manager.setMainGroup(groupDao.findById(GroupDao.ID_ORG_ADMIN));
		manager.setOrganization(org);
		org.addUser(manager);

		// save game
		organizationDao.update(org);
	}
}
