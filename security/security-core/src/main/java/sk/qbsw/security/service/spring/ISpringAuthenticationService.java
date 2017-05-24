package sk.qbsw.security.service.spring;

import java.io.Serializable;

import org.springframework.security.core.Authentication;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.model.domain.CUser;

/**
 * The spring authentication service.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.13.4
 * @since 1.13.4
 */
public interface ISpringAuthenticationService extends Serializable
{
	/**
	 * Authenticates the user.
	 *
	 * @param authentication the authentication
	 * @return the user
	 * @throws CSecurityException the security exception
	 */
	CUser login (Authentication authentication) throws CSecurityException;

	/**
	 * Checks if the authentication service supports give authentication token.
	 *
	 * @param authentication the authentication token
	 * @return true, if supports, false otherwise
	 */
	boolean supports (Class<? extends Authentication> authentication);
}
