package sk.qbsw.security.management.db.service;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Unit;

import java.util.List;

/**
 * The group service.
 *
 * @author Michal Lacko
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.0.0
 */
public interface GroupService
{
	/**
	 * Read group.
	 *
	 * @param id the id
	 * @return the group
	 */
	Group read (Long id);

	/**
	 * Find by code group.
	 *
	 * @param code the code
	 * @return the group
	 * @throws CSecurityException the c security exception
	 */
	Group findByCode (String code) throws CSecurityException;

	/**
	 * Find by unit list.
	 *
	 * @param unit the unit
	 * @return the list
	 */
	List<Group> findByUnit (Unit unit);

	/**
	 * Find by unit and account list.
	 *
	 * @param unit the unit
	 * @param account the account
	 * @return the list
	 */
	List<Group> findByUnitAndAccount (Unit unit, Account account);

	/**
	 * Find by code and unit group.
	 *
	 * @param code the code
	 * @param unit the unit
	 * @return the group
	 * @throws CSecurityException the c security exception
	 */
	Group findByCodeAndUnit (String code, Unit unit) throws CSecurityException;

	/**
	 * Find all list.
	 *
	 * @return the list
	 */
	List<Group> findAll ();
}
