package sk.qbsw.organization.complex.management.service;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.organization.complex.core.model.domain.Organization;
import sk.qbsw.organization.complex.core.model.domain.Unit;
import sk.qbsw.organization.complex.core.model.domain.User;

import java.util.List;

/**
 * The interface Unit service.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public interface UnitService
{
	/**
	 * Create unit.
	 *
	 * @param code         the code
	 * @param name         the name
	 * @param organization the organization
	 * @param state        the state
	 * @return the unit
	 */
	Unit create (String code, String name, Organization organization, ActivityStates state);

	/**
	 * Read unit.
	 *
	 * @param id the id
	 * @return the unit
	 */
	Unit read (Long id);

	/**
	 * Find all list.
	 *
	 * @return the list
	 */
	List<Unit> findAll ();

	/**
	 * Find by user list.
	 *
	 * @param user the user
	 * @return the list
	 * @throws CSecurityException the c security exception
	 */
	List<Unit> findByUser (User user) throws CSecurityException;

	/**
	 * Update unit.
	 *
	 * @param unit the unit
	 * @return the unit
	 */
	Unit update (Unit unit);

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
	 * @param id     the id
	 * @param userId the user id
	 */
	void addUser (Long id, Long userId);

	/**
	 * Remove user.
	 *
	 * @param id     the id
	 * @param userId the user id
	 */
	void removeUser (Long id, Long userId);
}
