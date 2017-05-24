package sk.qbsw.security.oauth.service;

import sk.qbsw.security.oauth.model.domain.CAuthenticationToken;
import sk.qbsw.security.oauth.model.domain.CMasterToken;

/**
 * The check token expiration's limits strategy.
 *
 * @author Tomas Lauro
 * 
 * @version 1.13.1
 * @since 1.13.0
 */
public interface ICheckTokenStrategy
{
	/**
	 * Checks if is master token expired.
	 *
	 * @param token the token
	 * @return true, if is master token expired
	 */
	boolean isMasterTokenExpired (CMasterToken token);

	/**
	 * Checks if is master token inactivity reached.
	 *
	 * @param token the token
	 * @return true, if is master token inactivity reached
	 */
	boolean isMasterTokenInactivityReached (CMasterToken token);

	/**
	 * Checks if is authentication token expired.
	 *
	 * @param token the token
	 * @return true, if is authentication token expired
	 */
	boolean isAuthenticationTokenExpired (CAuthenticationToken token);

	/**
	 * Checks if is authentication token inactivity reached.
	 *
	 * @param token the token
	 * @return true, if is authentication token inactivity reached
	 */
	boolean isAuthenticationTokenInactivityReached (CAuthenticationToken token);
}
