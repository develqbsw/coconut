package sk.qbsw.security.rest.oauth.client.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import sk.qbsw.core.client.configuration.UrlConfiguration;
import sk.qbsw.security.rest.oauth.client.AuthenticationClientIpIgnoredImpl;
import sk.qbsw.security.rest.oauth.client.AuthenticationClientIpValidatedImpl;
import sk.qbsw.security.rest.oauth.client.base.AuthenticationClient;
import sk.qbsw.security.rest.oauth.client.base.autoconfigure.SecurityRestOAuthClientBaseAutoConfiguration;
import sk.qbsw.security.rest.oauth.client.model.CSAccountData;

/**
 * The security integration REST OAuth client auto configuration.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.6.0
 */
@Configuration
@AutoConfigureAfter (SecurityRestOAuthClientBaseAutoConfiguration.class)
public class SecurityRestOAuthClientAutoConfiguration
{
	private static final String AUTHENTICATION_CLIENT_BEAN_NAME = "restOauthRestAuthenticationClient";

	@Bean (name = AUTHENTICATION_CLIENT_BEAN_NAME)
	@ConditionalOnMissingBean (name = AUTHENTICATION_CLIENT_BEAN_NAME)
	@ConditionalOnProperty (name = "coconut.security.integration.rest.oauth.client.ip.validated", havingValue = "false", matchIfMissing = true)
	public AuthenticationClient<CSAccountData> authenticationClientIpIgnored (RestTemplate restOAuthRestTemplate, UrlConfiguration restOauthRestUrlConfiguration)
	{
		return new AuthenticationClientIpIgnoredImpl(restOAuthRestTemplate, restOauthRestUrlConfiguration);
	}

	@Bean (name = AUTHENTICATION_CLIENT_BEAN_NAME)
	@ConditionalOnMissingBean (name = AUTHENTICATION_CLIENT_BEAN_NAME)
	@ConditionalOnProperty (name = "coconut.security.integration.rest.oauth.client.ip.validated")
	public AuthenticationClient<CSAccountData> authenticationClientIpValidated (RestTemplate restOAuthRestTemplate, UrlConfiguration restOauthRestUrlConfiguration)
	{
		return new AuthenticationClientIpValidatedImpl(restOAuthRestTemplate, restOauthRestUrlConfiguration);
	}
}
