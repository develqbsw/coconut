package sk.qbsw.core.security.oauth.service;

import sk.qbsw.core.security.oauth.model.CSecurityToken;

/**
 * The check token expiration's limits strategy.
 *
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.13.0
 */
public interface ICheckTokenStrategy
{
	/**
	 * Checks for expired.
	 *
	 * @param token the token
	 * @return true, if successful
	 */
	boolean hasExpired (CSecurityToken token);

	/**
	 * Checks for to be changed due inactivity - in a shorter time period.
	 *
	 * @param token the token
	 * @return true, if successful
	 */
	boolean hasToBeChangedDueInactivity (CSecurityToken token);
}
