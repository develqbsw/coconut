package sk.qbsw.security.oauth.base.service;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.oauth.base.model.GeneratedTokenData;
import sk.qbsw.security.oauth.base.model.domain.AuthenticationTokenBase;

import java.util.List;

/**
 * The authentication token service.
 *
 * @param <A> the account type
 * @param <T> the token type
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.13.1
 */
public interface AuthenticationTokenService<A extends Account, T extends AuthenticationTokenBase<A>>
{
	/**
	 * Generate authentication token generated token data.
	 *
	 * @param accountId the account id
	 * @param masterToken the master token
	 * @param deviceId the device id
	 * @param ip the ip
	 * @param isIpIgnored the is ip ignored
	 * @return the generated token data
	 * @throws CBusinessException the c business exception
	 */
	GeneratedTokenData generateAuthenticationToken (Long accountId, String masterToken, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException;

	/**
	 * Revoke authentication token.
	 *
	 * @param accountId the account id
	 * @param authenticationToken the authentication token
	 * @throws CBusinessException the c business exception
	 */
	void revokeAuthenticationToken (Long accountId, String authenticationToken) throws CBusinessException;

	/**
	 * Gets account by authentication token.
	 *
	 * @param authenticationToken the authentication token
	 * @param deviceId the device id
	 * @param ip the ip
	 * @param isIpIgnored the is ip ignored
	 * @return the account by authentication token
	 * @throws CBusinessException the c business exception
	 */
	A getAccountByAuthenticationToken (String authenticationToken, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException;

	/**
	 * Find expired authentication tokens list.
	 *
	 * @return the list
	 */
	List<T> findExpiredAuthenticationTokens ();

	/**
	 * Remove authentication tokens long.
	 *
	 * @param ids the ids
	 * @return the long
	 */
	Long removeAuthenticationTokens (List<Long> ids);
}
