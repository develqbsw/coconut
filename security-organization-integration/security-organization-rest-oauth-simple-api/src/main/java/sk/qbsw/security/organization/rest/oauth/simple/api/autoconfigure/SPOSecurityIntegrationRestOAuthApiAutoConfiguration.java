package sk.qbsw.security.organization.rest.oauth.simple.api.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.oauth.service.facade.OAuthServiceFacade;
import sk.qbsw.security.organization.rest.oauth.simple.api.controller.AuthenticationSimpleController;
import sk.qbsw.security.organization.rest.oauth.simple.api.mapper.SecurityOrikaMapper;
import sk.qbsw.security.organization.simple.oauth.db.autoconfigure.SPOSecurityOAuthAutoConfiguration;
import sk.qbsw.security.rest.oauth.api.base.mapper.SecurityMapper;
import sk.qbsw.security.rest.oauth.client.model.CSAccountData;

/**
 * The simple organization security integration REST OAuth API auto configure.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.6.0
 */
@Configuration
@AutoConfigureAfter ({SPOSecurityOAuthAutoConfiguration.class})
public class SPOSecurityIntegrationRestOAuthApiAutoConfiguration
{
	@Bean
	@ConditionalOnMissingBean
	public SecurityMapper<AccountData, CSAccountData> securityMapper ()
	{
		return new SecurityOrikaMapper();
	}

	@Bean
	@ConditionalOnMissingBean
	public AuthenticationSimpleController authenticationController (OAuthServiceFacade<AccountData> oAuthService, SecurityMapper<AccountData, CSAccountData> securityMapper)
	{
		return new AuthenticationSimpleController(oAuthService, securityMapper);
	}
}
