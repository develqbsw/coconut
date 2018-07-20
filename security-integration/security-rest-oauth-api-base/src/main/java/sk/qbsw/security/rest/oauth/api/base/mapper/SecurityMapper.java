package sk.qbsw.security.rest.oauth.api.base.mapper;

import sk.qbsw.core.security.base.model.AccountData;
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
 * @param <D> the account data type
 * @param <S> the client account data type
 * @author Tomas Lauro
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.18.0
 */
public interface SecurityMapper<D extends AccountData, S extends CSAccountData>
{
	/**
	 * Map to authentication response authentication response body.
	 *
	 * @param authenticationData the authentication data
	 * @return the authentication response body
	 */
	AuthenticationResponseBody<S> mapToAuthenticationResponse (AuthenticationData<D> authenticationData);

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
	VerificationResponseBody<S> mapToVerificationResponse (VerificationData<D> verificationData);
}
