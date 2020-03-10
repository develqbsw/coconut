package sk.qbsw.security.core.service.mapper;

import sk.qbsw.core.security.base.model.GroupOutputData;
import sk.qbsw.security.core.model.domain.Group;

/**
 * The group output data mapper implementation.
 *
 * @author Tomas Lauro
 * @version 2.5.0
 * @since 2.5.0
 */
public class GroupOutputDataMapperImpl extends GroupOutputDataMapperBase<GroupOutputData, Group> implements GroupOutputDataMapper<GroupOutputData, Group>
{
	/**
	 * Instantiates a new Group output data mapper.
	 *
	 * @param roleOutputDataMapper the role output data mapper
	 */
	public GroupOutputDataMapperImpl (RoleOutputDataMapper roleOutputDataMapper)
	{
		super(roleOutputDataMapper);
	}

	@Override
	protected GroupOutputData instantiateGroupOutputDataWithCustomAttributes (Group group)
	{
		return new GroupOutputData();
	}
}
