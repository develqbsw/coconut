/**
 * 
 */
package sk.qbsw.core.security.service;

import java.util.List;

import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.CLicense;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * The Interface IUserService.
 *
 * @author Dalibor Rak
 * @version 1.0
 * @since 1.0
 */
public interface ISecurityService
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
	 * Update license.
	 *
	 * @param license the license
	 */
	public void updateLicense (CLicense<?> license);

	/**
	 * Disable organization.
	 *
	 * @param org the org
	 */
	public void disableOrganization (COrganization org);

	/**
	 * Disable user.
	 *
	 * @param user the user
	 */
	public void disableUser (CUser user);

	/**
	 * Enable organization.
	 *
	 * @param org the org
	 */
	public void enableOrganization (COrganization org);


	/**
	 * Enable user.
	 *
	 * @param user the user
	 */
	public void enableUser (CUser user);

	/**
	 * Gets the all organizations.
	 *
	 * @return the all organizations
	 */
	public List<COrganization> getAllOrganizations ();

	/**
	 * Gets the all users.
	 *
	 * @param organization the organization
	 * @return the all users
	 */
	public List<CUser> getAllUsers (COrganization organization);

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
	 * Gets the role group.
	 *
	 * @param pkId the pk id
	 * @return the role group
	 */
	public CGroup getRoleGroup (Long pkId);

	/**
	 * Gets the user for modification.
	 *
	 * @param pkId the pk id
	 * @return the user for modification
	 */
	public CUser getUserForModification (Long pkId);

	/**
	 * Checks if is login free.
	 *
	 * @param login the login
	 * @param pkId the pk id
	 * @return true, if is login free
	 */
	public boolean isLoginFree (String login, Long pkId);

	/**
	 * Checks if is org name free.
	 *
	 * @param name the name
	 * @param pkId the pk id
	 * @return true, if is org name free
	 */
	public boolean isOrgNameFree (String name, Long pkId);

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
	 * Renew password of the user.
	 *
	 * @param login the login
	 * @param email the email
	 * @param password the password
	 * @throws CSecurityException the c security exception
	 */
	public void renewPassword (String login, String email, String password) throws CSecurityException;

	/**
	 * Save user.
	 *
	 * @param user the user
	 */
	public void saveUser (CUser user);

	/**
	 * Finds user by login
	 * @param login
	 * @return CUser
	 */
	public CUser getUser (String login);
	
	/**
	 * Finds role by code
	 * @param login
	 * @return {@link CRole}
	 */
	public List<CRole> getRoleByCode (String code);


}
