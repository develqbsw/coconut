/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.configuration.dao;

import sk.qbsw.core.configuration.model.domain.CSystemParameter;
import sk.qbsw.core.persistence.dao.IEntityDao;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * The Interface ISystemParameterDao.
 *
 * @author Michal Lacko
 * @version 1.8.0
 * @since 1.8.0
 */
public interface ISystemParameterDao extends IEntityDao<Long, CSystemParameter>
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
	 * @return system parameter or empty collection if parameter doesn't exists
	 */
	List<CSystemParameter> findByModule (String module, OffsetDateTime validDateTime);
}
