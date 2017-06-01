/**
 * 
 */
package sk.qbsw.security.core.dao;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.security.core.model.domain.Role;
import sk.qbsw.security.core.model.domain.User;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.List;

/**
 * The Interface RoleDao.
 *
 * @author rosenberg
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.0.0
 */
public interface RoleDao extends IEntityDao<Long, Role>
{
	
	/**
	 * Find all by user.
	 *
	 * @param user the user (mandatory)
	 * @return the list
	 * @throws CSecurityException throws if the user is null
	 */
	List<Role> findByUser (User user) throws CSecurityException;

	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the list
	 * 
	 * @deprecated the code is unique
	 */
	List<Role> findByCode (String code);

	/**
	 * Find one by code - if there is no result or no unique result throws an exception.
	 *
	 * @param code the code (mandatory)
	 * @return the role
	 * @throws NonUniqueResultException there is no unique result
	 * @throws NoResultException there is no result
	 * @throws CSecurityException The code is null
	 */
	Role findOneByCode (String code) throws NonUniqueResultException, NoResultException, CSecurityException;
}
