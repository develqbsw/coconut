package sk.qbsw.security.oauth.db.mapper;

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.AccountOutputDataMapper;
import sk.qbsw.security.oauth.base.service.mapper.MasterTokenMapper;
import sk.qbsw.security.oauth.base.service.mapper.MasterTokenMapperBase;
import sk.qbsw.security.oauth.db.model.MasterTokenData;
import sk.qbsw.security.oauth.db.model.domain.MasterToken;

/**
 * The master token mapper implementation.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
public class MasterTokenMapperImpl extends MasterTokenMapperBase<Account, MasterToken, AccountData, MasterTokenData> implements MasterTokenMapper<Account, MasterToken, AccountData, MasterTokenData>
{
	/**
	 * Instantiates a new Master token mapper.
	 *
	 * @param accountOutputDataMapper the account output data mapper
	 */
	public MasterTokenMapperImpl (AccountOutputDataMapper<AccountData, Account> accountOutputDataMapper)
	{
		super(accountOutputDataMapper);
	}

	@Override
	protected MasterTokenData instantiateWithCustomAttributes (MasterToken masterToken)
	{
		return new MasterTokenData();
	}
}
