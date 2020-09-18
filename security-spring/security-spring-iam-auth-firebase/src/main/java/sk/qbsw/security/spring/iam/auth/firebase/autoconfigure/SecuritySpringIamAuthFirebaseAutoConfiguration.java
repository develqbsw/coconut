package sk.qbsw.security.spring.iam.auth.firebase.autoconfigure;

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
import sk.qbsw.core.security.base.model.AccountInputData;
import sk.qbsw.core.security.base.model.UserOutputData;
import sk.qbsw.security.management.service.AccountManagementService;
import sk.qbsw.security.management.service.AccountPermissionManagementService;
import sk.qbsw.security.spring.base.autoconfigure.SecuritySpringBaseAutoConfiguration;
import sk.qbsw.security.spring.base.mapper.UserDataMapper;
import sk.qbsw.security.spring.common.service.AuthorityConverter;
import sk.qbsw.security.spring.iam.auth.common.autoconfigure.SecuritySpringIamAuthCommonAutoConfiguration;
import sk.qbsw.security.spring.iam.auth.common.configuration.IAMAuthAccountPermissionConfiguration;
import sk.qbsw.security.spring.iam.auth.firebase.service.FirebaseAuthUserDetailsService;

/**
 * The security spring IAM auth firebase auto configuration.
 *
 * @author Tomas Leken
 * @version 2.6.0
 * @since 2.6.0
 */
@Configuration
@AutoConfigureAfter ({SecuritySpringIamAuthCommonAutoConfiguration.class, SecuritySpringBaseAutoConfiguration.class})
@EnableWebSecurity
@EnableGlobalMethodSecurity (securedEnabled = true, prePostEnabled = true)
public class SecuritySpringIamAuthFirebaseAutoConfiguration
{
	public static final String PRE_AUTHENTICATED_USER_DETAILS_SERVICE_BEAN_NAME = "iamAuthFirebasePreAuthenticatedUserDetailsService";
	public static final String PRE_AUTHENTICATED_AUTHENTICATION_PROVIDER_BEAN_NAME = "iamAuthFirebasePreAuthenticatedAuthenticationProvider";

	@Bean
	@ConditionalOnMissingBean (name = PRE_AUTHENTICATED_USER_DETAILS_SERVICE_BEAN_NAME)
	public AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> iamAuthFirebasePreAuthenticatedUserDetailsService (AccountManagementService<AccountInputData, AccountData> accountManagementService, AccountPermissionManagementService accountPermissionManagementService, IAMAuthAccountPermissionConfiguration iamAuthAccountPermissionConfiguration, AuthorityConverter authorityConverter, UserDataMapper<UserOutputData> userDataMapper)
	{
		return new FirebaseAuthUserDetailsService(accountManagementService, accountPermissionManagementService, authorityConverter, iamAuthAccountPermissionConfiguration, userDataMapper);
	}

	@Bean
	@ConditionalOnMissingBean (name = PRE_AUTHENTICATED_AUTHENTICATION_PROVIDER_BEAN_NAME)
	public AuthenticationProvider iamAuthFirebasePreAuthenticatedAuthenticationProvider (AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> iamAuthFirebasePreAuthenticatedUserDetailsService)
	{
		PreAuthenticatedAuthenticationProvider authenticationProvider = new PreAuthenticatedAuthenticationProvider();
		authenticationProvider.setPreAuthenticatedUserDetailsService(iamAuthFirebasePreAuthenticatedUserDetailsService);

		return authenticationProvider;
	}
}
