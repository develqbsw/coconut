package sk.qbsw.security.oauth.db.test.configuration;

import org.springframework.context.annotation.Bean;

import sk.qbsw.core.configuration.service.ISystemParameterService;
import sk.qbsw.security.oauth.db.autoconfigure.SecurityOAuthAutoConfigurationBase;
import sk.qbsw.security.oauth.base.configuration.OAuthValidationConfigurator;

/**
 * The base OAuth test configuration.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.18.2
 */
public class SecurityOAuthTestConfiguration extends SecurityOAuthAutoConfigurationBase
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
}
