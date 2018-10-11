package sk.qbsw.security.oauth.db.configuration;

import org.springframework.context.annotation.Bean;
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.oauth.db.service.facade.OAuthServiceIpValidatedCacheFacadeImpl;
import sk.qbsw.security.oauth.service.OAuthService;
import sk.qbsw.security.oauth.service.facade.OAuthServiceFacade;

/**
 * The default ip validated OAuth configuration.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.18.2
 */
public class OAuthIpValidatedConfiguration extends SecurityOAuthConfigurationBase
{
	@Bean
	public OAuthServiceFacade<AccountData> oAuthServiceCacheFacade (OAuthService<AccountData> oAuthService)
	{
		return new OAuthServiceIpValidatedCacheFacadeImpl(oAuthService);
	}
}
