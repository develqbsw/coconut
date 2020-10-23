package sk.qbsw.security.oauth.db.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.qbsw.core.configuration.dao.jpa.CSystemParameterDao;
import sk.qbsw.core.configuration.service.CSystemParameterService;
import sk.qbsw.core.configuration.service.ISystemParameterService;
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.authentication.service.AuthenticationService;
import sk.qbsw.security.core.autoconfigure.SecurityCoreAutoConfigurationCommon;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.AccountOutputDataMapper;
import sk.qbsw.security.core.service.mapper.AccountOutputDataMapperImpl;
import sk.qbsw.security.core.service.mapper.UserOutputDataMapper;
import sk.qbsw.security.oauth.base.configuration.DefaultOAuthValidationConfigurator;
import sk.qbsw.security.oauth.base.configuration.OAuthValidationConfigurator;
import sk.qbsw.security.oauth.base.dao.AuthenticationTokenDao;
import sk.qbsw.security.oauth.base.dao.MasterTokenDao;
import sk.qbsw.security.oauth.base.service.IdGeneratorService;
import sk.qbsw.security.oauth.base.service.RandomIdGeneratorServiceImpl;
import sk.qbsw.security.oauth.base.service.mapper.AuthenticationTokenMapper;
import sk.qbsw.security.oauth.base.service.mapper.MasterTokenMapper;
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
 * The security OAuth auto configuration common.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 1.18.2
 */
@Configuration
@AutoConfigureAfter (SecurityCoreAutoConfigurationCommon.class)
public class SecurityOAuthAutoConfigurationCommon
{
	@Bean
	@ConditionalOnMissingBean
	public AccountOutputDataMapper<AccountData, Account> accountOutputDataMapper (UserOutputDataMapper userOutputDataMapper)
	{
		return new AccountOutputDataMapperImpl(userOutputDataMapper);
	}

	@Bean
	@ConditionalOnMissingBean
	public CSystemParameterDao systemParameterDao ()
	{
		return new CSystemParameterDao();
	}

	@Bean
	@ConditionalOnMissingBean
	public CSystemParameterService systemParameterService ()
	{
		return new CSystemParameterService();
	}

	@Bean
	@ConditionalOnMissingBean
	public OAuthValidationConfigurator oAuthValidationConfiguration (ISystemParameterService systemParameterService)
	{
		return new DefaultOAuthValidationConfigurator(systemParameterService);
	}

	@Bean
	@ConditionalOnMissingBean
	public IdGeneratorService idGeneratorService ()
	{
		return new RandomIdGeneratorServiceImpl();
	}

	@Bean
	@ConditionalOnMissingBean
	public AuthenticationTokenMapper<Account, AuthenticationToken, AccountData, AuthenticationTokenData> authenticationTokenMapper (AccountOutputDataMapper<AccountData, Account> accountOutputDataMapper)
	{
		return new AuthenticationTokenMapperImpl(accountOutputDataMapper);
	}

	@Bean
	@ConditionalOnMissingBean
	public MasterTokenMapper<Account, MasterToken, AccountData, MasterTokenData> masterTokenMapper (AccountOutputDataMapper<AccountData, Account> accountOutputDataMapper)
	{
		return new MasterTokenMapperImpl(accountOutputDataMapper);
	}

	@Bean
	@ConditionalOnMissingBean
	public MasterTokenService<AccountData, MasterTokenData> masterTokenService (MasterTokenDao<Account, MasterToken> masterTokenDao, AuthenticationTokenDao<Account, AuthenticationToken> authenticationTokenDao, MasterTokenMapper<Account, MasterToken, AccountData, MasterTokenData> masterTokenMapper, AccountOutputDataMapper<AccountData, Account> accountOutputDataMapper, AccountDao accountDao, IdGeneratorService idGeneratorService, OAuthValidationConfigurator validationConfiguration)
	{
		return new MasterTokenServiceImpl(masterTokenDao, authenticationTokenDao, masterTokenMapper, accountOutputDataMapper, accountDao, idGeneratorService, validationConfiguration);
	}

	@Bean
	@ConditionalOnMissingBean
	public AuthenticationTokenService<AccountData, AuthenticationTokenData> authenticationTokenService (MasterTokenDao<Account, MasterToken> masterTokenDao, AuthenticationTokenDao<Account, AuthenticationToken> authenticationTokenDao, AuthenticationTokenMapper<Account, AuthenticationToken, AccountData, AuthenticationTokenData> authenticationTokenMapper, AccountOutputDataMapper<AccountData, Account> accountOutputDataMapper, AccountDao accountDao, IdGeneratorService idGeneratorService, OAuthValidationConfigurator validationConfiguration)
	{
		return new AuthenticationTokenServiceImpl(masterTokenDao, authenticationTokenDao, authenticationTokenMapper, accountOutputDataMapper, accountDao, idGeneratorService, validationConfiguration);
	}

	@Bean
	@ConditionalOnMissingBean
	public OAuthService<AccountData> oAuthService (MasterTokenService<AccountData, MasterTokenData> masterTokenService, AuthenticationTokenService<AccountData, AuthenticationTokenData> authenticationTokenService, AuthenticationService authenticationService, AccountOutputDataMapper<AccountData, Account> accountOutputDataMapper)
	{
		return new OAuthServiceImpl(masterTokenService, authenticationTokenService, authenticationService, accountOutputDataMapper);
	}
}
