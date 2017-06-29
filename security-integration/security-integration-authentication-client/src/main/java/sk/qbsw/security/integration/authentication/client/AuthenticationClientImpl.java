package sk.qbsw.security.integration.authentication.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
@Component
public class AuthenticationClientImpl implements AuthenticationClient
{
	private final RestTemplate authenticationRestTemplate;

	private final UrlConfiguration configuration;

	@Autowired
	public AuthenticationClientImpl (RestTemplate authenticationRestTemplate, UrlConfiguration configuration)
	{
		this.authenticationRestTemplate = authenticationRestTemplate;
		this.configuration = configuration;
	}

	@Override
	public CSAccountData verify (VerifyRequestBody requestBody)
	{
		return authenticationRestTemplate.postForObject(configuration.buildUrl(AuthenticationPaths.SECURITY_VERIFY), requestBody, CSAccountData.class);
	}

	@Override
	public AuthenticationResponseBody authenticate (AuthenticationRequestBody requestBody)
	{
		return authenticationRestTemplate.postForObject(configuration.buildUrl(AuthenticationPaths.SECURITY_AUTHENTICATE), requestBody, AuthenticationResponseBody.class);
	}
}
