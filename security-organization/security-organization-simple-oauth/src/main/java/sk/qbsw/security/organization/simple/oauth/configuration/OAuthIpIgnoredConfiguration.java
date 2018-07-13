package sk.qbsw.security.organization.simple.oauth.configuration;

import org.springframework.context.annotation.Bean;
import sk.qbsw.security.oauth.base.service.OAuthService;
import sk.qbsw.security.oauth.base.service.facade.OAuthServiceFacade;
import sk.qbsw.security.organization.simple.oauth.model.SimpleOrganizationAccountData;
import sk.qbsw.security.organization.simple.oauth.service.facade.OAuthServiceIpIgnoredCacheFacadeImpl;

/**
 * The simple organization default ip ignored OAuth configuration.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class OAuthIpIgnoredConfiguration extends BaseSecurityOAuthConfiguration
{
	@Bean
	public OAuthServiceFacade oAuthServiceCacheFacade (OAuthService<SimpleOrganizationAccountData> oAuthService)
	{
		return new OAuthServiceIpIgnoredCacheFacadeImpl(oAuthService);
	}
}
