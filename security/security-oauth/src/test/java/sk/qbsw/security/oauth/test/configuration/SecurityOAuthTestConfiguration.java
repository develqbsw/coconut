package sk.qbsw.security.oauth.test.configuration;

import org.springframework.context.annotation.Bean;

import sk.qbsw.core.configuration.service.ISystemParameterService;
import sk.qbsw.security.authentication.base.service.AuthenticationService;
import sk.qbsw.security.authentication.service.DatabaseAuthenticationServiceImpl;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AuthenticationParamsDao;
import sk.qbsw.security.core.dao.UnitDao;
import sk.qbsw.security.core.service.signature.PasswordDigester;
import sk.qbsw.security.oauth.configuration.BaseSecurityOAuthConfiguration;
import sk.qbsw.security.oauth.configuration.OAuthValidationConfigurator;

/**
 * The base OAuth test configuration.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.2
 */
public class SecurityOAuthTestConfiguration extends BaseSecurityOAuthConfiguration
{
	@Bean
	public OAuthValidationConfigurator oAuthValidationConfiguration (ISystemParameterService systemParameterService)
	{
		return new OAuthValidationConfigurator()
		{
			@Override
			public Integer getMasterTokenExpireLimit ()
			{
				return 1;
			}

			@Override
			public Integer getAuthenticationTokenExpireLimit ()
			{
				return 1;
			}

			@Override
			public Integer getMasterTokenChangeLimit ()
			{
				return 3;
			}

			@Override
			public Integer getAuthenticationTokenChangeLimit ()
			{
				return 3;
			}
		};
	}

	@Bean
	public AuthenticationService authenticationService (AccountDao accountDao, UnitDao unitDao, AuthenticationParamsDao authenticationParamsDao, PasswordDigester digester)
	{
		return new DatabaseAuthenticationServiceImpl(accountDao, unitDao, authenticationParamsDao, digester);
	}
}
