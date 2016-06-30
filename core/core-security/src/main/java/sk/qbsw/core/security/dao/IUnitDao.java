/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.NoResultException;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.core.security.model.domain.CUnit;

/**
 * The Interface IUnitDao.
 *
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.6.0
 */
public interface IUnitDao extends Serializable, IEntityDao<Long, CUnit>
{

	/**
	 * Find by name - if there is no result throws an exception.
	 *
	 * @param name the unit name (mandatory)
	 * @return the unit
	 * 
	 * @throws NoResultException there is no result
	 * @throws CSecurityException throws if the name is null
	 */
	CUnit findOneByName (String name) throws NoResultException, CSecurityException;

	/**
	 * Find all units by user id.
	 *
	 * @param userId the user id (mandatory)
	 * @return the list
	 * @throws CSecurityException throws if the userId is null
	 */
	List<CUnit> findByUserId (Long userId) throws CSecurityException;
}
