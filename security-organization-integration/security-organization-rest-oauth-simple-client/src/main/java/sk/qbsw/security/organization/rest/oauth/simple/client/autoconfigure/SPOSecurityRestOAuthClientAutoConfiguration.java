package sk.qbsw.security.organization.rest.oauth.simple.client.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import sk.qbsw.core.client.configuration.UrlConfiguration;
import sk.qbsw.security.organization.rest.oauth.simple.client.AuthenticationClientIpIgnoredImpl;
import sk.qbsw.security.organization.rest.oauth.simple.client.AuthenticationClientIpValidatedImpl;
import sk.qbsw.security.organization.rest.oauth.simple.client.model.CSSPOAccountData;
import sk.qbsw.security.rest.oauth.client.base.AuthenticationClient;
import sk.qbsw.security.rest.oauth.client.base.autoconfigure.SecurityRestOAuthClientBaseAutoConfiguration;

/**
 * The complex organization security integration REST OAuth client auto configuration.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.6.0
 */
@Configuration
@AutoConfigureAfter (SecurityRestOAuthClientBaseAutoConfiguration.class)
public class SPOSecurityRestOAuthClientAutoConfiguration
{
	private static final String AUTHENTICATION_CLIENT_BEAN_NAME = "spoRestOauthRestAuthenticationClient";

	@Bean (name = AUTHENTICATION_CLIENT_BEAN_NAME)
	@ConditionalOnMissingBean (name = AUTHENTICATION_CLIENT_BEAN_NAME)
	@ConditionalOnProperty (name = "coconut.security.spo.integration.rest.oauth.client.ip.validated", havingValue = "false", matchIfMissing = true)
	public AuthenticationClient<CSSPOAccountData> authenticationClientIpIgnored (RestTemplate restOAuthRestTemplate, UrlConfiguration restOauthRestUrlConfiguration)
	{
		return new AuthenticationClientIpIgnoredImpl(restOAuthRestTemplate, restOauthRestUrlConfiguration);
	}

	@Bean (name = AUTHENTICATION_CLIENT_BEAN_NAME)
	@ConditionalOnMissingBean (name = AUTHENTICATION_CLIENT_BEAN_NAME)
	@ConditionalOnProperty (name = "coconut.security.spo.integration.rest.oauth.client.ip.validated")
	public AuthenticationClient<CSSPOAccountData> authenticationClientIpValidated (RestTemplate restOAuthRestTemplate, UrlConfiguration restOauthRestUrlConfiguration)
	{
		return new AuthenticationClientIpValidatedImpl(restOAuthRestTemplate, restOauthRestUrlConfiguration);
	}
}
