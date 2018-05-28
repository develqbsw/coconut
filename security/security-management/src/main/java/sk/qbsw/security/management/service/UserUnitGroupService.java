package sk.qbsw.security.management.service;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.security.core.model.domain.AccountUnitGroup;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Unit;
import sk.qbsw.security.core.model.domain.Account;

/**
 * Service for UserUnitGroup entity operations 
 * 
 * @author farkas.roman
 * @version 1.7.0
 * @since 1.7.0
 */
public interface UserUnitGroupService extends Serializable
{

	
	/**
	 * Saves or updates entity
	 * 
	 * @param xuug
	 */
	void save (AccountUnitGroup xuug);

	/**
	 * Gets all by filter
	 * 
	 * @param user - optional
	 * @param unit - optional
	 * @param group - optional
	 * @return
	 */
	List<AccountUnitGroup> getAll (Account user, Unit unit, Group group);

	/**
	 * Gets all by user
	 * 
	 * @param user
	 * @return
	 */
	List<AccountUnitGroup> getAllByUser (Account user);

	/**
	 * Saves all entities in list
	 * 
	 * @param xuugList
	 */
	void saveAll (List<AccountUnitGroup> xuugList);

	/**
	 * Removes entity
	 * 
	 * @param xuug
	 */
	void remove (AccountUnitGroup xuug);
}
