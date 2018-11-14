package sk.qbsw.security.core.service.mapper;

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.core.model.domain.Account;

/**
 * The account output data mapper.
 *
 * @param <O> the account output data type
 * @param <A> the account type
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
public interface AccountOutputDataMapper<O extends AccountData, A extends Account>
{
	/**
	 * Map to account output data o.
	 *
	 * @param account the account
	 * @return the o
	 */
	O mapToAccountOutputData (A account);
}
