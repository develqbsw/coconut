package sk.qbsw.security.oauth.service;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.security.model.domain.CUser;

/**
 * The master token service.
 *
 * @author Tomas Lauro
 * 
 * @version 1.13.1
 * @since 1.13.1
 */
public interface IMasterTokenService
{
	/**
	 * Generate master token and save it.
	 *
	 * @param userId the user id
	 * @param deviceId the device id
	 * @param ip the ip
	 * @return the generated master token
	 */
	String generateMasterToken (Long userId, String deviceId, String ip) throws CBusinessException;

	/**
	 * Revoke master token.
	 *
	 * @param userId the user id
	 * @param masterToken the master token
	 */
	void revokeMasterToken (Long userId, String masterToken) throws CBusinessException;

	/**
	 * Gets the user by master token.
	 *
	 * @param token the token
	 * @param deviceId the device id
	 * @param ip the ip
	 * @return the user by master token
	 * @throws CBusinessException the c business exception
	 */
	CUser getUserByMasterToken (String token, String deviceId, String ip) throws CBusinessException;
}
