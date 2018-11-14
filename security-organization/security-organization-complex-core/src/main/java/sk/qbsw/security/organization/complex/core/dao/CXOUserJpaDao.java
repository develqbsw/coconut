package sk.qbsw.security.organization.complex.core.dao;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.security.organization.complex.core.model.domain.CXOUser;
import sk.qbsw.security.organization.complex.core.model.domain.QCXOUnit;
import sk.qbsw.security.organization.complex.core.model.domain.QCXOUser;

import java.util.List;

/**
 * The complex organization user jpa dao.
 *
 * @author Tomas Leken
 * @version 2.0.0
 * @since 2.0.0
 */
public class CXOUserJpaDao extends AEntityQDslDao<Long, CXOUser> implements CXOUserDao
{
	/**
	 * Instantiates a new User jpa dao.
	 */
	public CXOUserJpaDao ()
	{
		super(QCXOUser.cXOUser, Long.class);
	}

	@Override
	public List<CXOUser> findByUnit (String unitCode) throws CSecurityException
	{
		if (unitCode == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QCXOUser qUser = QCXOUser.cXOUser;
		QCXOUnit qUnit = QCXOUnit.cXOUnit;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qUnit.code.eq(unitCode));

		// create query
		JPAQuery<CXOUser> query = queryFactory.selectFrom(qUser).distinct() //
			.leftJoin(qUser.units, qUnit).fetchJoin() //
			.leftJoin(qUser.accounts).fetchJoin() //
			.where(builder);

		return query.fetch();
	}
}
