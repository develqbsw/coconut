package sk.qbsw.security.authentication.base.service;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.core.model.domain.Role;
import sk.qbsw.security.core.model.domain.User;

import java.io.Serializable;

/**
 * Authentication service
 * 
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.13.0
 * @since 1.0.0
 */
public interface AuthenticationService extends Serializable
{
	/**
	 * Authenticates the user
	 * 
	 * @param login login of the user
	 * @param password password of the user
	 * @return
	 */
	User login (String login, String password) throws CSecurityException;

	/**
	 * Authenticates the user.
	 *
	 * @param login login of the user
	 * @param password password of the user
	 * @param unit the organization unit
	 * @return the c user
	 * @throws CSecurityException the c security exception
	 */
	User login (String login, String password, String unit) throws CSecurityException;

	/**
	 * Find by login and role user must have login and role
	 * 
	 * @param login - login of the user
	 * @param role - role which must have user
	 * @param password password of the user
	 * @return user if user have login and role null otherwise
	 */
	User login (String login, String password, Role role) throws CSecurityException;

	/**
	 * Authenticates the user with his role
	 * 
	 * @param login login of the user
	 * @param role - role which must have user
	 * @param password password of the user
	 * @return
	 */
	boolean canLogin (String login, String password, Role role);

	/**
	 * Checks if the authentication service is online.
	 *
	 * @return true, if is online
	 */
	boolean isOnline ();
}
