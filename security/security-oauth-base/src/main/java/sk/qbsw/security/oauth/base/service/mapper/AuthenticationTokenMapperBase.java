package sk.qbsw.security.oauth.base.service.mapper;

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.AccountMapper;
import sk.qbsw.security.oauth.base.model.domain.AuthenticationTokenBase;
import sk.qbsw.security.oauth.model.AuthenticationTokenDataBase;

/**
 * The authentication token mapper base.
 *
 * @param <A> the account type
 * @param <T> the authentication token type
 * @param <D> the account data type
 * @param <TD> the authentication token data type
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public abstract class AuthenticationTokenMapperBase<A extends Account, T extends AuthenticationTokenBase<A>, D extends AccountData, TD extends AuthenticationTokenDataBase<D>> implements AuthenticationTokenMapper<A, T, D, TD>
{
	/**
	 * The Account mapper.
	 */
	protected final AccountMapper<D, A> accountMapper;

	/**
	 * Instantiates a new Authentication token mapper base.
	 *
	 * @param accountMapper the account mapper
	 */
	protected AuthenticationTokenMapperBase (AccountMapper<D, A> accountMapper)
	{
		this.accountMapper = accountMapper;
	}

	@Override
	public TD mapToAuthenticationTokenData (T authenticationToken)
	{
		TD authenticationTokenData = instantiateWithCustomAttributes(authenticationToken);

		authenticationTokenData.setId(authenticationToken.getId());
		authenticationTokenData.setCreated(authenticationToken.getCreateDate());
		authenticationTokenData.setLastAccessed(authenticationToken.getLastAccessDate());
		authenticationTokenData.setToken(authenticationToken.getToken());
		authenticationTokenData.setDeviceId(authenticationToken.getDeviceId());
		authenticationTokenData.setIp(authenticationToken.getIp());
		authenticationTokenData.setAccountData(accountMapper.mapToAccountData(authenticationToken.getAccount()));

		return authenticationTokenData;
	}

	/**
	 * Instantiate with custom attributes td.
	 *
	 * @param authenticationToken the authentication token
	 * @return the td
	 */
	protected abstract TD instantiateWithCustomAttributes (T authenticationToken);
}
