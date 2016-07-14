/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.core.persistence.dao.jpa.qdsl.CQDslDaoHelper;
import sk.qbsw.core.security.dao.IUnitDao;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.QCUnit;
import sk.qbsw.core.security.model.domain.QCUser;
import sk.qbsw.core.security.model.domain.QCXUserUnitGroup;

/**
 * The unit jpa dao.
 *
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.6.0
 */
@Repository (value = "unitDao")
public class CUnitJpaDao extends AEntityQDslDao<Long, CUnit> implements IUnitDao
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new unit jpa dao.
	 */
	public CUnitJpaDao ()
	{
		super(QCUnit.cUnit, Long.class);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUnitDao#findOneByName(java.lang.String)
	 */
	@Override
	public CUnit findOneByName (String name) throws NoResultException, CSecurityException
	{
		if (name == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QCUnit qUnit = QCUnit.cUnit;

		//create query
		JPAQuery<CUnit> query = queryFactory.selectFrom(qUnit).distinct().leftJoin(qUnit.groups).fetchJoin().leftJoin(qUnit.organization).fetchJoin().where(qUnit.name.eq(name));
		return CQDslDaoHelper.handleUniqueResultQuery(query);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUnitDao#findByUserId(java.lang.Long)
	 */
	@Override
	public List<CUnit> findByUserId (Long userId) throws CSecurityException
	{
		if (userId == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QCUnit qUnit = QCUnit.cUnit;
		QCXUserUnitGroup qUserUnitGroup = QCXUserUnitGroup.cXUserUnitGroup;
		QCUser qUser = QCUser.cUser;

		//create query
		JPAQuery<CUnit> query = queryFactory.selectFrom(qUnit).distinct().join(qUnit.xUserUnitGroups, qUserUnitGroup).join(qUserUnitGroup.user, qUser).where(qUser.id.eq(userId)).orderBy(qUnit.name.asc());
		return query.fetch();
	}
}
