package sk.qbsw.security.authentication.service;

import org.springframework.security.core.Authentication;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.core.model.domain.User;

/**
 * The spring authentication service.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.13.4
 * @since 1.13.4
 */
public interface AuthenticationSecurityService
{
	/**
	 * Authenticates the user.
	 *
	 * @param authentication the authentication
	 * @return the user
	 * @throws CSecurityException the security exception
	 */
	User login (Authentication authentication) throws CSecurityException;

	/**
	 * Checks if the authentication service supports give authentication token.
	 *
	 * @param authentication the authentication token
	 * @return true, if supports, false otherwise
	 */
	boolean supports (Class<? extends Authentication> authentication);
}
