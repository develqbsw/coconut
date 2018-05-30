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
import sk.qbsw.security.oauth.model.domain.MasterToken;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

/**
 * The master token service.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.13.1
 */
public class MasterTokenServiceImpl extends BaseTokenService implements MasterTokenService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(MasterTokenServiceImpl.class);

	/**
	 * Instantiates a new Master token service.
	 *
	 * @param masterTokenDao the master token dao
	 * @param authenticationTokenDao the authentication token dao
	 * @param accountDao the account dao
	 * @param idGeneratorService the id generator service
	 * @param validationConfiguration the validation configuration
	 */
	public MasterTokenServiceImpl (MasterTokenDao masterTokenDao, AuthenticationTokenDao authenticationTokenDao, AccountDao accountDao, IdGeneratorService idGeneratorService, OAuthValidationConfigurator validationConfiguration)
	{
		super(masterTokenDao, authenticationTokenDao, accountDao, idGeneratorService, validationConfiguration);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public GeneratedTokenData generateMasterToken (Long accountId, String deviceId, String ip) throws CBusinessException
	{
		MasterToken token = masterTokenDao.findByAccountIdAndDeviceId(accountId, deviceId);
		Account account;
		try
		{
			account = accountDao.findById(accountId);
		}
		catch (NoResultException ex)
		{
			LOGGER.error("The account {} not found", accountId);
			LOGGER.error("The account not found", ex);
			throw new CBusinessException(ECoreErrorResponse.ACCOUNT_NOT_FOUND);
		}

		// performs checks
		if (token != null)
		{
			masterTokenDao.remove(token);
		}

		MasterToken newToken = new MasterToken();
		newToken.setDeviceId(deviceId);
		newToken.setIp(ip);
		newToken.setToken(idGeneratorService.getGeneratedId());
		newToken.setAccount(account);

		// create to database and return token
		return new GeneratedTokenData(masterTokenDao.update(newToken).getToken(), token != null ? token.getToken() : null);

	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void revokeMasterToken (Long accountId, String masterToken) throws CBusinessException
	{
		MasterToken token = masterTokenDao.findByAccountIdAndToken(accountId, masterToken);

		// performs checks
		if (token == null)
		{
			LOGGER.error("The token {} not found", masterToken);
			throw new CBusinessException(ECoreErrorResponse.MASTER_TOKEN_NOT_FOUND);
		}

		masterTokenDao.remove(token);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Account getAccountByMasterToken (String token, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException
	{
		MasterToken persistedToken = masterTokenDao.findByTokenAndDeviceId(token, deviceId);

		if (persistedToken != null)
		{
			checkMasterToken(persistedToken, ip, isIpIgnored);
			persistedToken.getAccount().exportRoles();
			return persistedToken.getAccount();
		}
		else
		{
			return null;
		}
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<MasterToken> findExpiredMasterTokens ()
	{
		Integer changeLimit = null;
		Integer expireLimit = null;

		if (validationConfiguration.getMasterTokenExpireLimit() != null && validationConfiguration.getMasterTokenExpireLimit() > 0)
		{
			expireLimit = validationConfiguration.getMasterTokenExpireLimit();
		}
		if (validationConfiguration.getMasterTokenChangeLimit() != null && validationConfiguration.getMasterTokenChangeLimit() > 0)
		{
			changeLimit = validationConfiguration.getMasterTokenChangeLimit();
		}

		if (expireLimit != null || changeLimit != null)
		{
			return masterTokenDao.findByExpireLimitOrChangeLimit(expireLimit, changeLimit);
		}
		else
		{
			return new ArrayList<>();
		}
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Long removeMasterTokens (List<Long> ids)
	{
		return masterTokenDao.removeByIds(ids);
	}
}
