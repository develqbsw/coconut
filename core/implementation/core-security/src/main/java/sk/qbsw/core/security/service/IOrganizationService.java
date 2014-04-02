package sk.qbsw.core.security.service;

import java.util.List;

import sk.qbsw.core.security.model.domain.CAddress;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CUser;

public interface IOrganizationService
{

	/**
	 * Register new organization and admin user with selected group
	 * 
	 * @param organization
	 * @param user
	 * @param group
	 */
	public abstract void registerNewOrganization (COrganization organization, CUser user, String group);
	
	/**
	 * Register new organization and admin user with "ADMINISTRATOR" group
	 * 	
	 * @param organization
	 * @param user
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
	 */
	public abstract COrganization getOrganizationByGPS (Float longitude, Float latitude);

	/**
	 * Find all organizations
	 * 
	 * @return list of organizations
	 */
	public abstract List<COrganization> getOrganizations ();
	
	/**
	 * Update organization
	 * 
	 * @param organization
	 */
	public abstract void updateOrganization (COrganization organization);
	
	/**
	 * Find organization by id
	 * 
	 * @param id id of organization
	 * @return organization
	 */
	public abstract COrganization getOrganizationById (Long id);
	
	/**
	 * Add or update organization address
	 * 
	 * @param organization organization for which is address updated
	 * @param address address which is added or updated for organization
	 */
	public void setAddress(COrganization organization, CAddress address);
}
