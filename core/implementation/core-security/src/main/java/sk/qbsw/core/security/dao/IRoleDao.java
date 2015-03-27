/**
 * 
 */
package sk.qbsw.core.security.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * The Interface IRoleDao.
 *
 * @author rosenberg
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.0.0
 */
public interface IRoleDao extends Serializable, IEntityDao<Long, CRole>
{
	
	/**
	 * Find all by user.
	 *
	 * @param user the user (mandatory)
	 * @return the list
	 * @throws CSecurityException throws if the user is null
	 */
	List<CRole> findByUser (CUser user) throws CSecurityException;

	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the list
	 * 
	 * @deprecated the code is unique
	 */
	List<CRole> findByCode (String code);

	/**
	 * Find one by code - if there is no result or no unique result throws an exception.
	 *
	 * @param code the code (mandatory)
	 * @return the role
	 * @throws NonUniqueResultException there is no unique result
	 * @throws NoResultException there is no result
	 * @throws CSecurityException The code is null
	 */
	CRole findOneByCode (String code) throws NonUniqueResultException, NoResultException, CSecurityException;
}
