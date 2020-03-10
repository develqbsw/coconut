package sk.qbsw.security.core.service.mapper;

import sk.qbsw.core.security.base.model.RoleOutputData;
import sk.qbsw.security.core.model.domain.Role;

/**
 * The role output data mapper implementation.
 *
 * @author Tomas Lauro
 * @version 2.5.0
 * @since 2.5.0
 */
public class RoleOutputDataMapperImpl extends RoleOutputDataMapperBase implements RoleOutputDataMapper
{
	@Override
	protected RoleOutputData instantiateRoleOutputDataWithCustomAttributes (Role role)
	{
		return new RoleOutputData();
	}
}
