package sk.qbsw.security.core.dao;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.security.core.model.domain.Role;
import sk.qbsw.security.core.model.domain.Account;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.List;
import java.util.Set;

/**
 * The role dao.
 *
 * @author rosenberg
 * @author Tomas Lauro
 * @author Michal SLezák
 * @version 2.5.0
 * @since 1.0.0
 */
public interface RoleDao extends IEntityDao<Long, Role>
{
	/**
	 * Find by account list.
	 *
	 * @param account the user
	 * @return the list
	 * @throws CSecurityException the c security exception
	 */
	List<Role> findByAccount (Account account) throws CSecurityException;

	/**
	 * Find one by code role.
	 *
	 * @param code the code
	 * @return the role
	 * @throws NonUniqueResultException the non unique result exception
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	Role findOneByCode (String code) throws NonUniqueResultException, NoResultException, CSecurityException;

	/**
	 * Find all by id in.
	 *
	 * @param roleIds the role id-s
	 * @return the list
	 */
	List<Role> findAllByIdIn(Set<Long> roleIds);
}
