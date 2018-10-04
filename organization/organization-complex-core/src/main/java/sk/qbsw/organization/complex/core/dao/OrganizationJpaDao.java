package sk.qbsw.organization.complex.core.dao;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.organization.complex.core.model.domain.Organization;
import sk.qbsw.organization.complex.core.model.domain.QOrganization;

import java.util.List;

/**
 * The organization jpa dao.
 *
 * @author rosenberg
 * @author Tomas Lauro
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.0.0
 */
public class OrganizationJpaDao extends AEntityQDslDao<Long, Organization> implements OrganizationDao
{
	/**
	 * Instantiates a new Organization jpa dao.
	 */
	public OrganizationJpaDao ()
	{
		super(QOrganization.organization, Long.class);
	}

	@Override
	public List<Organization> findByName (String name)
	{
		QOrganization qOrganization = QOrganization.organization;

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
		JPAQuery<Organization> query = queryFactory.selectFrom(qOrganization).where(builder);
		return query.fetch();
	}

	@Override
	public List<Organization> findAll ()
	{
		QOrganization qOrganization = QOrganization.organization;

		// create query
		JPAQuery<Organization> query = queryFactory.selectFrom(qOrganization).distinct().leftJoin(qOrganization.units).fetchJoin().orderBy(qOrganization.name.asc());
		return query.fetch();
	}

	@Override
	public Organization findOneByCode (String code)
	{
		QOrganization qOrganization = QOrganization.organization;

		// create query
		JPAQuery<Organization> query = queryFactory.selectFrom(qOrganization).where(qOrganization.code.eq(code));
		List<Organization> organizations = query.fetch();

		if (organizations.isEmpty())
		{
			return null;
		}
		else
		{
			return organizations.get(0);
		}
	}
}
