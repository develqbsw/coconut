package sk.qbsw.security.authentication.db.configuration;

import org.springframework.context.annotation.Bean;
import sk.qbsw.security.authentication.service.AuthenticationService;
import sk.qbsw.security.authentication.db.service.DatabaseAuthenticationServiceImpl;
import sk.qbsw.security.core.configuration.SecurityCoreConfiguration;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AuthenticationParamsDao;
import sk.qbsw.security.core.dao.UnitDao;
import sk.qbsw.security.core.service.signature.PasswordDigester;

/**
 * The security authentication configuration.
 * 
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class SecurityAuthenticationConfiguration extends SecurityCoreConfiguration
{
	@Bean
	public AuthenticationService databaseAuthenticationService (AccountDao accountDao, UnitDao unitDao, AuthenticationParamsDao authenticationParamsDao, PasswordDigester digester)
	{
		return new DatabaseAuthenticationServiceImpl(accountDao, unitDao, authenticationParamsDao, digester);
	}
}
