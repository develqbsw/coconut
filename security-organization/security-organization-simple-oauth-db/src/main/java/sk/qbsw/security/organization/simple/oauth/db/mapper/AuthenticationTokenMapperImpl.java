package sk.qbsw.security.organization.simple.oauth.db.mapper;

import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.AccountOutputDataMapper;
import sk.qbsw.security.oauth.base.service.mapper.AuthenticationTokenMapper;
import sk.qbsw.security.oauth.base.service.mapper.AuthenticationTokenMapperBase;
import sk.qbsw.security.organization.simple.base.model.SimpleOrganizationAccountData;
import sk.qbsw.security.organization.simple.oauth.db.model.domain.AuthenticationToken;
import sk.qbsw.security.organization.simple.oauth.model.AuthenticationTokenData;

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
	 * @param accountOutputDataMapper the account output data mapper
	 */
	public AuthenticationTokenMapperImpl (AccountOutputDataMapper<SimpleOrganizationAccountData, Account> accountOutputDataMapper)
	{
		super(accountOutputDataMapper);
	}

	@Override
	protected AuthenticationTokenData instantiateWithCustomAttributes (AuthenticationToken authenticationToken)
	{
		return new AuthenticationTokenData();
	}
}
