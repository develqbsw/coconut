package sk.qbsw.security.core.dao;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.security.core.model.domain.*;

import java.util.List;

/**
 * DAO for cross entities between user, unit and group
 *
 * @author farkas.roman
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.7.0
 */
public class AccountUnitGroupJpaDao extends AEntityQDslDao<Long, AccountUnitGroup> implements AccountUnitGroupDao
{
	/**
	 * Instantiates a new User unit group jpa dao.
	 */
	public AccountUnitGroupJpaDao ()
	{
		super(QAccountUnitGroup.accountUnitGroup, Long.class);
	}

	@Override
	public List<AccountUnitGroup> findByAccountAndUnitAndGroup (Account account, Unit unit, Group group)
	{
		QAccountUnitGroup qAccountUnitGroup = QAccountUnitGroup.accountUnitGroup;
		QAccount qAccount = QAccount.account;
		QUnit qUnit = QUnit.unit;
		QGroup qGroup = QGroup.group;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		if (account != null)
		{
			builder.and(qAccount.eq(account));
		}
		if (unit != null)
		{
			builder.and(qUnit.eq(unit));
		}
		if (group != null)
		{
			builder.and(qGroup.eq(group));
		}

		// create query
		JPAQuery<AccountUnitGroup> query = queryFactory.selectFrom(qAccountUnitGroup).distinct() //
			.leftJoin(qAccountUnitGroup.account, qAccount).fetchJoin() //
			.leftJoin(qAccountUnitGroup.unit, qUnit).fetchJoin() //
			.leftJoin(qAccountUnitGroup.group, qGroup).fetchJoin() //
			.where(builder);
		return query.fetch();
	}
}
