package sk.qbsw.core.security.service;

import java.io.Serializable;

import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * Authentication service
 * 
 * @author Dalibor Rak
 * @author Tomas Lauro
 * 
 * @version 1.7.2
 * @since 1.0.0
 */
public interface IAuthenticationService extends Serializable
{
	/**
	 * Authenticates the user
	 * @param login login of the user
	 * @param password password of the user
	 * @return
	 */
	public CUser login (String login, String password) throws CSecurityException;

	/**
	 * Authenticates the user.
	 *
	 * @param login login of the user
	 * @param password password of the user
	 * @param unit the organization unit
	 * @return the c user
	 * @throws CSecurityException the c security exception
	 */
	public CUser login (String login, String password, String unit) throws CSecurityException;

	/** Find by login and role user must have login and role
	 * @param login - login of the user
	 * @param role - role which must have user
	 * @param password password of the user
	 * @return user if user have login and role null otherwise
	 */
	public CUser login (String login, String password, CRole role) throws CSecurityException;

	/**
	 * Authenticates the user with his role
	 * @param login login of the user
	 * @param role - role which must have user
	 * @param password password of the user
	 * @return
	 */
	public boolean canLogin (String login, String password, CRole role);

	/**
	 * Change password.
	 *
	 * @param login the login
	 * @param password the password
	 */
	public void changeEncryptedPassword (String login, String password) throws CSecurityException;

	/**
	 * Renew password of the user.
	 *
	 * @param login the login
	 * @param email the email
	 * @param password the password
	 * @throws CSecurityException the c security exception
	 */
	public void changePlainPassword (String login, String email, String password) throws CSecurityException;

	/**
	 * Change login.
	 *
	 * @param userId the user
	 * @param login the login
	 * @throws CSecurityException the exception raised
	 */
	public void changeLogin (Long userId, String login) throws CSecurityException;
	
	/**
	 * Checks if the authentication service is online.
	 *
	 * @return true, if is online
	 */
	public boolean isOnline ();
}
