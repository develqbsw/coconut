package sk.qbsw.security.oauth.base.service;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.oauth.base.model.GeneratedTokenData;
import sk.qbsw.security.oauth.base.model.domain.MasterTokenBase;

import java.util.List;

/**
 * The master token service.
 *
 * @param <A> the account type
 * @param <T> the token type
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.13.1
 */
public interface MasterTokenService<A extends Account, T extends MasterTokenBase<A>>
{
	/**
	 * Generate master token generated token data.
	 *
	 * @param accountId the account id
	 * @param deviceId the device id
	 * @param ip the ip
	 * @return the generated token data
	 * @throws CBusinessException the c business exception
	 */
	GeneratedTokenData generateMasterToken (Long accountId, String deviceId, String ip) throws CBusinessException;

	/**
	 * Revoke master token.
	 *
	 * @param accountId the account id
	 * @param masterToken the master token
	 * @throws CBusinessException the c business exception
	 */
	void revokeMasterToken (Long accountId, String masterToken) throws CBusinessException;

	/**
	 * Gets account by master token.
	 *
	 * @param token the token
	 * @param deviceId the device id
	 * @param ip the ip
	 * @param isIpIgnored the is ip ignored
	 * @return the account by master token
	 * @throws CBusinessException the c business exception
	 */
	A getAccountByMasterToken (String token, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException;

	/**
	 * Find expired master tokens list.
	 *
	 * @return the list
	 */
	List<T> findExpiredMasterTokens ();

	/**
	 * Remove master tokens long.
	 *
	 * @param ids the ids
	 * @return the long
	 */
	Long removeMasterTokens (List<Long> ids);
}
