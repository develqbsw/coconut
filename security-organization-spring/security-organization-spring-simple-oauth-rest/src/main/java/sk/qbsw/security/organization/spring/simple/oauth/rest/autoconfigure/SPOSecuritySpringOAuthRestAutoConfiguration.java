package sk.qbsw.security.organization.spring.simple.oauth.rest.autoconfigure;

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

import sk.qbsw.security.organization.rest.oauth.simple.client.autoconfigure.SPOSecurityRestOAuthClientAutoConfiguration;
import sk.qbsw.security.organization.rest.oauth.simple.client.model.CSSPOAccountData;
import sk.qbsw.security.organization.rest.oauth.simple.client.model.CSSPOUserData;
import sk.qbsw.security.organization.spring.simple.oauth.rest.mapper.SPOUserDataMapperImpl;
import sk.qbsw.security.organization.spring.simple.oauth.rest.service.SPOOAuthWebServiceUserDetailsService;
import sk.qbsw.security.rest.oauth.client.base.AuthenticationClient;
import sk.qbsw.security.spring.base.mapper.UserDataMapper;
import sk.qbsw.security.spring.oauth.common.autoconfigure.SecuritySpringOAuthCommonAutoConfiguration;

/**
 * The simple organization security spring OAuth REST auto configure.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.6.0
 */
@Configuration
@AutoConfigureAfter ({SPOSecurityRestOAuthClientAutoConfiguration.class, SecuritySpringOAuthCommonAutoConfiguration.class})
@EnableWebSecurity
@EnableGlobalMethodSecurity (securedEnabled = true, prePostEnabled = true)
public class SPOSecuritySpringOAuthRestAutoConfiguration
{
	public static final String USER_DATA_MAPPER_BEAN_NAME = "spoOauthRestUserDataMapper";
	public static final String PRE_AUTHENTICATED_USER_DETAILS_SERVICE_BEAN_NAME = "spoOauthRestPreAuthenticatedUserDetailsService";
	public static final String PRE_AUTHENTICATED_AUTHENTICATION_PROVIDER_BEAN_NAME = "spoOauthRestPreAuthenticatedAuthenticationProvider";

	@Bean
	@ConditionalOnMissingBean (name = USER_DATA_MAPPER_BEAN_NAME)
	public UserDataMapper<CSSPOUserData> spoOauthRestUserDataMapper ()
	{
		return new SPOUserDataMapperImpl();
	}

	@Bean
	@ConditionalOnMissingBean (name = PRE_AUTHENTICATED_USER_DETAILS_SERVICE_BEAN_NAME)
	public AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> spoOauthRestPreAuthenticatedUserDetailsService (AuthenticationClient<CSSPOAccountData> spoRestOauthRestAuthenticationClient, UserDataMapper<CSSPOUserData> spoOauthRestUserDataMapper)
	{
		return new SPOOAuthWebServiceUserDetailsService(spoRestOauthRestAuthenticationClient, spoOauthRestUserDataMapper);
	}

	@Bean
	@ConditionalOnMissingBean (name = PRE_AUTHENTICATED_AUTHENTICATION_PROVIDER_BEAN_NAME)
	public AuthenticationProvider spoOauthRestPreAuthenticatedAuthenticationProvider (AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> spoOauthRestPreAuthenticatedUserDetailsService)
	{
		PreAuthenticatedAuthenticationProvider authenticationProvider = new PreAuthenticatedAuthenticationProvider();
		authenticationProvider.setPreAuthenticatedUserDetailsService(spoOauthRestPreAuthenticatedUserDetailsService);

		return authenticationProvider;
	}
}
