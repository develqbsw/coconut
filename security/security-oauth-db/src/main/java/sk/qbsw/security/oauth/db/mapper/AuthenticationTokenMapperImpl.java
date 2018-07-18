package sk.qbsw.security.oauth.db.mapper;

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.AccountMapper;
import sk.qbsw.security.oauth.base.service.mapper.AuthenticationTokenMapper;
import sk.qbsw.security.oauth.base.service.mapper.AuthenticationTokenMapperBase;
import sk.qbsw.security.oauth.db.model.AuthenticationTokenData;
import sk.qbsw.security.oauth.db.model.domain.AuthenticationToken;

/**
 * The authentication token mapper implementation.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class AuthenticationTokenMapperImpl extends AuthenticationTokenMapperBase<Account, AuthenticationToken, AccountData, AuthenticationTokenData> implements AuthenticationTokenMapper<Account, AuthenticationToken, AccountData, AuthenticationTokenData>
{
	/**
	 * Instantiates a new Authentication token mapper.
	 *
	 * @param accountMapper the account mapper
	 */
	public AuthenticationTokenMapperImpl (AccountMapper<AccountData, Account> accountMapper)
	{
		super(accountMapper);
	}

	@Override
	protected AuthenticationTokenData instantiateWithCustomAttributes (AuthenticationToken authenticationToken)
	{
		return new AuthenticationTokenData();
	}
}
