package sk.qbsw.core.security.service;

import java.io.Serializable;

import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.domain.CAuthenticationParams;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * Authentication service
 * 
 * @author Dalibor Rak
 * @version 1.6.0
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
	public abstract CUser login (String login, String password) throws CSecurityException;

	/**
	 * Authenticates the user.
	 *
	 * @param login login of the user
	 * @param password password of the user
	 * @param unit the organization unit
	 * @return the c user
	 * @throws CSecurityException the c security exception
	 */
	public abstract CUser login (String login, String password, String unit) throws CSecurityException;

	/** Find by login and role user must have login and role
	 * @param login - login of the user
	 * @param role - role which must have user
	 * @param password password of the user
	 * @return user if user have login and role null otherwise
	 */
	public abstract CUser login (String login, String password, CRole role) throws CSecurityException;

	/**
	 * Authenticates the user with his role
	 * @param login login of the user
	 * @param role - role which must have user
	 * @param password password of the user
	 * @return
	 */
	public abstract boolean canLogin (String login, String password, CRole role);

	/**
	 * Create new authentication params for user.
	 *
	 * @param login the login
	 * @param password the password
	 */
	public CAuthenticationParams createEncryptedPassword (String login, String password) throws CSecurityException;
	
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
}
