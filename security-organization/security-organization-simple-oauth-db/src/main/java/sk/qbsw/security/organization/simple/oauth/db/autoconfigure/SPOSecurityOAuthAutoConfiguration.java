package sk.qbsw.security.organization.simple.oauth.db.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.oauth.db.service.facade.OAuthServiceIpIgnoredCacheFacadeImpl;
import sk.qbsw.security.oauth.db.service.facade.OAuthServiceIpValidatedCacheFacadeImpl;
import sk.qbsw.security.oauth.service.OAuthService;
import sk.qbsw.security.oauth.service.facade.OAuthServiceFacade;

/**
 * The simple organization security OAuth auto configuration.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.6.0
 */
@Configuration
@AutoConfigureAfter (SPOSecurityOAuthAutoConfigurationBase.class)
public class SPOSecurityOAuthAutoConfiguration
{
	private static final String OAUTH_SERVICE_CACHE_FACADE_BEAN_NAME = "oAuthServiceCacheFacade";

	@Bean (name = OAUTH_SERVICE_CACHE_FACADE_BEAN_NAME)
	@ConditionalOnMissingBean (name = OAUTH_SERVICE_CACHE_FACADE_BEAN_NAME)
	@ConditionalOnProperty (name = "coconut.security.oauth.ip.validated", havingValue = "false", matchIfMissing = true)
	public OAuthServiceFacade<AccountData> oAuthServiceIpIgnoredCacheFacade (OAuthService<AccountData> oAuthService)
	{
		return new OAuthServiceIpIgnoredCacheFacadeImpl(oAuthService);
	}

	@Bean (name = OAUTH_SERVICE_CACHE_FACADE_BEAN_NAME)
	@ConditionalOnMissingBean (name = OAUTH_SERVICE_CACHE_FACADE_BEAN_NAME)
	@ConditionalOnProperty (name = "coconut.security.oauth.ip.validated", havingValue = "true")
	public OAuthServiceFacade<AccountData> oAuthServiceIpValidatedCacheFacade (OAuthService<AccountData> oAuthService)
	{
		return new OAuthServiceIpValidatedCacheFacadeImpl(oAuthService);
	}
}
