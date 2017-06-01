/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.security.core.dao;

import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.security.core.model.domain.License;

import java.util.List;

/**
 * The Interface LicenseDao.
 *
 * @author rosenberg
 * @version 1.0
 * @since 1.0
 */
public interface LicenseDao extends IEntityDao<Long, License<?>>
{
	/**
	 * Find by organization id.
	 *
	 * @param orgId the org id (mandatory)
	 * @return the list
	 */
	List<License<?>> findByOrganizationId (Long orgId);

}
