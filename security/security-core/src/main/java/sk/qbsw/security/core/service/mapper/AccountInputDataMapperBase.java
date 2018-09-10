package sk.qbsw.security.core.service.mapper;

import sk.qbsw.core.security.base.model.AccountDataTypes;
import sk.qbsw.core.security.base.model.AccountInputData;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.AccountTypes;
import sk.qbsw.security.core.model.domain.Organization;

/**
 * The account input data mapper base.
 *
 * @param <I> the account input data type
 * @param <A> the account type
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public abstract class AccountInputDataMapperBase<I extends AccountInputData, A extends Account> implements AccountInputDataMapper<I, A>
{
	@Override
	public A mapToAccount (I accountInputData)
	{
		if (accountInputData == null)
		{
			return null;
		}

		Organization organization = new Organization();
		organization.setId(accountInputData.getOrganizationId());

		A account = instantiateAccountWithCustomAttributes(accountInputData);
		account.setId(accountInputData.getId());
		account.setUid(accountInputData.getUid());
		account.setLogin(accountInputData.getLogin());
		account.setEmail(accountInputData.getEmail());
		account.setType(mapToAccountType(accountInputData.getType()));
		account.setOrganization(organization);

		return account;
	}

	private AccountTypes mapToAccountType (AccountDataTypes accountDataType)
	{
		switch (accountDataType)
		{
			case PERSONAL:
				return AccountTypes.PERSONAL;
			case TECHNICAL:
				return AccountTypes.TECHNICAL;
			default:
				throw new IllegalArgumentException("Invalid account type: " + accountDataType.name());
		}
	}

	/**
	 * Instantiate account with custom attributes a.
	 *
	 * @param accountInputData the account input data
	 * @return the a
	 */
	protected abstract A instantiateAccountWithCustomAttributes (I accountInputData);
}
