package sk.qbsw.security.organization.complex.core.dao;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.security.organization.complex.core.model.domain.CXOUnit;

import java.util.List;

/**
 * The complex organization unit dao.
 *
 * @author Tomas Leken
 * @version 2.0.0
 * @since 2.0.0
 */
public interface CXOUnitDao extends IEntityDao<Long, CXOUnit>
{

	/**
	 * Find one by code unit.
	 *
	 * @param code the code
	 * @return the unit
	 * @throws CSecurityException the c security exception
	 */
	CXOUnit findOneByCode (String code) throws CSecurityException;

	/**
	 * Find by user id list.InheritanceType.SINGLE_TABLE
	 *
	 * @param userId the user id
	 * @return the list
	 * @throws CSecurityException the c security exception
	 */
	List<CXOUnit> findByUserId (Long userId) throws CSecurityException;

	/**
	 * Find with users unit.
	 *
	 * @param id the id
	 * @return the unit
	 */
	CXOUnit findWithUsers (Long id);

	/**
	 * Find active list.
	 *
	 * @return the list
	 */
	List<CXOUnit> findActive ();
}
