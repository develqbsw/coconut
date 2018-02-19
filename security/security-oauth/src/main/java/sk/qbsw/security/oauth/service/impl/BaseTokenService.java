/**
 * 
 */
package sk.qbsw.security.oauth.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.security.core.dao.UserDao;
import sk.qbsw.security.oauth.configuration.OAuthValidationConfiguration;
import sk.qbsw.security.oauth.dao.AuthenticationTokenDao;
import sk.qbsw.security.oauth.dao.MasterTokenDao;
import sk.qbsw.security.oauth.model.domain.AuthenticationToken;
import sk.qbsw.security.oauth.model.domain.MasterToken;
import sk.qbsw.security.oauth.model.domain.SecurityToken;
import sk.qbsw.security.oauth.service.IdGeneratorService;

/**
 * The token service.
 *
 * @author Tomas Lauro
 * @version 1.13.1
 * @since 1.13.1
 */
abstract class BaseTokenService
{
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseTokenService.class);

	/**
	 * The Master token dao.
	 */
	final MasterTokenDao masterTokenDao;

	/**
	 * The Authentication token dao.
	 */
	final AuthenticationTokenDao authenticationTokenDao;

	/**
	 * The User dao.
	 */
	final UserDao userDao;

	/**
	 * The Id generator service.
	 */
	final IdGeneratorService idGeneratorService;

	/**
	 * The Validation configuration.
	 */
	final OAuthValidationConfiguration validationConfiguration;

	/**
	 * Instantiates a new Base token service.
	 *
	 * @param masterTokenDao the master token dao
	 * @param authenticationTokenDao the authentication token dao
	 * @param userDao the user dao
	 * @param idGeneratorService the id generator service
	 * @param validationConfiguration the validation configuration
	 */
	BaseTokenService (MasterTokenDao masterTokenDao, AuthenticationTokenDao authenticationTokenDao, UserDao userDao, IdGeneratorService idGeneratorService, OAuthValidationConfiguration validationConfiguration)
	{
		this.masterTokenDao = masterTokenDao;
		this.authenticationTokenDao = authenticationTokenDao;
		this.userDao = userDao;
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
			LOGGER.warn("The token {} for user {} and device {} deleted because of invalid ip", token.getToken(), token.getUser().getLogin(), token.getDeviceId());
			return false;
		}
		else
		{
			return true;
		}
	}
}
