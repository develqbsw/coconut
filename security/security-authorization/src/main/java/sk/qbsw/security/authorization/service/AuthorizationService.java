package sk.qbsw.security.authorization.service;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.core.model.domain.Role;

/**
 * The Interface AuthorizationService.
 * 
 * @author Dalibor Rak
 * @version 1.6.0
 * @since 1.6.0
 */
public interface AuthorizationService extends Serializable
{

	/**
	 * Checks if the end user has access rights (if not, it throws exception)
	 * @param login the user login - mandatory
	 * @param role the user role - mandatory
	 * @param unit the user organization unit - optional
	 * @param category the category of groups - optional
	 */
	public void checkAccessRights(String login, Role role, String unit, String category) throws CSecurityException;

	/**
	 * Finds role by code.
	 *
	 * @param code the code
	 * @return {@link Role}
	 */
	public List<Role> getRoleByCode(String code);

}
