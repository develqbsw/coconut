package sk.qbsw.security.authentication.ldap.configuration;

import org.springframework.context.annotation.Bean;

import sk.qbsw.security.authentication.service.AuthenticationService;
import sk.qbsw.security.authentication.ldap.provider.LdapConnectionFactory;
import sk.qbsw.security.authentication.ldap.provider.LdapConnectionFactoryImpl;
import sk.qbsw.security.authentication.ldap.provider.LdapProvider;
import sk.qbsw.security.authentication.ldap.provider.LdapProviderImpl;
import sk.qbsw.security.authentication.ldap.service.LdapAuthenticationServiceImpl;
import sk.qbsw.security.core.configuration.SecurityCoreConfiguration;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.UnitDao;

/**
 * The security ldap authentication configuration.
 * 
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
public abstract class BaseSecurityLdapAuthenticationConfiguration extends SecurityCoreConfiguration
{
	@Bean
	public LdapConnectionFactory ldapConnectionFactory (SecurityLdapAuthenticationConfigurator ldapAuthenticationConfigurator)
	{
		return new LdapConnectionFactoryImpl(ldapAuthenticationConfigurator);
	}

	@Bean
	public LdapProvider ldapProvider (LdapConnectionFactory ldapConnectionFactory)
	{
		return new LdapProviderImpl(ldapConnectionFactory);
	}

	@Bean
	public AuthenticationService databaseAuthenticationService (SecurityLdapAuthenticationConfigurator ldapAuthenticationConfigurator, UnitDao unitDao, AccountDao accountDao, LdapProvider ldapProvider)
	{
		return new LdapAuthenticationServiceImpl(ldapAuthenticationConfigurator, unitDao, accountDao, ldapProvider);
	}

	public abstract SecurityLdapAuthenticationConfigurator ldapAuthenticationConfigurator ();
}
