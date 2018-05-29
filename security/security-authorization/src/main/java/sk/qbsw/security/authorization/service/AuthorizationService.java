package sk.qbsw.security.authorization.service;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.core.model.domain.Role;

/**
 * The authorization service.
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.6.0
 */
public interface AuthorizationService
{
	/**
	 * Checks if the end user has access rights (if not, it throws exception)
	 *
	 * @param login the user login - mandatory
	 * @param role the user role - mandatory
	 * @param unit the user organization unit - optional
	 * @param category the category of groups - optional
	 * @throws CSecurityException the c security exception
	 */
	void checkAccessRights (String login, Role role, String unit, String category) throws CSecurityException;
}
