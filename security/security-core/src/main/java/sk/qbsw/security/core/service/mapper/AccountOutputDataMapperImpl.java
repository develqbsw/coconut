package sk.qbsw.security.core.service.mapper;

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.core.model.domain.Account;

/**
 * The account output data mapper implementation.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class AccountOutputDataMapperImpl extends AccountOutputDataMapperBase<AccountData, Account> implements AccountOutputDataMapper<AccountData, Account>
{
	@Override
	protected AccountData instantiateAccountOutputDataWithCustomAttributes (Account account)
	{
		return new AccountData();
	}
}
