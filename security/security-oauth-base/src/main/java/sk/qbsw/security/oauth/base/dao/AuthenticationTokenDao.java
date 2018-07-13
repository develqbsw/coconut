package sk.qbsw.security.oauth.base.dao;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.persistence.dao.ICrudDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.oauth.base.model.domain.AuthenticationTokenBase;

import java.util.List;

/**
 * The authentication token dao.
 *
 * @param <A> the account type
 * @param <T> the token type
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.13.1
 */
public interface AuthenticationTokenDao<A extends Account, T extends AuthenticationTokenBase<A>>extends ICrudDao<Long, T>
{
	/**
	 * Find by account id and device id authentication token.
	 *
	 * @param accountId the account id
	 * @param deviceId the device id
	 * @return the authentication token
	 * @throws CBusinessException the c business exception
	 */
	T findByAccountIdAndDeviceId (Long accountId, String deviceId) throws CBusinessException;

	/**
	 * Find by account id and token authentication token.
	 *
	 * @param accountId the account id
	 * @param token the token
	 * @return the authentication token
	 * @throws CBusinessException the c business exception
	 */
	T findByAccountIdAndToken (Long accountId, String token) throws CBusinessException;

	/**
	 * Find by token and device id authentication token.
	 *
	 * @param token the token
	 * @param deviceId the device id
	 * @return the authentication token
	 * @throws CBusinessException the c business exception
	 */
	T findByTokenAndDeviceId (String token, String deviceId) throws CBusinessException;

	/**
	 * Find by expire limit or change limit list.
	 *
	 * @param expireLimit the expire limit
	 * @param changeLimit the change limit
	 * @return the list
	 */
	List<T> findByExpireLimitOrChangeLimit (Integer expireLimit, Integer changeLimit);

	/**
	 * Remove by ids long.
	 *
	 * @param ids the ids
	 * @return the long
	 */
	Long removeByIds (List<Long> ids);
}
