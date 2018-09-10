package sk.qbsw.security.oauth.base.service.mapper;

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.AccountOutputDataMapper;
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
	protected final AccountOutputDataMapper<D, A> accountOutputDataMapper;

	/**
	 * Instantiates a new Authentication token mapper base.
	 *
	 * @param accountOutputDataMapper the account output data mapper
	 */
	protected AuthenticationTokenMapperBase (AccountOutputDataMapper<D, A> accountOutputDataMapper)
	{
		this.accountOutputDataMapper = accountOutputDataMapper;
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
		authenticationTokenData.setAccountData(accountOutputDataMapper.mapToAccountOutputData(authenticationToken.getAccount()));

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
