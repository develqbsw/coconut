package sk.qbsw.security.organization.simple.oauth.db.configuration;

import org.springframework.context.annotation.Bean;

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.oauth.db.service.facade.OAuthServiceIpValidatedCacheFacadeImpl;
import sk.qbsw.security.oauth.service.OAuthService;
import sk.qbsw.security.oauth.service.facade.OAuthServiceFacade;

/**
 * The simple organization default ip validated OAuth configuration.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class SPOOAuthIpValidatedConfiguration extends SPOSecurityOAuthConfigurationBase
{
	@Bean
	public OAuthServiceFacade<AccountData> oAuthServiceCacheFacade (OAuthService<AccountData> oAuthService)
	{
		return new OAuthServiceIpValidatedCacheFacadeImpl(oAuthService);
	}
}
