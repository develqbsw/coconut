package sk.qbsw.security.integration.authentication.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.qbsw.core.client.configuration.UrlConfiguration;
import sk.qbsw.security.api.authentication.client.AuthenticationPaths;
import sk.qbsw.security.api.authentication.client.model.CSAccountData;
import sk.qbsw.security.api.authentication.client.model.request.AuthenticationRequestBody;
import sk.qbsw.security.api.authentication.client.model.request.VerifyRequestBody;
import sk.qbsw.security.api.authentication.client.model.response.AuthenticationResponseBody;

/**
 * The authentication client.
 * 
 * @author Jana Branisova
 * @author Tomas Lauro
 * @version 1.18.0
 * @since 1.18.0
 */
public class AuthenticationClientImpl implements AuthenticationClient
{
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private UrlConfiguration configuration;

	@Override
	public CSAccountData verify (VerifyRequestBody requestBody)
	{
		return restTemplate.postForObject(configuration.buildUrl(AuthenticationPaths.VERIFY), requestBody, CSAccountData.class);
	}

	@Override
	public AuthenticationResponseBody authenticate (AuthenticationRequestBody requestBody)
	{
		return restTemplate.postForObject(configuration.buildUrl(AuthenticationPaths.AUTHENTICATE), requestBody, AuthenticationResponseBody.class);
	}
}
