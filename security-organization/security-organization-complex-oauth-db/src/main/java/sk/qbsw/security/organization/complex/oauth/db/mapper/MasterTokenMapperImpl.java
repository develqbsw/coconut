package sk.qbsw.security.organization.complex.oauth.db.mapper;

import sk.qbsw.security.core.service.mapper.AccountOutputDataMapper;
import sk.qbsw.security.oauth.base.service.mapper.MasterTokenMapper;
import sk.qbsw.security.oauth.base.service.mapper.MasterTokenMapperBase;
import sk.qbsw.security.organization.complex.base.model.ComplexOrganizationAccountData;
import sk.qbsw.security.organization.complex.core.model.domain.UserAccount;
import sk.qbsw.security.organization.complex.oauth.db.model.domain.MasterToken;
import sk.qbsw.security.organization.complex.oauth.model.MasterTokenData;

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
	 * @param accountOutputDataMapper the account output data mapper
	 */
	public MasterTokenMapperImpl (AccountOutputDataMapper<ComplexOrganizationAccountData, UserAccount> accountOutputDataMapper)
	{
		super(accountOutputDataMapper);
	}

	@Override
	protected MasterTokenData instantiateWithCustomAttributes (MasterToken masterToken)
	{
		return new MasterTokenData();
	}
}
