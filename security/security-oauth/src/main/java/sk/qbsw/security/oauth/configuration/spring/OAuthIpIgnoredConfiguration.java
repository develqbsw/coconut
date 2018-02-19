package sk.qbsw.security.oauth.configuration.spring;

import org.springframework.context.annotation.Bean;
import sk.qbsw.security.oauth.service.OAuthService;
import sk.qbsw.security.oauth.service.OAuthServiceFacade;
import sk.qbsw.security.oauth.service.impl.OAuthServiceIpIgnoredCacheFacadeImpl;

/**
 * The default ip ignored OAuth configuration.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.18.2
 */
public class OAuthIpIgnoredConfiguration extends BaseOAuthConfiguration
{
	@Bean
	public OAuthServiceFacade oAuthServiceCacheFacade (OAuthService oAuthService)
	{
		return new OAuthServiceIpIgnoredCacheFacadeImpl(oAuthService);
	}
}
