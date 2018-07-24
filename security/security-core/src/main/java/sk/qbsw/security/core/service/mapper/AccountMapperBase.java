package sk.qbsw.security.core.service.mapper;

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.core.model.domain.Account;

/**
 * The account mapper base.
 *
 * @param <D> the account data type
 * @param <A> the account type
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public abstract class AccountMapperBase<D extends AccountData, A extends Account> implements AccountMapper<D, A>
{
	@Override
	public D mapToAccountData (A account)
	{
		D accountData = instantiateWithCustomAttributes(account);

		accountData.setId(account.getId());
		accountData.setLogin(account.getLogin());
		accountData.setEmail(account.getEmail());
		accountData.setGroups(account.exportGroups());
		accountData.setRoles(account.exportRoles());

		return accountData;
	}

	/**
	 * Instantiate with custom attributes.
	 *
	 * @param account the account
	 * @return the d
	 */
	protected abstract D instantiateWithCustomAttributes (A account);
}
