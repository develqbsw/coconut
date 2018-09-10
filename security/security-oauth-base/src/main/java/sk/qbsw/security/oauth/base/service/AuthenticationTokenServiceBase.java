package sk.qbsw.security.oauth.base.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.AccountOutputDataMapper;
import sk.qbsw.security.oauth.base.configuration.OAuthValidationConfigurator;
import sk.qbsw.security.oauth.base.dao.AuthenticationTokenDao;
import sk.qbsw.security.oauth.base.dao.MasterTokenDao;
import sk.qbsw.security.oauth.base.model.domain.AuthenticationTokenBase;
import sk.qbsw.security.oauth.base.model.domain.MasterTokenBase;
import sk.qbsw.security.oauth.base.service.mapper.AuthenticationTokenMapper;
import sk.qbsw.security.oauth.model.AuthenticationTokenDataBase;
import sk.qbsw.security.oauth.model.GeneratedTokenData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The authentication token service base.
 *
 * @param <A> the account type
 * @param <T> the authentication token type
 * @param <M> the master token type
 * @param <D> the account data type
 * @param <TD> the authentication token data type
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public abstract class AuthenticationTokenServiceBase<A extends Account, T extends AuthenticationTokenBase<A>, M extends MasterTokenBase<A>, D extends AccountData, TD extends AuthenticationTokenDataBase<D>>extends SecurityTokenServiceBase<A, D, T, M>
{
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationTokenServiceBase.class);

	/**
	 * The Authentication token mapper.
	 */
	protected final AuthenticationTokenMapper<A, T, D, TD> authenticationTokenMapper;

	/**
	 * Instantiates a new Authentication token service base.
	 *
	 * @param masterTokenDao the master token dao
	 * @param authenticationTokenDao the authentication token dao
	 * @param authenticationTokenMapper the authentication token mapper
	 * @param accountOutputDataMapper the account output data mapper
	 * @param accountDao the account dao
	 * @param idGeneratorService the id generator service
	 * @param validationConfiguration the validation configuration
	 */
	protected AuthenticationTokenServiceBase (MasterTokenDao<A, M> masterTokenDao, AuthenticationTokenDao<A, T> authenticationTokenDao, AuthenticationTokenMapper<A, T, D, TD> authenticationTokenMapper, AccountOutputDataMapper<D, A> accountOutputDataMapper, AccountDao accountDao, IdGeneratorService idGeneratorService, OAuthValidationConfigurator validationConfiguration)
	{
		super(masterTokenDao, authenticationTokenDao, accountOutputDataMapper, accountDao, idGeneratorService, validationConfiguration);
		this.authenticationTokenMapper = authenticationTokenMapper;
	}

	/**
	 * Generate authentication token base generated token data.
	 *
	 * @param accountId the account id
	 * @param masterToken the master token
	 * @param deviceId the device id
	 * @param ip the ip
	 * @param isIpIgnored the is ip ignored
	 * @return the generated token data
	 * @throws CBusinessException the c business exception
	 */
	protected GeneratedTokenData generateAuthenticationTokenBase (Long accountId, String masterToken, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException
	{
		// read master token and check it
		M persistedMasterToken = masterTokenDao.findByAccountIdAndTokenAndDeviceId(accountId, masterToken, deviceId);
		checkMasterToken(persistedMasterToken, ip, isIpIgnored);

		T authenticationToken = authenticationTokenDao.findByAccountIdAndDeviceId(accountId, deviceId);
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

		T newAuthenticationToken = createAuthenticationToken(deviceId, ip, idGeneratorService.getGeneratedId(), (A) account);

		// create to database and return token
		return new GeneratedTokenData(authenticationTokenDao.update(newAuthenticationToken).getToken(), authenticationToken != null ? authenticationToken.getToken() : null);

	}

	/**
	 * Create authentication token t.
	 *
	 * @param deviceId the device id
	 * @param ip the ip
	 * @param token the token
	 * @param account the account
	 * @return the t
	 */
	protected abstract T createAuthenticationToken (String deviceId, String ip, String token, A account);

	/**
	 * Revoke authentication token base.
	 *
	 * @param accountId the account id
	 * @param authenticationToken the authentication token
	 * @throws CBusinessException the c business exception
	 */
	protected void revokeAuthenticationTokenBase (Long accountId, String authenticationToken) throws CBusinessException
	{
		T token = authenticationTokenDao.findByAccountIdAndToken(accountId, authenticationToken);

		// performs checks
		if (token == null)
		{
			LOGGER.error("The token {} not found", authenticationToken);
			throw new CBusinessException(ECoreErrorResponse.AUTHENTICATION_TOKEN_NOT_FOUND);
		}

		authenticationTokenDao.remove(token);

	}

	/**
	 * Gets account by authentication token base.
	 *
	 * @param token the token
	 * @param deviceId the device id
	 * @param ip the ip
	 * @param isIpIgnored the is ip ignored
	 * @return the account by authentication token base
	 * @throws CBusinessException the c business exception
	 */
	protected D getAccountByAuthenticationTokenBase (String token, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException
	{
		T persistedToken = authenticationTokenDao.findByTokenAndDeviceId(token, deviceId);

		if (persistedToken != null)
		{
			checkAuthenticationToken(persistedToken, ip, isIpIgnored);
			return accountOutputDataMapper.mapToAccountOutputData(persistedToken.getAccount());
		}
		else
		{
			return null;
		}
	}

	/**
	 * Find expired authentication tokens base list.
	 *
	 * @return the list
	 */
	protected List<TD> findExpiredAuthenticationTokensBase ()
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
			return authenticationTokenDao.findByExpireLimitOrChangeLimit(expireLimit, changeLimit).stream().map(authenticationTokenMapper::mapToAuthenticationTokenData).collect(Collectors.toList());
		}
		else
		{
			return new ArrayList<>();
		}
	}

	/**
	 * Remove authentication tokens base long.
	 *
	 * @param ids the ids
	 * @return the long
	 */
	protected Long removeAuthenticationTokensBase (List<Long> ids)
	{
		return authenticationTokenDao.removeByIds(ids);
	}
}
