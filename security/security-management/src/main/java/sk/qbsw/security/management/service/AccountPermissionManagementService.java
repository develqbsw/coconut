package sk.qbsw.security.management.service;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;

/**
 * The account permission management service.
 *
 * @author Dalibor Rak
 * @author farkas.roman
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.13.0
 */
public interface AccountPermissionManagementService
{
	/**
	 * Assign account to group.
	 *
	 * @param login the login
	 * @param groupCode the group code
	 * @param unitName the unit name
	 * @throws CBusinessException the c business exception
	 */
	void assignAccountToGroup (String login, String groupCode, String unitName) throws CBusinessException;

	/**
	 * Assign account to group.
	 *
	 * @param login the login
	 * @param groupCode the group code
	 * @throws CBusinessException the c business exception
	 */
	void assignAccountToGroup (String login, String groupCode) throws CBusinessException;

	/**
	 * Unassign account from group.
	 *
	 * @param login the login
	 * @param groupCode the group code
	 * @param unitName the unit name
	 * @throws CSecurityException the c security exception
	 */
	void unassignAccountFromGroup (String login, String groupCode, String unitName) throws CSecurityException;

	/**
	 * Unassign account from group.
	 *
	 * @param login the login
	 * @param groupCode the group code
	 * @throws CSecurityException the c security exception
	 */
	void unassignAccountFromGroup (String login, String groupCode) throws CSecurityException;
}
