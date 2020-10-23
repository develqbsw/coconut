package sk.qbsw.security.organization.spring.complex.oauth.local.autoconfigure;

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
import sk.qbsw.security.organization.complex.base.model.CXOUserOutputData;
import sk.qbsw.security.organization.complex.oauth.db.autoconfigure.CXOSecurityOAuthAutoConfiguration;
import sk.qbsw.security.organization.spring.complex.base.autoconfigure.CXOSecuritySpringBaseAutoConfiguration;
import sk.qbsw.security.organization.spring.complex.oauth.local.service.CXOOAuthServiceUserDetailsService;
import sk.qbsw.security.spring.base.mapper.UserDataMapper;
import sk.qbsw.security.spring.oauth.common.autoconfigure.SecuritySpringOAuthCommonAutoConfiguration;

/**
 * The complex organization security spring OAuth local auto configure.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.6.0
 */
@Configuration
@AutoConfigureAfter ({CXOSecurityOAuthAutoConfiguration.class, CXOSecuritySpringBaseAutoConfiguration.class, SecuritySpringOAuthCommonAutoConfiguration.class})
@EnableWebSecurity
@EnableGlobalMethodSecurity (securedEnabled = true, prePostEnabled = true)
public class CXOSecuritySpringOAuthLocalAutoConfiguration
{
	public static final String PRE_AUTHENTICATED_USER_DETAILS_SERVICE_BEAN_NAME = "cxoOauthLocalPreAuthenticatedUserDetailsService";
	public static final String PRE_AUTHENTICATED_AUTHENTICATION_PROVIDER_BEAN_NAME = "cxoOauthLocalPreAuthenticatedAuthenticationProvider";

	@Bean
	@ConditionalOnMissingBean (name = PRE_AUTHENTICATED_USER_DETAILS_SERVICE_BEAN_NAME)
	public AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> cxoOauthLocalPreAuthenticatedUserDetailsService (UserDataMapper<CXOUserOutputData> cxoUserDataMapper, OAuthServiceFacade<AccountData> oAuthServiceCacheFacade)
	{
		return new CXOOAuthServiceUserDetailsService(oAuthServiceCacheFacade, cxoUserDataMapper);
	}

	@Bean
	@ConditionalOnMissingBean (name = PRE_AUTHENTICATED_AUTHENTICATION_PROVIDER_BEAN_NAME)
	public AuthenticationProvider cxoOauthLocalPreAuthenticatedAuthenticationProvider (AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> cxoOauthLocalPreAuthenticatedUserDetailsService)
	{
		PreAuthenticatedAuthenticationProvider authenticationProvider = new PreAuthenticatedAuthenticationProvider();
		authenticationProvider.setPreAuthenticatedUserDetailsService(cxoOauthLocalPreAuthenticatedUserDetailsService);

		return authenticationProvider;
	}
}
