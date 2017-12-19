package sk.qbsw.security.oauth.service;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.security.oauth.model.AccountData;

/**
 * The oauth service.
 *
 * @author Tomas Lauro
 * @version 1.18.1
 * @since 1.18.1
 */
public interface OAuthService
{
	/**
	 * Gets user by o auth token.
	 *
	 * @param token the token
	 * @param deviceId the device id
	 * @param ip the ip
	 * @return the user by o auth token
	 * @throws CBusinessException the c business exception
	 */
	AccountData getUserByOAuthToken (String token, String deviceId, String ip);
}
