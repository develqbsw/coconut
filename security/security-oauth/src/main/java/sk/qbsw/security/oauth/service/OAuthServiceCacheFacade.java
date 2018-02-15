package sk.qbsw.security.oauth.service;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.security.oauth.model.AuthenticationData;
import sk.qbsw.security.oauth.model.GeneratedTokenData;
import sk.qbsw.security.oauth.model.VerificationData;

/**
 * The oauth service facade for caching.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.18.1
 */
public interface OAuthServiceCacheFacade
{
	/**
	 * Authenticate authentication data.
	 *
	 * @param login the login
	 * @param password the password
	 * @param deviceId the device id
	 * @param ip the ip
	 * @return the authentication data
	 * @throws CBusinessException the c business exception
	 */
	AuthenticationData authenticate (String login, String password, String deviceId, String ip) throws CBusinessException;

	/**
	 * Reauthenticate string.
	 *
	 * @param masterToken the master token
	 * @param deviceId the device id
	 * @param ip the ip
	 * @return the string
	 * @throws CBusinessException the c business exception
	 */
	GeneratedTokenData reauthenticate (String masterToken, String deviceId, String ip) throws CBusinessException;

	/**
	 * Invalidate.
	 *
	 * @param masterToken the master token
	 * @param authenticationToken the authentication token
	 * @param deviceId the device id
	 * @param ip the ip
	 * @throws CBusinessException the c business exception
	 */
	void invalidate (String masterToken, String authenticationToken, String deviceId, String ip) throws CBusinessException;

	/**
	 * Verify verification data.
	 *
	 * @param token the token
	 * @param deviceId the device id
	 * @param ip the ip
	 * @return the verification data
	 * @throws CBusinessException the c business exception
	 */
	VerificationData verify (String token, String deviceId, String ip) throws CBusinessException;
}
