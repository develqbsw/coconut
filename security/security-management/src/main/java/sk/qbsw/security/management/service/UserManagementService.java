package sk.qbsw.security.management.service;

import java.util.List;

import javax.persistence.NoResultException;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.Address;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Organization;
import sk.qbsw.security.core.model.domain.Role;

/**
 * The Interface UserManagementService.
 * 
 * @author Dalibor Rak
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.0.0
 */
public interface UserManagementService
{
	/**
	 * Enable user.
	 *
	 * @param user the user
	 */
	public void enableUser (Account user);

	/**
	 * Disable user.
	 *
	 * @param user the user
	 */
	public void disableUser (Account user);

	/**
	 * Gets the.
	 *
	 * @param id the id
	 * @return the c user
	 */
	public abstract Account get (Long id);

	/**
	 * Gets the all users.
	 *
	 * @param organization the organization (mandatory)
	 * @return the all users
	 */
	public List<Account> getAllUsers (Organization organization);

	/**
	 * Get users without user with incoming parameters.
	 *
	 * @param organization - organization for which are selected users (optional)
	 * @param group - group for which are selected users (optional)
	 * @param user - user without are users returned (mandatory)
	 * @return list of users
	 */
	public abstract List<Account> getOtherActiveUsers (Organization organization, Group group, Account user);

	/**
	 * Gets the user by login.
	 *
	 * @param login the login
	 * @return the user by login or null if user doesn't exist
	 * 
	 * @throws NoResultException there is no result
	 * @throws CSecurityException the login is null
	 */
	public Account getUserByLogin (String login) throws NoResultException, CSecurityException;

	/**
	 * Gets the user for modification.
	 *
	 * @param id the pk id
	 * @return the user for modification
	 */
	public Account getUserForModification (Long id);

	/**
	 * Gets all users.
	 *
	 * @return the users
	 */
	public List<Account> getUsers ();

	/**
	 * Gets all users.
	 *
	 * @param organization the organization (optional)
	 * @param enabled the enabled (mandatory)
	 * @return the users
	 */
	public List<Account> getUsers (Organization organization, Boolean enabled);

	/**
	 * Gets the users.
	 *
	 * @param organization the organization (optional)
	 * @param enabled the enabled (optional)
	 * @param group the group (optional)
	 * @return the users
	 */
	public List<Account> getUsers (Organization organization, Boolean enabled, Group group);

	/**
	 * Gets the users.
	 *
	 * @param organization the organization (optional)
	 * @param role the role (mandatory)
	 * @return the users
	 */
	public List<Account> getUsers (Organization organization, Role role);

	/**
	 * Gets the users.
	 *
	 * @param name the name (optional)
	 * @param surname the surname (optional)
	 * @param login the login (optional)
	 * @param enabled the enabled (optional)
	 * @return the users
	 */
	public List<Account> getUsers (String name, String surname, String login, Boolean enabled);

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
	public List<Account> getUsers (String name, String surname, String login, Boolean enabled, Organization organization);

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
	public List<Account> getUsers (String name, String surname, String login, Boolean enabled, String groupCodePrefix);

	/**
	 * Gets the users.
	 *
	 * @param email the email (mandatory)
	 * @return the users
	 */
	public List<Account> getUsers (String email);

	/**
	 * Gets the users order by organization.
	 *
	 * @param organization the organization (optional)
	 * @param enabled the enabled (optional)
	 * @param group the group (optional)
	 * @return the users order by organization
	 */
	public List<Account> getUsersOrderByOrganization (Organization organization, Boolean enabled, Group group);

	/**
	 * Register new user.
	 *
	 * @param user the user
	 * @param password the password
	 * @param organization the organization
	 * @throws CSecurityException if user with such login already exists
	 */
	public void registerNewUser (Account user, String password, Organization organization) throws CSecurityException;

	/**
	 * Register new user without password - usually used with LDAP authentication because the user can't change the password in domain.
	 *
	 * @param user the user
	 * @param organization the organization
	 * @throws CSecurityException if user with such login already exists
	 */
	public void registerNewUser (Account user, Organization organization) throws CSecurityException;

	/**
	 * Update user.
	 *
	 * @param user the user
	 */
	public void updateUser (Account user);

	/**
	 * Add or update user address
	 * 
	 * @param user user for which is address updated
	 * @param address address which is added or updated for user
	 */
	public void setAddress (Account user, Address address);


	/**
	 * Checks if the user with defined login exist.
	 *
	 * @param login the login
	 * @return true, if successful
	 */
	public boolean checksUserExist (String login);

}
