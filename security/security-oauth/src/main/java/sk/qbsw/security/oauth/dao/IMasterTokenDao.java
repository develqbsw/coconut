/**
 * 
 */
package sk.qbsw.security.oauth.dao;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.persistence.dao.ICrudDao;
import sk.qbsw.security.oauth.model.domain.CMasterToken;

/**
 * The master token dao.
 *
 * @author Tomas Lauro
 * 
 * @version 1.13.1
 * @since 1.13.1
 */
public interface IMasterTokenDao extends ICrudDao<Long, CMasterToken>
{
	/**
	 * Find by user and device.
	 *
	 * @param userId the user id
	 * @param deviceId the device id
	 * @return the c authentication token
	 * @throws CBusinessException the c business exception
	 */
	CMasterToken findByUserAndDevice (Long userId, String deviceId) throws CBusinessException;

	/**
	 * Find by user and token.
	 *
	 * @param userId the user id
	 * @param token the token
	 * @return the c master token
	 * @throws CBusinessException the c business exception
	 */
	CMasterToken findByUserAndToken (Long userId, String token) throws CBusinessException;

	/**
	 * Find by user and token and device.
	 *
	 * @param userId the user id
	 * @param token the token
	 * @param deviceId the device id
	 * @return the c master token
	 * @throws CBusinessException the c business exception
	 */
	CMasterToken findByUserAndTokenAndDevice (Long userId, String token, String deviceId) throws CBusinessException;

	/**
	 * Find by token and device id.
	 *
	 * @param token the token
	 * @param deviceId the device id
	 * @return the c master token
	 * @throws CBusinessException the c business exception
	 */
	CMasterToken findByTokenAndDeviceId (String token, String deviceId) throws CBusinessException;
}
