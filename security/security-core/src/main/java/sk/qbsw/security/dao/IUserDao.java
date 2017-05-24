/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.security.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.NoResultException;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.security.model.domain.CGroup;
import sk.qbsw.security.model.domain.CUnit;
import sk.qbsw.security.model.domain.CUser;
import sk.qbsw.security.model.filter.CUserAssociationsFilter;
import sk.qbsw.security.model.filter.CUserDetailFilter;
import sk.qbsw.security.model.order.COrderModel;
import sk.qbsw.security.model.order.IOrderByAttributeSpecifier;

/**
 * The Interface IUserDao.
 *
 * @author rosenberg
 * @author Tomas Lauro
 * @version 1.13.0
 * @since 1.0.0
 */
public interface IUserDao extends IEntityDao<Long, CUser>
{
	/**
	 * Find for modification.
	 *
	 * @param id the pk id
	 * @return the c user
	 * @deprecated use {@link sk.qbsw.security.dao.IUserDao#findById(Long)}
	 */
	CUser findForModification (Long id);

	/**
	 * Find by login with fetched organization, groups, units, roles and default groups - if there is no result throws an exception.
	 *
	 * @param login the login (mandatory)
	 * @return the user
	 * @throws NoResultException there is no result
	 * @throws CSecurityException the login is null
	 */
	CUser findOneByLogin (String login) throws NoResultException, CSecurityException;

	/**
	 * Find by login and unit with fetched organization, groups, units, roles and default groups - if there is no result throws an exception.
	 *
	 * @param login the login (optional)
	 * @param unit the unit (optional)
	 * @return the user
	 * @throws NoResultException there is no result
	 * @throws CSecurityException the login is null
	 */
	CUser findOneByLoginAndUnit (String login, CUnit unit) throws NoResultException, CSecurityException;

	/**
	 * Find by PIN.
	 *
	 * @param pinCode
	 * @return the users
	 * @throws CSecurityException if the input pinCode is null
	 */
	List<CUser> findByPinCode (String pinCode) throws CSecurityException;

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
	List<CUser> findByUnitAndGroup (CUnit unit, CGroup group, COrderModel<? extends IOrderByAttributeSpecifier> orderModel) throws CSecurityException;

	/**
	 * Find by user detail filter with fetched organization, groups, units and default groups.
	 *
	 * @param filter the filter with user details
	 * @param orderModel the order model
	 * @return the list
	 */
	List<CUser> findByUserDetailFilter (CUserDetailFilter filter, COrderModel<? extends IOrderByAttributeSpecifier> orderModel);

	/**
	 * Find by user associations filter with fetched organization, groups, units, roles and default groups.
	 *
	 * @param filter the filter
	 * @param orderModel the order model
	 * @return the list
	 */
	List<CUser> findByUserAssociationsFilter (CUserAssociationsFilter filter, COrderModel<? extends IOrderByAttributeSpecifier> orderModel);
}
