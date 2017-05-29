package sk.qbsw.security.management.service;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.core.model.domain.CGroup;
import sk.qbsw.security.core.model.domain.CUnit;
import sk.qbsw.security.core.model.domain.CUser;

/**
 * Service for groups management.
 *
 * @author Michal Lacko
 * @author Tomas Lauro
 * @version 1.6.0
 * @since 1.0.0
 */
public interface IGroupService extends Serializable
{

	/**
	 * Gets the role group.
	 *
	 * @param id the pk id
	 * @return the role group
	 */
	public CGroup read (Long id);

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	public abstract List<CGroup> getAll ();

	/**
	 * Gets the by code.
	 *
	 * @param code the code
	 * @return the by code
	 */
	public abstract List<CGroup> getByCode (String code);

	/**
	 * Gets the by unit.
	 *
	 * @param unit the unit
	 * @return the by unit
	 */
	public abstract List<CGroup> getByUnit (CUnit unit);

	/**
	 * get users by unit and user
	 * 
	 * @param unit
	 * @param user
	 * @return
	 */
	List<CGroup> getByUnitUser (CUnit unit, CUser user);

	/**
	 * Gets the by code and unit.
	 *
	 * @param code the code
	 * @param unit the unit
	 * @return the by code and unit
	 * @throws CSecurityException throws if the code is null
	 */
	List<CGroup> getByCodeAndUnit (String code, CUnit unit) throws CSecurityException;
}
