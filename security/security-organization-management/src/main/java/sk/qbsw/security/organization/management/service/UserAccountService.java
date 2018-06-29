package sk.qbsw.security.organization.management.service;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.organization.core.model.domain.User;
import sk.qbsw.security.core.model.domain.AccountTypes;
import sk.qbsw.security.core.model.domain.Organization;
import sk.qbsw.security.organization.core.model.domain.UserAccount;

import java.util.List;

/**
 * The interface User account service.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public interface UserAccountService
{
	/**
	 * Create user account.
	 *
	 * @param login        the login
	 * @param email        the email
	 * @param type         the type
	 * @param user         the user
	 * @param password     the password
	 * @param organization the organization
	 * @return the user account
	 * @throws CSecurityException the c security exception
	 */
	UserAccount create (String login, String email, AccountTypes type, User user, String password, Organization organization) throws CSecurityException;

	/**
	 * Create user account.
	 *
	 * @param login        the login
	 * @param email        the email
	 * @param type         the type
	 * @param user         the user
	 * @param organization the organization
	 * @return the user account
	 * @throws CSecurityException the c security exception
	 */
	UserAccount create (String login, String email, AccountTypes type, User user, Organization organization) throws CSecurityException;
}
