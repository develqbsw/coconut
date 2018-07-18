package sk.qbsw.security.organization.simple.oauth.mapper;

import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.AccountMapper;
import sk.qbsw.security.oauth.base.service.mapper.AuthenticationTokenMapper;
import sk.qbsw.security.oauth.base.service.mapper.AuthenticationTokenMapperBase;
import sk.qbsw.security.organization.simple.oauth.model.AuthenticationTokenData;
import sk.qbsw.security.organization.simple.oauth.model.SimpleOrganizationAccountData;
import sk.qbsw.security.organization.simple.oauth.model.domain.AuthenticationToken;

/**
 * The authentication token mapper implementation.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class AuthenticationTokenMapperImpl extends AuthenticationTokenMapperBase<Account, AuthenticationToken, SimpleOrganizationAccountData, AuthenticationTokenData> implements AuthenticationTokenMapper<Account, AuthenticationToken, SimpleOrganizationAccountData, AuthenticationTokenData>
{
	/**
	 * Instantiates a new Authentication token mapper.
	 *
	 * @param accountMapper the account mapper
	 */
	public AuthenticationTokenMapperImpl (AccountMapper<SimpleOrganizationAccountData, Account> accountMapper)
	{
		super(accountMapper);
	}

	@Override
	protected AuthenticationTokenData instantiateWithCustomAttributes (AuthenticationToken authenticationToken)
	{
		return new AuthenticationTokenData();
	}
}
