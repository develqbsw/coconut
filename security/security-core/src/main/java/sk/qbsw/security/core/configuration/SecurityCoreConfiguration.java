package sk.qbsw.security.core.configuration;

import org.springframework.context.annotation.Bean;
import sk.qbsw.security.core.configuration.model.AdditionalAuthenticationParamsTypes;
import sk.qbsw.security.core.configuration.model.AuthenticationSchemas;
import sk.qbsw.security.core.configuration.model.HashMethods;
import sk.qbsw.security.core.dao.*;
import sk.qbsw.security.core.service.signature.PasswordDigester;
import sk.qbsw.security.core.service.signature.PasswordDigesterImpl;

import java.util.EnumMap;
import java.util.Map;

/**
 * The security core configuration.
 * 
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class SecurityCoreConfiguration
{
	@Bean
	public SecurityCoreConfigurator securityCoreConfigurator ()
	{
		return new SecurityCoreConfigurator()
		{
			@Override
			public String getPasswordPattern ()
			{
				return null;
			}

			@Override
			public HashMethods getPasswordHashMethod ()
			{
				return HashMethods.SHA;
			}

			@Override
			public AuthenticationSchemas getAuthenticationSchema ()
			{
				return AuthenticationSchemas.CUSTOM;
			}

			@Override
			public Map<AdditionalAuthenticationParamsTypes, String> getAdditionalAuthenticationParams ()
			{
				return new EnumMap<>(AdditionalAuthenticationParamsTypes.class);
			}
		};
	}

	@Bean
	public AccountDao accountDao ()
	{
		return new AccountJpaDao();
	}

	@Bean
	public AccountUnitGroupDao accountUnitGroupDao ()
	{
		return new AccountUnitGroupJpaDao();
	}

	@Bean
	public AuthenticationParamsDao authenticationParamsDao ()
	{
		return new AuthenticationParamsJpaDao();
	}

	@Bean
	public BlockedLoginDao blockedLoginDao ()
	{
		return new BlockedLoginJpaDao();
	}

	@Bean
	public GroupDao groupDao ()
	{
		return new GroupJpaDao();
	}

	@Bean
	public OrganizationDao organizationDao ()
	{
		return new OrganizationJpaDao();
	}

	@Bean
	public RoleDao roleDao ()
	{
		return new RoleJpaDao();
	}

	@Bean
	public UnitDao unitDao ()
	{
		return new UnitJpaDao();
	}

	@Bean
	public PasswordDigester passwordDigester (SecurityCoreConfigurator securityCoreConfigurator)
	{
		return new PasswordDigesterImpl(securityCoreConfigurator);
	}
}
