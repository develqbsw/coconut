package sk.qbsw.security.core.dao.jpa;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.security.core.dao.UserUnitGroupDao;
import sk.qbsw.security.core.model.domain.*;

import java.util.List;

/**
 * DAO for cross entities between user, unit and group
 * 
 * @author farkas.roman
 * @author Tomas Lauro
 * @version 1.16.0
 * @since 1.7.0
 */
@Repository (value = "xUserUnitGroupDao")
public class UserUnitGroupJpaDao extends AEntityQDslDao<Long, CXUserUnitGroup> implements UserUnitGroupDao
{
	/**
	 * Instantiates a new x user unit group dao.
	 */
	public UserUnitGroupJpaDao ()
	{
		super(QCXUserUnitGroup.cXUserUnitGroup, Long.class);
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.IXUserUnitGroupDao#findByUserAndUnitAndGroup(sk.qbsw.security.core.core.model.domain.CUser, sk.qbsw.security.core.core.model.domain.CUnit, sk.qbsw.security.core.core.model.domain.CGroup)
	 */
	@Override
	public List<CXUserUnitGroup> findByUserAndUnitAndGroup (CUser user, CUnit unit, CGroup group)
	{
		QCXUserUnitGroup qUserUnitGroup = QCXUserUnitGroup.cXUserUnitGroup;
		QCUser qUser = QCUser.cUser;
		QCUnit qUnit = QCUnit.cUnit;
		QCGroup qGroup = QCGroup.cGroup;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		if (user != null)
		{
			builder.and(qUser.eq(user));
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
		JPAQuery<CXUserUnitGroup> query = queryFactory.selectFrom(qUserUnitGroup).distinct().leftJoin(qUserUnitGroup.user, qUser).fetchJoin().leftJoin(qUserUnitGroup.unit, qUnit).fetchJoin().leftJoin(qUserUnitGroup.group, qGroup).fetchJoin().where(builder);
		return query.fetch();
	}
}
