package sk.qbsw.security.organization.complex.core.dao;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.core.persistence.dao.jpa.qdsl.CQDslDaoHelper;
import sk.qbsw.security.organization.complex.core.model.domain.CXOOrganization;
import sk.qbsw.security.organization.complex.core.model.domain.QCXOOrganization;

import java.util.List;

/**
 * The complex organization jpa dao.
 *
 * @author rosenberg
 * @author Tomas Lauro
 * @author Tomas Leken
 * @version 2.0.0
 * @since 1.0.0
 */
public class CXOOrganizationJpaDao extends AEntityQDslDao<Long, CXOOrganization> implements CXOOrganizationDao
{
	/**
	 * Instantiates a new Organization jpa dao.
	 */
	public CXOOrganizationJpaDao ()
	{
		super(QCXOOrganization.cXOOrganization, Long.class);
	}

	@Override
	public List<CXOOrganization> findByName (String name)
	{
		QCXOOrganization qOrganization = QCXOOrganization.cXOOrganization;

		BooleanBuilder builder = new BooleanBuilder();
		if (name != null)
		{
			builder.and(qOrganization.name.eq(name));
		}
		else
		{
			builder.and(qOrganization.name.isNull());
		}

		// create query
		JPAQuery<CXOOrganization> query = queryFactory.selectFrom(qOrganization).where(builder);
		return query.fetch();
	}

	@Override
	public List<CXOOrganization> findAll ()
	{
		QCXOOrganization qOrganization = QCXOOrganization.cXOOrganization;

		// create query
		JPAQuery<CXOOrganization> query = queryFactory.selectFrom(qOrganization) //
			.distinct() //
			.leftJoin(qOrganization.units).fetchJoin() //
			.orderBy(qOrganization.name.asc());
		return query.fetch();
	}

	@Override
	public CXOOrganization findOneByCode (String code)
	{
		QCXOOrganization qOrganization = QCXOOrganization.cXOOrganization;

		// create query
		JPAQuery<CXOOrganization> query = queryFactory.selectFrom(qOrganization) //
			.where(qOrganization.code.eq(code));
		return CQDslDaoHelper.handleUniqueResultQueryByNull(query);
	}
}
