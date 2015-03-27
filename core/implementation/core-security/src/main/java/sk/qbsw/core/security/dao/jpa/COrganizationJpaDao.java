/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao;
import sk.qbsw.core.security.dao.IOrganizationDao;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.QCOrganization;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;

/**
 * The organization jpa dao.
 *
 * @author rosenberg
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.0.0
 */
@Repository (value = "orgDao")
public class COrganizationJpaDao extends AEntityJpaDao<Long, COrganization> implements IOrganizationDao
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new organization jpa dao.
	 */
	public COrganizationJpaDao ()
	{
		super(COrganization.class);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IOrganizationDao#findByName(java.lang.String)
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

		//create query
		JPAQuery query = new JPAQuery(getEntityManager());
		return query.from(qOrganization).where(builder).list(qOrganization);
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

		//create query
		JPAQuery query = new JPAQuery(getEntityManager());
		return query.from(qOrganization).orderBy(qOrganization.name.asc()).list(qOrganization);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IOrganizationDao#findOneByName(java.lang.String)
	 */
	@Deprecated
	@Override
	public COrganization findOneByName (String name)
	{
		QCOrganization qOrganization = QCOrganization.cOrganization;

		//create query
		JPAQuery query = new JPAQuery(getEntityManager());
		List<COrganization> organizations = query.from(qOrganization).where(qOrganization.name.eq(name)).list(qOrganization);

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
