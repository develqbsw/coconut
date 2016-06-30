/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.core.security.model.domain.CLicense;

/**
 * The Interface ILicenseDao.
 *
 * @author rosenberg
 * @version 1.0
 * @since 1.0
 */
public interface ILicenseDao extends Serializable, IEntityDao<Long, CLicense<?>>
{
	/**
	 * Find by organization id.
	 *
	 * @param orgId the org id (mandatory)
	 * @return the list
	 */
	List<CLicense<?>> findByOrganizationId (Long orgId);

}
