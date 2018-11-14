package sk.qbsw.security.core.dao;

import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.QAccount;

/**
 * User DAO implementation.
 *
 * @author rosenberg
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.0.0
 */
public class AccountJpaDao extends AccountJpaDaoBase<Account> implements AccountDao<Account>
{
	/**
	 * Instantiates a new User jpa dao.
	 */
	public AccountJpaDao ()
	{
		super(QAccount.account);
	}

	@Override
	protected QAccount instantiateAccountEntityPath ()
	{
		return QAccount.account;
	}
}
