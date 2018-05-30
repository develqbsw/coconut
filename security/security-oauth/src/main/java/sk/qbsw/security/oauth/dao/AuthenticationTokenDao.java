package sk.qbsw.security.oauth.dao;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.persistence.dao.ICrudDao;
import sk.qbsw.security.oauth.model.domain.AuthenticationToken;

import java.util.List;

/**
 * The authentication token dao.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.13.1
 */
public interface AuthenticationTokenDao extends ICrudDao<Long, AuthenticationToken>
{
	/**
	 * Find by account id and device id authentication token.
	 *
	 * @param accountId the account id
	 * @param deviceId the device id
	 * @return the authentication token
	 * @throws CBusinessException the c business exception
	 */
	AuthenticationToken findByAccountIdAndDeviceId (Long accountId, String deviceId) throws CBusinessException;

	/**
	 * Find by account id and token authentication token.
	 *
	 * @param accountId the account id
	 * @param token the token
	 * @return the authentication token
	 * @throws CBusinessException the c business exception
	 */
	AuthenticationToken findByAccountIdAndToken (Long accountId, String token) throws CBusinessException;

	/**
	 * Find by token and device id authentication token.
	 *
	 * @param token the token
	 * @param deviceId the device id
	 * @return the authentication token
	 * @throws CBusinessException the c business exception
	 */
	AuthenticationToken findByTokenAndDeviceId (String token, String deviceId) throws CBusinessException;

	/**
	 * Find by expire limit or change limit list.
	 *
	 * @param expireLimit the expire limit
	 * @param changeLimit the change limit
	 * @return the list
	 */
	List<AuthenticationToken> findByExpireLimitOrChangeLimit (Integer expireLimit, Integer changeLimit);

	/**
	 * Remove by ids long.
	 *
	 * @param ids the ids
	 * @return the long
	 */
	Long removeByIds (List<Long> ids);
}
