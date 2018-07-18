package sk.qbsw.security.organization.complex.oauth.mapper;

import sk.qbsw.security.core.service.mapper.AccountMapper;
import sk.qbsw.security.oauth.base.service.mapper.MasterTokenMapper;
import sk.qbsw.security.oauth.base.service.mapper.MasterTokenMapperBase;
import sk.qbsw.security.organization.complex.core.model.domain.UserAccount;
import sk.qbsw.security.organization.complex.oauth.model.ComplexOrganizationAccountData;
import sk.qbsw.security.organization.complex.oauth.model.MasterTokenData;
import sk.qbsw.security.organization.complex.oauth.model.domain.MasterToken;

/**
 * The master token mapper implementation.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class MasterTokenMapperImpl extends MasterTokenMapperBase<UserAccount, MasterToken, ComplexOrganizationAccountData, MasterTokenData> implements MasterTokenMapper<UserAccount, MasterToken, ComplexOrganizationAccountData, MasterTokenData>
{
	/**
	 * Instantiates a new Master token mapper.
	 *
	 * @param accountMapper the account mapper
	 */
	public MasterTokenMapperImpl (AccountMapper<ComplexOrganizationAccountData, UserAccount> accountMapper)
	{
		super(accountMapper);
	}

	@Override
	protected MasterTokenData instantiateWithCustomAttributes (MasterToken masterToken)
	{
		return new MasterTokenData();
	}
}
