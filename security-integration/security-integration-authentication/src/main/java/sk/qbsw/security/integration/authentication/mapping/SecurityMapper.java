package sk.qbsw.security.integration.authentication.mapping;

import sk.qbsw.security.api.authentication.client.model.response.AuthenticationResponseBody;
import sk.qbsw.security.api.authentication.client.model.response.ReauthenticationResponseBody;
import sk.qbsw.security.api.authentication.client.model.response.VerificationResponseBody;
import sk.qbsw.security.oauth.model.AuthenticationData;
import sk.qbsw.security.oauth.model.GeneratedTokenData;
import sk.qbsw.security.oauth.model.VerificationData;

/**
 * The security mapper.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.18.0
 */
public interface SecurityMapper
{
	/**
	 * Map to authentication response authentication response body.
	 *
	 * @param authenticationData the authentication data
	 * @return the authentication response body
	 */
	AuthenticationResponseBody mapToAuthenticationResponse (AuthenticationData authenticationData);

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
	VerificationResponseBody mapToVerificationResponse (VerificationData verificationData);
}
