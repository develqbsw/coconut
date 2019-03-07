package sk.qbsw.security.spring.auth.base.service;

import org.springframework.security.core.Authentication;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.spring.common.model.LoggedAccount;

/**
 * The authentication service - abstraction layer above the core authentication service.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.13.4
 */
public interface AuthenticationSecurityService
{
	/**
	 * Authenticates the user.
	 *
	 * @param authentication the authentication
	 * @return the logged account
	 * @throws CSecurityException the security exception
	 */
	LoggedAccount authenticate (Authentication authentication) throws CSecurityException;

	/**
	 * Checks if the authentication service supports give authentication token.
	 *
	 * @param authentication the authentication token
	 * @return true, if supports, false otherwise
	 */
	boolean supports (Class<? extends Authentication> authentication);
}
