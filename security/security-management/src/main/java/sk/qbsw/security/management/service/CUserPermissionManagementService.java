package sk.qbsw.security.management.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.security.core.dao.IGroupDao;
import sk.qbsw.security.core.dao.IUnitDao;
import sk.qbsw.security.core.dao.IUserDao;
import sk.qbsw.security.core.dao.IXUserUnitGroupDao;
import sk.qbsw.security.core.model.domain.CGroup;
import sk.qbsw.security.core.model.domain.CUnit;
import sk.qbsw.security.core.model.domain.CUser;
import sk.qbsw.security.core.model.domain.CXUserUnitGroup;

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
public class CUserPermissionManagementService extends AService implements IUserPermissionManagementService
{

	/** The user dao. */
	@Autowired
	private IUserDao userDao;

	/** The group dao. */
	@Autowired
	private IGroupDao groupDao;

	/** The unit dao. */
	@Autowired
	private IUnitDao unitDao;

	/** The cross user unit group dao. */
	@Autowired
	private IXUserUnitGroupDao crossUserUnitGroupDao;


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
		CUser user = userDao.findById(userId);
		CGroup group = groupDao.findById(groupId);
		CUnit unit = null;

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
	public void unsetUserFromGroup (CUser user, CGroup group, CUnit unit) throws CSecurityException
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

	private void removeUserUnitGroup (CUser user, CGroup group, CUnit unit)
	{
		//find user <-> group mapping records - the list should contains only one record, but the method handles the case if not
		List<CXUserUnitGroup> userUnitGroupRecords = crossUserUnitGroupDao.findByUserAndUnitAndGroup(user, unit, group);

		//remove records
		for (CXUserUnitGroup userUnitGroupRecord : userUnitGroupRecords)
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
	public void unsetUserFromGroup (CUser user, CGroup group) throws CSecurityException
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
		CUser user = userDao.findById(userId);
		CGroup group = groupDao.findById(groupId);
		CUnit unit = null;

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
	public void setUserToGroup (CUser user, CGroup group, CUnit unit) throws CBusinessException
	{
		//validates input objects
		if (!CUser.isKnown(user) || !CGroup.isKnown(group))
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
			CXUserUnitGroup userUnitGroupRecord = new CXUserUnitGroup();
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
	public void setUserToGroup (CUser user, CGroup group) throws CBusinessException
	{
		setUserToGroup(user, group, null);
	}

	private boolean canBeGroupUnitAssignedToUser (CUser user, CGroup group, CUnit unit)
	{
		//find all groups assigned to user
		List<CXUserUnitGroup> userUnitGroupRecords = crossUserUnitGroupDao.findByUserAndUnitAndGroup(user, unit, null);

		for (CXUserUnitGroup userUnitGroup : userUnitGroupRecords)
		{
			if (group.equals(userUnitGroup.getGroup()))
			{
				//if is group already added
				return false;
			}

			//if added group is excluded by group which user already have then group cannot be added
			Set<CGroup> excludedGroups = userUnitGroup.getGroup().getExcludedGroups();
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
