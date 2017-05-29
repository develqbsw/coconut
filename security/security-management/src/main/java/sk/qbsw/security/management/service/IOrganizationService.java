package sk.qbsw.security.management.service;

import java.util.List;

import sk.qbsw.security.core.model.domain.CAddress;
import sk.qbsw.security.core.model.domain.COrganization;
import sk.qbsw.security.core.model.domain.CUser;

/**
 * The service for a organizations.
 * 
 * @author Tomas Leken
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.0.0
 */
public interface IOrganizationService
{

	/**
	 * Disable organization.
	 *
	 * @param org the org
	 */
	public void disableOrganization(COrganization org);

	/**
	 * Enable organization.
	 *
	 * @param org the org
	 */
	public void enableOrganization(COrganization org);

	/**
	 * Find by name and return NULL if organization not exist - NOT exeption.
	 *
	 * @param longitude - longitude of mobile position
	 * @param latitude - latitude of mobile position
	 * @return organization or null if organization not exist

	 * @deprecated the organization name is no longer unique
	 */
	@Deprecated
	public abstract COrganization getOrganizationByGPS(Float longitude, Float latitude);
	
	/**
	 * Gets the organization by name.
	 *
	 * @param name the name
	 * @return the organizations by name
	 */
	public abstract List<COrganization> getOrganizationByName (String name);

	/**
	 * Find organization by id.
	 *
	 * @param id id of organization
	 * @return organization
	 */
	public abstract COrganization getOrganizationById(Long id);

	/**
	 * Find by name and return NULL if organization not exist - NOT exeption.
	 *
	 * @param name the name
	 * @return organization or null if organization not exist
	 */
	public abstract COrganization getOrganizationByNameNull(String name);

	/**
	 * Find all organizations.
	 *
	 * @return list of organizations
	 */
	public abstract List<COrganization> getOrganizations();

	/**
	 * Find all organizations by name.
	 *
	 * @return list of organizations or empty list if there is no such organization
	 */
	public abstract List<COrganization> getOrganizations(String name);

	/**
	 * Register new organization and admin user with "ADMINISTRATOR" group
	 * 	
	 *
	 * @param organization the organization
	 * @param user the user
	 */
	public abstract void registerNewOrganization(COrganization organization, CUser user);

	/**
	 * Register new organization and admin user with selected group.
	 *
	 * @param organization the organization
	 * @param user the user
	 * @param group the group
	 */
	public abstract void registerNewOrganization(COrganization organization, CUser user, String group);

	/**
	 * Register organization.
	 *
	 * @param org the org
	 * @param manager the manager
	 */
	public void registerOrganization(COrganization org, CUser manager);

	/**
	 * Add or update organization address.
	 *
	 * @param organization organization for which is address updated
	 * @param address address which is added or updated for organization
	 */
	public void setAddress(COrganization organization, CAddress address);

	/**
	 * Update organization.
	 *
	 * @param organization the organization
	 */
	public abstract void updateOrganization(COrganization organization);
}
