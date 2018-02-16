/**
 * 
 */
package sk.qbsw.security.oauth.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.security.core.dao.UserDao;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.oauth.configuration.OAuthValidationConfiguration;
import sk.qbsw.security.oauth.dao.AuthenticationTokenDao;
import sk.qbsw.security.oauth.dao.MasterTokenDao;
import sk.qbsw.security.oauth.model.GeneratedTokenData;
import sk.qbsw.security.oauth.model.domain.MasterToken;
import sk.qbsw.security.oauth.service.IdGeneratorService;
import sk.qbsw.security.oauth.service.MasterTokenService;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * The master token service.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.13.1
 */
public class MasterTokenServiceImpl extends BaseTokenService implements MasterTokenService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(MasterTokenServiceImpl.class);

	/**
	 * Instantiates a new Base token service.
	 *
	 * @param masterTokenDao the master token dao
	 * @param authenticationTokenDao the authentication token dao
	 * @param userDao the user dao
	 * @param idGeneratorService the id generator service
	 * @param validationConfiguration the validation configuration
	 */
	public MasterTokenServiceImpl (MasterTokenDao masterTokenDao, AuthenticationTokenDao authenticationTokenDao, UserDao userDao, IdGeneratorService idGeneratorService, OAuthValidationConfiguration validationConfiguration)
	{
		super(masterTokenDao, authenticationTokenDao, userDao, idGeneratorService, validationConfiguration);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public GeneratedTokenData generateMasterToken (Long userId, String deviceId, String ip) throws CBusinessException
	{
		MasterToken token = masterTokenDao.findByUserAndDevice(userId, deviceId);
		User user;
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

		// performs checks
		if (token != null)
		{
			masterTokenDao.remove(token);
		}

		MasterToken newToken = new MasterToken();
		newToken.setDeviceId(deviceId);
		newToken.setIp(ip);
		newToken.setToken(idGeneratorService.getGeneratedId());
		newToken.setUser(user);

		// save to database and return token
		return new GeneratedTokenData(masterTokenDao.update(newToken).getToken(), token != null ? token.getToken() : null);

	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void revokeMasterToken (Long userId, String masterToken) throws CBusinessException
	{
		MasterToken token = masterTokenDao.findByUserAndToken(userId, masterToken);

		// performs checks
		if (token == null)
		{
			LOGGER.error("The token {} not found", masterToken);
			throw new CBusinessException(ECoreErrorResponse.MASTER_TOKEN_NOT_FOUND);
		}

		masterTokenDao.remove(token);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public User getUserByMasterToken (String token, String deviceId, String ip) throws CBusinessException
	{
		MasterToken persistedToken = masterTokenDao.findByTokenAndDeviceId(token, deviceId);

		if (persistedToken != null)
		{
			checkMasterToken(persistedToken, ip);
			persistedToken.getUser().exportRoles();
			return persistedToken.getUser();
		}
		else
		{
			return null;
		}
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<MasterToken> findExpiredMasterTokens ()
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

		return masterTokenDao.findByExpireLimitOrChangeLimit(expireLimit, changeLimit);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Long removeMasterTokens (List<Long> ids)
	{
		return masterTokenDao.removeByIds(ids);
	}
}
