package sk.qbsw.security.management.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.security.core.dao.GroupDao;
import sk.qbsw.security.core.dao.UnitDao;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AccountUnitGroupDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Unit;
import sk.qbsw.security.core.model.domain.AccountUnitGroup;
import sk.qbsw.security.management.service.UserPermissionManagementService;

/**
 * Authentication service.
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @author farkas.roman
 * 
 * @version 1.18.0
 * @since 1.0.0
 */
@Service (value = "userPermissionManagementService")
public class UserPermissionManagementServiceImpl extends AService implements UserPermissionManagementService
{

	/** The user dao. */
	@Autowired
	private AccountDao userDao;

	/** The group dao. */
	@Autowired
	private GroupDao groupDao;

	/** The unit dao. */
	@Autowired
	private UnitDao unitDao;

	/** The cross user unit group dao. */
	@Autowired
	private AccountUnitGroupDao crossUserUnitGroupDao;


	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#unsetUserFromGroup(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	@Transactional
	public void unsetUserFromGroup (Long userId, Long groupId, Long unitId) throws CSecurityException
	{
		if (userId == null || groupId == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		//get persisted objects
		Account user = userDao.findById(userId);
		Group group = groupDao.findById(groupId);
		Unit unit = null;

		if (unitId != null)
		{
			unit = unitDao.findById(unitId);
		}

		//call method with logic
		if (unit != null)
		{
			unsetUserFromGroup(user, group, unit);
		}
		else
		{
			unsetUserFromGroup(user, group);
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#unsetUserFromGroup(java.lang.Long, java.lang.Long)
	 */
	@Override
	@Transactional
	public void unsetUserFromGroup (Long userId, Long groupId) throws CSecurityException
	{
		unsetUserFromGroup(userId, groupId, null);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#unsetUserFromGroup(sk.qbsw.security.core.core.model.domain.CUser, sk.qbsw.security.core.core.model.domain.CGroup, sk.qbsw.security.core.core.model.domain.CUnit)
	 */
	@Override
	@Transactional
	public void unsetUserFromGroup (Account user, Group group, Unit unit) throws CSecurityException
	{
		//validates input objects
		if (user == null || user.getId() == null || group == null || group.getId() == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		if (unit != null && unit.getId() == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		removeUserUnitGroup(user, group, unit);
	}

	private void removeUserUnitGroup (Account user, Group group, Unit unit)
	{
		//find user <-> group mapping records - the list should contains only one record, but the method handles the case if not
		List<AccountUnitGroup> userUnitGroupRecords = crossUserUnitGroupDao.findByUserAndUnitAndGroup(user, unit, group);

		//remove records
		for (AccountUnitGroup userUnitGroupRecord : userUnitGroupRecords)
		{
			if (user.getxUserUnitGroups().contains(userUnitGroupRecord))
			{
				user.getxUserUnitGroups().remove(userUnitGroupRecord);
			}
			crossUserUnitGroupDao.remove(userUnitGroupRecord);
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#unsetUserFromGroup(sk.qbsw.security.core.core.model.domain.CUser, sk.qbsw.security.core.core.model.domain.CGroup)
	 */
	@Override
	@Transactional
	public void unsetUserFromGroup (Account user, Group group) throws CSecurityException
	{
		unsetUserFromGroup(user, group, null);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#setUserToGroup(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	@Transactional
	public void setUserToGroup (Long userId, Long groupId, Long unitId) throws CBusinessException
	{
		if (userId == null || groupId == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		//get persisted objects
		Account user = userDao.findById(userId);
		Group group = groupDao.findById(groupId);
		Unit unit = null;

		if (unitId != null)
		{
			unit = unitDao.findById(unitId);
		}

		//call method with logic
		if (unit != null)
		{
			setUserToGroup(user, group, unit);
		}
		else
		{
			setUserToGroup(user, group);
		}

	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#setUserToGroup(java.lang.Long, java.lang.Long)
	 */
	@Override
	@Transactional
	public void setUserToGroup (Long userId, Long groupId) throws CBusinessException
	{
		setUserToGroup(userId, groupId, null);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#setUserToGroup(sk.qbsw.security.core.core.model.domain.CUser, sk.qbsw.security.core.core.model.domain.CGroup, sk.qbsw.security.core.core.model.domain.CUnit)
	 */
	@Override
	@Transactional
	public void setUserToGroup (Account user, Group group, Unit unit) throws CBusinessException
	{
		//validates input objects
		if (!Account.isKnown(user) || !Group.isKnown(group))
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		if (unit != null && unit.getId() == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		boolean canBeAdded = canBeGroupUnitAssignedToUser(user, group, unit);
		if (canBeAdded)
		{
			AccountUnitGroup userUnitGroupRecord = new AccountUnitGroup();
			userUnitGroupRecord.setUser(user);
			userUnitGroupRecord.setGroup(group);
			userUnitGroupRecord.setUnit(unit);
			crossUserUnitGroupDao.update(userUnitGroupRecord);
		}
		else
		{
			throw new CBusinessException("The group " + group.getCode() + " cannot be set to user: it's excluded or already has been set to user.");
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUserManagementService#setUserToGroup(sk.qbsw.security.core.core.model.domain.CUser, sk.qbsw.security.core.core.model.domain.CGroup)
	 */
	@Override
	@Transactional
	public void setUserToGroup (Account user, Group group) throws CBusinessException
	{
		setUserToGroup(user, group, null);
	}

	private boolean canBeGroupUnitAssignedToUser (Account user, Group group, Unit unit)
	{
		//find all groups assigned to user
		List<AccountUnitGroup> userUnitGroupRecords = crossUserUnitGroupDao.findByUserAndUnitAndGroup(user, unit, null);

		for (AccountUnitGroup userUnitGroup : userUnitGroupRecords)
		{
			if (group.equals(userUnitGroup.getGroup()))
			{
				//if is group already added
				return false;
			}

			//if added group is excluded by group which user already have then group cannot be added
			Set<Group> excludedGroups = userUnitGroup.getGroup().getExcludedGroups();
			if (excludedGroups != null && excludedGroups.contains(group))
			{
				return false;
			}
		}
		//if is not combination user group unit already added
		//or if existing group assigned to user not exclude added group
		return true;
	}

}
