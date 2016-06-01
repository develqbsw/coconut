package sk.qbsw.core.logging.service.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.logging.dao.IInstallationLogDao;
import sk.qbsw.core.logging.model.domain.CInstallationLog;
import sk.qbsw.core.logging.service.IInstallationLogService;

/**
 * The installation log service.
 *
 * @author Tomas Lauro
 * 
 * @version 1.9.0
 * @since 1.9.0
 */
@Service
public class CInstallationLogService implements IInstallationLogService
{
	/** The installation log dao. */
	@Autowired
	@Qualifier ("jpaInstallationLogDao")
	private IInstallationLogDao installationLogDao;

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CInstallationLogService.class);


	/* (non-Javadoc)
	 * @see sk.qbsw.core.logging.service.IInstallationLogService#checkVersion(java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public boolean checkVersion (String version)
	{
		try
		{
			if (installationLogDao.findByVersion(version) != null)
			{
				return true;
			}
		}
		catch (NoResultException e)
		{
			LOGGER.error("Version not found", e);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.logging.service.IInstallationLogService#checkVersion(java.util.List)
	 */
	@Override
	@Transactional (readOnly = true)
	public boolean checkVersionList (List<String> versions)
	{
		//checks if the list is null or empty
		if (versions == null || versions.isEmpty())
		{
			throw new IllegalArgumentException("The versions list is empty");
		}

		List<CInstallationLog> logs = installationLogDao.findAll(versions);

		if (logs != null && logs.size() == versions.size())
		{
			return true;
		}
		return false;
	}
}
