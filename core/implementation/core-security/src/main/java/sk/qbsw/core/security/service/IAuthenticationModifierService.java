package sk.qbsw.core.security.service;

import org.joda.time.DateTime;

import sk.qbsw.core.base.exception.CSecurityException;

/**
 * The Interface IAuthenticationModifierService.
 * 
 * @author Dalibor Rak
 * @version 1.13.0
 * @since 1.13.0
 */
public interface IAuthenticationModifierService {

	/**
	 * Change password - the valid from and valid to parameters are preserved. 
	 *
	 * @param login the login
	 * @param password the password
	 * @throws CSecurityException the password change failed
	 */
	public abstract void changeEncryptedPassword(String login, String password) throws CSecurityException;

	/**
	 * Change password - the valid from and valid to are set from parameters (null can be set as well).
	 *
	 * @param login the login
	 * @param password the password
	 * @param validFrom the date from the auth data are valid
	 * @param validTo the date to the auth data are valid
	 * @throws CSecurityException the password change failed
	 */
	public abstract void changeEncryptedPassword(String login, String password, DateTime validFrom, DateTime validTo) throws CSecurityException;

	/**
	 * Renew password of the user - the valid from and valid to parameters are preserved.
	 *
	 * @param login the login
	 * @param email the email
	 * @param password the password
	 * @throws CSecurityException the c security exception
	 */
	public abstract void changePlainPassword(String login, String email, String password) throws CSecurityException;

	/**
	 * Renew password of the user - the valid from and valid to are set from parameters (null can be set as well).
	 *
	 * @param login the login
	 * @param email the email
	 * @param password the password
	 * @param validFrom the date from the auth data are valid
	 * @param validTo the date to the auth data are valid
	 * @throws CSecurityException the c security exception
	 */
	public abstract void changePlainPassword(String login, String email, String password, DateTime validFrom, DateTime validTo) throws CSecurityException;

	/**
	 * Change login.
	 *
	 * @param userId the user
	 * @param login the login
	 * @throws CSecurityException the exception raised
	 */
	public abstract void changeLogin(Long userId, String login) throws CSecurityException;

}