package sk.qbsw.organization.complex.core.dao;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.organization.complex.core.model.domain.Unit;

import java.util.List;

/**
 * The interface Unit dao.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public interface UnitDao extends IEntityDao<Long, Unit>
{

	/**
	 * Find one by code unit.
	 *
	 * @param code the code
	 * @return the unit
	 * @throws CSecurityException the c security exception
	 */
	Unit findOneByCode (String code) throws CSecurityException;

	/**
	 * Find by user id list.
	 *
	 * @param userId the user id
	 * @return the list
	 * @throws CSecurityException the c security exception
	 */
	List<Unit> findByUserId (Long userId) throws CSecurityException;

	/**
	 * Find with users unit.
	 *
	 * @param id the id
	 * @return the unit
	 */
	Unit findWithUsers (Long id);
}
