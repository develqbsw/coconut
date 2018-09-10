package sk.qbsw.security.organization.simple.oauth.db.mapper;

import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.AccountOutputDataMapper;
import sk.qbsw.security.oauth.base.service.mapper.MasterTokenMapper;
import sk.qbsw.security.oauth.base.service.mapper.MasterTokenMapperBase;
import sk.qbsw.security.organization.simple.base.model.SimpleOrganizationAccountData;
import sk.qbsw.security.organization.simple.oauth.db.model.domain.MasterToken;
import sk.qbsw.security.organization.simple.oauth.model.MasterTokenData;

/**
 * The master token mapper implementation.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class MasterTokenMapperImpl extends MasterTokenMapperBase<Account, MasterToken, SimpleOrganizationAccountData, MasterTokenData> implements MasterTokenMapper<Account, MasterToken, SimpleOrganizationAccountData, MasterTokenData>
{
	/**
	 * Instantiates a new Master token mapper.
	 *
	 * @param accountOutputDataMapper the account output data mapper
	 */
	public MasterTokenMapperImpl (AccountOutputDataMapper<SimpleOrganizationAccountData, Account> accountOutputDataMapper)
	{
		super(accountOutputDataMapper);
	}

	@Override
	protected MasterTokenData instantiateWithCustomAttributes (MasterToken masterToken)
	{
		return new MasterTokenData();
	}
}
