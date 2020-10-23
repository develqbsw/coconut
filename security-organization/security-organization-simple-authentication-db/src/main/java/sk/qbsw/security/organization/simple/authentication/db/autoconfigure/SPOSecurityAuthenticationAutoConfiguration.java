package sk.qbsw.security.organization.simple.authentication.db.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.qbsw.security.authentication.db.service.DatabaseAuthenticationServiceImpl;
import sk.qbsw.security.authentication.service.AuthenticationService;
import sk.qbsw.security.core.autoconfigure.SecurityCoreAutoConfiguration;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AuthenticationParamsDao;
import sk.qbsw.security.core.dao.UnitDao;
import sk.qbsw.security.core.service.signature.PasswordDigester;

/**
 * The simple organization security authentication auto configuration.
 * 
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.6.0
 */
@Configuration
@AutoConfigureAfter (SecurityCoreAutoConfiguration.class)
public class SPOSecurityAuthenticationAutoConfiguration
{
	@Bean
	@ConditionalOnMissingBean
	public AuthenticationService databaseAuthenticationService (AccountDao accountDao, UnitDao unitDao, AuthenticationParamsDao authenticationParamsDao, PasswordDigester digester)
	{
		return new DatabaseAuthenticationServiceImpl(accountDao, unitDao, authenticationParamsDao, digester);
	}
}
