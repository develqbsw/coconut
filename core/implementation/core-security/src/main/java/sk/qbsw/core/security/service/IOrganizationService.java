package sk.qbsw.core.security.service;

import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CUser;

public interface IOrganizationService
{

	/**
	 * Register new organization and admin user
	 * 
	 * @param model
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

}
