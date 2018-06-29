package sk.qbsw.organization.management.service;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.organization.core.model.domain.Organization;

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
