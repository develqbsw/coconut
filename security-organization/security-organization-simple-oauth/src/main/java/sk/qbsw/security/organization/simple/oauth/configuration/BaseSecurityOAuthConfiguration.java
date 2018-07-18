package sk.qbsw.security.organization.simple.oauth.configuration;

import org.springframework.context.annotation.Bean;
import sk.qbsw.core.configuration.dao.jpa.CSystemParameterDao;
import sk.qbsw.core.configuration.service.CSystemParameterService;
import sk.qbsw.core.configuration.service.ISystemParameterService;
import sk.qbsw.security.authentication.service.AuthenticationService;
import sk.qbsw.security.core.configuration.SecurityCoreConfiguration;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.AccountMapper;
import sk.qbsw.security.oauth.base.configuration.DefaultOAuthValidationConfigurator;
import sk.qbsw.security.oauth.base.configuration.OAuthValidationConfigurator;
import sk.qbsw.security.oauth.base.dao.AuthenticationTokenDao;
import sk.qbsw.security.oauth.base.dao.MasterTokenDao;
import sk.qbsw.security.oauth.base.service.IdGeneratorService;
import sk.qbsw.security.oauth.base.service.RandomIdGeneratorServiceImpl;
import sk.qbsw.security.oauth.base.service.mapper.AuthenticationTokenMapper;
import sk.qbsw.security.oauth.base.service.mapper.MasterTokenMapper;
import sk.qbsw.security.oauth.service.AuthenticationTokenService;
import sk.qbsw.security.oauth.service.MasterTokenService;
import sk.qbsw.security.oauth.service.OAuthService;
import sk.qbsw.security.organization.simple.oauth.dao.AuthenticationTokenJpaDaoImpl;
import sk.qbsw.security.organization.simple.oauth.dao.MasterTokenJpaDaoImpl;
import sk.qbsw.security.organization.simple.oauth.mapper.AccountMapperImpl;
import sk.qbsw.security.organization.simple.oauth.mapper.AuthenticationTokenMapperImpl;
import sk.qbsw.security.organization.simple.oauth.mapper.MasterTokenMapperImpl;
import sk.qbsw.security.organization.simple.oauth.model.AuthenticationTokenData;
import sk.qbsw.security.organization.simple.oauth.model.MasterTokenData;
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
	public AccountMapper<SimpleOrganizationAccountData, Account> accountMapper ()
	{
		return new AccountMapperImpl();
	}

	@Bean
	public AuthenticationTokenMapper<Account, AuthenticationToken, SimpleOrganizationAccountData, AuthenticationTokenData> authenticationTokenMapper (AccountMapper<SimpleOrganizationAccountData, Account> accountMapper)
	{
		return new AuthenticationTokenMapperImpl(accountMapper);
	}

	@Bean
	public MasterTokenMapper<Account, MasterToken, SimpleOrganizationAccountData, MasterTokenData> masterTokenMapper (AccountMapper<SimpleOrganizationAccountData, Account> accountMapper)
	{
		return new MasterTokenMapperImpl(accountMapper);
	}

	@Bean
	public MasterTokenService<SimpleOrganizationAccountData, MasterTokenData> masterTokenService (MasterTokenDao<Account, MasterToken> masterTokenDao, AuthenticationTokenDao<Account, AuthenticationToken> authenticationTokenDao, MasterTokenMapper<Account, MasterToken, SimpleOrganizationAccountData, MasterTokenData> masterTokenMapper, AccountMapper<SimpleOrganizationAccountData, Account> accountMapper, AccountDao accountDao, IdGeneratorService idGeneratorService, OAuthValidationConfigurator validationConfiguration)
	{
		return new MasterTokenServiceImpl(masterTokenDao, authenticationTokenDao, masterTokenMapper, accountMapper, accountDao, idGeneratorService, validationConfiguration);
	}

	@Bean
	public AuthenticationTokenService<SimpleOrganizationAccountData, AuthenticationTokenData> authenticationTokenService (MasterTokenDao<Account, MasterToken> masterTokenDao, AuthenticationTokenDao<Account, AuthenticationToken> authenticationTokenDao, AuthenticationTokenMapper<Account, AuthenticationToken, SimpleOrganizationAccountData, AuthenticationTokenData> authenticationTokenMapper, AccountMapper<SimpleOrganizationAccountData, Account> accountMapper, AccountDao accountDao, IdGeneratorService idGeneratorService, OAuthValidationConfigurator validationConfiguration)
	{
		return new AuthenticationTokenServiceImpl(masterTokenDao, authenticationTokenDao, authenticationTokenMapper, accountMapper, accountDao, idGeneratorService, validationConfiguration);
	}

	@Bean
	public OAuthService<SimpleOrganizationAccountData> oAuthService (MasterTokenService<SimpleOrganizationAccountData, MasterTokenData> masterTokenService, AuthenticationTokenService<SimpleOrganizationAccountData, AuthenticationTokenData> authenticationTokenService, AuthenticationService authenticationService)
	{
		return new OAuthServiceImpl(masterTokenService, authenticationTokenService, authenticationService);
	}
}
