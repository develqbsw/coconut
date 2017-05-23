/**
 * 
 */
package sk.qbsw.core.security.service;

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
import sk.qbsw.core.security.dao.IGroupDao;
import sk.qbsw.core.security.dao.ILicenseDao;
import sk.qbsw.core.security.dao.IOrganizationDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.CLicense;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.jmx.CLicensingRules;

/**
 * The Class CSecurityServiceImpl.
 *
 * @author Dalibor Rak
 * @version 1.13.0
 * @since 1.0.0
 */
@Service (value = "securityService")
public class CSecurityServiceImpl extends AService implements ISecurityService
{
	/** The license generator. */
	@Autowired
	private ILicenseGenerator licenseGenerator;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CSecurityServiceImpl.class);

	/** The group dao. */
	@Autowired
	private IGroupDao groupDao;

	/** License dao*. */
	@Autowired
	private ILicenseDao licenseDao;

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
	@Override
	@Transactional (readOnly = false)
	public void addLicense (CLicense<?> license)
	{
		license.recalculateLicensePrice(rules.getDayPricing());
		licenseDao.update(license);
	}

	/**
	 * Delete license.
	 *
	 * @param license the license
	 * @see sk.qbsw.core.security.service.ISecurityService#deleteLicense(sk.qbsw.core.security.model.domain.CLicense)
	 */
	@Override
	@Transactional (readOnly = false)
	public void deleteLicense (CLicense<?> license)
	{
		CLicense<?> toModify = licenseDao.findById(license.getId());
		licenseDao.remove(toModify);
	}

	/** 
	 * @see sk.qbsw.core.security.service.ISecurityService#getAvailabelLicenses()
	 */
	@Override
	@Transactional (readOnly = true)
	public List<CLicense<?>> getAvailabelLicenses ()
	{
		return licenseGenerator.getAvailableLicenses();
	}

	/** 
	 * @see sk.qbsw.core.security.service.ISecurityService#getAvailabelLicensesForCustomer()
	 */
	@Override
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
	@Override
	@Transactional (readOnly = false)
	public List<CGroup> getAvailableGroups ()
	{
		return groupDao.findByFlagSystem(Boolean.FALSE);
	}

	/**
	 * Gets the organization licenses.
	 *
	 * @param org the org
	 * @return the organization licenses
	 * @see sk.qbsw.core.security.service.ISecurityService#getOrganizationLicenses(sk.qbsw.core.security.model.domain.COrganization)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<CLicense<?>> getOrganizationLicenses (COrganization org)
	{
		COrganization organization = orgDao.findById(org.getId());
		return new ArrayList<>(organization.getLicences());
	}

	/**
	 * Checks if is login free.
	 *
	 * @param login the login
	 * @param id the pk id
	 * @return true, if is login free
	 * @see sk.qbsw.core.security.service.ISecurityService#isLoginFree(java.lang.String, java.lang.Long)
	 */
	@Override
	@Transactional (readOnly = true)
	public boolean isLoginFree (String login, Long id)
	{
		CUser user;

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
	 * @see sk.qbsw.core.security.service.ISecurityService#matchLicensePayment(sk.qbsw.core.security.model.domain.CLicense, java.lang.Boolean)
	 */
	@Override
	@Transactional (readOnly = false)
	public void matchLicensePayment (CLicense<?> license, Boolean payed)
	{
		CLicense<?> toModify = licenseDao.findById(license.getId());
		toModify.setFlagPayed(payed);
		licenseDao.update(toModify);
	}

	/**
	 * Update license.
	 *
	 * @param license the license
	 * @see sk.qbsw.core.security.service.ISecurityService#updateLicense(sk.qbsw.core.security.model.domain.CLicense)
	 */
	@Override
	@Transactional (readOnly = false)
	public void updateLicense (CLicense<?> license)
	{
		licenseDao.update(license);
	}
}
