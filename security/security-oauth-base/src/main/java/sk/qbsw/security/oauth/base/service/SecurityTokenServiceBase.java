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
import sk.qbsw.security.oauth.base.model.domain.AuthenticationTokenBase;
import sk.qbsw.security.oauth.base.model.domain.MasterTokenBase;
import sk.qbsw.security.oauth.base.model.domain.SecurityTokenBase;

/**
 * The security token service.
 *
 * @param <A> the account type
 * @param <T> the authentication token type
 * @param <M> the master token type
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.13.1
 */
public abstract class SecurityTokenServiceBase<A extends Account, T extends AuthenticationTokenBase<A>, M extends MasterTokenBase<A>>
{
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityTokenServiceBase.class);

	/**
	 * The Master token dao.
	 */
	protected final MasterTokenDao<A, M> masterTokenDao;

	/**
	 * The Authentication token dao.
	 */
	protected final AuthenticationTokenDao<A, T> authenticationTokenDao;

	/**
	 * The Account dao.
	 */
	protected final AccountDao accountDao;

	/**
	 * The Id generator service.
	 */
	protected final IdGeneratorService idGeneratorService;

	/**
	 * The Validation configuration.
	 */
	protected final OAuthValidationConfigurator validationConfiguration;

	/**
	 * Instantiates a new Base token service.
	 *
	 * @param masterTokenDao the master token dao
	 * @param authenticationTokenDao the authentication token dao
	 * @param accountDao the account dao
	 * @param idGeneratorService the id generator service
	 * @param validationConfiguration the validation configuration
	 */
	SecurityTokenServiceBase (MasterTokenDao<A, M> masterTokenDao, AuthenticationTokenDao<A, T> authenticationTokenDao, AccountDao accountDao, IdGeneratorService idGeneratorService, OAuthValidationConfigurator validationConfiguration)
	{
		this.masterTokenDao = masterTokenDao;
		this.authenticationTokenDao = authenticationTokenDao;
		this.accountDao = accountDao;
		this.idGeneratorService = idGeneratorService;
		this.validationConfiguration = validationConfiguration;
	}

	/**
	 * Check master token.
	 *
	 * @param masterToken the master token
	 * @param ip the ip
	 * @param isIpIgnored the is ip ignored
	 * @throws CBusinessException the c business exception
	 */
	void checkMasterToken (M masterToken, String ip, boolean isIpIgnored) throws CBusinessException
	{
		if (masterToken != null)
		{
			if (!isIpIgnored && !isTokenIpValid(masterToken, ip))
			{
				masterTokenDao.remove(masterToken);
				throw new CBusinessException(ECoreErrorResponse.MASTER_TOKEN_INVALIDATED);
			}
		}
		else
		{
			LOGGER.error("The master token not found");
			throw new CBusinessException(ECoreErrorResponse.MASTER_TOKEN_NOT_FOUND);
		}
	}

	/**
	 * Check authentication token.
	 *
	 * @param authenticationToken the authentication token
	 * @param ip the ip
	 * @param isIpIgnored the is ip ignored
	 * @throws CBusinessException the c business exception
	 */
	void checkAuthenticationToken (T authenticationToken, String ip, boolean isIpIgnored) throws CBusinessException
	{
		if (authenticationToken != null)
		{
			if (!isIpIgnored && !isTokenIpValid(authenticationToken, ip))
			{
				authenticationTokenDao.remove(authenticationToken);
				throw new CBusinessException(ECoreErrorResponse.AUTHENTICATION_TOKEN_INVALIDATED);
			}
		}
		else
		{
			LOGGER.error("The authentication token not found");
			throw new CBusinessException(ECoreErrorResponse.AUTHENTICATION_TOKEN_NOT_FOUND);
		}
	}

	private boolean isTokenIpValid (SecurityTokenBase<A> token, String ip)
	{
		if ( (token.getIp() != null && !token.getIp().equals(ip)) || (token.getIp() == null && ip != null))
		{
			LOGGER.warn("The token {} for account {} and device {} deleted because of invalid ip", token.getToken(), token.getAccount().getLogin(), token.getDeviceId());
			return false;
		}
		else
		{
			return true;
		}
	}
}
