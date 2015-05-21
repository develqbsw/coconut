/**
 * 
 */
package sk.qbsw.core.security.oauth.service;

import sk.qbsw.core.security.model.domain.CUser;

/**
 * The security token service.
 *
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.13.0
 */
public interface INewSecurityTokenService
{
	/**
	 * Gets the user by master or authentication token.
	 *
	 * @param token the token
	 * @param ip the ip
	 * @return the user by token
	 */
	CUser getUserByToken (String token, String ip);

	/**
	 * Generate master token and save it.
	 *
	 * @param userId the user id
	 * @param deviceId the device id
	 * @param ip the ip
	 * @return the generated master token
	 */
	String generateMasterToken (Long userId, String deviceId, String ip);

	/**
	 * Generate authentication token and save it or replace it, if the token exists.
	 *
	 * @param userId the user id
	 * @param masterToken the master token
	 * @param deviceId the device id
	 * @param ip the ip
	 * @return the generated authentication token
	 */
	String generateAuthenticationToken (Long userId, String masterToken, String deviceId, String ip);

	/**
	 * Revoke master token.
	 *
	 * @param userId the user id
	 * @param masterToken the master token
	 */
	void revokeMasterToken (Long userId, String masterToken);

	/**
	 * Revoke authentication token.
	 *
	 * @param userId the user id
	 * @param masterToken the master token
	 */
	void revokeAuthenticationToken (Long userId, String masterToken);
}
