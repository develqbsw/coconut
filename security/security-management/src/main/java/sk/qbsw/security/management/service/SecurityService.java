/**
 * 
 */
package sk.qbsw.security.management.service;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.License;
import sk.qbsw.security.core.model.domain.Organization;

/**
 * The Interface UserManagementService.
 *
 * @author Dalibor Rak
 * @version 1.13.0
 * @since 1.0.0
 */
public interface SecurityService extends Serializable
{

	/**
	 * Adds the license.
	 *
	 * @param license the license
	 */
	public void addLicense(License<?> license);

	/**
	 * Delete license.
	 *
	 * @param license the license
	 */
	public void deleteLicense(License<?> license);

	/**
	 * Gets the availabel licenses.
	 *
	 * @return the availabel licenses
	 */
	public List<License<?>> getAvailabelLicenses();

	/**
	 * Gets the availabel licenses for customer.
	 *
	 * @return the availabel licenses for customer
	 */
	public List<License<?>> getAvailabelLicensesForCustomer();

	/**
	 * Gets the available groups.
	 *
	 * @return the available groups
	 */
	public List<Group> getAvailableGroups();

	/**
	 * Gets the organization licenses.
	 *
	 * @param org the org
	 * @return the organization licenses
	 */
	public List<License<?>> getOrganizationLicenses(Organization org);

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
	public void matchLicensePayment(License<?> license, Boolean payed);

	/**
	 * Update license.
	 *
	 * @param license the license
	 */
	public void updateLicense(License<?> license);
}
