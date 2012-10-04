package sk.qbsw.core.security.service;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;


/**
 * The Interface IUserService.
 * 
 * @author Dalibor Rak
 * @version 1.2.0
 * @since 1.0.0
 */
public interface IUserService extends Serializable
{

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	public abstract List<CUser> getUsers ();

	/**
	 * Gets the users.
	 *
	 * @param organization the organization
	 * @param role the role
	 * @return the users
	 */
	public abstract List<CUser> getUsers (COrganization organization, CRole role);

	/**
	 * Gets the users.
	 *
	 * @param organization the organization
	 * @param enabled the enabled
	 * @param group the group
	 * @return the users
	 */
	public abstract List<CUser> getUsers (COrganization organization, Boolean enabled, CGroup group);

	/**
	 * Gets the user by login.
	 *
	 * @param login the login
	 * @return the user by login
	 */
	public abstract CUser getUserByLogin (String login);

	/**
	 * Gets the user by pin.
	 *
	 * @param pin the pin
	 * @return the user by pin
	 */
	public abstract CUser getUserByPin (String pin);

	/**
	 * Change password.
	 *
	 * @param user the user
	 * @param password the password
	 */
	public void changePassword (CUser user, String password);


	/**
	 * Update user.
	 *
	 * @param user the user
	 */
	public abstract void updateUser (CUser user);

	/**
	 * Find by login  and return NULL if user not exist - NOT exeption.
	 *
	 * @param login the login
	 * @return the c user or null if user not exist
	 */
	public abstract CUser getUserByLoginNull (String login);

	/**
	 * Gets the.
	 *
	 * @param id the id
	 * @return the c user
	 */
	public abstract CUser get (Long id);

	/**
	 * Register new user.
	 *
	 * @param user the user
	 * @param organization the organization
	 */
	public void registerNewUser (CUser user, COrganization organization);

	/**
	 * Get users without user what come as parameter.
	 *
	 * @param organization - organization for which are selected users
	 * @param group - group for which are selected users
	 * @param user - user without are users returned
	 * @return list of users
	 */
	public abstract List<CUser> getOtherActiveUsers (COrganization organization, CGroup group, CUser user);


}
