/**
 * 
 */
package sk.qbsw.security.oauth.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.security.core.dao.IUserDao;
import sk.qbsw.security.oauth.dao.IAuthenticationTokenDao;
import sk.qbsw.security.oauth.dao.IMasterTokenDao;
import sk.qbsw.security.oauth.model.domain.CAuthenticationToken;
import sk.qbsw.security.oauth.model.domain.CMasterToken;
import sk.qbsw.security.oauth.model.jmx.IOauthConfigurator;
import sk.qbsw.security.oauth.service.ICheckTokenStrategy;
import sk.qbsw.security.oauth.service.IIdGeneratorService;

/**
 * The token service.
 *
 * @author Tomas Lauro
 * 
 * @version 1.13.1
 * @since 1.13.1
 */
abstract class ATokenService
{
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ATokenService.class);

	/** The check token strategy. */
	@Autowired
	protected ICheckTokenStrategy checkTokenStrategy;

	/** The id generator service. */
	@Autowired
	protected IIdGeneratorService idGeneratorService;

	/** The master token dao. */
	@Autowired
	protected IMasterTokenDao masterTokenDao;

	/** The authentication token dao. */
	@Autowired
	protected IAuthenticationTokenDao authenticationTokenDao;

	/** The user dao. */
	@Autowired
	protected IUserDao userDao;

	/** The configurator. */
	@Autowired
	protected IOauthConfigurator oauthConfigurator;

	/**
	 * Check master token.
	 *
	 * @param masterToken the master token
	 * @param ip the ip
	 * @throws CBusinessException the c business exception
	 */
	protected void checkMasterToken (CMasterToken masterToken, String ip) throws CBusinessException
	{
		if (masterToken != null)
		{
			if (oauthConfigurator.isIpIgnored() == false)
			{
				if ( (masterToken.getIp() != null && masterToken.getIp().equals(ip) == false) || (masterToken.getIp() == null && ip != null))
				{
					masterTokenDao.remove(masterToken);
					LOGGER.warn("The master token {} for user {} and device {} deleted because of invalid ip", masterToken.getToken(), masterToken.getUser().getLogin(), masterToken.getDeviceId());
					throw new CBusinessException(ECoreErrorResponse.MASTER_TOKEN_INVALIDATED);
				}
			}

			checkMasterTokenValidity(masterToken);
		}
		else
		{
			LOGGER.error("The master token not found");
			throw new CBusinessException(ECoreErrorResponse.MASTER_TOKEN_NOT_FOUND);
		}
	}

	/**
	 * Check master token validity.
	 *
	 * @param masterToken the master token
	 * @throws CBusinessException the c business exception
	 */
	protected void checkMasterTokenValidity (CMasterToken masterToken) throws CBusinessException
	{
		if (checkTokenStrategy.isMasterTokenExpired(masterToken))
		{
			masterTokenDao.remove(masterToken);
			LOGGER.warn("The token {} has expired", masterToken.getToken());
			throw new CBusinessException(ECoreErrorResponse.MASTER_TOKEN_INVALIDATED);
		}

		if (checkTokenStrategy.isMasterTokenInactivityReached(masterToken))
		{
			masterTokenDao.remove(masterToken);
			LOGGER.warn("The token {} inactivity limit reached", masterToken.getToken());
			throw new CBusinessException(ECoreErrorResponse.MASTER_TOKEN_INVALIDATED);
		}
	}

	/**
	 * Check authentication token.
	 *
	 * @param authenticationToken the authentication token
	 * @param ip the ip
	 * @throws CBusinessException the c business exception
	 */
	protected void checkAuthenticationToken (CAuthenticationToken authenticationToken, String ip) throws CBusinessException
	{
		if (authenticationToken != null)
		{
			if (oauthConfigurator.isAuthIpIgnored() == false)
			{
				if ( (authenticationToken.getIp() != null && authenticationToken.getIp().equals(ip) == false) || (authenticationToken.getIp() == null && ip != null))
				{
					authenticationTokenDao.remove(authenticationToken);
					LOGGER.warn("The authentication token {} for user {} and device {} deleted because of invalid ip", authenticationToken.getToken(), authenticationToken.getUser().getLogin(), authenticationToken.getDeviceId());
					throw new CBusinessException(ECoreErrorResponse.AUTHENTICATION_TOKEN_INVALIDATED);
				}
			}

			checkAuthenticationTokenValidity(authenticationToken);
		}
		else
		{
			LOGGER.error("The authentication token not found");
			throw new CBusinessException(ECoreErrorResponse.AUTHENTICATION_TOKEN_NOT_FOUND);
		}
	}

	/**
	 * Check authentication token validity.
	 *
	 * @param authenticationToken the authentication token
	 * @throws CBusinessException the c business exception
	 */
	protected void checkAuthenticationTokenValidity (CAuthenticationToken authenticationToken) throws CBusinessException
	{
		if (checkTokenStrategy.isAuthenticationTokenExpired(authenticationToken))
		{
			authenticationTokenDao.remove(authenticationToken);
			LOGGER.warn("The token {} has expired", authenticationToken.getToken());
			throw new CBusinessException(ECoreErrorResponse.AUTHENTICATION_TOKEN_INVALIDATED);
		}

		if (checkTokenStrategy.isAuthenticationTokenInactivityReached(authenticationToken))
		{
			authenticationTokenDao.remove(authenticationToken);
			LOGGER.warn("The token {} inactivity limit reached", authenticationToken.getToken());
			throw new CBusinessException(ECoreErrorResponse.AUTHENTICATION_TOKEN_INVALIDATED);
		}
	}
}
