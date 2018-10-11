package sk.qbsw.security.core.service.mapper;

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.core.security.base.model.AccountDataTypes;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.AccountTypes;

/**
 * The account output data mapper base.
 *
 * @param <O> the account output data type
 * @param <A> the account type
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public abstract class AccountOutputDataMapperBase<O extends AccountData, A extends Account> implements AccountOutputDataMapper<O, A>
{
	private final UserOutputDataMapper userOutputDataMapper;

	protected AccountOutputDataMapperBase (UserOutputDataMapper userOutputDataMapper)
	{
		this.userOutputDataMapper = userOutputDataMapper;
	}

	@Override
	public O mapToAccountOutputData (A account)
	{
		if (account == null)
		{
			return null;
		}

		O accountData = instantiateAccountOutputDataWithCustomAttributes(account);

		accountData.setId(account.getId());
		accountData.setUid(account.getUid());
		accountData.setLogin(account.getLogin());
		accountData.setEmail(account.getEmail());
		accountData.setType(mapToAccountDataType(account.getType()));
		accountData.setGroups(account.exportGroups());
		accountData.setRoles(account.exportRoles());
		accountData.setUser(userOutputDataMapper.mapToUserOutputData(account.getUser()));

		return accountData;
	}

	private AccountDataTypes mapToAccountDataType (AccountTypes accountType)
	{
		switch (accountType)
		{
			case PERSONAL:
				return AccountDataTypes.PERSONAL;
			case TECHNICAL:
				return AccountDataTypes.TECHNICAL;
			default:
				throw new IllegalArgumentException("Invalid account type: " + accountType.name());
		}
	}

	/**
	 * Instantiate account output data with custom attributes o.
	 *
	 * @param account the account
	 * @return the o
	 */
	protected abstract O instantiateAccountOutputDataWithCustomAttributes (A account);
}
