package sk.qbsw.security.management.db.dao;

import java.util.List;
import java.util.Set;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.security.core.model.domain.GroupTypes;
import sk.qbsw.security.management.db.model.domain.GroupManageable;

/**
 * The group manageable dao.
 *
 * @author Michal Slezák
 * @version 2.5.0
 * @since 2.5.0
 */
public interface GroupManageableDao extends IEntityDao<Long, GroupManageable>
{
	/**
	 * Find one by id.
	 *
	 * @param groupId the group id
	 * @return the group manageable
	 */
	GroupManageable findOneById (Long groupId);

	/**
	 * Find by type list.
	 *
	 * @param type the type
	 * @return the list
	 */
	List<GroupManageable> findByType (GroupTypes type);

	/**
	 * Find one by code group.
	 *
	 * @param code the code
	 * @return the group
	 * @throws CSecurityException the c security exception
	 */
	GroupManageable findOneByCode (String code) throws CSecurityException;

	/**
	 * Find one by code and unit id.
	 *
	 * @param code the code
	 * @param unitId the unitId
	 * @return the group
	 * @throws CSecurityException the c security exception
	 */
	GroupManageable findOneByCodeAndUnitId (String code, Long unitId) throws CSecurityException;

	/**
	 * Find by unit list.
	 *
	 * @param unitId the unit id
	 * @return the list
	 */
	List<GroupManageable> findByUnitId (Long unitId);

	/**
	 * Find by unit id and user list.
	 *
	 * @param unitId the unit id
	 * @param accountId the account id
	 * @return the list
	 */
	List<GroupManageable> findByUnitIdAndAccountId (Long unitId, Long accountId);

	/**
	 * Find all by id-s in.
	 *
	 * @param groupIds the group ids
	 * @return the list
	 */
	List<GroupManageable> findAllByIdIn (Set<Long> groupIds);

	/**
	 * Delete by id.
	 *
	 * @param id the id
	 * @return the long
	 */
	long deleteById (Long id);
}
