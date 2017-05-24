/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.security.dao;

import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.security.model.domain.COrganization;

import java.util.List;

/**
 * The Interface IOrganizationDao.
 *
 * @author rosenberg
 * @author Tomas Lauro
 * @version 1.13.0
 * @since 1.0.0
 */
public interface IOrganizationDao extends IEntityDao<Long, COrganization>
{
	/**
	 * Find all organizations by name.
	 *
	 * @param name the name (optional)
	 * @return the list of organizations - if there is no result returns empty list
	 */
	List<COrganization> findByName (String name);

	/**
	 * Find by name and return NULL if organization not exist - NOT exception.
	 *
	 * @param name the name (mandatory)
	 * @return organization or null if organization not exist
	 * @deprecated the organization name is no longer unique
	 */
	COrganization findOneByName (String name);
}
