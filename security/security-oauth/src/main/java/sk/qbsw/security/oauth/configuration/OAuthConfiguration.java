package sk.qbsw.security.oauth.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import sk.qbsw.core.configuration.service.ISystemParameterService;
import sk.qbsw.security.authentication.base.service.AuthenticationService;
import sk.qbsw.security.core.dao.UserDao;
import sk.qbsw.security.oauth.dao.AuthenticationTokenDao;
import sk.qbsw.security.oauth.dao.MasterTokenDao;
import sk.qbsw.security.oauth.dao.jpa.AuthenticationTokenJpaDaoImpl;
import sk.qbsw.security.oauth.dao.jpa.MasterTokenJpaDaoImpl;
import sk.qbsw.security.oauth.service.*;
import sk.qbsw.security.oauth.service.impl.*;

/**
 * The OAuth validation configuration.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.18.2
 */
public class OAuthConfiguration
{
	@Autowired
	private UserDao userDao;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private ISystemParameterService systemParameterService;

	@Bean
	public OAuthValidationConfiguration oAuthValidationConfiguration (ISystemParameterService systemParameterService)
	{
		return new OAuthValidationConfigurationImpl(systemParameterService);
	}

	@Bean
	public AuthenticationTokenDao authenticationTokenDao ()
	{
		return new AuthenticationTokenJpaDaoImpl();
	}

	@Bean
	public MasterTokenDao masterTokenDao ()
	{
		return new MasterTokenJpaDaoImpl();
	}

	@Bean
	public IdGeneratorService idGeneratorService ()
	{
		return new RandomIdGeneratorServiceImpl();
	}

	@Bean
	public MasterTokenService masterTokenService (MasterTokenDao masterTokenDao, AuthenticationTokenDao authenticationTokenDao, UserDao userDao, IdGeneratorService idGeneratorService, OAuthValidationConfiguration validationConfiguration)
	{
		return new MasterTokenServiceImpl(masterTokenDao, authenticationTokenDao, userDao, idGeneratorService, validationConfiguration);
	}

	@Bean
	public AuthenticationTokenService authenticationTokenService (MasterTokenDao masterTokenDao, AuthenticationTokenDao authenticationTokenDao, UserDao userDao, IdGeneratorService idGeneratorService, OAuthValidationConfiguration validationConfiguration)
	{
		return new AuthenticationTokenServiceImpl(masterTokenDao, authenticationTokenDao, userDao, idGeneratorService, validationConfiguration);
	}

	@Bean
	public OAuthService oAuthService (MasterTokenService masterTokenService, AuthenticationTokenService authenticationTokenService, AuthenticationService authenticationService)
	{
		return new OAuthServiceImpl(masterTokenService, authenticationTokenService, authenticationService);
	}

	@Bean
	public OAuthServiceCacheFacade oAuthServiceCacheFacade (OAuthService oAuthService)
	{
		return new OAuthServiceCacheFacadeImpl(oAuthService);
	}
}
