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
 * The type authentication client ip ignored base.
 *
 * @param <D> the account data type
 * @author Jana Branisova
 * @author Tomas Lauro
 * @author Tomas Leken
 * @version 2.2.0
 * @since 1.18.0
 */
public abstract class AuthenticationClientIpIgnoredBase<D extends CSAccountData> implements AuthenticationClient<D>
{
	private final RestTemplate authenticationRestTemplate;

	private final UrlConfiguration configuration;

	/**
	 * Instantiates a new Authentication client.
	 *
	 * @param authenticationRestTemplate the authentication rest template
	 * @param configuration the configuration
	 */
	public AuthenticationClientIpIgnoredBase (RestTemplate authenticationRestTemplate, UrlConfiguration configuration)
	{
		this.authenticationRestTemplate = authenticationRestTemplate;
		this.configuration = configuration;
	}

	@Override
	public AuthenticationResponseBody<D> authenticate (AuthenticationRequestBody requestBody)
	{
		return authenticationRestTemplate.exchange(configuration.buildUrl(AuthenticationPaths.SECURITY_AUTHENTICATE), HttpMethod.POST, new HttpEntity<>(requestBody), provideAuthenticationResponseBodyType()).getBody();
	}

	@Override
	@Cacheable (value = {OAuthIntegrationCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#requestBody.token, #requestBody.deviceId}")
	public VerificationResponseBody<D> verify (VerifyRequestBody requestBody)
	{
		return authenticationRestTemplate.exchange(configuration.buildUrl(AuthenticationPaths.SECURITY_VERIFY), HttpMethod.POST, new HttpEntity<>(requestBody), provideVerificationResponseBodyType()).getBody();
	}

	@Override
	@CacheEvict (value = {OAuthIntegrationCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#expiredToken.token, #expiredToken.deviceId}")
	public void removeExpiredToken (CSExpiredTokenData expiredToken)
	{
		// intentionally empty
	}

	/**
	 * Provide authentication response body type parameterized type reference.
	 *
	 * @return the parameterized type reference
	 */
	protected abstract ParameterizedTypeReference<AuthenticationResponseBody<D>> provideAuthenticationResponseBodyType ();

	/**
	 * Provide verification response body type parameterized type reference.
	 *
	 * @return the parameterized type reference
	 */
	protected abstract ParameterizedTypeReference<VerificationResponseBody<D>> provideVerificationResponseBodyType ();
}
