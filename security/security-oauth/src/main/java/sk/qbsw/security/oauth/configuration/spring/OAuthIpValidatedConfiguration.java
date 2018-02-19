package sk.qbsw.security.oauth.configuration.spring;

import org.springframework.context.annotation.Bean;
import sk.qbsw.security.oauth.service.OAuthService;
import sk.qbsw.security.oauth.service.OAuthServiceFacade;
import sk.qbsw.security.oauth.service.impl.OAuthServiceIpValidatedCacheFacadeImpl;

/**
 * The default ip validated OAuth configuration.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.18.2
 */
public class OAuthIpValidatedConfiguration extends BaseOAuthConfiguration
{
	@Bean
	public OAuthServiceFacade oAuthServiceCacheFacade (OAuthService oAuthService)
	{
		return new OAuthServiceIpValidatedCacheFacadeImpl(oAuthService);
	}
}
