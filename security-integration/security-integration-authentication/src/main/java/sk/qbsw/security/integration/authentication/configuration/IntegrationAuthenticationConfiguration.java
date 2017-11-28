package sk.qbsw.security.integration.authentication.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import sk.qbsw.security.authentication.base.service.AuthenticationService;
import sk.qbsw.security.integration.authentication.core.service.IntegrationAuthenticationService;
import sk.qbsw.security.integration.authentication.core.service.IntegrationAuthenticationServiceImpl;
import sk.qbsw.security.oauth.service.AuthenticationTokenService;
import sk.qbsw.security.oauth.service.MasterTokenService;

/**
 * The integration authentication configuration.
 *
 * @author Tomas Lauro
 * @version 1.0.0
 * @since 1.0.0
 */
public class IntegrationAuthenticationConfiguration
{
	private final AuthenticationService authenticationService;

	private final MasterTokenService masterTokenService;

	private final AuthenticationTokenService authenticationTokenService;

	/**
	 * Instantiates a new Integration authentication configuration.
	 *
	 * @param authenticationService the authentication service
	 * @param masterTokenService the master token service
	 * @param authenticationTokenService the authentication token service
	 */
	@Autowired
	public IntegrationAuthenticationConfiguration (AuthenticationService authenticationService, MasterTokenService masterTokenService, AuthenticationTokenService authenticationTokenService)
	{
		this.authenticationService = authenticationService;
		this.masterTokenService = masterTokenService;
		this.authenticationTokenService = authenticationTokenService;
	}

	/**
	 * Integration authentication service integration authentication service.
	 *
	 * @return the integration authentication service
	 */
	@Bean
	public IntegrationAuthenticationService integrationAuthenticationService ()
	{
		return new IntegrationAuthenticationServiceImpl(authenticationService, masterTokenService, authenticationTokenService);
	}
}
