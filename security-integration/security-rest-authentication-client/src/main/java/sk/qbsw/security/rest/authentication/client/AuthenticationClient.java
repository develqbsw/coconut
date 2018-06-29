package sk.qbsw.security.rest.authentication.client;

import sk.qbsw.security.rest.authentication.client.model.CSExpiredTokenData;
import sk.qbsw.security.rest.authentication.client.model.request.AuthenticationRequestBody;
import sk.qbsw.security.rest.authentication.client.model.request.VerifyRequestBody;
import sk.qbsw.security.rest.authentication.client.model.response.AuthenticationResponseBody;
import sk.qbsw.security.rest.authentication.client.model.response.VerificationResponseBody;

/**
 * The authentication client.
 *
 * @author Jana Branisova
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.0
 */
public interface AuthenticationClient
{
	/**
	 * Authenticate authentication response body.
	 *
	 * @param requestBody the request body
	 * @return the authentication response body
	 */
	AuthenticationResponseBody authenticate (AuthenticationRequestBody requestBody);

	/**
	 * Verify verification response body.
	 *
	 * @param requestBody the request body
	 * @return the verification response body
	 */
	VerificationResponseBody verify (VerifyRequestBody requestBody);

	/**
	 * Remove expired token from cache.
	 *
	 * @param expiredToken the expired token
	 */
	void removeExpiredToken (CSExpiredTokenData expiredToken);
}
