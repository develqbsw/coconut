package sk.qbsw.core.security.service;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;


/**
 * The Interface IUserService.
 * 
 * @author Dalibor Rak
 * @version 1.3.0
 * @since 1.0.0
 */
public interface IUserService extends Serializable
{
	/**
	 * Change password.
	 *
	 * @param user the user
	 * @param password the password
	 */
	public void changePassword (CUser user, String password);

	/**
	 * Disable user.
	 *
	 * @param user the user
	 */
	public void disableUser (CUser user);


	/**
	 * Enable user.
	 *
	 * @param user the user
	 */
	public void enableUser (CUser user);

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
	 * @param organization the organization
	 * @return the all users
	 */
	public List<CUser> getAllUsers (COrganization organization);

	/**
	 * Get users without user with incoming parameters.
	 *
	 * @param organization - organization for which are selected users
	 * @param group - group for which are selected users
	 * @param user - user without are users returned
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
	 * Gets the user by pin.
	 *
	 * @param pin the pin
	 * @return the user by pin or null if user doesn't exist
	 */
	public CUser getUserByPin (String pin);

	/**
	 * Gets the user for modification.
	 *
	 * @param pkId the pk id
	 * @return the user for modification
	 */
	public CUser getUserForModification (Long pkId);

	/**
	 * Gets all users.
	 *
	 * @return the users
	 */
	public List<CUser> getUsers ();

	/**
	 * Gets the users.
	 *
	 * @param organization the organization
	 * @param enabled the enabled
	 * @param group the group
	 * @return the users
	 */
	public List<CUser> getUsers (COrganization organization, Boolean enabled, CGroup group);

	/**
	 * Gets the users.
	 *
	 * @param organization the organization
	 * @param role the role
	 * @return the users
	 */
	public List<CUser> getUsers (COrganization organization, CRole role);

	/**
	 * Gets the users order by organization.
	 *
	 * @param organization the organization
	 * @param enabled the enabled
	 * @param group the group
	 * @return the users order by organization
	 */
	public List<CUser> getUsersOrderByOrganization (COrganization organization, Boolean enabled, CGroup group);

	/**
	 * Register new user.
	 *
	 * @param user the user
	 * @param organization the organization
	 * @throws CSecurityException if user with such logiun already exists
	 */
	public void registerNewUser (CUser user, COrganization organization) throws CSecurityException;

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
	 * Update user.
	 *
	 * @param user the user
	 */
	public void updateUser (CUser user);


}
