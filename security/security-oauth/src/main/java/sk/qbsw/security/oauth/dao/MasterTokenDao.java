/**
 * 
 */
package sk.qbsw.security.oauth.dao;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.persistence.dao.ICrudDao;
import sk.qbsw.security.oauth.model.domain.MasterToken;

import java.util.List;

/**
 * The master token dao.
 *
 * @author Tomas Lauro
 * @version 1.13.1
 * @since 1.13.1
 */
public interface MasterTokenDao extends ICrudDao<Long, MasterToken>
{
	/**
	 * Find by user and device.
	 *
	 * @param userId the user id
	 * @param deviceId the device id
	 * @return the c authentication token
	 * @throws CBusinessException the c business exception
	 */
	MasterToken findByUserAndDevice (Long userId, String deviceId) throws CBusinessException;

	/**
	 * Find by user and token.
	 *
	 * @param userId the user id
	 * @param token the token
	 * @return the c master token
	 * @throws CBusinessException the c business exception
	 */
	MasterToken findByUserAndToken (Long userId, String token) throws CBusinessException;

	/**
	 * Find by user and token and device.
	 *
	 * @param userId the user id
	 * @param token the token
	 * @param deviceId the device id
	 * @return the c master token
	 * @throws CBusinessException the c business exception
	 */
	MasterToken findByUserAndTokenAndDevice (Long userId, String token, String deviceId) throws CBusinessException;

	/**
	 * Find by token and device id.
	 *
	 * @param token the token
	 * @param deviceId the device id
	 * @return the c master token
	 * @throws CBusinessException the c business exception
	 */
	MasterToken findByTokenAndDeviceId (String token, String deviceId) throws CBusinessException;

	/**
	 * Find by expire limit or change limit list.
	 *
	 * @param expireLimit the expire limit
	 * @param changeLimit the change limit
	 * @return the list
	 */
	List<MasterToken> findByExpireLimitOrChangeLimit (Integer expireLimit, Integer changeLimit);

	/**
	 * Remove by ids long.
	 *
	 * @param ids the ids
	 * @return the long
	 */
	Long removeByIds (List<Long> ids);
}
