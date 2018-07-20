package sk.qbsw.security.rest.oauth.client.base;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import sk.qbsw.core.client.configuration.UrlConfiguration;
import sk.qbsw.security.rest.oauth.client.model.CSAccountData;
import sk.qbsw.security.rest.oauth.client.model.CSExpiredTokenData;
import sk.qbsw.security.rest.oauth.client.model.configuration.AuthenticationPaths;
import sk.qbsw.security.rest.oauth.client.model.configuration.OAuthIntegrationCacheNames;
import sk.qbsw.security.rest.oauth.client.model.request.AuthenticationRequestBody;
import sk.qbsw.security.rest.oauth.client.model.request.VerifyRequestBody;
import sk.qbsw.security.rest.oauth.client.model.response.AuthenticationResponseBody;
import sk.qbsw.security.rest.oauth.client.model.response.VerificationResponseBody;

/**
 * The type Authentication client ip validated.
 *
 * @author Jana Branisova
 * @author Tomas Lauro
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.18.0
 */
public class AuthenticationBaseClientIpValidatedImpl<T extends CSAccountData> implements AuthenticationClient<T>
{
	private final RestTemplate authenticationRestTemplate;

	private final UrlConfiguration configuration;

	/**
	 * Instantiates a new Authentication client.
	 *
	 * @param authenticationRestTemplate the authentication rest template
	 * @param configuration              the configuration
	 */
	public AuthenticationBaseClientIpValidatedImpl (RestTemplate authenticationRestTemplate, UrlConfiguration configuration)
	{
		this.authenticationRestTemplate = authenticationRestTemplate;
		this.configuration = configuration;
	}

	@Override
	public AuthenticationResponseBody<T> authenticate (AuthenticationRequestBody requestBody)
	{
		return authenticationRestTemplate.exchange(configuration.buildUrl(AuthenticationPaths.SECURITY_AUTHENTICATE), HttpMethod.POST, new HttpEntity<>(requestBody), new ParameterizedTypeReference<AuthenticationResponseBody<T>>()
		{
		}).getBody();
	}

	@Override
	@Cacheable (value = {OAuthIntegrationCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#requestBody.token, #requestBody.deviceId, #requestBody.ip}")
	public VerificationResponseBody<T> verify (VerifyRequestBody requestBody)
	{
		return authenticationRestTemplate.exchange(configuration.buildUrl(AuthenticationPaths.SECURITY_VERIFY), HttpMethod.POST, new HttpEntity<>(requestBody), new ParameterizedTypeReference<VerificationResponseBody<T>>()
		{
		}).getBody();
	}

	@Override
	@CacheEvict (value = {OAuthIntegrationCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#expiredToken.token, #expiredToken.deviceId, #expiredToken.ip}")
	public void removeExpiredToken (CSExpiredTokenData expiredToken)
	{
		// intentionally empty
	}
}
