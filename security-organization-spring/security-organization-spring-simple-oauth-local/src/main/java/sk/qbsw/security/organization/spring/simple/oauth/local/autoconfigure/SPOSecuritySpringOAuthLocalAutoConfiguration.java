package sk.qbsw.security.organization.spring.simple.oauth.local.autoconfigure;

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

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.oauth.service.facade.OAuthServiceFacade;
import sk.qbsw.security.organization.simple.base.model.SPOUserOutputData;
import sk.qbsw.security.organization.simple.oauth.db.autoconfigure.SPOSecurityOAuthAutoConfiguration;
import sk.qbsw.security.organization.spring.simple.base.autoconfigure.SPOSecuritySpringBaseAutoConfiguration;
import sk.qbsw.security.organization.spring.simple.oauth.local.service.SPOOAuthServiceUserDetailsService;
import sk.qbsw.security.spring.base.mapper.UserDataMapper;
import sk.qbsw.security.spring.oauth.common.autoconfigure.SecuritySpringOAuthCommonAutoConfiguration;

/**
 * The security spring OAuth local auto configure.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.6.0
 */
@Configuration
@AutoConfigureAfter ({SPOSecurityOAuthAutoConfiguration.class, SPOSecuritySpringBaseAutoConfiguration.class, SecuritySpringOAuthCommonAutoConfiguration.class})
@EnableWebSecurity
@EnableGlobalMethodSecurity (securedEnabled = true, prePostEnabled = true)
public class SPOSecuritySpringOAuthLocalAutoConfiguration
{
	public static final String PRE_AUTHENTICATED_USER_DETAILS_SERVICE_BEAN_NAME = "spoOauthLocalPreAuthenticatedUserDetailsService";
	public static final String PRE_AUTHENTICATED_AUTHENTICATION_PROVIDER_BEAN_NAME = "spoOauthLocalPreAuthenticatedAuthenticationProvider";

	@Bean
	@ConditionalOnMissingBean (name = PRE_AUTHENTICATED_USER_DETAILS_SERVICE_BEAN_NAME)
	public AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> spoOauthLocalPreAuthenticatedUserDetailsService (UserDataMapper<SPOUserOutputData> spoUserDataMapper, OAuthServiceFacade<AccountData> oAuthServiceCacheFacade)
	{
		return new SPOOAuthServiceUserDetailsService(oAuthServiceCacheFacade, spoUserDataMapper);
	}

	@Bean
	@ConditionalOnMissingBean (name = PRE_AUTHENTICATED_AUTHENTICATION_PROVIDER_BEAN_NAME)
	public AuthenticationProvider spoOauthLocalPreAuthenticatedAuthenticationProvider (AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> spoOauthLocalPreAuthenticatedUserDetailsService)
	{
		PreAuthenticatedAuthenticationProvider authenticationProvider = new PreAuthenticatedAuthenticationProvider();
		authenticationProvider.setPreAuthenticatedUserDetailsService(spoOauthLocalPreAuthenticatedUserDetailsService);

		return authenticationProvider;
	}
}
