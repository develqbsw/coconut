package sk.qbsw.security.oauth.configuration;

import org.springframework.context.annotation.Bean;
import sk.qbsw.security.oauth.base.model.AccountData;
import sk.qbsw.security.oauth.base.service.OAuthService;
import sk.qbsw.security.oauth.base.service.facade.OAuthServiceFacade;
import sk.qbsw.security.oauth.service.facade.OAuthServiceIpIgnoredCacheFacadeImpl;

/**
 * The default ip ignored OAuth configuration.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.18.2
 */
public class OAuthIpIgnoredConfiguration extends BaseSecurityOAuthConfiguration
{
	@Bean
	public OAuthServiceFacade oAuthServiceCacheFacade (OAuthService<AccountData> oAuthService)
	{
		return new OAuthServiceIpIgnoredCacheFacadeImpl(oAuthService);
	}
}
