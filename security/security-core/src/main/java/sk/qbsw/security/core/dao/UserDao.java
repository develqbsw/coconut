/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.security.core.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.NoResultException;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Unit;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.core.model.filter.CUserAssociationsFilter;
import sk.qbsw.security.core.model.filter.CUserDetailFilter;
import sk.qbsw.security.core.model.order.COrderModel;
import sk.qbsw.security.core.model.order.IOrderByAttributeSpecifier;

/**
 * The Interface UserDao.
 *
 * @author rosenberg
 * @author Tomas Lauro
 * @version 1.13.0
 * @since 1.0.0
 */
public interface UserDao extends IEntityDao<Long, User>
{
	/**
	 * Find for modification.
	 *
	 * @param id the pk id
	 * @return the c user
	 * @deprecated use {@link sk.qbsw.security.core.UserDao.dao.IUserDao#findById(Long)}
	 */
	User findForModification (Long id);

	/**
	 * Find by login with fetched organization, groups, units, roles and default groups - if there is no result throws an exception.
	 *
	 * @param login the login (mandatory)
	 * @return the user
	 * @throws NoResultException there is no result
	 * @throws CSecurityException the login is null
	 */
	User findOneByLogin (String login) throws NoResultException, CSecurityException;

	/**
	 * Find by login and unit with fetched organization, groups, units, roles and default groups - if there is no result throws an exception.
	 *
	 * @param login the login (optional)
	 * @param unit the unit (optional)
	 * @return the user
	 * @throws NoResultException there is no result
	 * @throws CSecurityException the login is null
	 */
	User findOneByLoginAndUnit (String login, Unit unit) throws NoResultException, CSecurityException;

	/**
	 * Find by PIN.
	 *
	 * @param pinCode
	 * @return the users
	 * @throws CSecurityException if the input pinCode is null
	 */
	List<User> findByPinCode (String pinCode) throws CSecurityException;

	/**
	 * Count all users.
	 *
	 * @return the count of all users
	 */
	long countAll ();

	/**
	 * Find users by unit and group with fetched groups and units.
	 *
	 * @param unit the unit
	 * @param group the group
	 * @param orderModel the order model
	 * @return the list
	 * @throws CSecurityException the unit is null
	 */
	List<User> findByUnitAndGroup (Unit unit, Group group, COrderModel<? extends IOrderByAttributeSpecifier> orderModel) throws CSecurityException;

	/**
	 * Find by user detail filter with fetched organization, groups, units and default groups.
	 *
	 * @param filter the filter with user details
	 * @param orderModel the order model
	 * @return the list
	 */
	List<User> findByUserDetailFilter (CUserDetailFilter filter, COrderModel<? extends IOrderByAttributeSpecifier> orderModel);

	/**
	 * Find by user associations filter with fetched organization, groups, units, roles and default groups.
	 *
	 * @param filter the filter
	 * @param orderModel the order model
	 * @return the list
	 */
	List<User> findByUserAssociationsFilter (CUserAssociationsFilter filter, COrderModel<? extends IOrderByAttributeSpecifier> orderModel);
}
