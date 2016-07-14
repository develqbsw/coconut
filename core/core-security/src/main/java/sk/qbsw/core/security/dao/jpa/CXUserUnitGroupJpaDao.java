package sk.qbsw.core.security.dao.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;

import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.core.security.dao.IXUserUnitGroupDao;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.domain.CXUserUnitGroup;
import sk.qbsw.core.security.model.domain.QCGroup;
import sk.qbsw.core.security.model.domain.QCUnit;
import sk.qbsw.core.security.model.domain.QCUser;
import sk.qbsw.core.security.model.domain.QCXUserUnitGroup;

/**
 * DAO for cross entities between user, unit and group
 * 
 * @author farkas.roman
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.7.0
 */
@Repository (value = "xUserUnitGroupDao")
public class CXUserUnitGroupJpaDao extends AEntityQDslDao<Long, CXUserUnitGroup> implements IXUserUnitGroupDao
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new x user unit group dao.
	 */
	public CXUserUnitGroupJpaDao ()
	{
		super(QCXUserUnitGroup.cXUserUnitGroup, Long.class);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IXUserUnitGroupDao#findByUserAndUnitAndGroup(sk.qbsw.core.security.model.domain.CUser, sk.qbsw.core.security.model.domain.CUnit, sk.qbsw.core.security.model.domain.CGroup)
	 */
	@Override
	public List<CXUserUnitGroup> findByUserAndUnitAndGroup (CUser user, CUnit unit, CGroup group)
	{
		QCXUserUnitGroup qUserUnitGroup = QCXUserUnitGroup.cXUserUnitGroup;
		QCUser qUser = QCUser.cUser;
		QCUnit qUnit = QCUnit.cUnit;
		QCGroup qGroup = QCGroup.cGroup;

		//create where condition
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

		//create query
		JPAQuery<CXUserUnitGroup> query = queryFactory.selectFrom(qUserUnitGroup).distinct().leftJoin(qUserUnitGroup.user, qUser).fetchJoin().leftJoin(qUserUnitGroup.unit, qUnit).fetchJoin().leftJoin(qUserUnitGroup.group, qGroup).fetchJoin().where(builder);
		return query.fetch();
	}
}
