package sk.qbsw.security.oauth.service;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.oauth.model.GeneratedTokenData;

/**
 * The authentication token service.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.13.1
 */
public interface AuthenticationTokenService
{
	/**
	 * Generate authentication token and save it or replace it, if the token exists.
	 *
	 * @param userId the user id
	 * @param masterToken the master token
	 * @param deviceId the device id
	 * @param ip the ip
	 * @return the generated authentication token
	 * @throws CBusinessException the c business exception
	 */
	GeneratedTokenData generateAuthenticationToken (Long userId, String masterToken, String deviceId, String ip) throws CBusinessException;

	/**
	 * Revoke authentication token.
	 *
	 * @param userId the user id
	 * @param authenticationToken the authentication token
	 * @throws CBusinessException the c business exception
	 */
	void revokeAuthenticationToken (Long userId, String authenticationToken) throws CBusinessException;

	/**
	 * Gets the user by authentication token.
	 *
	 * @param authenticationToken the authentication token
	 * @param deviceId the device id
	 * @param ip the ip
	 * @return the user by authentication token
	 * @throws CBusinessException the c business exception
	 */
	User getUserByAuthenticationToken (String authenticationToken, String deviceId, String ip) throws CBusinessException;
}
