package sk.qbsw.security.management.db.service;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.core.security.base.model.DataActivityStates;
import sk.qbsw.core.security.base.model.GroupDataTypes;
import sk.qbsw.security.core.dao.RoleDao;
import sk.qbsw.security.core.dao.UserDao;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.GroupTypes;
import sk.qbsw.security.core.model.domain.Role;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.core.service.mapper.GroupOutputDataMapper;
import sk.qbsw.security.management.db.dao.GroupManageableDao;
import sk.qbsw.security.management.db.model.domain.GroupManageable;
import sk.qbsw.security.management.model.GroupManageableOutputData;
import sk.qbsw.security.management.service.GroupManagementService;

/**
 * Service for group management.
 *
 * @author Michal Lacko
 * @author Tomas Lauro
 * @author Michal Slezák
 * @version 2.5.0
 * @since 1.0.0
 */
public class GroupManageableServiceImpl extends AService implements GroupManagementService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(GroupManageableServiceImpl.class);

	private final GroupManageableDao groupManageableDao;

	private final RoleDao roleDao;

	private final UserDao userDao;

	private final GroupOutputDataMapper<GroupManageableOutputData, GroupManageable> groupManageableOutputDataMapper;

	/**
	 * Instantiates a new Group service.
	 *
	 * @param groupManageableDao the group manageable dao
	 * @param roleDao the role dao
	 * @param userDao the user dao
	 * @param groupManageableOutputDataMapper the group manageable output data mapper
	 */
	public GroupManageableServiceImpl (GroupManageableDao groupManageableDao, RoleDao roleDao, UserDao userDao, //
		GroupOutputDataMapper<GroupManageableOutputData, GroupManageable> groupManageableOutputDataMapper)
	{
		this.groupManageableDao = groupManageableDao;
		this.roleDao = roleDao;
		this.userDao = userDao;
		this.groupManageableOutputDataMapper = groupManageableOutputDataMapper;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public GroupManageableOutputData findByCode (String code) throws CSecurityException
	{
		GroupManageable groupManageable = groupManageableDao.findOneByCode(code);
		return groupManageableOutputDataMapper.mapToGroupOutputData(groupManageable);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<GroupManageableOutputData> findByUnitId (Long unitId)
	{
		List<GroupManageable> groupManageables = groupManageableDao.findByUnitId(unitId);
		return groupManageables.stream().map(groupManageableOutputDataMapper::mapToGroupOutputData).collect(Collectors.toList());
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<GroupManageableOutputData> findByUnitIdAndAccountId (Long unitId, Long accountId)
	{
		List<GroupManageable> groupManageables = groupManageableDao.findByUnitIdAndAccountId(unitId, accountId);
		return groupManageables.stream().map(groupManageableOutputDataMapper::mapToGroupOutputData).collect(Collectors.toList());
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public GroupManageableOutputData findByCodeAndUnitId (String code, Long unitId) throws CSecurityException
	{
		GroupManageable groupManageable = groupManageableDao.findOneByCodeAndUnitId(code, unitId);
		return groupManageableOutputDataMapper.mapToGroupOutputData(groupManageable);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<GroupManageableOutputData> findAll ()
	{
		List<GroupManageable> groupManageables = groupManageableDao.findAll();
		return groupManageables.stream().map(groupManageableOutputDataMapper::mapToGroupOutputData).collect(Collectors.toList());
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public GroupManageableOutputData findById (Long groupId)
	{
		GroupManageable groupManageable = groupManageableDao.findOneById(groupId);
		return groupManageableOutputDataMapper.mapToGroupOutputData(groupManageable);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public GroupManageableOutputData create (String code, GroupDataTypes type, String category, Set<Long> roleIds, Set<Long> excludedGroupIds, Long updatedBy) throws CBusinessException
	{
		User user = userDao.findById(updatedBy);
		if (user == null)
		{
			LOGGER.error("User {} not found.", updatedBy);
			throw new CBusinessException(ECoreErrorResponse.USER_NOT_FOUND);
		}

		Set<Role> roles = roleIds != null ? new HashSet<>(roleDao.findAllByIdIn(roleIds)) : new HashSet<>();
		Set<Group> excludedGroups = excludedGroupIds != null ? new HashSet<>(groupManageableDao.findAllByIdIn(excludedGroupIds)) : new HashSet<>();

		GroupManageable groupManageable = new GroupManageable();
		groupManageable.setCode(code);
		groupManageable.setType(mapToGroupType(type));
		groupManageable.setCategory(category);
		groupManageable.setRoles(roles);
		groupManageable.setExcludedGroups(excludedGroups);
		groupManageable.setUpdated(OffsetDateTime.now());
		groupManageable.setUpdatedBy(user);

		return groupManageableOutputDataMapper.mapToGroupOutputData(groupManageableDao.update(groupManageable));
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public GroupManageableOutputData update (Long id, String code, GroupDataTypes type, String category, Set<Long> roleIds, Set<Long> excludedGroupIds, Long updatedBy) throws CBusinessException
	{
		GroupManageable groupManageable = groupManageableDao.findById(id);

		if (groupManageable == null)
		{
			LOGGER.error("Group Manageable {} not found.", id);
			throw new CBusinessException(ECoreErrorResponse.GROUP_NOT_FOUND);
		}

		User user = userDao.findById(updatedBy);
		if (user == null)
		{
			LOGGER.error("User {} not found.", updatedBy);
			throw new CBusinessException(ECoreErrorResponse.USER_NOT_FOUND);
		}

		Set<Role> roles = (roleIds == null) ? new HashSet<>() : new HashSet<>(roleDao.findAllByIdIn(roleIds));

		Set<Group> excludedGroups = (excludedGroupIds == null) ? new HashSet<>() : new HashSet<>(groupManageableDao.findAllByIdIn(excludedGroupIds));

		groupManageable.setCode(code);
		groupManageable.setType(mapToGroupType(type));
		groupManageable.setCategory(category);
		groupManageable.setRoles(roles);
		groupManageable.setExcludedGroups(excludedGroups);
		groupManageable.setUpdated(OffsetDateTime.now());
		groupManageable.setUpdatedBy(user);

		groupManageableDao.update(groupManageable);

		return groupManageableOutputDataMapper.mapToGroupOutputData(groupManageableDao.update(groupManageable));
	}

	private GroupTypes mapToGroupType (GroupDataTypes groupDataType)
	{
		switch (groupDataType)
		{
			case STANDARD:
				return GroupTypes.STANDARD;
			case TECHNICAL:
				return GroupTypes.TECHNICAL;
			default:
				throw new IllegalArgumentException("Invalid group type: " + groupDataType.name());
		}
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public long deleteGroup (Long groupId) throws CBusinessException
	{
		if (groupId == null)
		{
			LOGGER.error("The group {} is null.", groupId);
			throw new CBusinessException(ECoreErrorResponse.ILLEGAL_ARGUMENT);
		}

		return groupManageableDao.deleteById(groupId);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public GroupManageableOutputData updateState (Long groupId, DataActivityStates state, Long updatedBy) throws CBusinessException
	{
		GroupManageable group = groupManageableDao.findOneById(groupId);

		if (group == null)
		{
			LOGGER.error("The group with id {} not found.", groupId);
			throw new CBusinessException(ECoreErrorResponse.GROUP_NOT_FOUND);
		}

		User user = userDao.findById(updatedBy);
		if (user == null)
		{
			LOGGER.error("User {} not found.", updatedBy);
			throw new CBusinessException(ECoreErrorResponse.USER_NOT_FOUND);
		}

		group.setState(mapToActivityState(state));
		group.setUpdated(OffsetDateTime.now());
		group.setUpdatedBy(user);

		return groupManageableOutputDataMapper.mapToGroupOutputData(groupManageableDao.update(group));
	}

	private ActivityStates mapToActivityState (DataActivityStates state)
	{
		switch (state)
		{
			case ACTIVE:
				return ActivityStates.ACTIVE;
			case INACTIVE:
				return ActivityStates.INACTIVE;
			default:
				throw new IllegalArgumentException("Invalid activity  state: " + state.name());
		}
	}
}
