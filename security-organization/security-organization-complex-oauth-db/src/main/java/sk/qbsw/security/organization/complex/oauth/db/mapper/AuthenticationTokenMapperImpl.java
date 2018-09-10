package sk.qbsw.security.organization.complex.oauth.db.mapper;

import sk.qbsw.security.core.service.mapper.AccountOutputDataMapper;
import sk.qbsw.security.oauth.base.service.mapper.AuthenticationTokenMapper;
import sk.qbsw.security.oauth.base.service.mapper.AuthenticationTokenMapperBase;
import sk.qbsw.security.organization.complex.base.model.ComplexOrganizationAccountData;
import sk.qbsw.security.organization.complex.core.model.domain.UserAccount;
import sk.qbsw.security.organization.complex.oauth.db.model.domain.AuthenticationToken;
import sk.qbsw.security.organization.complex.oauth.model.AuthenticationTokenData;

/**
 * The authentication token mapper implementation.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class AuthenticationTokenMapperImpl extends AuthenticationTokenMapperBase<UserAccount, AuthenticationToken, ComplexOrganizationAccountData, AuthenticationTokenData> implements AuthenticationTokenMapper<UserAccount, AuthenticationToken, ComplexOrganizationAccountData, AuthenticationTokenData>
{
	/**
	 * Instantiates a new Authentication token mapper.
	 *
	 * @param accountOutputDataMapper the account output data mapper
	 */
	public AuthenticationTokenMapperImpl (AccountOutputDataMapper<ComplexOrganizationAccountData, UserAccount> accountOutputDataMapper)
	{
		super(accountOutputDataMapper);
	}

	@Override
	protected AuthenticationTokenData instantiateWithCustomAttributes (AuthenticationToken authenticationToken)
	{
		return new AuthenticationTokenData();
	}
}
