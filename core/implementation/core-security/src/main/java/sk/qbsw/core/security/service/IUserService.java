package sk.qbsw.core.security.service;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.domain.CAddress;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * The Interface IUserService.
 * 
 * @author Dalibor Rak
 * @author Tomas Lauro
 * 
 * @version 1.12.1
 * @since 1.0.0
 */
public interface IUserService extends Serializable
{
	/**
	 * Enable user.
	 *
	 * @param user the user
	 */
	public void enableUser (CUser user);

	/**
	 * Disable user.
	 *
	 * @param user the user
	 */
	public void disableUser (CUser user);

	/**
	 * Gets the.
	 *
	 * @param id the id
	 * @return the c user
	 */
	public abstract CUser get (Long id);

	/**
	 * Gets the all users.
	 *
	 * @param organization the organization (mandatory)
	 * @return the all users
	 */
	public List<CUser> getAllUsers (COrganization organization);

	/**
	 * Get users without user with incoming parameters.
	 *
	 * @param organization - organization for which are selected users (optional)
	 * @param group - group for which are selected users (optional)
	 * @param user - user without are users returned (mandatory)
	 * @return list of users
	 */
	public abstract List<CUser> getOtherActiveUsers (COrganization organization, CGroup group, CUser user);

	/**
	 * Gets the user by login.
	 *
	 * @param login the login
	 * @return the user by login or null if user doesn't exist
	 */
	public CUser getUserByLogin (String login);

	/**
	 * Gets the user for modification.
	 *
	 * @param id the pk id
	 * @return the user for modification
	 */
	public CUser getUserForModification (Long id);

	/**
	 * Gets all users.
	 *
	 * @return the users
	 */
	public List<CUser> getUsers ();

	/**
	 * Gets all users.
	 *
	 * @param organization the organization (optional)
	 * @param enabled the enabled (mandatory)
	 * @return the users
	 */
	public List<CUser> getUsers (COrganization organization, Boolean enabled);

	/**
	 * Gets the users.
	 *
	 * @param organization the organization (optional)
	 * @param enabled the enabled (optional)
	 * @param group the group (optional)
	 * @return the users
	 */
	public List<CUser> getUsers (COrganization organization, Boolean enabled, CGroup group);

	/**
	 * Gets the users.
	 *
	 * @param organization the organization (optional)
	 * @param role the role (mandatory)
	 * @return the users
	 */
	public List<CUser> getUsers (COrganization organization, CRole role);

	/**
	 * Gets the users.
	 *
	 * @param name the name (optional)
	 * @param surname the surname (optional)
	 * @param login the login (optional)
	 * @param enabled the enabled (optional)
	 * @return the users
	 */
	public List<CUser> getUsers (String name, String surname, String login, Boolean enabled);

	/**
	 * Gets the users.
	 *
	 * @param name the name (optional)
	 * @param surname the surname (optional)
	 * @param login the login (optional)
	 * @param enabled the enabled (optional)
	 * @param organization the organization
	 * @return the users
	 */
	public List<CUser> getUsers (String name, String surname, String login, Boolean enabled, COrganization organization);

	/**
	 * Gets the users.
	 *
	 * @param name the name (optional)
	 * @param surname the surname (optional)
	 * @param login the login (optional)
	 * @param enabled the enabled (optional)
	 * @param groupCodePrefix the group code prefix (optional)
	 * @return the users
	 */
	public List<CUser> getUsers (String name, String surname, String login, Boolean enabled, String groupCodePrefix);

	/**
	 * Gets the users.
	 *
	 * @param email the email (mandatory)
	 * @return the users
	 */
	public List<CUser> getUsers (String email);

	/**
	 * Gets the users order by organization.
	 *
	 * @param organization the organization (optional)
	 * @param enabled the enabled (optional)
	 * @param group the group (optional)
	 * @return the users order by organization
	 */
	public List<CUser> getUsersOrderByOrganization (COrganization organization, Boolean enabled, CGroup group);

	/**
	 * Register new user.
	 *
	 * @param user the user
	 * @param password the password
	 * @param organization the organization
	 * @throws CSecurityException if user with such login already exists
	 */
	public void registerNewUser (CUser user, String password, COrganization organization) throws CSecurityException;

	/**
	 * Register new user without password - usually used with LDAP authentication because the user can't change the password in domain.
	 *
	 * @param user the user
	 * @param organization the organization
	 * @throws CSecurityException if user with such login already exists
	 */
	public void registerNewUser (CUser user, COrganization organization) throws CSecurityException;

	/**
	 * Update user.
	 *
	 * @param user the user
	 */
	public void updateUser (CUser user);

	/**
	 * Add or update user address
	 * 
	 * @param user user for which is address updated
	 * @param address address which is added or updated for user
	 */
	public void setAddress (CUser user, CAddress address);

	/**
	 * Unset user from group in specified unit.
	 *
	 * @param user the user (mandatory)
	 * @param group the group (mandatory)
	 * @param unit the unit (optional)
	 * @throws CSecurityException the security exception occurs if the input parameters are incorrect or the unsetting failed
	 */
	public void unsetUserFromGroup (CUser user, CGroup group, CUnit unit) throws CSecurityException;

	/**
	 * Unset user from group.
	 *
	 * @param user the user (mandatory)
	 * @param group the group (mandatory)
	 * @throws CSecurityException the security exception occurs if the input parameters are incorrect or the unsetting failed
	 */
	public void unsetUserFromGroup (CUser user, CGroup group) throws CSecurityException;

	/**
	 * Set user to group in specified unit.
	 *
	 * @param user the user (mandatory)
	 * @param group the group (mandatory)
	 * @param unit the unit (optional)
	 * @throws CSecurityException the security exception occurs if the input parameters are incorrect or the setting failed
	 * @throws CBusinessException the exception is thrown if the group cannot be set to user
	 */
	public void setUserToGroup (CUser user, CGroup group, CUnit unit) throws CSecurityException, CBusinessException;

	/**
	 * Set user to group.
	 *
	 * @param user the user (mandatory)
	 * @param group the group (mandatory)
	 * @throws CSecurityException the security exception occurs if the input parameters are incorrect or the setting failed
	 * @throws CBusinessException the exception is thrown if the group cannot be set to user
	 */
	public void setUserToGroup (CUser user, CGroup group) throws CSecurityException, CBusinessException;
}
