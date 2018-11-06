package sk.qbsw.security.organization.complex.core.dao;

import com.querydsl.jpa.impl.JPAQuery;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AccountJpaDaoBase;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.QAccount;
import sk.qbsw.security.organization.complex.core.model.domain.QCXOUnit;
import sk.qbsw.security.organization.complex.core.model.domain.QCXOUser;

/**
 * The complex organization account DAO implementation.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class CXOAccountJpaDao extends AccountJpaDaoBase<Account> implements AccountDao<Account>
{
	/**
	 * Instantiates a new User jpa dao.
	 */
	public CXOAccountJpaDao ()
	{
		super(QAccount.account);
	}

	@Override
	protected QAccount instantiateAccountEntityPath ()
	{
		return QAccount.account;
	}

	@Override
	protected void leftJoinFetchUser (JPAQuery<Account> query, QAccount qAccount)
	{
		QCXOUser qcxoUser = QCXOUser.cXOUser;
		QCXOUnit qcxoUnit = QCXOUnit.cXOUnit;

		query.leftJoin(qAccount.user.as(QCXOUser.class), qcxoUser).fetchJoin() //
			.leftJoin(qcxoUser.units, qcxoUnit).fetchJoin() //
			.leftJoin(qcxoUnit.organization).fetchJoin();
	}
}
