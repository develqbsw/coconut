package sk.qbsw.security.management.service;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Unit;
import sk.qbsw.security.core.model.domain.User;

/**
 * Service for groups management.
 *
 * @author Michal Lacko
 * @author Tomas Lauro
 * @version 1.6.0
 * @since 1.0.0
 */
public interface GroupService extends Serializable
{

	/**
	 * Gets the role group.
	 *
	 * @param id the pk id
	 * @return the role group
	 */
	public Group read (Long id);

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	public abstract List<Group> getAll ();

	/**
	 * Gets the by code.
	 *
	 * @param code the code
	 * @return the by code
	 */
	public abstract List<Group> getByCode (String code);

	/**
	 * Gets the by unit.
	 *
	 * @param unit the unit
	 * @return the by unit
	 */
	public abstract List<Group> getByUnit (Unit unit);

	/**
	 * get users by unit and user
	 * 
	 * @param unit
	 * @param user
	 * @return
	 */
	List<Group> getByUnitUser (Unit unit, User user);

	/**
	 * Gets the by code and unit.
	 *
	 * @param code the code
	 * @param unit the unit
	 * @return the by code and unit
	 * @throws CSecurityException throws if the code is null
	 */
	List<Group> getByCodeAndUnit (String code, Unit unit) throws CSecurityException;
}
