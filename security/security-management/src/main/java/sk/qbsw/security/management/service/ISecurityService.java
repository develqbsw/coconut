/**
 * 
 */
package sk.qbsw.security.management.service;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.security.core.model.domain.CGroup;
import sk.qbsw.security.core.model.domain.CLicense;
import sk.qbsw.security.core.model.domain.COrganization;

/**
 * The Interface IUserManagementService.
 *
 * @author Dalibor Rak
 * @version 1.13.0
 * @since 1.0.0
 */
public interface ISecurityService extends Serializable
{

	/**
	 * Adds the license.
	 *
	 * @param license the license
	 */
	public void addLicense(CLicense<?> license);

	/**
	 * Delete license.
	 *
	 * @param license the license
	 */
	public void deleteLicense(CLicense<?> license);

	/**
	 * Gets the availabel licenses.
	 *
	 * @return the availabel licenses
	 */
	public List<CLicense<?>> getAvailabelLicenses();

	/**
	 * Gets the availabel licenses for customer.
	 *
	 * @return the availabel licenses for customer
	 */
	public List<CLicense<?>> getAvailabelLicensesForCustomer();

	/**
	 * Gets the available groups.
	 *
	 * @return the available groups
	 */
	public List<CGroup> getAvailableGroups();

	/**
	 * Gets the organization licenses.
	 *
	 * @param org the org
	 * @return the organization licenses
	 */
	public List<CLicense<?>> getOrganizationLicenses(COrganization org);

	/**
	 * Checks if is login free.
	 *
	 * @param login the login
	 * @param id the pk id
	 * @return true, if is login free
	 */
	public boolean isLoginFree(String login, Long id);

	/**
	 * Sets the license payed.
	 *
	 * @param license the license
	 * @param payed the payed
	 */
	public void matchLicensePayment(CLicense<?> license, Boolean payed);

	/**
	 * Update license.
	 *
	 * @param license the license
	 */
	public void updateLicense(CLicense<?> license);
}
