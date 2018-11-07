package sk.qbsw.security.management.service;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.core.security.base.model.AccountInputData;

import java.util.List;

/**
 * The account management service.
 *
 * @param <I> the account input data type
 * @param <O> the account type
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.0.0
 */
public interface AccountManagementService<I extends AccountInputData, O extends AccountData>
{
	/**
	 * Register o.
	 *
	 * @param account the account
	 * @param password the password
	 * @return the o
	 * @throws CSecurityException the c security exception
	 */
	O register (I account, String password) throws CBusinessException;

	/**
	 * Register o.
	 *
	 * @param account the account
	 * @return the o
	 * @throws CSecurityException the c security exception
	 */
	O register (I account) throws CBusinessException;

	/**
	 * Find one by id o.
	 *
	 * @param id the id
	 * @return the o
	 */
	O findOneById (Long id);

	/**
	 * Find one by login o.
	 *
	 * @param login the login
	 * @return the o
	 * @throws CSecurityException the c security exception
	 */
	O findOneByLogin (String login) throws CSecurityException;

	/**
	 * Find one by uid o.
	 *
	 * @param uid the uid
	 * @return the o
	 */
	O findOneByUid (String uid);

	/**
	 * Find one by login and state o.
	 *
	 * @param login the login
	 * @param state the state
	 * @return the o
	 */
	O findOneByLoginAndState (String login, ActivityStates state);

	/**
	 * Find one by login and state and group code prefix o.
	 *
	 * @param login the login
	 * @param state the state
	 * @param groupCodePrefix the group code prefix
	 * @return the o
	 */
	O findOneByLoginAndStateAndGroupCodePrefix (String login, ActivityStates state, String groupCodePrefix);

	/**
	 * Find all list.
	 *
	 * @return the list
	 */
	List<O> findAll ();

	/**
	 * Find by email list.
	 *
	 * @param email the email
	 * @return the list
	 */
	List<O> findByEmail (String email);

	/**
	 * Exists by login boolean.
	 *
	 * @param login the login
	 * @return the boolean
	 */
	boolean existsByLogin (String login);

	/**
	 * Exists by pin boolean.
	 *
	 * @param pin the pin
	 * @return the boolean
	 */
	boolean existsByPin (String pin);

	/**
	 * Update o.
	 *
	 * @param account the account
	 * @return the o
	 */
	O update (I account);

	/**
	 * Activate o.
	 *
	 * @param id the id
	 * @return the o
	 */
	O activate (Long id);

	/**
	 * Inactivate o.
	 *
	 * @param id the id
	 * @return the o
	 */
	O inactivate (Long id);
}
