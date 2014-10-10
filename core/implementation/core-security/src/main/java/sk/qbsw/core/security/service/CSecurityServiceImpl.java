/**
 * 
 */
package sk.qbsw.core.security.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.service.CService;
import sk.qbsw.core.security.dao.IGroupDao;
import sk.qbsw.core.security.dao.ILicenseDao;
import sk.qbsw.core.security.dao.IOrganizationDao;
import sk.qbsw.core.security.dao.IRoleDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.CLicense;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.jmx.CLicensingRules;

/**
 * The Class CSecurityServiceImpl.
 *
 * @author Dalibor Rak
 * @version 1.2.1
 * @since 1.0.0
 */
@Service (value = "securityService")
public class CSecurityServiceImpl extends CService implements ISecurityService
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	/** The group dao. */
	private IRoleDao roleDao;

	/** The group dao. */
	@Autowired
	private IGroupDao groupDao;

	/** License dao*. */
	@Autowired
	private ILicenseDao licenseDao;

	/** The license generator. */
	@Autowired
	private ILicenseGenerator licenseGenerator;

	/** The org dao. */
	@Autowired
	private IOrganizationDao orgDao;

	/** The user dao. */
	@Autowired
	private IUserDao userDao;

	/** The rules. */
	@Autowired
	private CLicensingRules rules;

	public void setRules (CLicensingRules rules)
	{
		this.rules = rules;
	}

	public CLicensingRules getRules ()
	{
		return this.rules;
	}

	/**
	 * Adds the license.
	 *
	 * @param license the license
	 * @see sk.qbsw.core.security.service.ISecurityService#addLicense(sk.qbsw.core.security.model.domain.CLicense)
	 */
	@Transactional (readOnly = false)
	public void addLicense (CLicense<?> license)
	{
		license.recalculateLicensePrice(rules.getDayPricing());
		licenseDao.save(license);
	}


	/**
	 * Delete license.
	 *
	 * @param license the license
	 * @see sk.qbsw.core.security.service.ISecurityService#deleteLicense(sk.qbsw.core.security.model.domain.CLicense)
	 */
	@Transactional (readOnly = false)
	public void deleteLicense (CLicense<?> license)
	{
		CLicense<?> toModify = licenseDao.findById(license.getId());
		licenseDao.remove(toModify);
	}


	/**
	 * Disable organization.
	 *
	 * @param org the org
	 * @see sk.qbsw.core.security.service.ISecurityService#disableOrganization(sk.qbsw.core.security.model.domain.COrganization)
	 */
	@Transactional (readOnly = false)
	public void disableOrganization (COrganization org)
	{
		COrganization toModify = orgDao.findById(org.getId());
		toModify.setFlagEnabled(Boolean.FALSE);
		orgDao.save(toModify);
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
		CUser toModify = userDao.findById(user.getId());
		toModify.setFlagEnabled(Boolean.FALSE);
		userDao.save(toModify);
	}


	/**
	 * Enable organization.
	 *
	 * @param org the org
	 * @see sk.qbsw.core.security.service.ISecurityService#enableOrganization(sk.qbsw.core.security.model.domain.COrganization)
	 */
	@Transactional (readOnly = false)
	public void enableOrganization (COrganization org)
	{
		COrganization toModify = orgDao.findById(org.getId());
		toModify.setFlagEnabled(Boolean.TRUE);
		orgDao.save(toModify);
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
		CUser toModify = userDao.findById(user.getId());
		toModify.setFlagEnabled(Boolean.TRUE);
		userDao.save(toModify);
	}

	/**
	 * Gets the all organizations.
	 *
	 * @return the all organizations
	 * @see sk.qbsw.core.security.service.ISecurityService#getAllOrganizations()
	 */
	@Transactional (readOnly = false)
	public List<COrganization> getAllOrganizations ()
	{
		return orgDao.findAll();
	}


	/** 
	 * @see sk.qbsw.core.security.service.ISecurityService#getAvailabelLicenses()
	 */
	@Transactional (readOnly = true)
	public List<CLicense<?>> getAvailabelLicenses ()
	{
		return licenseGenerator.getAvailableLicenses();
	}

	/** 
	 * @see sk.qbsw.core.security.service.ISecurityService#getAvailabelLicensesForCustomer()
	 */
	@Transactional (readOnly = true)
	public List<CLicense<?>> getAvailabelLicensesForCustomer ()
	{
		return licenseGenerator.getAvailableLicensesForCustomer();
	}

	/**
	 * Gets the available groups.
	 *
	 * @return the available groups
	 * @see sk.qbsw.core.security.service.ISecurityService#getAvailableGroups()
	 */
	@Transactional (readOnly = false)
	public List<CGroup> getAvailableGroups ()
	{
		return groupDao.findAllByFlagSystem(Boolean.FALSE);
	}


	/**
	 * Gets the organization licenses.
	 *
	 * @param org the org
	 * @return the organization licenses
	 * @see sk.qbsw.core.security.service.ISecurityService#getOrganizationLicenses(sk.qbsw.core.security.model.domain.COrganization)
	 */
	@Transactional (readOnly = true)
	public List<CLicense<?>> getOrganizationLicenses (COrganization org)
	{
		COrganization organization = orgDao.findById(org.getId());
		return new ArrayList<CLicense<?>>(organization.getLicences());
	}


	/**
	 * Gets the role group.
	 *
	 * @param id the pk id
	 * @return the role group
	 * @see sk.qbsw.core.security.service.ISecurityService#getRoleGroup(java.lang.Long)
	 */
	@Transactional (readOnly = true)
	public CGroup getRoleGroup (Long id)
	{
		return groupDao.findById(id);
	}


	/**
	 * Checks if is login free.
	 *
	 * @param login the login
	 * @param id the pk id
	 * @return true, if is login free
	 * @see sk.qbsw.core.security.service.ISecurityService#isLoginFree(java.lang.String, java.lang.Long)
	 */
	@Transactional (readOnly = true)
	public boolean isLoginFree (String login, Long id)
	{
		CUser user;
		
		try
		{
			user = userDao.findByLogin(login);
		}
		catch (NoResultException nre)
		{
			user = null;
		}

		if (user != null)
		{
			return user.getId().equals(id);
		}
		return true;
	}


	/**
	 * Checks if is org name free.
	 *
	 * @param name the name
	 * @param id the pk id
	 * @return true, if is org name free
	 * @see sk.qbsw.core.security.service.ISecurityService#isOrgNameFree(java.lang.String, java.lang.Long)
	 */
	@Transactional (readOnly = true)
	public boolean isOrgNameFree (String name, Long id)
	{
		COrganization organization = orgDao.findByNameNull(name);

		if (organization != null)
		{
			return organization.getId().equals(id);
		}
		return true;
	}


	/**
	 * Match license payment.
	 *
	 * @param license the license
	 * @param payed the payed
	 * @see sk.qbsw.core.security.service.ISecurityService#matchLicensePayment(sk.qbsw.core.security.model.domain.CLicense, java.lang.Boolean)
	 */
	@Transactional (readOnly = false)
	public void matchLicensePayment (CLicense<?> license, Boolean payed)
	{
		CLicense<?> toModify = licenseDao.findById(license.getId());
		toModify.setFlagPayed(payed);
		licenseDao.save(toModify);
	}

	/**
	 * Register organization.
	 *
	 * @param org the org
	 * @param manager the manager
	 * @see sk.qbsw.core.security.service.ISecurityService#registerOrganization(sk.qbsw.core.security.model.domain.COrganization)
	 */
	@Transactional (readOnly = false)
	public void registerOrganization (COrganization org, CUser manager)
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
		orgDao.save(org);
	}

	/**
	 * Save user.
	 *
	 * @param user the user
	 * @see sk.qbsw.core.security.service.ISecurityService#saveUser(sk.qbsw.core.security.model.domain.CUser)
	 */
	@Transactional (readOnly = false)
	public void saveUser (CUser user)
	{
		userDao.save(user);
	}


	/**
	 * Update license.
	 *
	 * @param license the license
	 * @see sk.qbsw.core.security.service.ISecurityService#updateLicense(sk.qbsw.core.security.model.domain.CLicense)
	 */
	@Transactional (readOnly = false)
	public void updateLicense (CLicense<?> license)
	{
		licenseDao.save(license);
	}


	public List<CRole> getRoleByCode (String code)
	{

		return roleDao.findByCode(code);
	}

}
