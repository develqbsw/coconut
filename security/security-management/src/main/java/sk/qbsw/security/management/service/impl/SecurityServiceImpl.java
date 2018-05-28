/**
 * 
 */
package sk.qbsw.security.management.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.security.core.dao.GroupDao;
import sk.qbsw.security.core.dao.LicenseDao;
import sk.qbsw.security.core.dao.OrganizationDao;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.License;
import sk.qbsw.security.core.model.domain.Organization;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.jmx.CLicensingRules;
import sk.qbsw.security.core.service.LicenseGenerator;
import sk.qbsw.security.management.service.SecurityService;

/**
 * The Class SecurityServiceImpl.
 *
 * @author Dalibor Rak
 * @version 1.13.0
 * @since 1.0.0
 */
@Service (value = "securityService")
public class SecurityServiceImpl extends AService implements SecurityService
{
	/** The license generator. */
	@Autowired
	private LicenseGenerator licenseGenerator;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityServiceImpl.class);

	/** The group dao. */
	@Autowired
	private GroupDao groupDao;

	/** License dao*. */
	@Autowired
	private LicenseDao licenseDao;

	/** The org dao. */
	@Autowired
	private OrganizationDao orgDao;

	/** The user dao. */
	@Autowired
	private AccountDao userDao;

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
	 * @see sk.qbsw.security.core.SecurityService.management.service.ISecurityService#addLicense(sk.qbsw.security.core.core.model.domain.License)
	 */
	@Override
	@Transactional (readOnly = false)
	public void addLicense (License<?> license)
	{
		license.recalculateLicensePrice(rules.getDayPricing());
		licenseDao.update(license);
	}

	/**
	 * Delete license.
	 *
	 * @param license the license
	 * @see sk.qbsw.security.core.SecurityService.management.service.ISecurityService#deleteLicense(sk.qbsw.security.core.core.model.domain.License)
	 */
	@Override
	@Transactional (readOnly = false)
	public void deleteLicense (License<?> license)
	{
		License<?> toModify = licenseDao.findById(license.getId());
		licenseDao.remove(toModify);
	}

	/** 
	 * @see sk.qbsw.security.core.SecurityService.management.service.ISecurityService#getAvailabelLicenses()
	 */
	@Override
	@Transactional (readOnly = true)
	public List<License<?>> getAvailabelLicenses ()
	{
		return licenseGenerator.getAvailableLicenses();
	}

	/** 
	 * @see sk.qbsw.security.core.SecurityService.management.service.ISecurityService#getAvailabelLicensesForCustomer()
	 */
	@Override
	@Transactional (readOnly = true)
	public List<License<?>> getAvailabelLicensesForCustomer ()
	{
		return licenseGenerator.getAvailableLicensesForCustomer();
	}

	/**
	 * Gets the available groups.
	 *
	 * @return the available groups
	 * @see sk.qbsw.security.core.SecurityService.management.service.ISecurityService#getAvailableGroups()
	 */
	@Override
	@Transactional (readOnly = false)
	public List<Group> getAvailableGroups ()
	{
		return groupDao.findByFlagSystem(Boolean.FALSE);
	}

	/**
	 * Gets the organization licenses.
	 *
	 * @param org the org
	 * @return the organization licenses
	 * @see sk.qbsw.security.core.SecurityService.management.service.ISecurityService#getOrganizationLicenses(sk.qbsw.security.core.Organization.model.domain.COrganization)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<License<?>> getOrganizationLicenses (Organization org)
	{
		Organization organization = orgDao.findById(org.getId());
		return new ArrayList<>(organization.getLicences());
	}

	/**
	 * Checks if is login free.
	 *
	 * @param login the login
	 * @param id the pk id
	 * @return true, if is login free
	 * @see sk.qbsw.security.core.SecurityService.management.service.ISecurityService#isLoginFree(java.lang.String, java.lang.Long)
	 */
	@Override
	@Transactional (readOnly = true)
	public boolean isLoginFree (String login, Long id)
	{
		Account user;

		try
		{
			user = userDao.findOneByLogin(login);
		}
		catch (NoResultException | CSecurityException e)
		{
			LOGGER.debug("User not found", e);
			user = null;
		}

		if (user != null)
		{
			return user.getId().equals(id);
		}

		return true;
	}


	/**
	 * Match license payment.
	 *
	 * @param license the license
	 * @param payed the payed
	 * @see sk.qbsw.security.core.SecurityService.management.service.ISecurityService#matchLicensePayment(sk.qbsw.security.core.core.model.domain.License, java.lang.Boolean)
	 */
	@Override
	@Transactional (readOnly = false)
	public void matchLicensePayment (License<?> license, Boolean payed)
	{
		License<?> toModify = licenseDao.findById(license.getId());
		toModify.setFlagPayed(payed);
		licenseDao.update(toModify);
	}

	/**
	 * Update license.
	 *
	 * @param license the license
	 * @see sk.qbsw.security.core.SecurityService.management.service.ISecurityService#updateLicense(sk.qbsw.security.core.core.model.domain.License)
	 */
	@Override
	@Transactional (readOnly = false)
	public void updateLicense (License<?> license)
	{
		licenseDao.update(license);
	}
}
