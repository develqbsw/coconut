/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.security.core.dao.jpa;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.security.core.dao.LicenseDao;
import sk.qbsw.security.core.model.domain.CLicense;
import sk.qbsw.security.core.model.domain.QCLicense;

import java.util.List;

/**
 * The Class LicenseJpaDao.
 *
 * @author rosenberg
 * @author Tomas Lauro
 * @version 1.16.0
 * @since 1.0.0
 */

@Repository (value = "licenceDao")
public class LicenseJpaDao extends AEntityQDslDao<Long, CLicense<?>> implements LicenseDao
{
	/**
	 * Instantiates a new c license jpa dao.
	 */
	public LicenseJpaDao ()
	{
		super(QCLicense.cLicense, Long.class);
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.ILicenseDao#findByOrganizationId(java.lang.Long)
	 */
	public List<CLicense<?>> findByOrganizationId (Long orgId)
	{
		QCLicense qLicense = QCLicense.cLicense;

		// create query
		JPAQuery<CLicense<?>> query = queryFactory.selectFrom(qLicense).where(qLicense.organization.id.eq(orgId));
		return query.fetch();
	}

}
