/**
 * 
 */
package sk.qbsw.core.security.dao;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.security.model.domain.CGroup;

/**
 * The Interface IGroupDao.
 *
 * @author rosenberg
 * @author lacko
 * @version 1.0
 * @since 1.0
 */
public interface IGroupDao extends Serializable
{

	/** The I d_ or g_ admin. */
	public static Long ID_ORG_ADMIN = 2l;

	/** The I d_ sy s_ admin. */
	public static Long ID_SYS_ADMIN = 1l;

	/**
	 * Make/update persistent enity
	 * @param group entity
	 */
	public void persit(CGroup group);
	
	/**
	 * Find by identifier.
	 *
	 * @param id entity identifier
	 * @return the entity
	 */
	public CGroup findById (Long id);	
	
	/**
	 * Find all by flag system.
	 *
	 * @param flagSystem the flag system
	 * @return the list
	 */
	public List<CGroup> findAllByFlagSystem (boolean flagSystem);
	
	/**
	 * Find all
	 *
	 * @return the list
	 */
	public List<CGroup> findAll();
	
	/**
	 * Find groups by code
	 *
	 * @return the list
	 */
	public List<CGroup> findByCode(String code);
}
