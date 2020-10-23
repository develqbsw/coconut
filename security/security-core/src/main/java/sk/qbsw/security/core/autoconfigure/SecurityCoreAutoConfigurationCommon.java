package sk.qbsw.security.core.autoconfigure;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.qbsw.security.core.configuration.SecurityCoreConfigurator;
import sk.qbsw.security.core.configuration.model.AdditionalAuthenticationParamsTypes;
import sk.qbsw.security.core.configuration.model.AuthenticationSchemas;
import sk.qbsw.security.core.configuration.model.HashMethods;
import sk.qbsw.security.core.dao.*;
import sk.qbsw.security.core.service.signature.PasswordDigester;
import sk.qbsw.security.core.service.signature.PasswordDigesterImpl;

/**
 * The security core auto configuration common.
 * 
 * @author Tomas Lauro
 * @author Michal SLezák
 * @version 2.6.0
 * @since 2.0.0
 */
@Configuration
public class SecurityCoreAutoConfigurationCommon
{
	@Bean
	@ConditionalOnMissingBean
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
	@ConditionalOnMissingBean
	public AccountUnitGroupDao accountUnitGroupDao ()
	{
		return new AccountUnitGroupJpaDao();
	}

	@Bean
	@ConditionalOnMissingBean
	public AuthenticationParamsDao authenticationParamsDao ()
	{
		return new AuthenticationParamsJpaDao();
	}

	@Bean
	@ConditionalOnMissingBean
	public BlockedLoginDao blockedLoginDao ()
	{
		return new BlockedLoginJpaDao();
	}

	@Bean
	@ConditionalOnMissingBean
	public UserDao userDao ()
	{
		return new UserJpaDao();
	}

	@Bean
	@ConditionalOnMissingBean
	public GroupDao groupDao ()
	{
		return new GroupJpaDao();
	}

	@Bean
	@ConditionalOnMissingBean
	public OrganizationDao organizationDao ()
	{
		return new OrganizationJpaDao();
	}

	@Bean
	@ConditionalOnMissingBean
	public RoleDao roleDao ()
	{
		return new RoleJpaDao();
	}

	@Bean
	@ConditionalOnMissingBean
	public UnitDao unitDao ()
	{
		return new UnitJpaDao();
	}

	@Bean
	@ConditionalOnMissingBean
	public PasswordDigester passwordDigester (SecurityCoreConfigurator securityCoreConfigurator)
	{
		return new PasswordDigesterImpl(securityCoreConfigurator);
	}
}
