/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * The Interface IUserDao.
 *
 * @author rosenberg
 * @author Tomas Lauro
 * @version 1.8.0
 * @since 1.0.0
 */
public interface IUserDao extends Serializable, IEntityDao<Long, CUser>
{
	/**
	 * Find for modification.
	 *
	 * @param id the pk id
	 * @return the c user
	 */
	public CUser findForModification (Long id);

	/**
	 * Find by login - there are groups with user default unit in the list.
	 *
	 * @param login the login
	 * @return the user
	 */
	public CUser findByLogin (String login);

	/**
	 * Find by login - there are groups with unit from parameter in the list.
	 *
	 * @param login the login
	 * @param unit the unit
	 * @return the user
	 */
	public CUser findByLogin (String login, CUnit unit);

	/**
	 * Find by PIN.
	 *
	 * @param pinCode
	 * @return the c user
	 */
	public CUser findByPinNull (String pinCode);

	/**
	 * Count all users.
	 *
	 * @return the count of all users
	 */
	public int countAllUsers ();

	/**
	 * Find all users.
	 *
	 * @return the list
	 */
	public List<CUser> findAllUsers ();

	/**
	 * Find all users for organization.
	 *
	 * @param organization the organization (mandatory)
	 * @return the list
	 */
	public List<CUser> findAllUsers (COrganization organization);

	/**
	 * Find all users.
	 *
	 * @param organization the organization (optional)
	 * @param enabled the enabled (optional)
	 * @return the list
	 */

	public List<CUser> findAllUsers (COrganization organization, Boolean enabled);

	/**
	 * Find all users.
	 *
	 * @param organization the organization (optional)
	 * @param enabled the enabled (optional)
	 * @param group the group (optional)
	 * @return the list
	 */

	public List<CUser> findAllUsers (COrganization organization, Boolean enabled, CGroup group);

	/**
	 * Find all users order by organization.
	 *
	 * @param organization the organization (optional)
	 * @param enabled the enabled (optional)
	 * @param group the group (optional)
	 * @return the list
	 */
	public List<CUser> findAllUsersOrderByOrganization (COrganization organization, Boolean enabled, CGroup group);

	/** Get users without user what come as parameter
	 * @param organization - organization for which are selected users (optional)
	 * @param group - group for which are selected users (optional)
	 * @param user - user without are users returned (mandatory)
	 * @return list of users
	 */
	public abstract List<CUser> getOtherActiveUsers (COrganization organization, CGroup group, CUser user);

	/**
	 * Find all operator users for organization.
	 *
	 * @param organization the organization (optional)
	 * @param role the role (mandatory)
	 * @return the list
	 */
	public List<CUser> findAllUsersByRole (COrganization organization, CRole role);

	/**
	 * Find all users.
	 *
	 * @param name the name (optional)
	 * @param surname the surname (optional)
	 * @param login the login (optional)
	 * @param enabled the enabled (optional)
	 * @return the list
	 */
	public List<CUser> findAllUsers (String name, String surname, String login, Boolean enabled);

	/**
	 * Find all users.
	 *
	 * @param name the name (optional)
	 * @param surname the surname (optional)
	 * @param login the login (optional)
	 * @param enabled the enabled (optional)
	 * @param groupCodePrefix the group code prefix (optional)
	 * @return the list
	 */
	public List<CUser> findAllUsers (String name, String surname, String login, Boolean enabled, String groupCodePrefix);

	/**
	 * Find all users.
	 *
	 * @param email the email (mandatory)
	 * @return the list
	 */
	public List<CUser> findAllUsers (String email);

	/**
	 * Find users by unit and group
	 * 
	 * @param unit
	 * @param group
	 * @return
	 */
	List<CUser> findAllUsers (CUnit unit, CGroup group);
}
