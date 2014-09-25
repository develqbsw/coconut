package sk.qbsw.core.security.service;

import java.util.List;

import sk.qbsw.core.security.model.domain.CAddress;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * The service for a organizations.
 * 
 * @author Tomas Leken
 * @author Tomas Lauro
 * 
 * @version 1.11.5
 * @since 1.0.0
 */
public interface IOrganizationService
{
	/**
	 * Register new organization and admin user with selected group.
	 *
	 * @param organization the organization
	 * @param user the user
	 * @param group the group
	 */
	public abstract void registerNewOrganization (COrganization organization, CUser user, String group);

	/**
	 * Register new organization and admin user with "ADMINISTRATOR" group
	 * 	
	 *
	 * @param organization the organization
	 * @param user the user
	 */
	public abstract void registerNewOrganization (COrganization organization, CUser user);

	/**
	 * Find by name and return NULL if organization not exist - NOT exeption.
	 *
	 * @param name the name
	 * @return organization or null if organization not exist
	 */
	public abstract COrganization getOrganizationByNameNull (String name);

	/**
	 * Find by name and return NULL if organization not exist - NOT exeption.
	 *
	 * @param longitude - longitude of mobile position
	 * @param latitude - latitude of mobile position
	 * @return organization or null if organization not exist
	 * @deprecated the method has never been implemented
	 */
	@Deprecated
	public abstract COrganization getOrganizationByGPS (Float longitude, Float latitude);

	/**
	 * Find all organizations.
	 *
	 * @return list of organizations
	 */
	public abstract List<COrganization> getOrganizations ();

	/**
	 * Find all organizations by name.
	 *
	 * @return list of organizations or empty list if there is no such organization
	 */
	public abstract List<COrganization> getOrganizations (String name);

	/**
	 * Update organization.
	 *
	 * @param organization the organization
	 */
	public abstract void updateOrganization (COrganization organization);

	/**
	 * Find organization by id.
	 *
	 * @param id id of organization
	 * @return organization
	 */
	public abstract COrganization getOrganizationById (Long id);

	/**
	 * Add or update organization address.
	 *
	 * @param organization organization for which is address updated
	 * @param address address which is added or updated for organization
	 */
	public void setAddress (COrganization organization, CAddress address);
}
