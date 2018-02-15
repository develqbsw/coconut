package sk.qbsw.security.integration.authentication.client;

import sk.qbsw.security.api.authentication.client.model.request.AuthenticationRequestBody;
import sk.qbsw.security.api.authentication.client.model.request.VerifyRequestBody;
import sk.qbsw.security.api.authentication.client.model.response.AuthenticationResponseBody;
import sk.qbsw.security.api.authentication.client.model.response.VerificationResponseBody;

/**
 * The authentication client.
 *
 * @author Jana Branisova
 * @author Tomas Lauro
 * @version 1.18.2
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
}
