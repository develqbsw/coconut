package sk.qbsw.security.core.service.mapper;

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.core.model.domain.Account;

/**
 * The account output data mapper implementation.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
public class AccountOutputDataMapperImpl extends AccountOutputDataMapperBase<AccountData, Account> implements AccountOutputDataMapper<AccountData, Account>
{
	/**
	 * Instantiates a new Account output data mapper.
	 *
	 * @param userOutputDataMapper the user output data mapper
	 */
	public AccountOutputDataMapperImpl (UserOutputDataMapper userOutputDataMapper)
	{
		super(userOutputDataMapper);
	}

	@Override
	protected AccountData instantiateAccountOutputDataWithCustomAttributes (Account account)
	{
		return new AccountData();
	}
}
