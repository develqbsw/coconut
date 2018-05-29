package sk.qbsw.security.management.service;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Organization;
import sk.qbsw.security.core.model.domain.Role;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * The account management service.
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.0.0
 */
public interface AccountManagementService
{
	/**
	 * Register account.
	 *
	 * @param account the account
	 * @param password the password
	 * @param organization the organization
	 * @return the account
	 * @throws CSecurityException the c security exception
	 */
	Account register (Account account, String password, Organization organization) throws CSecurityException;

	/**
	 * Register account.
	 *
	 * @param account the account
	 * @param organization the organization
	 * @return the account
	 * @throws CSecurityException the c security exception
	 */
	Account register (Account account, Organization organization) throws CSecurityException;

	/**
	 * Read account.
	 *
	 * @param id the id
	 * @return the account
	 */
	Account read (Long id);

	/**
	 * Find all list.
	 *
	 * @return the list
	 */
	List<Account> findAll ();

	/**
	 * Find by organization list.
	 *
	 * @param organization the organization
	 * @return the list
	 */
	List<Account> findByOrganization (Organization organization);

	/**
	 * Find all except account list.
	 *
	 * @param organization the organization
	 * @param group the group
	 * @param account the account
	 * @return the list
	 */
	List<Account> findAllExceptAccount (Organization organization, Group group, Account account);

	/**
	 * Find by organization and state list.
	 *
	 * @param organization the organization
	 * @param state the state
	 * @return the list
	 */
	List<Account> findByOrganizationAndState (Organization organization, ActivityStates state);

	/**
	 * Find by organization and state and group list.
	 *
	 * @param organization the organization
	 * @param state the state
	 * @param group the group
	 * @return the list
	 */
	List<Account> findByOrganizationAndStateAndGroup (Organization organization, ActivityStates state, Group group);

	/**
	 * Find by organization and state and group order by organization name and login list.
	 *
	 * @param organization the organization
	 * @param state the state
	 * @param group the group
	 * @return the list
	 */
	List<Account> findByOrganizationAndStateAndGroupOrderByOrganizationNameAndLogin (Organization organization, ActivityStates state, Group group);

	/**
	 * Find by organization and role list.
	 *
	 * @param organization the organization
	 * @param role the role
	 * @return the list
	 */
	List<Account> findByOrganizationAndRole (Organization organization, Role role);

	/**
	 * Find by organization and login and state list.
	 *
	 * @param organization the organization
	 * @param login the login
	 * @param state the state
	 * @return the list
	 */
	List<Account> findByOrganizationAndLoginAndState (Organization organization, String login, ActivityStates state);

	/**
	 * Find by login and state list.
	 *
	 * @param login the login
	 * @param state the state
	 * @return the list
	 */
	List<Account> findByLoginAndState (String login, ActivityStates state);

	/**
	 * Find by login and state and group prefix list.
	 *
	 * @param login the login
	 * @param state the state
	 * @param groupCodePrefix the group code prefix
	 * @return the list
	 */
	List<Account> findByLoginAndStateAndGroupPrefix (String login, ActivityStates state, String groupCodePrefix);

	/**
	 * Find by email list.
	 *
	 * @param email the email
	 * @return the list
	 */
	List<Account> findByEmail (String email);

	/**
	 * Find by by login account.
	 *
	 * @param login the login
	 * @return the account
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	Account findByByLogin (String login) throws NoResultException, CSecurityException;

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
	 * Update account.
	 *
	 * @param account the account
	 * @return the account
	 */
	Account update (Account account);

	/**
	 * Activate account.
	 *
	 * @param id the id
	 * @return the account
	 */
	Account activate (Long id);

	/**
	 * Inactivate account.
	 *
	 * @param id the id
	 * @return the account
	 */
	Account inactivate (Long id);
}
