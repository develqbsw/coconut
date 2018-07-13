package sk.qbsw.security.organization.simple.oauth.configuration;

import org.springframework.context.annotation.Bean;
import sk.qbsw.security.oauth.base.service.OAuthService;
import sk.qbsw.security.oauth.base.service.facade.OAuthServiceFacade;
import sk.qbsw.security.organization.simple.oauth.model.SimpleOrganizationAccountData;
import sk.qbsw.security.organization.simple.oauth.service.facade.OAuthServiceIpValidatedCacheFacadeImpl;

/**
 * The default ip validated OAuth configuration.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class OAuthIpValidatedConfiguration extends BaseSecurityOAuthConfiguration
{
	@Bean
	public OAuthServiceFacade oAuthServiceCacheFacade (OAuthService<SimpleOrganizationAccountData> oAuthService)
	{
		return new OAuthServiceIpValidatedCacheFacadeImpl(oAuthService);
	}
}
