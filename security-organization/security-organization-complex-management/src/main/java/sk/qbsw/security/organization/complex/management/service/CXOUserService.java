package sk.qbsw.security.organization.complex.management.service;

import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.security.organization.complex.core.model.domain.CXOUser;

import java.util.List;

/**
 * The complex organization user service.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public interface CXOUserService
{
	/**
	 * Create user.
	 *
	 * @param state the state
	 * @return the user
	 */
	CXOUser create (ActivityStates state);

	/**
	 * Read user.
	 *
	 * @param id the id
	 * @return the user
	 */
	CXOUser read (Long id);

	/**
	 * Find all list.
	 *
	 * @return the list
	 */
	List<CXOUser> findAll ();

	/**
	 * Update user.
	 *
	 * @param user the user
	 * @return the user
	 */
	CXOUser update (CXOUser user);

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
}
