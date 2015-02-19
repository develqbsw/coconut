/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao.jpa;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

import sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao;
import sk.qbsw.core.security.dao.IOrganizationDao;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.QCGroup;
import sk.qbsw.core.security.model.domain.QCOrganization;

/**
 * The organization jpa dao.
 *
 * @author rosenberg
 * @author Tomas Lauro
 * 
 * @version 1.11.5
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
		
		//create query
		JPAQuery query = new JPAQuery(getEntityManager());
		return query.from(qOrganization).where(qOrganization.name.eq(name)).list(qOrganization);
	}

	/**
	 * Get all organizations order by name.
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
	 * @see sk.qbsw.core.security.dao.IOrganizationDao#findByNameNull(java.lang.String)
	 */
	@Override
	@SuppressWarnings ("unchecked")
	public COrganization findByNameNull (String name)
	{
		COrganization organization;
		String strQuery = "select o from COrganization o where o.name = :name";

		Query query = getEntityManager().createQuery(strQuery);
		query.setParameter("name", name);

		List<COrganization> organizations = query.getResultList();

		if (organizations.isEmpty())
		{
			organization = null;
		}
		else
		{
			organization = organizations.get(0);
		}

		return organization;
	}
}
