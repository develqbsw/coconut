/**
 * 
 */
package sk.qbsw.core.security.dao;

import java.util.List;

import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * The Interface IRoleDao.
 *
 * @author rosenberg
 * @version 1.0
 * @since 1.0
 */
public interface IRoleDao
{

	/**
	 * Make/update persistent entity
	 * @param role entity
	 */
	public void persit (CRole role);

	/**
	 * Find by identifier.
	 *
	 * @param id entity identifier
	 * @return the entity
	 */
	public CRole findById (Long id);

	/**
	 * Find all by user.
	 *
	 * @param user the user
	 * @return the list
	 */
	public List<CRole> findAllByUser (CUser user);
	
	/**
	 * Find by code.
	 *
	 * @param user the user
	 * @return the list
	 */
	public List<CRole> findByCode (String code);
}
