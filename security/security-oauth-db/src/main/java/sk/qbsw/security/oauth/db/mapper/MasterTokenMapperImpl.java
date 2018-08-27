package sk.qbsw.security.oauth.db.mapper;

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.AccountMapper;
import sk.qbsw.security.oauth.base.service.mapper.MasterTokenMapper;
import sk.qbsw.security.oauth.base.service.mapper.MasterTokenMapperBase;
import sk.qbsw.security.oauth.db.model.MasterTokenData;
import sk.qbsw.security.oauth.db.model.domain.MasterToken;

/**
 * The master token mapper implementation.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class MasterTokenMapperImpl extends MasterTokenMapperBase<Account, MasterToken, AccountData, MasterTokenData> implements MasterTokenMapper<Account, MasterToken, AccountData, MasterTokenData>
{
	/**
	 * Instantiates a new Master token mapper.
	 *
	 * @param accountMapper the account mapper
	 */
	public MasterTokenMapperImpl (AccountMapper<AccountData, Account> accountMapper)
	{
		super(accountMapper);
	}

	@Override
	protected MasterTokenData instantiateWithCustomAttributes (MasterToken masterToken)
	{
		return new MasterTokenData();
	}
}