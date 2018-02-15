package sk.qbsw.security.oauth.service;

import sk.qbsw.security.oauth.model.domain.AuthenticationToken;
import sk.qbsw.security.oauth.model.domain.MasterToken;

/**
 * The token validation service.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.18.2
 */
public interface TokenValidationService
{
	/**
	 * Is master token ip valid boolean.
	 *
	 * @param token the token
	 * @param ip the ip
	 * @return the boolean
	 */
	boolean isMasterTokenIpValid (MasterToken token, String ip);

	/**
	 * Is authentication token ip valid boolean.
	 *
	 * @param token the token
	 * @param ip the ip
	 * @return the boolean
	 */
	boolean isAuthenticationTokenIpValid (AuthenticationToken token, String ip);

	/**
	 * Is master token expired boolean.
	 *
	 * @param token the token
	 * @return the boolean
	 */
	boolean isMasterTokenExpired (MasterToken token);

	/**
	 * Is authentication token expired boolean.
	 *
	 * @param token the token
	 * @return the boolean
	 */
	boolean isAuthenticationTokenExpired (AuthenticationToken token);
}
