package sk.qbsw.security.organization.complex.authentication.db.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.qbsw.security.authentication.db.service.DatabaseAuthenticationServiceImpl;
import sk.qbsw.security.authentication.service.AuthenticationService;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AuthenticationParamsDao;
import sk.qbsw.security.core.dao.UnitDao;
import sk.qbsw.security.core.service.signature.PasswordDigester;
import sk.qbsw.security.organization.complex.core.autoconfigure.CXOSecurityCoreAutoConfiguration;

/**
 * The CXO security authentication auto configuration.
 * 
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.6.0
 */
@Configuration
@AutoConfigureAfter (CXOSecurityCoreAutoConfiguration.class)
public class CXOSecurityAuthenticationAutoConfiguration
{
	@Bean
	@ConditionalOnMissingBean
	public AuthenticationService databaseAuthenticationService (AccountDao accountDao, UnitDao unitDao, AuthenticationParamsDao authenticationParamsDao, PasswordDigester digester)
	{
		return new DatabaseAuthenticationServiceImpl(accountDao, unitDao, authenticationParamsDao, digester);
	}
}
