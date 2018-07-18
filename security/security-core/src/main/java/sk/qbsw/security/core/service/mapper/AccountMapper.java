package sk.qbsw.security.core.service.mapper;

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.core.model.domain.Account;

/**
 * The account mapper.
 *
 * @param <D> the account data type
 * @param <A> the account type
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public interface AccountMapper<D extends AccountData, A extends Account>
{
	/**
	 * Map to account data d.
	 *
	 * @param account the account
	 * @return the d
	 */
	D mapToAccountData (A account);
}
