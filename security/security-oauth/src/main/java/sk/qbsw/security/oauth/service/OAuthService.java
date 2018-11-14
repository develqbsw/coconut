package sk.qbsw.security.oauth.service;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.oauth.model.AuthenticationData;
import sk.qbsw.security.oauth.model.ExpiredTokenData;
import sk.qbsw.security.oauth.model.GeneratedTokenData;
import sk.qbsw.security.oauth.model.VerificationData;

import java.util.List;

/**
 * The oauth service.
 *
 * @param <D> the account data type
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.18.1
 */
public interface OAuthService<D extends AccountData>
{
	/**
	 * Authenticate authentication data.
	 *
	 * @param login the login
	 * @param password the password
	 * @param deviceId the device id
	 * @param ip the ip
	 * @param isIpIgnored the is ip ignored
	 * @return the authentication data
	 * @throws CBusinessException the c business exception
	 */
	AuthenticationData<D> authenticate (String login, String password, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException;

	/**
	 * Reauthenticate string.
	 *
	 * @param masterToken the master token
	 * @param deviceId the device id
	 * @param ip the ip
	 * @param isIpIgnored the is ip ignored
	 * @return the string
	 * @throws CBusinessException the c business exception
	 */
	GeneratedTokenData reauthenticate (String masterToken, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException;

	/**
	 * Invalidate.
	 *
	 * @param masterToken the master token
	 * @param authenticationToken the authentication token
	 * @param deviceId the device id
	 * @param ip the ip
	 * @param isIpIgnored the is ip ignored
	 * @throws CBusinessException the c business exception
	 */
	void invalidate (String masterToken, String authenticationToken, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException;

	/**
	 * Verify verification data.
	 *
	 * @param token the token
	 * @param deviceId the device id
	 * @param ip the ip
	 * @param isIpIgnored the is ip ignored
	 * @return the verification data
	 * @throws CBusinessException the c business exception
	 */
	VerificationData<D> verify (String token, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException;

	/**
	 * Remove expired tokens list.
	 *
	 * @return the list
	 */
	List<ExpiredTokenData> removeExpiredTokens ();
}
