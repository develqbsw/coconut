package sk.qbsw.security.organization.complex.oauth.configuration;

import org.springframework.context.annotation.Bean;
import sk.qbsw.security.oauth.base.service.OAuthService;
import sk.qbsw.security.oauth.base.service.facade.OAuthServiceFacade;
import sk.qbsw.security.organization.complex.oauth.model.ComplexOrganizationAccountData;
import sk.qbsw.security.organization.complex.oauth.service.facade.OAuthServiceIpIgnoredCacheFacadeImpl;

/**
 * The complex organization default ip ignored OAuth configuration.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class OAuthIpIgnoredConfiguration extends BaseSecurityOAuthConfiguration
{
	@Bean
	public OAuthServiceFacade<ComplexOrganizationAccountData> oAuthServiceCacheFacade (OAuthService<ComplexOrganizationAccountData> oAuthService)
	{
		return new OAuthServiceIpIgnoredCacheFacadeImpl(oAuthService);
	}
}
