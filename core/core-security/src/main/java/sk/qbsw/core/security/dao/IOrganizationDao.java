/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.core.security.model.domain.COrganization;

/**
 * The Interface IOrganizationDao.
 *
 * @author rosenberg
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.0.0
 */
public interface IOrganizationDao extends Serializable, IEntityDao<Long, COrganization>
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
	 * 
	 * @deprecated the organization name is no longer unique
	 */
	COrganization findOneByName (String name);
}
