package sk.qbsw.security.management.service;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Unit;
import sk.qbsw.security.core.model.domain.User;

/**
 * The Interface UserCredentialManagementService.
 * 
 * @author Dalibor Rak
 * @author farkas.roman
 * 
 * @version 1.18.0
 * @since 1.13.0
 */
public interface UserPermissionManagementService
{

	/**
	 * Unset user from group in specified unit. The user, group and unit are loaded from persistence manager.
	 *
	 * @param userId the user id (mandatory)
	 * @param groupId the group id (mandatory)
	 * @param unitId the unit id (optional)
	 * @throws CSecurityException the security exception occurs if the input parameters are incorrect
	 */
	public void unsetUserFromGroup (Long userId, Long groupId, Long unitId) throws CSecurityException;

	/**
	 * Unset user from group. The user and group are loaded from persistence manager.
	 *
	 * @param userId the user id (mandatory)
	 * @param groupId the group id (mandatory)
	 * @throws CSecurityException the security exception occurs if the input parameters are incorrect
	 */
	public void unsetUserFromGroup (Long userId, Long groupId) throws CSecurityException;

	/**
	 * Unset user from group in specified unit. The user, group and unit must be attached objects.
	 *
	 * @param user the user (mandatory)
	 * @param group the group (mandatory)
	 * @param unit the unit (optional)
	 * @throws CSecurityException the security exception occurs if the input parameters are incorrect or the unsetting failed
	 */
	public void unsetUserFromGroup (User user, Group group, Unit unit) throws CSecurityException;

	/**
	 * Unset user from group. The user and group must be attached objects.
	 *
	 * @param user the user (mandatory)
	 * @param group the group (mandatory)
	 * @throws CSecurityException the security exception occurs if the input parameters are incorrect or the unsetting failed
	 */
	public void unsetUserFromGroup (User user, Group group) throws CSecurityException;

	/**
	 * Set user to group in specified unit. The user, group and unit are loaded from persistence manager.
	 *
	 * @param userId the user id (mandatory)
	 * @param groupId the group id (mandatory)
	 * @param unitId the unit id (optional)
	 * 
	 * @throws CSecurityException the security exception occurs if the input parameters are incorrect or the setting failed
	 * @throws CBusinessException the exception is thrown if the group cannot be set to user
	 */
	public void setUserToGroup (Long userId, Long groupId, Long unitId) throws CBusinessException;

	/**
	 * Set user to group. The user and group are loaded from persistence manager.
	 *
	 * @param userId the user id (mandatory)
	 * @param groupId the group id (mandatory)
	 * 
	 * @throws CSecurityException the security exception occurs if the input parameters are incorrect or the setting failed
	 * @throws CBusinessException the exception is thrown if the group cannot be set to user
	 */
	public void setUserToGroup (Long userId, Long groupId) throws CBusinessException;

	/**
	 * Set user to group in specified unit. The user, group and unit must be attached objects.
	 *
	 * @param user the user (mandatory)
	 * @param group the group (mandatory)
	 * @param unit the unit (optional)
	 * @throws CSecurityException the security exception occurs if the input parameters are incorrect or the setting failed
	 * @throws CBusinessException the exception is thrown if the group cannot be set to user
	 */
	public void setUserToGroup (User user, Group group, Unit unit) throws CBusinessException;

	/**
	 * Set user to group. The user and group must be attached objects.
	 *
	 * @param user the user (mandatory)
	 * @param group the group (mandatory)
	 * @throws CSecurityException the security exception occurs if the input parameters are incorrect or the setting failed
	 * @throws CBusinessException the exception is thrown if the group cannot be set to user
	 */
	public void setUserToGroup (User user, Group group) throws CBusinessException;

}