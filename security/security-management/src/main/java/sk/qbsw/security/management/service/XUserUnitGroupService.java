package sk.qbsw.security.management.service;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Unit;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.core.model.domain.UserUnitGroup;

/**
 * Service for UserUnitGroup entity operations 
 * 
 * @author farkas.roman
 * @version 1.7.0
 * @since 1.7.0
 */
public interface XUserUnitGroupService extends Serializable
{

	
	/**
	 * Saves or updates entity
	 * 
	 * @param xuug
	 */
	void save (UserUnitGroup xuug);

	/**
	 * Gets all by filter
	 * 
	 * @param user - optional
	 * @param unit - optional
	 * @param group - optional
	 * @return
	 */
	List<UserUnitGroup> getAll (User user, Unit unit, Group group);

	/**
	 * Gets all by user
	 * 
	 * @param user
	 * @return
	 */
	List<UserUnitGroup> getAllByUser (User user);

	/**
	 * Saves all entities in list
	 * 
	 * @param xuugList
	 */
	void saveAll (List<UserUnitGroup> xuugList);

	/**
	 * Removes entity
	 * 
	 * @param xuug
	 */
	void remove (UserUnitGroup xuug);
}
