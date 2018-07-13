package sk.qbsw.security.oauth.base.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.oauth.base.configuration.OAuthValidationConfigurator;
import sk.qbsw.security.oauth.base.dao.AuthenticationTokenDao;
import sk.qbsw.security.oauth.base.dao.MasterTokenDao;
import sk.qbsw.security.oauth.base.model.GeneratedTokenData;
import sk.qbsw.security.oauth.base.model.domain.AuthenticationTokenBase;
import sk.qbsw.security.oauth.base.model.domain.MasterTokenBase;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

/**
 * The master token service.
 *
 * @param <A> the account type
 * @param <T> the authentication token type
 * @param <M> the master token type
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.13.1
 */
public abstract class MasterTokenServiceBase<A extends Account, T extends AuthenticationTokenBase<A>, M extends MasterTokenBase<A>>extends SecurityTokenServiceBase<A, T, M>
{
	private static final Logger LOGGER = LoggerFactory.getLogger(MasterTokenServiceBase.class);

	/**
	 * Instantiates a new Master token service base.
	 *
	 * @param masterTokenDao the master token dao
	 * @param authenticationTokenDao the authentication token dao
	 * @param accountDao the account dao
	 * @param idGeneratorService the id generator service
	 * @param validationConfiguration the validation configuration
	 */
	protected MasterTokenServiceBase (MasterTokenDao<A, M> masterTokenDao, AuthenticationTokenDao<A, T> authenticationTokenDao, AccountDao accountDao, IdGeneratorService idGeneratorService, OAuthValidationConfigurator validationConfiguration)
	{
		super(masterTokenDao, authenticationTokenDao, accountDao, idGeneratorService, validationConfiguration);
	}

	/**
	 * Generate master token base generated token data.
	 *
	 * @param accountId the account id
	 * @param deviceId the device id
	 * @param ip the ip
	 * @return the generated token data
	 * @throws CBusinessException the c business exception
	 */
	protected GeneratedTokenData generateMasterTokenBase (Long accountId, String deviceId, String ip) throws CBusinessException
	{
		M token = masterTokenDao.findByAccountIdAndDeviceId(accountId, deviceId);
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

		M newToken = createMasterToken(deviceId, ip, idGeneratorService.getGeneratedId(), (A) account);

		// create to database and return token
		return new GeneratedTokenData(masterTokenDao.update(newToken).getToken(), token != null ? token.getToken() : null);
	}

	/**
	 * Create master token m.
	 *
	 * @param deviceId the device id
	 * @param ip the ip
	 * @param token the token
	 * @param account the account
	 * @return the m
	 */
	protected abstract M createMasterToken (String deviceId, String ip, String token, A account);

	/**
	 * Revoke master token base.
	 *
	 * @param accountId the account id
	 * @param masterToken the master token
	 * @throws CBusinessException the c business exception
	 */
	protected void revokeMasterTokenBase (Long accountId, String masterToken) throws CBusinessException
	{
		M token = masterTokenDao.findByAccountIdAndToken(accountId, masterToken);

		// performs checks
		if (token == null)
		{
			LOGGER.error("The token {} not found", masterToken);
			throw new CBusinessException(ECoreErrorResponse.MASTER_TOKEN_NOT_FOUND);
		}

		masterTokenDao.remove(token);
	}

	/**
	 * Gets account by master token base.
	 *
	 * @param token the token
	 * @param deviceId the device id
	 * @param ip the ip
	 * @param isIpIgnored the is ip ignored
	 * @return the account by master token base
	 * @throws CBusinessException the c business exception
	 */
	protected A getAccountByMasterTokenBase (String token, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException
	{
		M persistedToken = masterTokenDao.findByTokenAndDeviceId(token, deviceId);

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

	/**
	 * Find expired master tokens base list.
	 *
	 * @return the list
	 */
	protected List<M> findExpiredMasterTokensBase ()
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

	/**
	 * Remove master tokens base long.
	 *
	 * @param ids the ids
	 * @return the long
	 */
	protected Long removeMasterTokensBase (List<Long> ids)
	{
		return masterTokenDao.removeByIds(ids);
	}
}
