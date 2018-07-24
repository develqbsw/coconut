package sk.qbsw.security.organization.simple.oauth.db.configuration;

import org.springframework.context.annotation.Bean;

import sk.qbsw.security.oauth.service.OAuthService;
import sk.qbsw.security.organization.simple.oauth.db.service.facade.OAuthServiceIpValidatedCacheFacadeImpl;
import sk.qbsw.security.organization.simple.oauth.model.SimpleOrganizationAccountData;
import sk.qbsw.security.organization.simple.oauth.service.facade.SimpleOrganizationOAuthServiceFacade;

/**
 * The simple organization default ip validated OAuth configuration.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class OAuthIpValidatedConfiguration extends BaseSecurityOAuthConfiguration
{
	@Bean
	public SimpleOrganizationOAuthServiceFacade<SimpleOrganizationAccountData> oAuthServiceCacheFacade (OAuthService<SimpleOrganizationAccountData> oAuthService)
	{
		return new OAuthServiceIpValidatedCacheFacadeImpl(oAuthService);
	}
}
