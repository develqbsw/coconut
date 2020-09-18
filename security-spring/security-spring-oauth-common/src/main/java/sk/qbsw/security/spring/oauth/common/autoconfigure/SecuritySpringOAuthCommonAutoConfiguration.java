package sk.qbsw.security.spring.oauth.common.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.qbsw.security.spring.common.autoconfigure.SecuritySpringCommonAutoConfiguration;
import sk.qbsw.security.spring.oauth.common.configuration.DefaultOAuthHeaderConfiguration;
import sk.qbsw.security.spring.oauth.common.configuration.OAuthHeaderConfiguration;

/**
 * The security spring OAuth common auto configuration.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.6.0
 */
@Configuration
@AutoConfigureAfter ({SecuritySpringCommonAutoConfiguration.class})
public class SecuritySpringOAuthCommonAutoConfiguration
{
	@Bean
	@ConditionalOnMissingBean
	public OAuthHeaderConfiguration oAuthHeaderConfiguration ()
	{
		return new DefaultOAuthHeaderConfiguration();
	}
}
