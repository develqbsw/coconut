/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.security.core.dao.jpa;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.core.persistence.dao.jpa.qdsl.CQDslDaoHelper;
import sk.qbsw.security.core.dao.UnitDao;
import sk.qbsw.security.core.model.domain.Unit;
import sk.qbsw.security.core.model.domain.QUnit;
import sk.qbsw.security.core.model.domain.QUser;
import sk.qbsw.security.core.model.domain.QUserUnitGroup;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * The unit jpa dao.
 *
 * @author Tomas Lauro
 * @version 1.16.0
 * @since 1.6.0
 */
@Repository (value = "unitDao")
public class UnitJpaDao extends AEntityQDslDao<Long, Unit> implements UnitDao
{
	/**
	 * Instantiates a new unit jpa dao.
	 */
	public UnitJpaDao ()
	{
		super(QUnit.unit, Long.class);
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.IUnitDao#findOneByName(java.lang.String)
	 */
	@Override
	public Unit findOneByName (String name) throws NoResultException, CSecurityException
	{
		if (name == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QUnit qUnit = QUnit.unit;

		// create query
		JPAQuery<Unit> query = queryFactory.selectFrom(qUnit).distinct().leftJoin(qUnit.groups).fetchJoin().leftJoin(qUnit.organization).fetchJoin().where(qUnit.name.eq(name));
		return CQDslDaoHelper.handleUniqueResultQuery(query);
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.IUnitDao#findByUserId(java.lang.Long)
	 */
	@Override
	public List<Unit> findByUserId (Long userId) throws CSecurityException
	{
		if (userId == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QUnit qUnit = QUnit.unit;
		QUserUnitGroup qUserUnitGroup = QUserUnitGroup.userUnitGroup;
		QUser qUser = QUser.user;

		// create query
		JPAQuery<Unit> query = queryFactory.selectFrom(qUnit).distinct().join(qUnit.xUserUnitGroups, qUserUnitGroup).join(qUserUnitGroup.user, qUser).where(qUser.id.eq(userId)).orderBy(qUnit.name.asc());
		return query.fetch();
	}
}
