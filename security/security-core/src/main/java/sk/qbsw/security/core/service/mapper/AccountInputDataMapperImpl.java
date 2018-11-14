package sk.qbsw.security.core.service.mapper;

import sk.qbsw.core.security.base.model.AccountInputData;
import sk.qbsw.security.core.model.domain.Account;

/**
 * The account input data mapper implementation.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
public class AccountInputDataMapperImpl extends AccountInputDataMapperBase<AccountInputData, Account> implements AccountInputDataMapper<AccountInputData, Account>
{
	/**
	 * Instantiates a new Account input data mapper base.
	 *
	 * @param userInputDataMapper the user input data mapper
	 */
	public AccountInputDataMapperImpl (UserInputDataMapper userInputDataMapper)
	{
		super(userInputDataMapper);
	}

	@Override
	protected Account instantiateAccountWithCustomAttributes (AccountInputData accountInputData)
	{
		return new Account();
	}
}
