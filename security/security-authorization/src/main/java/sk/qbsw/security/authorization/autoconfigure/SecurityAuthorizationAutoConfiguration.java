package sk.qbsw.security.authorization.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.qbsw.security.authorization.service.AuthorizationService;
import sk.qbsw.security.authorization.service.AuthorizationServiceImpl;
import sk.qbsw.security.core.autoconfigure.SecurityCoreAutoConfiguration;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.UnitDao;

/**
 * The security authorization auto configuration.
 * 
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.0.0
 */
@Configuration
@AutoConfigureAfter (SecurityCoreAutoConfiguration.class)
public class SecurityAuthorizationAutoConfiguration
{
	@Bean
	@ConditionalOnMissingBean
	public AuthorizationService authorizationService (UnitDao unitDao, AccountDao accountDao)
	{
		return new AuthorizationServiceImpl(unitDao, accountDao);
	}
}
