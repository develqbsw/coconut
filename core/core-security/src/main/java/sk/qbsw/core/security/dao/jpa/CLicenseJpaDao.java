/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao;
import sk.qbsw.core.security.dao.ILicenseDao;
import sk.qbsw.core.security.model.domain.CLicense;
import sk.qbsw.core.security.model.domain.QCLicense;

import com.mysema.query.jpa.impl.JPAQuery;

/**
 * The Class CLicenseJpaDao.
 *
 * @author rosenberg
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.0.0
 */

@Repository (value = "licenceDao")
public class CLicenseJpaDao extends AEntityJpaDao<Long, CLicense<?>> implements ILicenseDao
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new c license jpa dao.
	 */
	@SuppressWarnings ({"unchecked", "rawtypes"})
	public CLicenseJpaDao ()
	{
		super((Class) CLicense.class);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.ILicenseDao#findByOrganizationId(java.lang.Long)
	 */
	public List<CLicense<?>> findByOrganizationId (Long orgId)
	{
		QCLicense qLicense = QCLicense.cLicense;

		//create query
		JPAQuery query = new JPAQuery(getEntityManager());
		return query.from(qLicense).where(qLicense.organization.id.eq(orgId)).list(qLicense);
	}

}
