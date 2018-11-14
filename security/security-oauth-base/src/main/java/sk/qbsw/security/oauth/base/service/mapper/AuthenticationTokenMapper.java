package sk.qbsw.security.oauth.base.service.mapper;

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.oauth.base.model.domain.AuthenticationTokenBase;
import sk.qbsw.security.oauth.model.AuthenticationTokenDataBase;

/**
 * The master token authenticationTokenMapper.
 *
 * @param <A> the account type
 * @param <T> the authentication token type
 * @param <D> the account data type
 * @param <TD> the authentication token data type
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
public interface AuthenticationTokenMapper<A extends Account, T extends AuthenticationTokenBase<A>, D extends AccountData, TD extends AuthenticationTokenDataBase<D>>
{
	/**
	 * Map to authentication token data td.
	 *
	 * @param authenticationToken the authentication token
	 * @return the td
	 */
	TD mapToAuthenticationTokenData (T authenticationToken);
}
