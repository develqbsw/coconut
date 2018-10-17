package sk.qbsw.security.organization.complex.management.service;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.security.organization.complex.core.model.domain.CXOOrganization;
import sk.qbsw.security.organization.complex.core.model.domain.CXOUnit;
import sk.qbsw.security.organization.complex.core.model.domain.CXOUser;

import java.util.List;

/**
 * The complex organization unit service.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public interface CXOUnitService
{
	/**
	 * Create unit.
	 *
	 * @param code the code
	 * @param name the name
	 * @param organization the organization
	 * @param state the state
	 * @return the unit
	 */
	CXOUnit create (String code, String name, CXOOrganization organization, ActivityStates state);

	/**
	 * Read unit.
	 *
	 * @param id the id
	 * @return the unit
	 */
	CXOUnit read (Long id);

	/**
	 * Find all list.
	 *
	 * @return the list
	 */
	List<CXOUnit> findAll ();

	/**
	 * Find by user list.
	 *
	 * @param user the user
	 * @return the list
	 * @throws CSecurityException the c security exception
	 */
	List<CXOUnit> findByUser (CXOUser user) throws CSecurityException;

	/**
	 * Update unit.
	 *
	 * @param unit the unit
	 * @return the unit
	 */
	CXOUnit update (CXOUnit unit);

	/**
	 * Activate.
	 *
	 * @param id the id
	 */
	void activate (Long id);

	/**
	 * Inactivate.
	 *
	 * @param id the id
	 */
	void inactivate (Long id);

	/**
	 * Add user.
	 *
	 * @param id the id
	 * @param userId the user id
	 */
	void addUser (Long id, Long userId);

	/**
	 * Remove user.
	 *
	 * @param id the id
	 * @param userId the user id
	 */
	void removeUser (Long id, Long userId);
}
