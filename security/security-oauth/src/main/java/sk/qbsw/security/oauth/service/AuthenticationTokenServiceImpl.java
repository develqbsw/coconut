package sk.qbsw.security.oauth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.oauth.configuration.OAuthValidationConfigurator;
import sk.qbsw.security.oauth.dao.AuthenticationTokenDao;
import sk.qbsw.security.oauth.dao.MasterTokenDao;
import sk.qbsw.security.oauth.model.GeneratedTokenData;
import sk.qbsw.security.oauth.model.domain.AuthenticationToken;
import sk.qbsw.security.oauth.model.domain.MasterToken;

import java.util.ArrayList;
import java.util.List;

/**
 * The authentication token service.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.13.1
 */
public class AuthenticationTokenServiceImpl extends BaseTokenService implements AuthenticationTokenService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationTokenServiceImpl.class);

	/**
	 * Instantiates a new Base token service.
	 *
	 * @param masterTokenDao the master token dao
	 * @param authenticationTokenDao the authentication token dao
	 * @param accountDao the account dao
	 * @param idGeneratorService the id generator service
	 * @param validationConfiguration the validation configuration
	 */
	public AuthenticationTokenServiceImpl (MasterTokenDao masterTokenDao, AuthenticationTokenDao authenticationTokenDao, AccountDao accountDao, IdGeneratorService idGeneratorService, OAuthValidationConfigurator validationConfiguration)
	{
		super(masterTokenDao, authenticationTokenDao, accountDao, idGeneratorService, validationConfiguration);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public GeneratedTokenData generateAuthenticationToken (Long accountId, String masterToken, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException
	{
		// read master token and check it
		MasterToken persistedMasterToken = masterTokenDao.findByAccountIdAndTokenAndDeviceId(accountId, masterToken, deviceId);
		checkMasterToken(persistedMasterToken, ip, isIpIgnored);

		AuthenticationToken authenticationToken = authenticationTokenDao.findByAccountIdAndDeviceId(accountId, deviceId);
		Account account = accountDao.findById(accountId);

		// performs checks
		if (authenticationToken != null)
		{
			authenticationTokenDao.remove(authenticationToken);
		}

		if (account == null)
		{
			LOGGER.error("The account {} not found", accountId);
			throw new CBusinessException(ECoreErrorResponse.ACCOUNT_NOT_FOUND);
		}

		AuthenticationToken newAuthenticationToken = new AuthenticationToken();
		newAuthenticationToken.setDeviceId(deviceId);
		newAuthenticationToken.setIp(ip);
		newAuthenticationToken.setToken(idGeneratorService.getGeneratedId());
		newAuthenticationToken.setAccount(account);

		// create to database and return token
		return new GeneratedTokenData(authenticationTokenDao.update(newAuthenticationToken).getToken(), authenticationToken != null ? authenticationToken.getToken() : null);

	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void revokeAuthenticationToken (Long accountId, String authenticationToken) throws CBusinessException
	{
		AuthenticationToken token = authenticationTokenDao.findByAccountIdAndToken(accountId, authenticationToken);

		// performs checks
		if (token == null)
		{
			LOGGER.error("The token {} not found", authenticationToken);
			throw new CBusinessException(ECoreErrorResponse.AUTHENTICATION_TOKEN_NOT_FOUND);
		}

		authenticationTokenDao.remove(token);

	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Account getAccountByAuthenticationToken (String token, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException
	{
		AuthenticationToken persistedToken = authenticationTokenDao.findByTokenAndDeviceId(token, deviceId);

		if (persistedToken != null)
		{
			checkAuthenticationToken(persistedToken, ip, isIpIgnored);
			return persistedToken.getAccount();
		}
		else
		{
			return null;
		}
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<AuthenticationToken> findExpiredAuthenticationTokens ()
	{
		Integer changeLimit = null;
		Integer expireLimit = null;

		if (validationConfiguration.getAuthenticationTokenExpireLimit() != null && validationConfiguration.getAuthenticationTokenExpireLimit() > 0)
		{
			expireLimit = validationConfiguration.getAuthenticationTokenExpireLimit();
		}
		if (validationConfiguration.getAuthenticationTokenChangeLimit() != null && validationConfiguration.getAuthenticationTokenChangeLimit() > 0)
		{
			changeLimit = validationConfiguration.getAuthenticationTokenChangeLimit();
		}

		if (expireLimit != null || changeLimit != null)
		{
			return authenticationTokenDao.findByExpireLimitOrChangeLimit(expireLimit, changeLimit);
		}
		else
		{
			return new ArrayList<>();
		}
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Long removeAuthenticationTokens (List<Long> ids)
	{
		return authenticationTokenDao.removeByIds(ids);
	}
}
