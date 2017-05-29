/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.security.core.dao.jpa;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.security.core.dao.IOrganizationDao;
import sk.qbsw.security.core.model.domain.COrganization;
import sk.qbsw.security.core.model.domain.QCOrganization;

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
public class COrganizationJpaDao extends AEntityQDslDao<Long, COrganization> implements IOrganizationDao
{
	/**
	 * Instantiates a new organization jpa dao.
	 */
	public COrganizationJpaDao ()
	{
		super(QCOrganization.cOrganization, Long.class);
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.IOrganizationDao#findByName(java.lang.String)
	 */
	@Override
	public List<COrganization> findByName (String name)
	{
		QCOrganization qOrganization = QCOrganization.cOrganization;

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
		JPAQuery<COrganization> query = queryFactory.selectFrom(qOrganization).where(builder);
		return query.fetch();
	}

	/**
	 * Get all organizations ordered by name.
	 *
	 * @return the list of organizations
	 * @see sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao#findAll()
	 */
	@Override
	public List<COrganization> findAll ()
	{
		QCOrganization qOrganization = QCOrganization.cOrganization;

		// create query
		JPAQuery<COrganization> query = queryFactory.selectFrom(qOrganization).orderBy(qOrganization.name.asc());
		return query.fetch();
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.IOrganizationDao#findOneByName(java.lang.String)
	 */
	@Deprecated
	@Override
	public COrganization findOneByName (String name)
	{
		QCOrganization qOrganization = QCOrganization.cOrganization;

		// create query
		JPAQuery<COrganization> query = queryFactory.selectFrom(qOrganization).where(qOrganization.name.eq(name));
		List<COrganization> organizations = query.fetch();

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
