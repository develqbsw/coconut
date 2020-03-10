package sk.qbsw.security.management.service;

import java.util.List;
import java.util.Set;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.security.base.model.DataActivityStates;
import sk.qbsw.core.security.base.model.GroupDataTypes;
import sk.qbsw.security.management.model.GroupManageableOutputData;

/**
 * The group management service.
 *
 * @author Tomas Lauro
 * @author Michal Slezák
 * @version 2.5.0
 * @since 1.0.0
 */
public interface GroupManagementService
{
	/**
	 * Find by code group.
	 *
	 * @param code the code
	 * @return the group
	 * @throws CSecurityException the c security exception
	 */
	GroupManageableOutputData findByCode (String code) throws CSecurityException;

	/**
	 * Find by unit list.
	 *
	 * @param unitId the unitId
	 * @return the list
	 */
	List<GroupManageableOutputData> findByUnitId (Long unitId);

	/**
	 * Find by unit and account list.
	 *
	 * @param unitId the unitId
	 * @param accountId the account id
	 * @return the list
	 */
	List<GroupManageableOutputData> findByUnitIdAndAccountId (Long unitId, Long accountId);

	/**
	 * Find by code and unit group.
	 *
	 * @param code the code
	 * @param unitId the unitId
	 * @return the group
	 * @throws CSecurityException the c security exception
	 */
	GroupManageableOutputData findByCodeAndUnitId (String code, Long unitId) throws CSecurityException;

	/**
	 * Find all list.
	 *
	 * @return the list
	 */
	List<GroupManageableOutputData> findAll ();

	/**
	 * Find Group manageable by id.
	 *
	 * @param groupId the group id
	 * @return the group manageable
	 */
	GroupManageableOutputData findById (Long groupId);

	/**
	 * Create Group manageable.
	 *
	 * @param code the code
	 * @param type the type
	 * @param category the category
	 * @param roleIds the role id-s
	 * @param excludedGroupIds the excluded group id-s
	 * @param updatedBy the updated by
	 * @return the group manageable
	 * @throws CBusinessException the c business exception
	 */
	GroupManageableOutputData create (String code, GroupDataTypes type, String category, Set<Long> roleIds, Set<Long> excludedGroupIds, Long updatedBy) throws CBusinessException;

	/**
	 * Update Group manageable.
	 *
	 * @param id the id
	 * @param code the code
	 * @param type the type
	 * @param category the category
	 * @param roleIds the role id-s
	 * @param excludedGroupIds the excluded id-s
	 * @param updatedBy the updated by
	 * @return the group manageable
	 * @throws CBusinessException the c business exception
	 */
	GroupManageableOutputData update (Long id, String code, GroupDataTypes type, String category, Set<Long> roleIds, Set<Long> excludedGroupIds, Long updatedBy) throws CBusinessException;

	/**
	 * Update Group manageable state.
	 *
	 * @param groupId the group id
	 * @param state the state
	 * @param updatedBy the updated by
	 * @return the Group
	 * @throws CBusinessException the c business exception
	 */
	GroupManageableOutputData updateState (Long groupId, DataActivityStates state, Long updatedBy) throws CBusinessException;

	/**
	 * Delete group.
	 *
	 * @param groupId the group id.
	 * @return the long
	 * @throws CBusinessException the c business exception
	 */
	long deleteGroup (Long groupId) throws CBusinessException;
}
