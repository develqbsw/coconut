package sk.qbsw.security.organization.simple.oauth.configuration;

import org.springframework.context.annotation.Bean;

import sk.qbsw.core.configuration.dao.jpa.CSystemParameterDao;
import sk.qbsw.core.configuration.service.CSystemParameterService;
import sk.qbsw.core.configuration.service.ISystemParameterService;
import sk.qbsw.security.authentication.base.service.AuthenticationService;
import sk.qbsw.security.core.configuration.SecurityCoreConfiguration;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.oauth.base.configuration.DefaultOAuthValidationConfigurator;
import sk.qbsw.security.oauth.base.configuration.OAuthValidationConfigurator;
import sk.qbsw.security.oauth.base.dao.AuthenticationTokenDao;
import sk.qbsw.security.oauth.base.dao.MasterTokenDao;
import sk.qbsw.security.oauth.base.service.*;
import sk.qbsw.security.organization.simple.oauth.dao.AuthenticationTokenJpaDaoImpl;
import sk.qbsw.security.organization.simple.oauth.dao.MasterTokenJpaDaoImpl;
import sk.qbsw.security.organization.simple.oauth.model.SimpleOrganizationAccountData;
import sk.qbsw.security.organization.simple.oauth.model.domain.AuthenticationToken;
import sk.qbsw.security.organization.simple.oauth.model.domain.MasterToken;
import sk.qbsw.security.organization.simple.oauth.service.AuthenticationTokenServiceImpl;
import sk.qbsw.security.organization.simple.oauth.service.MasterTokenServiceImpl;
import sk.qbsw.security.organization.simple.oauth.service.OAuthServiceImpl;

/**
 * The simple organization base OAuth configuration.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
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
	public AuthenticationTokenDao<Account, AuthenticationToken> authenticationTokenDao ()
	{
		return new AuthenticationTokenJpaDaoImpl();
	}

	@Bean
	public MasterTokenDao<Account, MasterToken> masterTokenDao ()
	{
		return new MasterTokenJpaDaoImpl();
	}

	@Bean
	public IdGeneratorService idGeneratorService ()
	{
		return new RandomIdGeneratorServiceImpl();
	}

	@Bean
	public MasterTokenService<Account, MasterToken> masterTokenService (MasterTokenDao<Account, MasterToken> masterTokenDao, AuthenticationTokenDao<Account, AuthenticationToken> authenticationTokenDao, AccountDao accountDao, IdGeneratorService idGeneratorService, OAuthValidationConfigurator validationConfiguration)
	{
		return new MasterTokenServiceImpl(masterTokenDao, authenticationTokenDao, accountDao, idGeneratorService, validationConfiguration);
	}

	@Bean
	public AuthenticationTokenService<Account, AuthenticationToken> authenticationTokenService (MasterTokenDao<Account, MasterToken> masterTokenDao, AuthenticationTokenDao<Account, AuthenticationToken> authenticationTokenDao, AccountDao accountDao, IdGeneratorService idGeneratorService, OAuthValidationConfigurator validationConfiguration)
	{
		return new AuthenticationTokenServiceImpl(masterTokenDao, authenticationTokenDao, accountDao, idGeneratorService, validationConfiguration);
	}

	@Bean
	public OAuthService<SimpleOrganizationAccountData> oAuthService (MasterTokenService<Account, MasterToken> masterTokenService, AuthenticationTokenService<Account, AuthenticationToken> authenticationTokenService, AuthenticationService authenticationService)
	{
		return new OAuthServiceImpl(masterTokenService, authenticationTokenService, authenticationService);
	}
}
