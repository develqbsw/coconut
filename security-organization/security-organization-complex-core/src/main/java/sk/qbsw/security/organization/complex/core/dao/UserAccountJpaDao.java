package sk.qbsw.security.organization.complex.core.dao;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.organization.complex.core.model.domain.QUnit;
import sk.qbsw.organization.complex.core.model.domain.QUser;
import sk.qbsw.security.core.dao.AccountJpaDaoBase;
import sk.qbsw.security.core.model.domain.QAccount;
import sk.qbsw.security.organization.complex.core.model.domain.QUserAccount;
import sk.qbsw.security.organization.complex.core.model.domain.UserAccount;

import java.util.List;

/**
 * The user account JPA DAO implementation.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class UserAccountJpaDao extends AccountJpaDaoBase<UserAccount> implements UserAccountDao
{
	/**
	 * Instantiates a new User account jpa dao.
	 */
	public UserAccountJpaDao ()
	{
		super(QUserAccount.userAccount);
	}

	@Override
	protected QAccount instantiateAccountEntityPath ()
	{
		return QUserAccount.userAccount._super;
	}

	@Override
	public List<UserAccount> findByUnitCode (String unitCode) throws CSecurityException
	{
		if (unitCode == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QUserAccount qUserAccount = QUserAccount.userAccount;
		QUser qUser = QUser.user;
		QUnit qUnit = QUnit.unit;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qUnit.code.eq(unitCode));

		// create query
		JPAQuery<UserAccount> query = queryFactory.selectFrom(qUserAccount).distinct() //
			.leftJoin(qUserAccount.user, qUser).fetchJoin() //
			.leftJoin(qUser.units, qUnit).fetchJoin() //
			.where(builder);

		return query.fetch();
	}
}
