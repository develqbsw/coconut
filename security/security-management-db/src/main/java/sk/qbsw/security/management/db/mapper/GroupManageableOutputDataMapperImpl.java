package sk.qbsw.security.management.db.mapper;

import sk.qbsw.security.core.service.mapper.GroupOutputDataMapper;
import sk.qbsw.security.core.service.mapper.GroupOutputDataMapperBase;
import sk.qbsw.security.core.service.mapper.RoleOutputDataMapper;
import sk.qbsw.security.core.service.mapper.UserOutputDataMapper;
import sk.qbsw.security.management.model.GroupManageableOutputData;
import sk.qbsw.security.management.db.model.domain.GroupManageable;

/**
 * The group managable output data mapper implementation.
 *
 * @author Tomas Lauro
 * @version 2.5.0
 * @since 2.5.0
 */
public class GroupManageableOutputDataMapperImpl extends GroupOutputDataMapperBase<GroupManageableOutputData, GroupManageable> implements GroupOutputDataMapper<GroupManageableOutputData, GroupManageable>
{
	private final UserOutputDataMapper userOutputDataMapper;

	/**
	 * Instantiates a new Group output data mapper.
	 *
	 * @param roleOutputDataMapper the role output data mapper
	 * @param userOutputDataMapper the user output data mapper
	 */
	public GroupManageableOutputDataMapperImpl (RoleOutputDataMapper roleOutputDataMapper, UserOutputDataMapper userOutputDataMapper)
	{
		super(roleOutputDataMapper);
		this.userOutputDataMapper = userOutputDataMapper;
	}

	@Override
	protected GroupManageableOutputData instantiateGroupOutputDataWithCustomAttributes (GroupManageable group)
	{
		GroupManageableOutputData groupManageableOutputData = new GroupManageableOutputData();
		groupManageableOutputData.setUpdated(group.getUpdated());
		groupManageableOutputData.setUpdatedBy(userOutputDataMapper.mapToUserOutputData(group.getUpdatedBy()));

		return groupManageableOutputData;
	}
}
