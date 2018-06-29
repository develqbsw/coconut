package sk.qbsw.security.rest.authentication.client;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.client.RestTemplate;

import sk.qbsw.core.client.configuration.UrlConfiguration;
import sk.qbsw.security.rest.authentication.client.model.CSExpiredTokenData;
import sk.qbsw.security.rest.authentication.client.model.configuration.AuthenticationPaths;
import sk.qbsw.security.rest.authentication.client.model.configuration.OAuthIntegrationCacheNames;
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
public class AuthenticationClientIpValidatedImpl implements AuthenticationClient
{
	private final RestTemplate authenticationRestTemplate;

	private final UrlConfiguration configuration;

	/**
	 * Instantiates a new Authentication client.
	 *
	 * @param authenticationRestTemplate the authentication rest template
	 * @param configuration the configuration
	 */
	public AuthenticationClientIpValidatedImpl (RestTemplate authenticationRestTemplate, UrlConfiguration configuration)
	{
		this.authenticationRestTemplate = authenticationRestTemplate;
		this.configuration = configuration;
	}

	@Override
	public AuthenticationResponseBody authenticate (AuthenticationRequestBody requestBody)
	{
		return authenticationRestTemplate.postForObject(configuration.buildUrl(AuthenticationPaths.SECURITY_AUTHENTICATE), requestBody, AuthenticationResponseBody.class);
	}

	@Override
	@Cacheable (value = {OAuthIntegrationCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#requestBody.token, #requestBody.deviceId, #requestBody.ip}")
	public VerificationResponseBody verify (VerifyRequestBody requestBody)
	{
		return authenticationRestTemplate.postForObject(configuration.buildUrl(AuthenticationPaths.SECURITY_VERIFY), requestBody, VerificationResponseBody.class);
	}

	@Override
	@CacheEvict (value = {OAuthIntegrationCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#expiredToken.token, #expiredToken.deviceId, #expiredToken.ip}")
	public void removeExpiredToken (CSExpiredTokenData expiredToken)
	{
		// intentionally empty
	}
}
