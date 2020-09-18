package sk.qbsw.security.spring.common.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.qbsw.security.spring.common.service.AuthorityConverter;
import sk.qbsw.security.spring.common.service.AuthorityConverterImpl;

/**
 * The security spring common auto configuration.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.6.0
 */
@Configuration
public class SecuritySpringCommonAutoConfiguration
{
	@Bean
	@ConditionalOnMissingBean
	public AuthorityConverter authorityConverter ()
	{
		return new AuthorityConverterImpl();
	}
}
