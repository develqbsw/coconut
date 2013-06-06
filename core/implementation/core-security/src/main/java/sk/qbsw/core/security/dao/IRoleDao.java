/**
 * 
 */
package sk.qbsw.core.security.dao;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * The Interface IRoleDao.
 *
 * @author rosenberg
 * @version 1.0
 * @since 1.0
 */
public interface IRoleDao extends Serializable, IEntityDao<CRole>
{

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
