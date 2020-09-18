package sk.qbsw.security.spring.oauth.rest.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import sk.qbsw.security.rest.oauth.client.autoconfigure.SecurityRestOAuthClientAutoConfiguration;
import sk.qbsw.security.rest.oauth.client.base.AuthenticationClient;
import sk.qbsw.security.rest.oauth.client.model.CSAccountData;
import sk.qbsw.security.rest.oauth.client.model.CSUserData;
import sk.qbsw.security.spring.base.mapper.UserDataMapper;
import sk.qbsw.security.spring.oauth.common.autoconfigure.SecuritySpringOAuthCommonAutoConfiguration;
import sk.qbsw.security.spring.oauth.rest.mapper.UserDataMapperImpl;
import sk.qbsw.security.spring.oauth.rest.service.OAuthWebServiceUserDetailsService;

/**
 * The security spring OAuth REST auto configure.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.6.0
 */
@Configuration
@AutoConfigureAfter ({SecurityRestOAuthClientAutoConfiguration.class, SecuritySpringOAuthCommonAutoConfiguration.class})
@EnableWebSecurity
@EnableGlobalMethodSecurity (securedEnabled = true, prePostEnabled = true)
public class SecuritySpringOAuthRestAutoConfiguration
{
	public static final String USER_DATA_MAPPER_BEAN_NAME = "oauthRestUserDataMapper";
	public static final String PRE_AUTHENTICATED_USER_DETAILS_SERVICE_BEAN_NAME = "oauthRestPreAuthenticatedUserDetailsService";
	public static final String PRE_AUTHENTICATED_AUTHENTICATION_PROVIDER_BEAN_NAME = "oauthRestPreAuthenticatedAuthenticationProvider";

	@Bean
	@ConditionalOnMissingBean (name = USER_DATA_MAPPER_BEAN_NAME)
	public UserDataMapper<CSUserData> oauthRestUserDataMapper ()
	{
		return new UserDataMapperImpl();
	}

	@Bean
	@ConditionalOnMissingBean (name = PRE_AUTHENTICATED_USER_DETAILS_SERVICE_BEAN_NAME)
	public AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> oauthRestPreAuthenticatedUserDetailsService (AuthenticationClient<CSAccountData> restOauthRestAuthenticationClient, UserDataMapper<CSUserData> oauthRestUserDataMapper)
	{
		return new OAuthWebServiceUserDetailsService(restOauthRestAuthenticationClient, oauthRestUserDataMapper);
	}

	@Bean
	@ConditionalOnMissingBean (name = PRE_AUTHENTICATED_AUTHENTICATION_PROVIDER_BEAN_NAME)
	public AuthenticationProvider oauthLocalPreAuthenticatedAuthenticationProvider (AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> oauthRestPreAuthenticatedUserDetailsService)
	{
		PreAuthenticatedAuthenticationProvider authenticationProvider = new PreAuthenticatedAuthenticationProvider();
		authenticationProvider.setPreAuthenticatedUserDetailsService(oauthRestPreAuthenticatedUserDetailsService);

		return authenticationProvider;
	}
}
