package sk.qbsw.security.spring.oauth.local.autoconfigure;

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
import sk.qbsw.core.security.base.model.UserOutputData;
import sk.qbsw.security.oauth.db.autoconfigure.SecurityOAuthAutoConfiguration;
import sk.qbsw.security.oauth.service.facade.OAuthServiceFacade;
import sk.qbsw.security.spring.base.autoconfigure.SecuritySpringBaseAutoConfiguration;
import sk.qbsw.security.spring.base.mapper.UserDataMapper;
import sk.qbsw.security.spring.oauth.common.autoconfigure.SecuritySpringOAuthCommonAutoConfiguration;
import sk.qbsw.security.spring.oauth.local.service.OAuthServiceUserDetailsService;

/**
 * The security spring OAuth local auto configure.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.6.0
 */
@Configuration
@AutoConfigureAfter ({SecurityOAuthAutoConfiguration.class, SecuritySpringBaseAutoConfiguration.class, SecuritySpringOAuthCommonAutoConfiguration.class})
@EnableWebSecurity
@EnableGlobalMethodSecurity (securedEnabled = true, prePostEnabled = true)
public class SecuritySpringOAuthLocalAutoConfiguration
{
	public static final String PRE_AUTHENTICATED_USER_DETAILS_SERVICE_BEAN_NAME = "oauthLocalPreAuthenticatedUserDetailsService";
	public static final String PRE_AUTHENTICATED_AUTHENTICATION_PROVIDER_BEAN_NAME = "oauthLocalPreAuthenticatedAuthenticationProvider";

	@Bean
	@ConditionalOnMissingBean (name = PRE_AUTHENTICATED_USER_DETAILS_SERVICE_BEAN_NAME)
	public AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> oauthLocalPreAuthenticatedUserDetailsService (OAuthServiceFacade<AccountData> oAuthServiceCacheFacade, UserDataMapper<UserOutputData> userDataMapper)
	{
		return new OAuthServiceUserDetailsService(oAuthServiceCacheFacade, userDataMapper);
	}

	@Bean
	@ConditionalOnMissingBean (name = PRE_AUTHENTICATED_AUTHENTICATION_PROVIDER_BEAN_NAME)
	public AuthenticationProvider oauthLocalPreAuthenticatedAuthenticationProvider (AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> oauthLocalPreAuthenticatedUserDetailsService)
	{
		PreAuthenticatedAuthenticationProvider authenticationProvider = new PreAuthenticatedAuthenticationProvider();
		authenticationProvider.setPreAuthenticatedUserDetailsService(oauthLocalPreAuthenticatedUserDetailsService);

		return authenticationProvider;
	}
}
