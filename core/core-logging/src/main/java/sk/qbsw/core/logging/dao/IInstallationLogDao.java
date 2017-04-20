package sk.qbsw.core.logging.dao;

import sk.qbsw.core.logging.model.domain.CInstallationLog;
import sk.qbsw.core.persistence.dao.IEntityDao;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * The installation log dao.
 *
 * @author Tomas Lauro
 * @version 1.9.0
 * @since 1.9.0
 */
public interface IInstallationLogDao extends IEntityDao<Long, CInstallationLog>
{
	/**
	 * Find installation log by version.
	 *
	 * @param version the version
	 * @return the installation log
	 * @throws NoResultException when no log was found
	 */
	public CInstallationLog findByVersion (String version);

	/**
	 * Find installation logs by version.
	 *
	 * @param versions the versions
	 * @return the list of installation logs.
	 */
	public List<CInstallationLog> findAll (List<String> versions);
}
