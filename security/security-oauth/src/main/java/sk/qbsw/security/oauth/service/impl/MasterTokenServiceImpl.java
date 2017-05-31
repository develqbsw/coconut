/**
 * 
 */
package sk.qbsw.security.oauth.service.impl;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.security.core.model.domain.CUser;
import sk.qbsw.security.oauth.model.domain.MasterToken;
import sk.qbsw.security.oauth.service.MasterTokenService;

/**
 * The master token service.
 *
 * @author Tomas Lauro
 * 
 * @version 1.13.1
 * @since 1.13.1
 */
@Service ("masterTokenService")
public class MasterTokenServiceImpl extends BaseTokenService implements MasterTokenService
{
	/** The log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(MasterTokenServiceImpl.class);

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.oauth.service.IMasterTokenService#generateMasterToken(java.lang.Long, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public String generateMasterToken (Long userId, String deviceId, String ip) throws CBusinessException
	{
		MasterToken token = masterTokenDao.findByUserAndDevice(userId, deviceId);
		CUser user;
		try
		{
			user = userDao.findById(userId);
		}
		catch (NoResultException ex)
		{
			LOGGER.error("The user {} not found", userId);
			LOGGER.error("The user not found", ex);
			throw new CBusinessException(ECoreErrorResponse.USER_NOT_FOUND);
		}

		//performs checks
		if (token != null)
		{
			masterTokenDao.remove(token);
		}

		MasterToken newToken = new MasterToken();
		newToken.setDeviceId(deviceId);
		newToken.setIp(ip);
		newToken.setToken(idGeneratorService.getGeneratedId());
		newToken.setUser(user);

		//save to database and return token
		return masterTokenDao.update(newToken).getToken();

	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.oauth.service.IMasterTokenService#revokeMasterToken(java.lang.Long, java.lang.String)
	 */
	@Override
	@Transactional
	public void revokeMasterToken (Long userId, String masterToken) throws CBusinessException
	{
		MasterToken token = masterTokenDao.findByUserAndToken(userId, masterToken);

		//performs checks
		if (token == null)
		{
			LOGGER.error("The token {} not found", masterToken);
			throw new CBusinessException(ECoreErrorResponse.MASTER_TOKEN_NOT_FOUND);
		}

		masterTokenDao.remove(token);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.oauth.service.IMasterTokenService#getUserByMasterToken(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public CUser getUserByMasterToken (String token, String deviceId, String ip) throws CBusinessException
	{
		MasterToken persistedToken = masterTokenDao.findByTokenAndDeviceId(token, deviceId);

		if (persistedToken != null)
		{
			checkMasterToken(persistedToken, ip);
			return persistedToken.getUser();
		}
		else
		{
			return null;
		}
	}
}
