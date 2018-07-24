package sk.qbsw.security.oauth.db.configuration;

import org.springframework.context.annotation.Bean;

import sk.qbsw.core.configuration.dao.jpa.CSystemParameterDao;
import sk.qbsw.core.configuration.service.CSystemParameterService;
import sk.qbsw.core.configuration.service.ISystemParameterService;
import sk.qbsw.core.security.base.model.AccountData;
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
import sk.qbsw.security.oauth.db.dao.AuthenticationTokenJpaDaoImpl;
import sk.qbsw.security.oauth.db.dao.MasterTokenJpaDaoImpl;
import sk.qbsw.security.oauth.db.mapper.AccountMapperImpl;
import sk.qbsw.security.oauth.db.mapper.AuthenticationTokenMapperImpl;
import sk.qbsw.security.oauth.db.mapper.MasterTokenMapperImpl;
import sk.qbsw.security.oauth.db.model.AuthenticationTokenData;
import sk.qbsw.security.oauth.db.model.MasterTokenData;
import sk.qbsw.security.oauth.db.model.domain.AuthenticationToken;
import sk.qbsw.security.oauth.db.model.domain.MasterToken;
import sk.qbsw.security.oauth.db.service.AuthenticationTokenServiceImpl;
import sk.qbsw.security.oauth.db.service.MasterTokenServiceImpl;
import sk.qbsw.security.oauth.db.service.OAuthServiceImpl;
import sk.qbsw.security.oauth.service.AuthenticationTokenService;
import sk.qbsw.security.oauth.service.MasterTokenService;
import sk.qbsw.security.oauth.service.OAuthService;

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
	public AccountMapper<AccountData, Account> accountMapper ()
	{
		return new AccountMapperImpl();
	}

	@Bean
	public AuthenticationTokenMapper<Account, AuthenticationToken, AccountData, AuthenticationTokenData> authenticationTokenMapper (AccountMapper<AccountData, Account> accountMapper)
	{
		return new AuthenticationTokenMapperImpl(accountMapper);
	}

	@Bean
	public MasterTokenMapper<Account, MasterToken, AccountData, MasterTokenData> masterTokenMapper (AccountMapper<AccountData, Account> accountMapper)
	{
		return new MasterTokenMapperImpl(accountMapper);
	}

	@Bean
	public MasterTokenService<AccountData, MasterTokenData> masterTokenService (MasterTokenDao<Account, MasterToken> masterTokenDao, AuthenticationTokenDao<Account, AuthenticationToken> authenticationTokenDao, MasterTokenMapper<Account, MasterToken, AccountData, MasterTokenData> masterTokenMapper, AccountMapper<AccountData, Account> accountMapper, AccountDao accountDao, IdGeneratorService idGeneratorService, OAuthValidationConfigurator validationConfiguration)
	{
		return new MasterTokenServiceImpl(masterTokenDao, authenticationTokenDao, masterTokenMapper, accountMapper, accountDao, idGeneratorService, validationConfiguration);
	}

	@Bean
	public AuthenticationTokenService<AccountData, AuthenticationTokenData> authenticationTokenService (MasterTokenDao<Account, MasterToken> masterTokenDao, AuthenticationTokenDao<Account, AuthenticationToken> authenticationTokenDao, AuthenticationTokenMapper<Account, AuthenticationToken, AccountData, AuthenticationTokenData> authenticationTokenMapper, AccountMapper<AccountData, Account> accountMapper, AccountDao accountDao, IdGeneratorService idGeneratorService, OAuthValidationConfigurator validationConfiguration)
	{
		return new AuthenticationTokenServiceImpl(masterTokenDao, authenticationTokenDao, authenticationTokenMapper, accountMapper, accountDao, idGeneratorService, validationConfiguration);
	}

	@Bean
	public OAuthService<AccountData> oAuthService (MasterTokenService<AccountData, MasterTokenData> masterTokenService, AuthenticationTokenService<AccountData, AuthenticationTokenData> authenticationTokenService, AuthenticationService authenticationService)
	{
		return new OAuthServiceImpl(masterTokenService, authenticationTokenService, authenticationService);
	}
}
