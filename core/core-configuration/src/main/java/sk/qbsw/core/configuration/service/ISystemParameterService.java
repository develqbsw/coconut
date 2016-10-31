package sk.qbsw.core.configuration.service;

import java.time.OffsetDateTime;
import java.util.List;

import sk.qbsw.core.configuration.model.domain.CSystemParameter;

/**
 * Service ISystemParameterService
 *
 * @author Michal Lacko
 * 
 * @version 1.8.0
 * @since 1.8.0
 */
public interface ISystemParameterService
{
	/**
	 * find system parameter by name where actual system date is between validFromDate and validToDate 
	 * 
	 * @param name name for which is parameter searched
	 * @return system parameter or null if parameter doesn't exists
	 */
	CSystemParameter findByName (String name);


	/**
	 * find list of system parameters by module where actual system date is between validFromDate and validToDate  
	 * 
	 * @param module module for which is parameter searched
	 * @return system parameter or null if parameter doesn't exists
	 */
	List<CSystemParameter> findByModule (String module);

	/**
	 * find system parameter by name where parameter validDateTime is between validFromDate and validToDate 
	 * 
	 * @param name name for which is parameter searched
	 * @param validDateTime is time which must be between validFromDate and validToDate 
	 * @return system parameter or null if parameter doesn't exists
	 */
	CSystemParameter findByName (String name, OffsetDateTime validDateTime);

	/**
	 * find list of system parameters by module where parameter validDateTime is between validFromDate and validToDate  
	 * 
	 * @param module module for which is parameter searched
	 * @param validDateTime is time which must be between validFromDate and validToDate 
	 * @return system parameter or null if parameter doesn't exists
	 */
	List<CSystemParameter> findByModule (String module, OffsetDateTime validDateTime);

	/**
	 * save system parameters to database
	 * @param systemParameters parameters which will be saved to database
	 */
	void save (CSystemParameter... systemParameters);
}
