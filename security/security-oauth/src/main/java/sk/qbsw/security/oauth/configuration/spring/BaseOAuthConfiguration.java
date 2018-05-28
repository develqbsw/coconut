package sk.qbsw.security.oauth.configuration.spring;

import org.springframework.context.annotation.Bean;

import sk.qbsw.core.configuration.service.ISystemParameterService;
import sk.qbsw.security.authentication.base.service.AuthenticationService;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.oauth.configuration.DefaultOAuthValidationConfiguration;
import sk.qbsw.security.oauth.configuration.OAuthValidationConfiguration;
import sk.qbsw.security.oauth.dao.AuthenticationTokenDao;
import sk.qbsw.security.oauth.dao.MasterTokenDao;
import sk.qbsw.security.oauth.dao.jpa.AuthenticationTokenJpaDaoImpl;
import sk.qbsw.security.oauth.dao.jpa.MasterTokenJpaDaoImpl;
import sk.qbsw.security.oauth.service.*;
import sk.qbsw.security.oauth.service.impl.*;

/**
 * The base OAuth configuration.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.18.2
 */
public abstract class BaseOAuthConfiguration
{
	@Bean
	public OAuthValidationConfiguration oAuthValidationConfiguration (ISystemParameterService systemParameterService)
	{
		return new DefaultOAuthValidationConfiguration(systemParameterService);
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
	public MasterTokenService masterTokenService (MasterTokenDao masterTokenDao, AuthenticationTokenDao authenticationTokenDao, AccountDao userDao, IdGeneratorService idGeneratorService, OAuthValidationConfiguration validationConfiguration)
	{
		return new MasterTokenServiceImpl(masterTokenDao, authenticationTokenDao, userDao, idGeneratorService, validationConfiguration);
	}

	@Bean
	public AuthenticationTokenService authenticationTokenService (MasterTokenDao masterTokenDao, AuthenticationTokenDao authenticationTokenDao, AccountDao userDao, IdGeneratorService idGeneratorService, OAuthValidationConfiguration validationConfiguration)
	{
		return new AuthenticationTokenServiceImpl(masterTokenDao, authenticationTokenDao, userDao, idGeneratorService, validationConfiguration);
	}

	@Bean
	public OAuthService oAuthService (MasterTokenService masterTokenService, AuthenticationTokenService authenticationTokenService, AuthenticationService authenticationService)
	{
		return new OAuthServiceImpl(masterTokenService, authenticationTokenService, authenticationService);
	}
}
