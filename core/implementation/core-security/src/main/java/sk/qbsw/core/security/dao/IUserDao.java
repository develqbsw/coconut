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
import sk.qbsw.core.security.model.domain.CUser;

/**
 * The Interface IPreparedAnswerDao.
 *
 * @author rosenberg
 * @version 1.0
 * @since 1.0
 */
public interface IUserDao extends Serializable, IEntityDao<Long, CUser>
{


	/**
	 * Find for modification.
	 *
	 * @param pkId the pk id
	 * @return the c user
	 */
	public CUser findForModification (Long pkId);

	/**
	 * Find by login.
	 *
	 * @param login the login
	 * @return the c user
	 */
	public CUser findByLogin (String login);

	/**
	 * Find by PIN.
	 *
	 * @param pinCode
	 * @return the c user
	 */
	public CUser findByPinNull (String pinCode);

	/**
	 * Find all users for organization.
	 *
	 * @param organization the organization
	 * @return the list
	 */
	public List<CUser> findAllUsers (COrganization organization);

	/**
	 * Find all users.
	 *
	 * @param organization the organization
	 * @return the list
	 */

	public List<CUser> findAllUsers (COrganization organization, Boolean enabled, CGroup group);
	
	/**
	 * Find all users.
	 *
	 * @param organization the organization
	 * @return the list
	 */
	public List<CUser> findAllUsers ();

	/** Get users without user what come as parameter
	 * @param organization - organization for which are selected users
	 * @param group - group for which are selected users
	 * @param user - user without are users returned
	 * @return list of users
	 */
	public abstract List<CUser> getOtherActiveUsers (COrganization organization, CGroup group, CUser user);

	/**
	 * Find all operator users for organization.
	 *
	 * @param organization the organization
	 * @return the list
	 */
	public List<CUser> findAllUsersByRole (COrganization organization, CRole role);
}
