/**
 * 
 */
package sk.qbsw.core.security.service;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.CLicense;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * The Interface IUserService.
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * 
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
	public void addLicense (CLicense<?> license);

	/**
	 * Delete license.
	 *
	 * @param license the license
	 */
	public void deleteLicense (CLicense<?> license);

	/**
	 * Disable organization.
	 *
	 * @param org the org
	 */
	public void disableOrganization (COrganization org);

	/**
	 * Enable organization.
	 *
	 * @param org the org
	 */
	public void enableOrganization (COrganization org);


	/**
	 * Gets the all organizations.
	 *
	 * @return the all organizations
	 */
	public List<COrganization> getAllOrganizations ();



	/**
	 * Gets the availabel licenses.
	 *
	 * @return the availabel licenses
	 */
	public List<CLicense<?>> getAvailabelLicenses ();


	/**
	 * Gets the availabel licenses for customer.
	 *
	 * @return the availabel licenses for customer
	 */
	public List<CLicense<?>> getAvailabelLicensesForCustomer ();

	/**
	 * Gets the available groups.
	 *
	 * @return the available groups
	 */
	public List<CGroup> getAvailableGroups ();

	/**
	 * Gets the organization licenses.
	 *
	 * @param org the org
	 * @return the organization licenses
	 */
	public List<CLicense<?>> getOrganizationLicenses (COrganization org);


	/**
	 * Finds role by code
	 * @param login
	 * @return {@link CRole}
	 * 
	 * @deprecated role code is unique
	 */
	public List<CRole> getRoleByCode (String code);

	/**
	 * Finds role by code.
	 *
	 * @param code the code
	 * @return {@link CRole}
	 */
	public CRole getOneRoleByCode (String code);

	/**
	 * Gets the role group.
	 *
	 * @param id the pk id
	 * @return the role group
	 */
	public CGroup getRoleGroup (Long id);

	/**
	 * Checks if is login free.
	 *
	 * @param login the login
	 * @param id the pk id
	 * @return true, if is login free
	 */
	public boolean isLoginFree (String login, Long id);

	/**
	 * Checks if is org name free.
	 *
	 * @param name the name
	 * @param id the pk id
	 * @return true, if is org name free
	 * 
	 * @deprecated the organization name is no longer unique
	 */
	public boolean isOrgNameFree (String name, Long id);

	/**
	 * Sets the license payed.
	 *
	 * @param license the license
	 * @param payed the payed
	 */
	public void matchLicensePayment (CLicense<?> license, Boolean payed);

	/**
	 * Register organization.
	 *
	 * @param org the org
	 * @param manager the manager
	 */
	public void registerOrganization (COrganization org, CUser manager);


	/**
	 * Update license.
	 *
	 * @param license the license
	 */
	public void updateLicense (CLicense<?> license);
}
