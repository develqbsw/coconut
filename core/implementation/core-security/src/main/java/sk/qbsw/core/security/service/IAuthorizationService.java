package sk.qbsw.core.security.service;

import java.io.Serializable;

import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.domain.CRole;

/**
 * The Interface IAuthorizationService.
 * @author Dalibor Rak
 * @version 1.6.0
 * @since 1.6.0
 */
public interface IAuthorizationService extends Serializable
{
	/**
	 * Checks if the end user has access rights (if not, it throws exception)
	 * @param login user login - mandatory
	 * @param organizationUnit user organization unit - not mandatory
	 * @param category - not mandatory
	 * @param role - mandatory
	 */
	public void checkAccessRights (String login, CRole role, String organizationUnit, String category) throws CSecurityException;
}
