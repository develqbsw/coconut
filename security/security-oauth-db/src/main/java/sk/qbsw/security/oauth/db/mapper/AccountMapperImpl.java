package sk.qbsw.security.oauth.db.mapper;

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.AccountMapper;
import sk.qbsw.security.core.service.mapper.AccountMapperBase;

/**
 * The account mapper implementation.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class AccountMapperImpl extends AccountMapperBase<AccountData, Account> implements AccountMapper<AccountData, Account>
{
	@Override
	protected AccountData instantiateWithCustomAttributes (Account account)
	{
		return new AccountData();
	}
}
