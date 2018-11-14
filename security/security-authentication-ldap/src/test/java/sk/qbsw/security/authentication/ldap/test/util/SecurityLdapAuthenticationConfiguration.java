package sk.qbsw.security.authentication.ldap.test.util;

import org.springframework.context.annotation.Bean;
import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.testing.mock.CMockitoFactoryBean;
import sk.qbsw.security.authentication.ldap.configuration.BaseSecurityLdapAuthenticationConfiguration;
import sk.qbsw.security.authentication.ldap.configuration.SecurityLdapAuthenticationConfigurator;
import sk.qbsw.security.authentication.ldap.provider.LdapConnectionFactory;
import sk.qbsw.security.authentication.ldap.provider.LdapProvider;
import sk.qbsw.security.authentication.ldap.provider.LdapProviderImpl;

/**
 * The security ldap authentication configuration.
 * 
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
public class SecurityLdapAuthenticationConfiguration extends BaseSecurityLdapAuthenticationConfiguration
{
	@Bean
	public LdapProvider ldapProvider (LdapConnectionFactory ldapConnectionFactory)
	{
		try
		{
			return new CMockitoFactoryBean<>(LdapProviderImpl.class).getObject();
		}
		catch (Exception e)
		{
			throw new CSystemException("Cannot create ldap provider mock");
		}
	}

	@Bean
	public SecurityLdapAuthenticationConfigurator ldapAuthenticationConfigurator ()
	{
		return new SecurityLdapAuthenticationConfigurator()
		{
			@Override
			public String getServerName ()
			{
				return "192.168.123.162";
			}

			@Override
			public int getServerPort ()
			{
				return 10389;
			}

			@Override
			public String getSecondaryServerName ()
			{
				return null;
			}

			@Override
			public int getSecondaryServerPort ()
			{
				return 0;
			}

			@Override
			public boolean getUseSslFlag ()
			{
				return false;
			}

			@Override
			public String getAccountDn ()
			{
				return "cn=jozko.mrkvicka,ou=users,dc=mfsr,dc=sk";
			}

			@Override
			public String getAccountPassword ()
			{
				return "jozko.mrkvicka";
			}

			@Override
			public Long getAccountOrganizationId ()
			{
				return 1L;
			}

			@Override
			public String[] getAccountSearchBaseDns ()
			{
				return "ou=system,dc=mfsr,dc=sk;;ou=users,dc=mfsr,dc=sk".split(";;");
			}

			@Override
			public String getAccountObjectClass ()
			{
				return "inetOrgPerson";
			}

			@Override
			public String getAccountSearchFilter ()
			{
				return "";
			}

			@Override
			public Integer getPoolMaxIdle ()
			{
				return null;
			}

			@Override
			public Integer getPoolMinIdle ()
			{
				return null;
			}

			@Override
			public Integer getPoolMaxActive ()
			{
				return null;
			}

			@Override
			public Long getMaxWait ()
			{
				return null;
			}
		};
	}
}
