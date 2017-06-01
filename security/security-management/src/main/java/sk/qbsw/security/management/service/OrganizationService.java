package sk.qbsw.security.management.service;

import java.util.List;

import sk.qbsw.security.core.model.domain.Address;
import sk.qbsw.security.core.model.domain.Organization;
import sk.qbsw.security.core.model.domain.User;

/**
 * The service for a organizations.
 * 
 * @author Tomas Leken
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.0.0
 */
public interface OrganizationService
{

	/**
	 * Disable organization.
	 *
	 * @param org the org
	 */
	public void disableOrganization(Organization org);

	/**
	 * Enable organization.
	 *
	 * @param org the org
	 */
	public void enableOrganization(Organization org);

	/**
	 * Find by name and return NULL if organization not exist - NOT exeption.
	 *
	 * @param longitude - longitude of mobile position
	 * @param latitude - latitude of mobile position
	 * @return organization or null if organization not exist

	 * @deprecated the organization name is no longer unique
	 */
	@Deprecated
	public abstract Organization getOrganizationByGPS(Float longitude, Float latitude);
	
	/**
	 * Gets the organization by name.
	 *
	 * @param name the name
	 * @return the organizations by name
	 */
	public abstract List<Organization> getOrganizationByName (String name);

	/**
	 * Find organization by id.
	 *
	 * @param id id of organization
	 * @return organization
	 */
	public abstract Organization getOrganizationById(Long id);

	/**
	 * Find by name and return NULL if organization not exist - NOT exeption.
	 *
	 * @param name the name
	 * @return organization or null if organization not exist
	 */
	public abstract Organization getOrganizationByNameNull(String name);

	/**
	 * Find all organizations.
	 *
	 * @return list of organizations
	 */
	public abstract List<Organization> getOrganizations();

	/**
	 * Find all organizations by name.
	 *
	 * @return list of organizations or empty list if there is no such organization
	 */
	public abstract List<Organization> getOrganizations(String name);

	/**
	 * Register new organization and admin user with "ADMINISTRATOR" group
	 * 	
	 *
	 * @param organization the organization
	 * @param user the user
	 */
	public abstract void registerNewOrganization(Organization organization, User user);

	/**
	 * Register new organization and admin user with selected group.
	 *
	 * @param organization the organization
	 * @param user the user
	 * @param group the group
	 */
	public abstract void registerNewOrganization(Organization organization, User user, String group);

	/**
	 * Register organization.
	 *
	 * @param org the org
	 * @param manager the manager
	 */
	public void registerOrganization(Organization org, User manager);

	/**
	 * Add or update organization address.
	 *
	 * @param organization organization for which is address updated
	 * @param address address which is added or updated for organization
	 */
	public void setAddress(Organization organization, Address address);

	/**
	 * Update organization.
	 *
	 * @param organization the organization
	 */
	public abstract void updateOrganization(Organization organization);
}
