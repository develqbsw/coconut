/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;

import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.core.security.dao.ILicenseDao;
import sk.qbsw.core.security.model.domain.CLicense;
import sk.qbsw.core.security.model.domain.QCLicense;

/**
 * The Class CLicenseJpaDao.
 *
 * @author rosenberg
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.0.0
 */

@Repository (value = "licenceDao")
public class CLicenseJpaDao extends AEntityQDslDao<Long, CLicense<?>> implements ILicenseDao
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new c license jpa dao.
	 */
	public CLicenseJpaDao ()
	{
		super(QCLicense.cLicense, Long.class);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.ILicenseDao#findByOrganizationId(java.lang.Long)
	 */
	public List<CLicense<?>> findByOrganizationId (Long orgId)
	{
		QCLicense qLicense = QCLicense.cLicense;

		//create query
		JPAQuery<CLicense<?>> query = queryFactory.selectFrom(qLicense).where(qLicense.organization.id.eq(orgId));
		return query.fetch();
	}

}
