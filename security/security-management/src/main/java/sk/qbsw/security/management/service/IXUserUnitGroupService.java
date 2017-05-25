package sk.qbsw.security.management.service;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.security.model.domain.CGroup;
import sk.qbsw.security.model.domain.CUnit;
import sk.qbsw.security.model.domain.CUser;
import sk.qbsw.security.model.domain.CXUserUnitGroup;

/**
 * Service for CXUserUnitGroup entity operations 
 * 
 * @author farkas.roman
 * @version 1.7.0
 * @since 1.7.0
 */
public interface IXUserUnitGroupService extends Serializable
{

	
	/**
	 * Saves or updates entity
	 * 
	 * @param xuug
	 */
	void save (CXUserUnitGroup xuug);

	/**
	 * Gets all by filter
	 * 
	 * @param user - optional
	 * @param unit - optional
	 * @param group - optional
	 * @return
	 */
	List<CXUserUnitGroup> getAll (CUser user, CUnit unit, CGroup group);

	/**
	 * Gets all by user
	 * 
	 * @param user
	 * @return
	 */
	List<CXUserUnitGroup> getAllByUser (CUser user);

	/**
	 * Saves all entities in list
	 * 
	 * @param xuugList
	 */
	void saveAll (List<CXUserUnitGroup> xuugList);

	/**
	 * Removes entity
	 * 
	 * @param xuug
	 */
	void remove (CXUserUnitGroup xuug);
}
