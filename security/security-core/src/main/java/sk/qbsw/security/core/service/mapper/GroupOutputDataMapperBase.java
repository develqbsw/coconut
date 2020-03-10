package sk.qbsw.security.core.service.mapper;

import java.util.stream.Collectors;

import sk.qbsw.core.security.base.model.GroupDataTypes;
import sk.qbsw.core.security.base.model.GroupOutputData;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.GroupTypes;

/**
 * The group output data mapper base.
 *
 * @param <O> the output data type
 * @param <G> the group type
 * @author Tomas Lauro
 * @version 2.5.0
 * @since 2.5.0
 */
public abstract class GroupOutputDataMapperBase<O extends GroupOutputData, G extends Group>extends OutputDataMapperBase implements GroupOutputDataMapper<O, G>
{
	private final RoleOutputDataMapper roleOutputDataMapper;

	/**
	 * Instantiates a new Group output data mapper base.
	 *
	 * @param roleOutputDataMapper the role output data mapper
	 */
	protected GroupOutputDataMapperBase (RoleOutputDataMapper roleOutputDataMapper)
	{
		this.roleOutputDataMapper = roleOutputDataMapper;
	}

	@Override
	public O mapToGroupOutputData (G group)
	{
		if (group == null)
		{
			return null;
		}

		O groupData = instantiateGroupOutputDataWithCustomAttributes(group);
		mapCustomToGroupOutputData(groupData, group);
		return groupData;
	}

	/**
	 * Map custom to group output data.
	 *
	 * @param <U> the type parameter
	 * @param <R> the type parameter
	 * @param groupData the group data
	 * @param group the group
	 */
	protected <U extends GroupOutputData, R extends Group> void mapCustomToGroupOutputData (U groupData, R group)
	{
		groupData.setId(group.getId());
		groupData.setCode(group.getCode());
		groupData.setCategory(group.getCategory());
		groupData.setType(mapToGroupDataType(group.getType()));
		groupData.setState(mapToDataActivityState(group.getState()));
		groupData.setRoles(group.getRoles().stream().map(roleOutputDataMapper::mapToRoleOutputData).collect(Collectors.toSet()));
		groupData.setExcludedGroups(group.getExcludedGroups().stream().map(g -> {
			GroupOutputData excludedGroup = new GroupOutputData();
			mapCustomToGroupOutputData(excludedGroup, g);
			return excludedGroup;
		}).collect(Collectors.toSet()));
	}

	private GroupDataTypes mapToGroupDataType (GroupTypes groupType)
	{
		switch (groupType)
		{
			case STANDARD:
				return GroupDataTypes.STANDARD;
			case TECHNICAL:
				return GroupDataTypes.TECHNICAL;
			default:
				throw new IllegalArgumentException("Invalid group type: " + groupType.name());
		}
	}

	/**
	 * Instantiate group output data with custom attributes o.
	 *
	 * @param group the group
	 * @return the o
	 */
	protected abstract O instantiateGroupOutputDataWithCustomAttributes (G group);
}
