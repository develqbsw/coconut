package sk.qbsw.security.core.service.mapper;

import sk.qbsw.core.security.base.model.AccountInputData;
import sk.qbsw.security.core.model.domain.Account;

/**
 * The account input data mapper implementation.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class AccountInputDataMapperImpl extends AccountInputDataMapperBase<AccountInputData, Account> implements AccountInputDataMapper<AccountInputData, Account>
{
	@Override
	protected Account instantiateAccountWithCustomAttributes (AccountInputData accountInputData)
	{
		return new Account();
	}
}
