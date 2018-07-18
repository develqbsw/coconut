package sk.qbsw.security.organization.complex.oauth.configuration;

import org.springframework.context.annotation.Bean;
import sk.qbsw.security.oauth.service.OAuthService;
import sk.qbsw.security.oauth.service.facade.OAuthServiceFacade;
import sk.qbsw.security.organization.complex.oauth.model.ComplexOrganizationAccountData;
import sk.qbsw.security.organization.complex.oauth.service.facade.OAuthServiceIpValidatedCacheFacadeImpl;

/**
 * The complex organization default ip validated OAuth configuration.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class OAuthIpValidatedConfiguration extends BaseSecurityOAuthConfiguration
{
	@Bean
	public OAuthServiceFacade<ComplexOrganizationAccountData> oAuthServiceCacheFacade (OAuthService<ComplexOrganizationAccountData> oAuthService)
	{
		return new OAuthServiceIpValidatedCacheFacadeImpl(oAuthService);
	}
}
