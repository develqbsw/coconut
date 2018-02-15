package sk.qbsw.security.oauth.service;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.oauth.model.GeneratedTokenData;

/**
 * The master token service.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.13.1
 */
public interface MasterTokenService
{
	/**
	 * Generate master token and save it.
	 *
	 * @param userId the user id
	 * @param deviceId the device id
	 * @param ip the ip
	 * @return the generated master token
	 */
	GeneratedTokenData generateMasterToken (Long userId, String deviceId, String ip) throws CBusinessException;

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
	User getUserByMasterToken (String token, String deviceId, String ip) throws CBusinessException;
}
