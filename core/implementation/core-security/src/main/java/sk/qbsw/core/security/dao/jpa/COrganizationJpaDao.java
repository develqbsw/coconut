/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao.jpa;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao;
import sk.qbsw.core.security.dao.IOrganizationDao;
import sk.qbsw.core.security.model.domain.COrganization;

/**
 * The Class CSectionJpaDao.
 *
 * @author rosenberg
 * @version 1.2.1
 * @since 1.0.0
 */
@Repository (value = "orgDao")
public class COrganizationJpaDao extends AEntityJpaDao<COrganization> implements IOrganizationDao
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new c organization jpa dao.
	 */
	public COrganizationJpaDao ()
	{
		super(COrganization.class);
	}

	/**
	 * Find by name.
	 *
	 * @param name the name
	 * @return the c organization
	 * @see sk.qbsw.core.security.dao.IOrganizationDao#findByName(java.lang.String)
	 */
	public COrganization findByName (String name)
	{
		String strQuery = "from COrganization where name = :name";

		Query query = getEntityManager().createQuery(strQuery);
		query.setParameter("name", name);
		return (COrganization) query.getSingleResult();
	}

	/**
	 * Find all.
	 *
	 * @return the list
	 * @see sk.qbsw.core.security.dao.IOrganizationDao#findAll()
	 */
	@SuppressWarnings ("unchecked")
	public List<COrganization> findAll ()
	{
		String strQuery = "from COrganization order by name";

		Query query = getEntityManager().createQuery(strQuery);
		return (List<COrganization>) query.getResultList();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IOrganizationDao#findByNameNull(java.lang.String)
	 */
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
