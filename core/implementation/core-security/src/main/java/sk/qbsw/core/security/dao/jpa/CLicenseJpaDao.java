/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao.jpa;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao;
import sk.qbsw.core.security.dao.ILicenseDao;
import sk.qbsw.core.security.model.domain.CLicense;

/**
 * The Class CLicenseJpaDao.
 *
 * @author rosenberg
 * @version 1.0
 * @since 1.0
 */

@Repository (value = "licenceDao")
public class CLicenseJpaDao extends AEntityJpaDao<CLicense> implements ILicenseDao
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	

	/**
	 * Instantiates a new c license jpa dao.
	 */
	public CLicenseJpaDao ()
	{
		super(CLicense.class);
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

		Query query = getEntityManager().createQuery(strQuery);
		query.setParameter("id", orgId);
		return (List<CLicense<?>>) query.getResultList();
	}

}
