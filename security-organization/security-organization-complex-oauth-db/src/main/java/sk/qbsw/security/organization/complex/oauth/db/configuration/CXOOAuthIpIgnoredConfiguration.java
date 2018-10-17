package sk.qbsw.security.organization.complex.oauth.db.configuration;

import org.springframework.context.annotation.Bean;
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.oauth.db.service.facade.OAuthServiceIpIgnoredCacheFacadeImpl;
import sk.qbsw.security.oauth.service.OAuthService;
import sk.qbsw.security.oauth.service.facade.OAuthServiceFacade;

/**
 * The complex organization default ip ignored OAuth configuration.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class CXOOAuthIpIgnoredConfiguration extends CXOSecurityOAuthConfigurationBase
{
	@Bean
	public OAuthServiceFacade<AccountData> oAuthServiceCacheFacade (OAuthService<AccountData> oAuthService)
	{
		return new OAuthServiceIpIgnoredCacheFacadeImpl(oAuthService);
	}
}
