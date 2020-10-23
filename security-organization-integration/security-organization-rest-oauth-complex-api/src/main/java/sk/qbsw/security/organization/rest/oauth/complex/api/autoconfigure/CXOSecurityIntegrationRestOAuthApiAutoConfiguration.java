package sk.qbsw.security.organization.rest.oauth.complex.api.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.oauth.service.facade.OAuthServiceFacade;
import sk.qbsw.security.organization.complex.oauth.db.autoconfigure.CXOSecurityOAuthAutoConfiguration;
import sk.qbsw.security.organization.rest.oauth.complex.api.controller.AuthenticationComplexController;
import sk.qbsw.security.organization.rest.oauth.complex.api.mapper.SecurityOrikaMapper;
import sk.qbsw.security.rest.oauth.api.base.mapper.SecurityMapper;
import sk.qbsw.security.rest.oauth.client.model.CSAccountData;

/**
 * The complex organization security integration REST OAuth API auto configure.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.6.0
 */
@Configuration
@AutoConfigureAfter ({CXOSecurityOAuthAutoConfiguration.class})
public class CXOSecurityIntegrationRestOAuthApiAutoConfiguration
{
	@Bean
	@ConditionalOnMissingBean
	public SecurityMapper<AccountData, CSAccountData> securityMapper ()
	{
		return new SecurityOrikaMapper();
	}

	@Bean
	@ConditionalOnMissingBean
	public AuthenticationComplexController authenticationController (OAuthServiceFacade<AccountData> oAuthService, SecurityMapper<AccountData, CSAccountData> securityMapper)
	{
		return new AuthenticationComplexController(oAuthService, securityMapper);
	}
}
