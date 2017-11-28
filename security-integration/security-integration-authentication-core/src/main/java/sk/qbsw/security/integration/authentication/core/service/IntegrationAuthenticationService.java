package sk.qbsw.security.integration.authentication.core.service;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.security.integration.authentication.core.model.AuthenticationData;
import sk.qbsw.security.integration.authentication.core.model.VerificationData;

/**
 * The integration authentication service.
 *
 * @author Tomas Lauro
 * @author Roman Farkaš
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IntegrationAuthenticationService
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
	String reauthenticate (String masterToken, String deviceId, String ip) throws CBusinessException;

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
