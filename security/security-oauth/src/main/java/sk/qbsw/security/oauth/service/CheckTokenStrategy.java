package sk.qbsw.security.oauth.service;

import sk.qbsw.security.oauth.model.domain.AuthenticationToken;
import sk.qbsw.security.oauth.model.domain.MasterToken;

/**
 * The check token expiration's limits strategy.
 *
 * @author Tomas Lauro
 * 
 * @version 1.13.1
 * @since 1.13.0
 */
public interface CheckTokenStrategy
{
	/**
	 * Checks if is master token expired.
	 *
	 * @param token the token
	 * @return true, if is master token expired
	 */
	boolean isMasterTokenExpired (MasterToken token);

	/**
	 * Checks if is master token inactivity reached.
	 *
	 * @param token the token
	 * @return true, if is master token inactivity reached
	 */
	boolean isMasterTokenInactivityReached (MasterToken token);

	/**
	 * Checks if is authentication token expired.
	 *
	 * @param token the token
	 * @return true, if is authentication token expired
	 */
	boolean isAuthenticationTokenExpired (AuthenticationToken token);

	/**
	 * Checks if is authentication token inactivity reached.
	 *
	 * @param token the token
	 * @return true, if is authentication token inactivity reached
	 */
	boolean isAuthenticationTokenInactivityReached (AuthenticationToken token);
}
