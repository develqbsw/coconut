package sk.qbsw.security.organization.complex.core.dao;

import com.querydsl.jpa.impl.JPAQuery;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.core.persistence.dao.jpa.qdsl.CQDslDaoHelper;
import sk.qbsw.security.organization.complex.core.model.domain.CXOUnit;
import sk.qbsw.security.organization.complex.core.model.domain.QCXOUnit;
import sk.qbsw.security.organization.complex.core.model.domain.QCXOUser;

import java.util.List;

/**
 * The complex organization unit jpa dao.
 *
 * @author Tomas Leken
 * @version 2.0.0
 * @since 2.0.0
 */
public class CXOUnitJpaDao extends AEntityQDslDao<Long, CXOUnit> implements CXOUnitDao
{
	public CXOUnitJpaDao ()
	{
		super(QCXOUnit.cXOUnit, Long.class);
	}

	@Override
	public CXOUnit findOneByCode (String code) throws CSecurityException
	{
		if (code == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QCXOUnit qUnit = QCXOUnit.cXOUnit;

		// create query
		JPAQuery<CXOUnit> query = queryFactory.selectFrom(qUnit).distinct() //
			.leftJoin(qUnit.organization).fetchJoin() //
			.where(qUnit.code.eq(code));

		return CQDslDaoHelper.handleUniqueResultQueryByNull(query);
	}

	@Override
	public List<CXOUnit> findByUserId (Long userId) throws CSecurityException
	{
		if (userId == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QCXOUnit qUnit = QCXOUnit.cXOUnit;
		QCXOUser qUser = QCXOUser.cXOUser;

		// create query
		JPAQuery<CXOUnit> query = queryFactory.selectFrom(qUnit).distinct() //
			.leftJoin(qUnit.users, qUser) //
			.where(qUser.id.eq(userId)) //
			.orderBy(qUnit.name.asc());

		return query.fetch();
	}

	@Override
	public CXOUnit findWithUsers (Long id)
	{
		QCXOUnit qUnit = QCXOUnit.cXOUnit;
		QCXOUser qUser = QCXOUser.cXOUser;

		// create query
		JPAQuery<CXOUnit> query = queryFactory.selectFrom(qUnit).distinct() //
			.leftJoin(qUnit.users, qUser).fetchJoin() //
			.where(qUnit.id.eq(id));

		return CQDslDaoHelper.handleUniqueResultQuery(query);
	}

	@Override
	public List<CXOUnit> findActive ()
	{
		QCXOUnit qUnit = QCXOUnit.cXOUnit;
		QCXOUser qUser = QCXOUser.cXOUser;

		// create query
		JPAQuery<CXOUnit> query = queryFactory.selectFrom(qUnit).distinct() //
			.leftJoin(qUnit.users, qUser).fetchJoin() //
			.where(qUnit.state.eq(ActivityStates.ACTIVE));

		return query.fetch();
	}
}
