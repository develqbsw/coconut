package sk.qbsw.security.authorization.configuration;

import org.springframework.context.annotation.Bean;
import sk.qbsw.security.authorization.service.AuthorizationService;
import sk.qbsw.security.authorization.service.AuthorizationServiceImpl;
import sk.qbsw.security.core.configuration.SecurityCoreConfiguration;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.UnitDao;

/**
 * The security authorization configuration.
 * 
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
public class SecurityAuthorizationConfiguration extends SecurityCoreConfiguration
{
	@Bean
	public AuthorizationService authorizationService (UnitDao unitDao, AccountDao accountDao)
	{
		return new AuthorizationServiceImpl(unitDao, accountDao);
	}
}
