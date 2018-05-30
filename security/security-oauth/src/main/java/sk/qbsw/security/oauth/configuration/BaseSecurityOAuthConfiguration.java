package sk.qbsw.security.oauth.configuration;

import org.springframework.context.annotation.Bean;
import sk.qbsw.core.configuration.dao.jpa.CSystemParameterDao;
import sk.qbsw.core.configuration.service.CSystemParameterService;
import sk.qbsw.core.configuration.service.ISystemParameterService;
import sk.qbsw.security.authentication.base.service.AuthenticationService;
import sk.qbsw.security.core.configuration.SecurityCoreConfiguration;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.oauth.dao.AuthenticationTokenDao;
import sk.qbsw.security.oauth.dao.AuthenticationTokenJpaDaoImpl;
import sk.qbsw.security.oauth.dao.MasterTokenDao;
import sk.qbsw.security.oauth.dao.MasterTokenJpaDaoImpl;
import sk.qbsw.security.oauth.service.*;

/**
 * The base OAuth configuration.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.2
 */
public abstract class BaseSecurityOAuthConfiguration extends SecurityCoreConfiguration
{
	@Bean
	public CSystemParameterDao systemParameterDao ()
	{
		return new CSystemParameterDao();
	}

	@Bean
	public CSystemParameterService systemParameterService ()
	{
		return new CSystemParameterService();
	}

	@Bean
	public OAuthValidationConfigurator oAuthValidationConfiguration (ISystemParameterService systemParameterService)
	{
		return new DefaultOAuthValidationConfigurator(systemParameterService);
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
	public MasterTokenService masterTokenService (MasterTokenDao masterTokenDao, AuthenticationTokenDao authenticationTokenDao, AccountDao accountDao, IdGeneratorService idGeneratorService, OAuthValidationConfigurator validationConfiguration)
	{
		return new MasterTokenServiceImpl(masterTokenDao, authenticationTokenDao, accountDao, idGeneratorService, validationConfiguration);
	}

	@Bean
	public AuthenticationTokenService authenticationTokenService (MasterTokenDao masterTokenDao, AuthenticationTokenDao authenticationTokenDao, AccountDao accountDao, IdGeneratorService idGeneratorService, OAuthValidationConfigurator validationConfiguration)
	{
		return new AuthenticationTokenServiceImpl(masterTokenDao, authenticationTokenDao, accountDao, idGeneratorService, validationConfiguration);
	}

	@Bean
	public OAuthService oAuthService (MasterTokenService masterTokenService, AuthenticationTokenService authenticationTokenService, AuthenticationService authenticationService)
	{
		return new OAuthServiceImpl(masterTokenService, authenticationTokenService, authenticationService);
	}
}