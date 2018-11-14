package sk.qbsw.security.organization.complex.management.service;

import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.security.organization.complex.core.model.domain.CXOOrganization;

import java.util.List;

/**
 * The complex organization service.
 *
 * @author Tomas Leken
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
public interface CXOOrganizationService
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
	CXOOrganization create (String code, String name, String email, ActivityStates state);

	/**
	 * Read organization.
	 *
	 * @param id the id
	 * @return the organization
	 */
	CXOOrganization read (Long id);

	/**
	 * Find by name list.
	 *
	 * @param name the name
	 * @return the list
	 */
	List<CXOOrganization> findByName (String name);

	/**
	 * Find all list.
	 *
	 * @return the list
	 */
	List<CXOOrganization> findAll ();

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
	CXOOrganization update (CXOOrganization organization);

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
