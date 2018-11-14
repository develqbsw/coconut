package sk.qbsw.security.core.service.mapper;

import sk.qbsw.core.security.base.model.AccountInputData;
import sk.qbsw.security.core.model.domain.Account;

/**
 * The account input data mapper.
 *
 * @param <I> the account input data type
 * @param <A> the account type
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
public interface AccountInputDataMapper<I extends AccountInputData, A extends Account>
{
	/**
	 * Map to account a.
	 *
	 * @param accountInputData the account input data
	 * @return the a
	 */
	A mapToAccount (I accountInputData);
}
