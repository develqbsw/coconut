package sk.qbsw.security.oauth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.oauth.configuration.OAuthValidationConfigurator;
import sk.qbsw.security.oauth.dao.AuthenticationTokenDao;
import sk.qbsw.security.oauth.dao.MasterTokenDao;
import sk.qbsw.security.oauth.model.domain.AuthenticationToken;
import sk.qbsw.security.oauth.model.domain.MasterToken;
import sk.qbsw.security.oauth.model.domain.SecurityToken;

/**
 * The token service.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.13.1
 */
abstract class BaseTokenService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseTokenService.class);

	final MasterTokenDao masterTokenDao;

	final AuthenticationTokenDao authenticationTokenDao;

	final AccountDao accountDao;

	final IdGeneratorService idGeneratorService;

	final OAuthValidationConfigurator validationConfiguration;

	/**
	 * Instantiates a new Base token service.
	 *
	 * @param masterTokenDao the master token dao
	 * @param authenticationTokenDao the authentication token dao
	 * @param accountDao the account dao
	 * @param idGeneratorService the id generator service
	 * @param validationConfiguration the validation configuration
	 */
	BaseTokenService (MasterTokenDao masterTokenDao, AuthenticationTokenDao authenticationTokenDao, AccountDao accountDao, IdGeneratorService idGeneratorService, OAuthValidationConfigurator validationConfiguration)
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
	void checkMasterToken (MasterToken masterToken, String ip, boolean isIpIgnored) throws CBusinessException
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
	void checkAuthenticationToken (AuthenticationToken authenticationToken, String ip, boolean isIpIgnored) throws CBusinessException
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

	private boolean isTokenIpValid (SecurityToken token, String ip)
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
