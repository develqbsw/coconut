/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.security.core.dao.jpa;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.security.core.dao.OrganizationDao;
import sk.qbsw.security.core.model.domain.Organization;
import sk.qbsw.security.core.model.domain.QOrganization;

import java.util.List;

/**
 * The organization jpa dao.
 *
 * @author rosenberg
 * @author Tomas Lauro
 * @version 1.16.0
 * @since 1.0.0
 */
@Repository (value = "orgDao")
public class OrganizationJpaDao extends AEntityQDslDao<Long, Organization> implements OrganizationDao
{
	/**
	 * Instantiates a new organization jpa dao.
	 */
	public OrganizationJpaDao ()
	{
		super(QOrganization.organization, Long.class);
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.IOrganizationDao#findByName(java.lang.String)
	 */
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

	/**
	 * Get all organizations ordered by name.
	 *
	 * @return the list of organizations
	 * @see sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao#findAll()
	 */
	@Override
	public List<Organization> findAll ()
	{
		QOrganization qOrganization = QOrganization.organization;

		// create query
		JPAQuery<Organization> query = queryFactory.selectFrom(qOrganization).orderBy(qOrganization.name.asc());
		return query.fetch();
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.IOrganizationDao#findOneByName(java.lang.String)
	 */
	@Deprecated
	@Override
	public Organization findOneByName (String name)
	{
		QOrganization qOrganization = QOrganization.organization;

		// create query
		JPAQuery<Organization> query = queryFactory.selectFrom(qOrganization).where(qOrganization.name.eq(name));
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