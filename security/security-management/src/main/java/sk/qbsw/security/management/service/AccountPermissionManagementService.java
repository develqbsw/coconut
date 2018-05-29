package sk.qbsw.security.management.service;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Unit;

/**
 * The account permission management service.
 *
 * @author Dalibor Rak
 * @author farkas.roman
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.13.0
 */
public interface AccountPermissionManagementService
{
	/**
	 * Sets account to group.
	 *
	 * @param accountId the account id
	 * @param groupId the group id
	 * @param unitId the unit id
	 * @throws CBusinessException the c business exception
	 */
	void setAccountToGroup (Long accountId, Long groupId, Long unitId) throws CBusinessException;

	/**
	 * Sets account to group.
	 *
	 * @param accountId the account id
	 * @param groupId the group id
	 * @throws CBusinessException the c business exception
	 */
	void setAccountToGroup (Long accountId, Long groupId) throws CBusinessException;

	/**
	 * Sets account to group.
	 *
	 * @param account the account
	 * @param group the group
	 * @param unit the unit
	 * @throws CBusinessException the c business exception
	 */
	void setAccountToGroup (Account account, Group group, Unit unit) throws CBusinessException;

	/**
	 * Sets account to group.
	 *
	 * @param account the account
	 * @param group the group
	 * @throws CBusinessException the c business exception
	 */
	void setAccountToGroup (Account account, Group group) throws CBusinessException;

	/**
	 * Unset account from group.
	 *
	 * @param accountId the account id
	 * @param groupId the group id
	 * @param unitId the unit id
	 * @throws CSecurityException the c security exception
	 */
	void unsetAccountFromGroup (Long accountId, Long groupId, Long unitId) throws CSecurityException;

	/**
	 * Unset account from group.
	 *
	 * @param accountId the account id
	 * @param groupId the group id
	 * @throws CSecurityException the c security exception
	 */
	void unsetAccountFromGroup (Long accountId, Long groupId) throws CSecurityException;

	/**
	 * Unset account from group.
	 *
	 * @param account the account
	 * @param group the group
	 * @param unit the unit
	 * @throws CSecurityException the c security exception
	 */
	void unsetAccountFromGroup (Account account, Group group, Unit unit) throws CSecurityException;

	/**
	 * Unset account from group.
	 *
	 * @param account the account
	 * @param group the group
	 * @throws CSecurityException the c security exception
	 */
	void unsetAccountFromGroup (Account account, Group group) throws CSecurityException;
}
