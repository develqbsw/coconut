/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import sk.qbsw.core.security.dao.IOrganizationDao;
import sk.qbsw.core.security.model.domain.COrganization;

/**
 * The Class CSectionJpaDao.
 *
 * @author rosenberg
 * @version 1.0
 * @since 1.0
 */
@Repository (value = "orgDao")
public class COrganizationJpaDao implements IOrganizationDao
{


	@PersistenceContext (name = "airlinesPersistenceContext")
	private EntityManager em;

	/**
	 * @see sk.qbsw.core.security.dao.IOrganizationDao#persit(sk.qbsw.core.security.model.domain.COrganization)
	 */
	public void persit (COrganization organization)
	{
		this.em.persist(organization);
	}

	/**
	 * Merge.
	 *
	 * @param organization the organization
	 */
	public void merge (COrganization organization)
	{
		this.em.merge(organization);
	}

	/**
	 * @see sk.qbsw.core.security.dao.IGroupDao#findById(java.lang.Long)
	 */
	public COrganization findById (Long id)
	{
		String strQuery = "select o from COrganization o where o.pkId=:pkId";

		Query query = this.em.createQuery(strQuery);
		query.setParameter("pkId", id);
		return (COrganization) query.getSingleResult();
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

		Query query = this.em.createQuery(strQuery);
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

		Query query = this.em.createQuery(strQuery);
		return (List<COrganization>) query.getResultList();
	}

	@SuppressWarnings ("unchecked")
	public COrganization findByNameNull (String name)
	{
		COrganization organization;
		String strQuery = "select o from COrganization o where o.name = :name";

		Query query = this.em.createQuery(strQuery);
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
