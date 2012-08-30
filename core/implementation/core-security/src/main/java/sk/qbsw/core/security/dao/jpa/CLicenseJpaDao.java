/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import sk.qbsw.core.security.dao.ILicenseDao;
import sk.qbsw.core.security.model.domain.CLicense;

/**
 * The Class CLicenseJpaDao.
 *
 * @author rosenberg
 * @version 1.0
 * @since 1.0
 */

@Repository  (value = "licenceDao")
public class CLicenseJpaDao implements ILicenseDao
{


	@PersistenceContext(name="airlinesPersistenceContext")
	private EntityManager em;

	/**
	 * @see sk.qbsw.core.security.dao.ILicenseDao#persit(sk.qbsw.core.security.model.domain.CLicense)
	 */
	public void persit (CLicense<?> license)
	{
		this.em.persist(license);
	}

	/**
	 * @see sk.qbsw.core.security.dao.ILicenseDao#findById(java.lang.Long)
	 **/
	public CLicense<?> findById (Long id)
	{
		String strQuery = "select u from CLicence l where l.pkId=:pkId";

		Query query = this.em.createQuery(strQuery);
		query.setParameter("pkId", id);
		return (CLicense<?>) query.getSingleResult();
	}

	/**
	 * Delete object.
	 *
	 * @param license the license
	 * @see sk.qbsw.core.security.dao.ILicenseDao#delete(sk.qbsw.core.security.model.domain.CLicense)
	 */
	public void delete (CLicense<?> license)
	{
		this.em.remove(license);
	}

	/**
	 * Find by organization id.
	 *
	 * @param orgId the org id
	 * @return the list
	 * @see sk.qbsw.core.security.dao.ILicenseDao#findByOrganizationId(java.lang.Long)
	 */
	@SuppressWarnings ("unchecked")
	public List<CLicense<?>> findByOrganizationId (Long orgId)
	{
		String strQuery = "from CLicense where organization.pkId = :id";

		Query query = this.em.createQuery(strQuery);
		query.setParameter("id", orgId);
		return (List<CLicense<?>>) query.getResultList();
	}

}
