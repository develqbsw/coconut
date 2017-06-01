/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.security.core.dao;

import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.security.core.model.domain.Organization;

import java.util.List;

/**
 * The Interface OrganizationDao.
 *
 * @author rosenberg
 * @author Tomas Lauro
 * @version 1.13.0
 * @since 1.0.0
 */
public interface OrganizationDao extends IEntityDao<Long, Organization>
{
	/**
	 * Find all organizations by name.
	 *
	 * @param name the name (optional)
	 * @return the list of organizations - if there is no result returns empty list
	 */
	List<Organization> findByName (String name);

	/**
	 * Find by name and return NULL if organization not exist - NOT exception.
	 *
	 * @param name the name (mandatory)
	 * @return organization or null if organization not exist
	 * @deprecated the organization name is no longer unique
	 */
	Organization findOneByName (String name);
}
