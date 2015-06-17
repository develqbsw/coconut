/**
 * 
 */
package sk.qbsw.core.security.oauth.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.oauth.model.domain.CAuthenticationToken;
import sk.qbsw.core.security.oauth.model.domain.CMasterToken;
import sk.qbsw.core.security.oauth.service.IAuthenticationTokenService;

/**
 * The master token service.
 *
 * @author Tomas Lauro
 * 
 * @version 1.13.1
 * @since 1.13.1
 */
@Service ("authenticationTokenService")
public class CAuthenticationTokenService extends ATokenService implements IAuthenticationTokenService
{
	/** The log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CAuthenticationTokenService.class);

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.oauth.service.IMasterTokenService#generateMasterToken(java.lang.Long, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public String generateAuthenticationToken (Long userId, String masterToken, String deviceId, String ip) throws CBusinessException
	{
		//get master token and check it
		CMasterToken persistedMasterToken = masterTokenDao.findByUserAndTokenAndDevice(userId, masterToken, deviceId);
		checkMasterToken(persistedMasterToken, ip);

		CAuthenticationToken authenticationToken = authenticationTokenDao.findByUserAndDevice(userId, deviceId);
		CUser user = userDao.findById(userId);

		//performs checks
		if (authenticationToken != null)
		{
			authenticationTokenDao.remove(authenticationToken);
		}

		if (user == null)
		{
			LOGGER.error("The user {} not found", userId);
			throw new CBusinessException(ECoreErrorResponse.USER_NOT_FOUND);
		}

		CAuthenticationToken newAuthenticationToken = new CAuthenticationToken();
		newAuthenticationToken.setDeviceId(deviceId);
		newAuthenticationToken.setIp(ip);
		newAuthenticationToken.setToken(idGeneratorService.getGeneratedId());
		newAuthenticationToken.setUser(user);

		//save to database and return token
		return authenticationTokenDao.update(newAuthenticationToken).getToken();

	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.oauth.service.IAuthenticationTokenService#revokeAuthenticationToken(java.lang.Long, java.lang.String)
	 */
	@Override
	@Transactional
	public void revokeAuthenticationToken (Long userId, String authenticationToken) throws CBusinessException
	{
		CAuthenticationToken token = authenticationTokenDao.findByUserAndToken(userId, authenticationToken);

		//performs checks
		if (token == null)
		{
			LOGGER.error("The token {} not found", authenticationToken);
			throw new CBusinessException(ECoreErrorResponse.AUTHENTICATION_TOKEN_NOT_FOUND);
		}

		authenticationTokenDao.remove(token);

	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.oauth.service.IAuthenticationTokenService#getUserByAuthenticationToken(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public CUser getUserByAuthenticationToken (String token, String deviceId, String ip) throws CBusinessException
	{
		CAuthenticationToken persistedToken = authenticationTokenDao.findByTokenAndDeviceId(token, deviceId);

		if (persistedToken != null)
		{
			checkAuthenticationToken(persistedToken, ip);
			return persistedToken.getUser();
		}
		else
		{
			return null;
		}
	}
}
