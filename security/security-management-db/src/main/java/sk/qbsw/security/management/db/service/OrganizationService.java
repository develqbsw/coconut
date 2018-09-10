package sk.qbsw.security.management.db.service;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.Organization;

import java.util.List;

/**
 * The organization service.
 *
 * @author Tomas Leken
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.0.0
 */
public interface OrganizationService
{
	/**
	 * Create organization.
	 *
	 * @param code the code
	 * @param name the name
	 * @param email the email
	 * @param state the state
	 * @return the organization
	 */
	Organization create (String code, String name, String email, ActivityStates state);

	/**
	 * Register organization.
	 *
	 * @param code the code
	 * @param name the name
	 * @param email the email
	 * @param account the account
	 * @param group the group
	 * @return the organization
	 * @throws CBusinessException the c business exception
	 */
	Organization register (String code, String name, String email, Account account, String group) throws CBusinessException;

	/**
	 * Read organization.
	 *
	 * @param id the id
	 * @return the organization
	 */
	Organization read (Long id);

	/**
	 * Find by name list.
	 *
	 * @param name the name
	 * @return the list
	 */
	List<Organization> findByName (String name);

	/**
	 * Find all list.
	 *
	 * @return the list
	 */
	List<Organization> findAll ();

	/**
	 * Exists by name boolean.
	 *
	 * @param name the name
	 * @return the boolean
	 */
	boolean existsByName (String name);

	/**
	 * Update organization.
	 *
	 * @param organization the organization
	 * @return the organization
	 */
	Organization update (Organization organization);

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
