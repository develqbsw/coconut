/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao;

import java.io.Serializable;

import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.core.security.model.domain.COrganization;

/**
 * The Interface IOrganizationDao.
 *
 * @author rosenberg
 * @version 1.0
 * @since 1.0
 */
public interface IOrganizationDao extends Serializable, IEntityDao<COrganization>
{

	/**
	 * Find by name.
	 *
	 * @param name the organization name
	 * @return the organization
	 */
	public COrganization findByName (String name);

	/**
	 * Find by name and return NULL if organization not exist - NOT exeption.
	 *
	 * @param name the name
	 * @return organization or null if organization not exist
	 */
	public COrganization findByNameNull (String name);
}
