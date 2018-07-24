package sk.qbsw.organization.complex.management.service;

import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.organization.complex.core.model.domain.User;

import java.util.List;

/**
 * The interface User service.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public interface UserService
{
	/**
	 * Create user.
	 *
	 * @param state the state
	 * @return the user
	 */
	User create (ActivityStates state);

	/**
	 * Read user.
	 *
	 * @param id the id
	 * @return the user
	 */
	User read (Long id);

	/**
	 * Find all list.
	 *
	 * @return the list
	 */
	List<User> findAll ();

	/**
	 * Update user.
	 *
	 * @param user the user
	 * @return the user
	 */
	User update (User user);

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
