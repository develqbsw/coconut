package sk.qbsw.security.spring.iam.auth.ms.autoconfigure;

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

import io.jsonwebtoken.Claims;
import sk.qbsw.security.spring.base.mapper.UserDataMapper;
import sk.qbsw.security.spring.common.service.AuthorityConverter;
import sk.qbsw.security.spring.iam.auth.common.autoconfigure.SecuritySpringIamAuthCommonAutoConfiguration;
import sk.qbsw.security.spring.iam.auth.common.configuration.InternalJwtConfiguration;
import sk.qbsw.security.spring.iam.auth.ms.mapper.UserDataMapperImpl;
import sk.qbsw.security.spring.iam.auth.ms.service.MSAuthUserDetailsService;

/**
 * The security spring IAM auth MS auto configure.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.6.0
 */
@Configuration
@AutoConfigureAfter ({SecuritySpringIamAuthCommonAutoConfiguration.class})
@EnableWebSecurity
@EnableGlobalMethodSecurity (securedEnabled = true, prePostEnabled = true)
public class SecuritySpringIamAuthMsAutoConfiguration
{
	public static final String USER_DATA_MAPPER_BEAN_NAME = "iamAuthMsUserDataMapper";
	public static final String PRE_AUTHENTICATED_USER_DETAILS_SERVICE_BEAN_NAME = "iamAuthMsPreAuthenticatedUserDetailsService";
	public static final String PRE_AUTHENTICATED_AUTHENTICATION_PROVIDER_BEAN_NAME = "iamAuthMsPreAuthenticatedAuthenticationProvider";

	@Bean
	@ConditionalOnMissingBean (name = USER_DATA_MAPPER_BEAN_NAME)
	public UserDataMapper<Claims> iamAuthMsUserDataMapper (InternalJwtConfiguration internalJwtConfiguration)
	{
		return new UserDataMapperImpl(internalJwtConfiguration);
	}

	@Bean
	@ConditionalOnMissingBean (name = PRE_AUTHENTICATED_USER_DETAILS_SERVICE_BEAN_NAME)
	public AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> iamAuthMsPreAuthenticatedUserDetailsService (AuthorityConverter authorityConverter, UserDataMapper<Claims> iamAuthMsUserDataMapper, InternalJwtConfiguration internalJwtConfiguration)
	{
		return new MSAuthUserDetailsService(authorityConverter, iamAuthMsUserDataMapper, internalJwtConfiguration);
	}

	@Bean
	@ConditionalOnMissingBean (name = PRE_AUTHENTICATED_AUTHENTICATION_PROVIDER_BEAN_NAME)
	public AuthenticationProvider iamAuthMsPreAuthenticatedAuthenticationProvider (AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> iamAuthMsPreAuthenticatedUserDetailsService)
	{
		PreAuthenticatedAuthenticationProvider authenticationProvider = new PreAuthenticatedAuthenticationProvider();
		authenticationProvider.setPreAuthenticatedUserDetailsService(iamAuthMsPreAuthenticatedUserDetailsService);

		return authenticationProvider;
	}
}
