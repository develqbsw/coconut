package sk.qbsw.organization.complex.core.dao;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.organization.complex.core.model.domain.QUnit;
import sk.qbsw.organization.complex.core.model.domain.QUser;
import sk.qbsw.organization.complex.core.model.domain.Unit;
import sk.qbsw.organization.complex.core.model.domain.User;

import java.util.List;

/**
 * The type User jpa dao.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public class UserJpaDao extends AEntityQDslDao<Long, User> implements UserDao
{
	/**
	 * Instantiates a new User jpa dao.
	 */
	public UserJpaDao ()
	{
		super(QUser.user, Long.class);
	}

	@Override
	public List<User> findByUnit (Unit unit) throws CSecurityException
	{
		if (unit == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QUser qUser = QUser.user;
		QUnit qUnit = QUnit.unit;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qUnit.eq(unit));

		// create query
		JPAQuery<User> query = queryFactory.selectFrom(qUser).distinct() //
			.leftJoin(qUser.units, qUnit).fetchJoin() //
			.where(builder);

		return query.fetch();
	}
}
