package sk.qbsw.security.rest.oauth.api.base.mapper;

import sk.qbsw.security.oauth.model.AuthenticationData;
import sk.qbsw.security.oauth.model.GeneratedTokenData;
import sk.qbsw.security.oauth.model.VerificationData;
import sk.qbsw.security.rest.oauth.client.model.CSAccountData;
import sk.qbsw.security.rest.oauth.client.model.response.AuthenticationResponseBody;
import sk.qbsw.security.rest.oauth.client.model.response.ReauthenticationResponseBody;
import sk.qbsw.security.rest.oauth.client.model.response.VerificationResponseBody;

/**
 * The security authenticationTokenMapper.
 *
 * @author Tomas Lauro
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.18.0
 */
public interface SecurityMapper<T extends CSAccountData>
{
	/**
	 * Map to authentication response authentication response body.
	 *
	 * @param authenticationData the authentication data
	 * @return the authentication response body
	 */
	AuthenticationResponseBody<T> mapToAuthenticationResponse (AuthenticationData authenticationData);

	/**
	 * Map to reauthentication response.
	 *
	 * @param authenticationTokenData the authentication token data
	 * @return the reauthentication response
	 */
	ReauthenticationResponseBody mapToReauthenticationResponse (GeneratedTokenData authenticationTokenData);

	/**
	 * Map user to cs account data cs account data.
	 *
	 * @param verificationData the verification data
	 * @return the cs account data
	 */
	VerificationResponseBody<T> mapToVerificationResponse (VerificationData verificationData);
}
