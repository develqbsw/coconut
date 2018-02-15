/**
 * 
 */
package sk.qbsw.security.oauth.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.oauth.model.GeneratedTokenData;
import sk.qbsw.security.oauth.model.domain.AuthenticationToken;
import sk.qbsw.security.oauth.model.domain.MasterToken;
import sk.qbsw.security.oauth.service.AuthenticationTokenService;

/**
 * The authentication token service.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.13.1
 */
@Service ("authenticationTokenService")
public class AuthenticationTokenServiceImpl extends BaseTokenService implements AuthenticationTokenService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationTokenServiceImpl.class);

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public GeneratedTokenData generateAuthenticationToken (Long userId, String masterToken, String deviceId, String ip) throws CBusinessException
	{
		// get master token and check it
		MasterToken persistedMasterToken = masterTokenDao.findByUserAndTokenAndDevice(userId, masterToken, deviceId);
		checkMasterToken(persistedMasterToken, ip);

		AuthenticationToken authenticationToken = authenticationTokenDao.findByUserAndDevice(userId, deviceId);
		User user = userDao.findById(userId);

		// performs checks
		if (authenticationToken != null)
		{
			authenticationTokenDao.remove(authenticationToken);
		}

		if (user == null)
		{
			LOGGER.error("The user {} not found", userId);
			throw new CBusinessException(ECoreErrorResponse.USER_NOT_FOUND);
		}

		AuthenticationToken newAuthenticationToken = new AuthenticationToken();
		newAuthenticationToken.setDeviceId(deviceId);
		newAuthenticationToken.setIp(ip);
		newAuthenticationToken.setToken(idGeneratorService.getGeneratedId());
		newAuthenticationToken.setUser(user);

		// save to database and return token
		return new GeneratedTokenData(authenticationTokenDao.update(newAuthenticationToken).getToken(), authenticationToken != null ? authenticationToken.getToken() : null);

	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void revokeAuthenticationToken (Long userId, String authenticationToken) throws CBusinessException
	{
		AuthenticationToken token = authenticationTokenDao.findByUserAndToken(userId, authenticationToken);

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
	public User getUserByAuthenticationToken (String token, String deviceId, String ip) throws CBusinessException
	{
		AuthenticationToken persistedToken = authenticationTokenDao.findByTokenAndDeviceId(token, deviceId);

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
