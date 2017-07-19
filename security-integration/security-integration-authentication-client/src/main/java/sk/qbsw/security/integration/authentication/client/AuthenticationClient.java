package sk.qbsw.security.integration.authentication.client;

import sk.qbsw.security.api.authentication.client.model.CSAccountData;
import sk.qbsw.security.api.authentication.client.model.request.AuthenticationRequestBody;
import sk.qbsw.security.api.authentication.client.model.request.VerifyRequestBody;
import sk.qbsw.security.api.authentication.client.model.response.AuthenticationResponseBody;

/**
 * The internal client.
 * 
 * @author Jana Branisova
 * @version 1.0.0
 */
public interface AuthenticationClient
{
	CSAccountData verify (VerifyRequestBody requestBody);

	AuthenticationResponseBody authenticate (AuthenticationRequestBody requestBody);
}
