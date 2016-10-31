package sk.qbsw.core.logging.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import sk.qbsw.core.logging.dao.IInstallationLogDao;
import sk.qbsw.core.logging.model.domain.CInstallationLog;
import sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao;

/**
 * The installation log jpa dao.
 *
 * @author Tomas Lauro
 * @version 1.9.0
 * @since 1.9.0
 */
@Repository (value = "jpaInstallationLogDao")
public class CInstallationLogJpaDao extends AEntityJpaDao<Long, CInstallationLog> implements IInstallationLogDao
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new installation log jpa dao.
	 */
	public CInstallationLogJpaDao ()
	{
		super(CInstallationLog.class);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.logging.dao.IInstallationLogDao#findByVersion(java.lang.String)
	 */
	@Override
	public CInstallationLog findByVersion (String version)
	{
		String strQuery = "select il from CInstallationLog il where il.version=:version";

		Query query = getEntityManager().createQuery(strQuery);
		query.setParameter("version", version);

		return (CInstallationLog) query.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.logging.dao.IInstallationLogDao#findAll(java.util.List)
	 */
	@SuppressWarnings ("unchecked")
	@Override
	public List<CInstallationLog> findAll (List<String> versions)
	{
		String strQuery = "select il from CInstallationLog il where il.version in :versions";

		Query query = getEntityManager().createQuery(strQuery);
		query.setParameter("versions", versions);

		return (List<CInstallationLog>) query.getResultList();
	}
}
