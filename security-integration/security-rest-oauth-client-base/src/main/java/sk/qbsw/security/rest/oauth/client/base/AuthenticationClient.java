package sk.qbsw.security.rest.oauth.client.base;

import sk.qbsw.security.rest.oauth.client.model.CSAccountData;
import sk.qbsw.security.rest.oauth.client.model.CSExpiredTokenData;
import sk.qbsw.security.rest.oauth.client.model.request.AuthenticationRequestBody;
import sk.qbsw.security.rest.oauth.client.model.request.VerifyRequestBody;
import sk.qbsw.security.rest.oauth.client.model.response.AuthenticationResponseBody;
import sk.qbsw.security.rest.oauth.client.model.response.VerificationResponseBody;

/**
 * The authentication client.
 *
 * @author Jana Branisova
 * @author Tomas Lauro
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.18.0
 */
public interface AuthenticationClient<T extends CSAccountData>
{
	/**
	 * Authenticate authentication response body.
	 *
	 * @param requestBody the request body
	 * @return the authentication response body
	 */
	AuthenticationResponseBody<T> authenticate (AuthenticationRequestBody requestBody);

	/**
	 * Verify verification response body.
	 *
	 * @param requestBody the request body
	 * @return the verification response body
	 */
	VerificationResponseBody<T> verify (VerifyRequestBody requestBody);

	/**
	 * Remove expired token from cache.
	 *
	 * @param expiredToken the expired token
	 */
	void removeExpiredToken (CSExpiredTokenData expiredToken);
}
