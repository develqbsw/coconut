package sk.qbsw.security.spring.iam.auth.common.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.qbsw.security.spring.common.autoconfigure.SecuritySpringCommonAutoConfiguration;
import sk.qbsw.security.spring.common.configuration.AuthHeaderConfiguration;
import sk.qbsw.security.spring.iam.auth.common.configuration.DefaultIAMAuthHeaderConfiguration;
import sk.qbsw.security.spring.iam.auth.common.configuration.IAMAuthAccountPermissionConfiguration;
import sk.qbsw.security.spring.iam.auth.common.configuration.InternalJwtClaims;
import sk.qbsw.security.spring.iam.auth.common.configuration.InternalJwtConfiguration;
import sk.qbsw.security.spring.iam.auth.common.configuration.properties.InternalJwtClaimsConfigurationProperties;
import sk.qbsw.security.spring.iam.auth.common.configuration.properties.InternalJwtConfigurationProperties;

/**
 * The security spring IAM auth common auto configuration.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.6.0
 */
@Configuration
@AutoConfigureAfter ({SecuritySpringCommonAutoConfiguration.class})
public class SecuritySpringIamAuthCommonAutoConfiguration
{
	public static final String DEFAULT_ACCOUNT_GROUP_CODE = "IAM_USER";

	@Bean
	@ConditionalOnMissingBean
	public AuthHeaderConfiguration authHeaderConfiguration ()
	{
		return new DefaultIAMAuthHeaderConfiguration();
	}

	@Bean
	public IAMAuthAccountPermissionConfiguration iamAuthAccountPermissionConfiguration ()
	{
		return () -> DEFAULT_ACCOUNT_GROUP_CODE;
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty (name = "coconut.security.iam.auth.internal-jwt.claims")
	public InternalJwtClaims internalJwtClaims ()
	{
		return new InternalJwtClaimsConfigurationProperties();
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty (name = "coconut.security.iam.auth.internal-jwt")
	public InternalJwtConfiguration internalJwtConfiguration ()
	{
		return new InternalJwtConfigurationProperties();
	}
}
