/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao;

import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.core.security.model.domain.CLicense;

import java.util.List;

/**
 * The Interface ILicenseDao.
 *
 * @author rosenberg
 * @version 1.0
 * @since 1.0
 */
public interface ILicenseDao extends IEntityDao<Long, CLicense<?>>
{
	/**
	 * Find by organization id.
	 *
	 * @param orgId the org id (mandatory)
	 * @return the list
	 */
	List<CLicense<?>> findByOrganizationId (Long orgId);

}
