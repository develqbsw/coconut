package sk.qbsw.security.organization.complex.oauth.db.configuration;

import org.springframework.context.annotation.Bean;
import sk.qbsw.security.oauth.service.OAuthService;
import sk.qbsw.security.organization.complex.base.model.ComplexOrganizationAccountData;
import sk.qbsw.security.organization.complex.oauth.db.service.facade.OAuthServiceIpIgnoredCacheFacadeImpl;
import sk.qbsw.security.organization.complex.oauth.service.facade.ComplexOrganizationOAuthServiceFacade;

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
	public ComplexOrganizationOAuthServiceFacade<ComplexOrganizationAccountData> oAuthServiceCacheFacade (OAuthService<ComplexOrganizationAccountData> oAuthService)
	{
		return new OAuthServiceIpIgnoredCacheFacadeImpl(oAuthService);
	}
}
