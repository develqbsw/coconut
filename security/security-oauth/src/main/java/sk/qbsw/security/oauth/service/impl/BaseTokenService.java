/**
 * 
 */
package sk.qbsw.security.oauth.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.security.core.dao.UserDao;
import sk.qbsw.security.oauth.dao.AuthenticationTokenDao;
import sk.qbsw.security.oauth.dao.MasterTokenDao;
import sk.qbsw.security.oauth.model.domain.AuthenticationToken;
import sk.qbsw.security.oauth.model.domain.MasterToken;
import sk.qbsw.security.oauth.service.IdGeneratorService;
import sk.qbsw.security.oauth.service.TokenValidationService;

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

	@Autowired
	protected TokenValidationService tokenValidationService;

	/** The id generator service. */
	@Autowired
	protected IdGeneratorService idGeneratorService;

	/** The master token dao. */
	@Autowired
	protected MasterTokenDao masterTokenDao;

	/** The authentication token dao. */
	@Autowired
	protected AuthenticationTokenDao authenticationTokenDao;

	/** The user dao. */
	@Autowired
	protected UserDao userDao;

	/**
	 * Check master token.
	 *
	 * @param masterToken the master token
	 * @param ip the ip
	 * @throws CBusinessException the c business exception
	 */
	protected void checkMasterToken (MasterToken masterToken, String ip) throws CBusinessException
	{
		if (masterToken != null)
		{
			if (!tokenValidationService.isMasterTokenIpValid(masterToken, ip) || tokenValidationService.isMasterTokenExpired(masterToken))
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
	 * @throws CBusinessException the c business exception
	 */
	protected void checkAuthenticationToken (AuthenticationToken authenticationToken, String ip) throws CBusinessException
	{
		if (authenticationToken != null)
		{
			if (!tokenValidationService.isAuthenticationTokenIpValid(authenticationToken, ip) || tokenValidationService.isAuthenticationTokenExpired(authenticationToken))
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
}
