package sk.qbsw.security.authentication.ldap.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import sk.qbsw.security.authentication.ldap.configuration.SecurityLdapAuthenticationConfigurator;
import sk.qbsw.security.authentication.ldap.provider.LdapConnectionFactory;
import sk.qbsw.security.authentication.ldap.provider.LdapConnectionFactoryImpl;
import sk.qbsw.security.authentication.ldap.provider.LdapProvider;
import sk.qbsw.security.authentication.ldap.provider.LdapProviderImpl;
import sk.qbsw.security.authentication.ldap.service.LdapAuthenticationServiceImpl;
import sk.qbsw.security.authentication.service.AuthenticationService;
import sk.qbsw.security.core.autoconfigure.SecurityCoreAutoConfiguration;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.UnitDao;

/**
 * The security ldap authentication auto configuration.
 * 
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.0.0
 */
@AutoConfigureAfter (SecurityCoreAutoConfiguration.class)
public abstract class BaseSecurityLdapAuthenticationAutoConfiguration
{
	@Bean
	@ConditionalOnMissingBean
	public LdapConnectionFactory ldapConnectionFactory (SecurityLdapAuthenticationConfigurator ldapAuthenticationConfigurator)
	{
		return new LdapConnectionFactoryImpl(ldapAuthenticationConfigurator);
	}

	@Bean
	@ConditionalOnMissingBean
	public LdapProvider ldapProvider (LdapConnectionFactory ldapConnectionFactory)
	{
		return new LdapProviderImpl(ldapConnectionFactory);
	}

	@Bean
	@ConditionalOnMissingBean
	public AuthenticationService databaseAuthenticationService (SecurityLdapAuthenticationConfigurator ldapAuthenticationConfigurator, UnitDao unitDao, AccountDao accountDao, LdapProvider ldapProvider)
	{
		return new LdapAuthenticationServiceImpl(ldapAuthenticationConfigurator, unitDao, accountDao, ldapProvider);
	}

	public abstract SecurityLdapAuthenticationConfigurator ldapAuthenticationConfigurator ();
}
