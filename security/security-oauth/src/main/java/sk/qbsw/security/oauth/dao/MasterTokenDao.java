package sk.qbsw.security.oauth.dao;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.persistence.dao.ICrudDao;
import sk.qbsw.security.oauth.model.domain.MasterToken;

import java.util.List;

/**
 * The master token dao.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.13.1
 */
public interface MasterTokenDao extends ICrudDao<Long, MasterToken>
{
	/**
	 * Find by account id and device id master token.
	 *
	 * @param accountId the account id
	 * @param deviceId the device id
	 * @return the master token
	 * @throws CBusinessException the c business exception
	 */
	MasterToken findByAccountIdAndDeviceId (Long accountId, String deviceId) throws CBusinessException;

	/**
	 * Find by account id and token master token.
	 *
	 * @param accountId the account id
	 * @param token the token
	 * @return the master token
	 * @throws CBusinessException the c business exception
	 */
	MasterToken findByAccountIdAndToken (Long accountId, String token) throws CBusinessException;

	/**
	 * Find by account id and token and device id master token.
	 *
	 * @param accountId the account id
	 * @param token the token
	 * @param deviceId the device id
	 * @return the master token
	 * @throws CBusinessException the c business exception
	 */
	MasterToken findByAccountIdAndTokenAndDeviceId (Long accountId, String token, String deviceId) throws CBusinessException;

	/**
	 * Find by token and device id master token.
	 *
	 * @param token the token
	 * @param deviceId the device id
	 * @return the master token
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
