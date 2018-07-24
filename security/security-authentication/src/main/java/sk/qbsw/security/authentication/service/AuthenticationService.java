package sk.qbsw.security.authentication.service;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.Role;

/**
 * Authentication service
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.0.0
 */
public interface AuthenticationService
{
	/**
	 * Authenticates the account
	 *
	 * @param login login of the account
	 * @param password password of the account
	 * @return account account
	 * @throws CSecurityException the c security exception
	 */
	Account login (String login, String password) throws CSecurityException;

	/**
	 * Authenticates the account.
	 *
	 * @param login login of the account
	 * @param password password of the account
	 * @param unit the organization unit
	 * @return the c account
	 * @throws CSecurityException the c security exception
	 */
	Account login (String login, String password, String unit) throws CSecurityException;

	/**
	 * Find by login and role account must have login and role
	 *
	 * @param login - login of the account
	 * @param password password of the account
	 * @param role - role which must have account
	 * @return account if account have login and role null otherwise
	 * @throws CSecurityException the c security exception
	 */
	Account login (String login, String password, Role role) throws CSecurityException;

	/**
	 * Authenticates the account with his role
	 *
	 * @param login login of the account
	 * @param password password of the account
	 * @param role - role which must have account
	 * @return boolean boolean
	 */
	boolean canLogin (String login, String password, Role role);

	/**
	 * Checks if the authentication service is online.
	 *
	 * @return true, if is online
	 */
	boolean isOnline ();
}
