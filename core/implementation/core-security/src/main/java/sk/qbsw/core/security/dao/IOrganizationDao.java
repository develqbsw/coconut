/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao;

import java.util.List;

import sk.qbsw.core.security.model.domain.COrganization;

/**
 * The Interface IOrganizationDao.
 *
 * @author rosenberg
 * @version 1.0
 * @since 1.0
 */
public interface IOrganizationDao
{

	/**
	 * Make/update persistent object
	 * @param organization entity
	 */
	public void persit (COrganization organization);

	/**
	 * Find by identifier.
	 *
	 * @param id entity identifier
	 * @return the entity
	 */
	public COrganization findById (Long id);


	/**
	 * Find by name.
	 *
	 * @param name the organization name
	 * @return the organization
	 */
	public COrganization findByName (String name);

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<COrganization> findAll ();

	/**
	 * Find by name and return NULL if organization not exist - NOT exeption.
	 *
	 * @param name the name
	 * @return organization or null if organization not exist
	 */
	public COrganization findByNameNull (String name);
}
