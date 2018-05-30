package sk.qbsw.security.core.dao;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Unit;
import sk.qbsw.security.core.model.filter.AccountAssociationsFilter;
import sk.qbsw.security.core.model.filter.AccountDetailFilter;
import sk.qbsw.security.core.model.order.OrderModel;
import sk.qbsw.security.core.model.order.OrderByAttributeSpecifier;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * The account dao.
 *
 * @author rosenberg
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.0.0
 */
public interface AccountDao extends IEntityDao<Long, Account>
{
	/**
	 * Find one by login account.
	 *
	 * @param login the login
	 * @return the account
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	Account findOneByLogin (String login) throws NoResultException, CSecurityException;

	/**
	 * Find one by login and unit account.
	 *
	 * @param login the login
	 * @param unit the unit
	 * @return the account
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	Account findOneByLoginAndUnit (String login, Unit unit) throws NoResultException, CSecurityException;

	/**
	 * Find by pin code list.
	 *
	 * @param pinCode the pin code
	 * @return the list
	 * @throws CSecurityException the c security exception
	 */
	List<Account> findByPinCode (String pinCode) throws CSecurityException;

	/**
	 * Count all long.
	 *
	 * @return the long
	 */
	long countAll ();

	/**
	 * Find by unit and group list.
	 *
	 * @param unit the unit
	 * @param group the group
	 * @param orderModel the order model
	 * @return the list
	 * @throws CSecurityException the c security exception
	 */
	List<Account> findByUnitAndGroup (Unit unit, Group group, OrderModel<? extends OrderByAttributeSpecifier> orderModel) throws CSecurityException;

	/**
	 * Find by account detail filter list.
	 *
	 * @param filter the filter
	 * @param orderModel the order model
	 * @return the list
	 */
	List<Account> findByAccountDetailFilter (AccountDetailFilter filter, OrderModel<? extends OrderByAttributeSpecifier> orderModel);

	/**
	 * Find by account associations filter list.
	 *
	 * @param filter the filter
	 * @param orderModel the order model
	 * @return the list
	 */
	List<Account> findByAccountAssociationsFilter (AccountAssociationsFilter filter, OrderModel<? extends OrderByAttributeSpecifier> orderModel);
}
