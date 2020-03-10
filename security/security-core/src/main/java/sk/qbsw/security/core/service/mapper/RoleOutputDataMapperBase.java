package sk.qbsw.security.core.service.mapper;

import sk.qbsw.core.security.base.model.RoleOutputData;
import sk.qbsw.security.core.model.domain.Role;

/**
 * The role output data mapper base.
 *
 * @author Tomas Lauro
 * @version 2.5.0
 * @since 2.5.0
 */
public abstract class RoleOutputDataMapperBase extends OutputDataMapperBase implements RoleOutputDataMapper
{
	@Override
	public RoleOutputData mapToRoleOutputData (Role role)
	{
		if (role == null)
		{
			return null;
		}

		RoleOutputData roleOutputData = instantiateRoleOutputDataWithCustomAttributes(role);

		roleOutputData.setId(role.getId());
		roleOutputData.setCode(role.getCode());

		return roleOutputData;
	}

	/**
	 * Instantiate role output data with custom attributes role output data.
	 *
	 * @param role the role
	 * @return the role output data
	 */
	protected abstract RoleOutputData instantiateRoleOutputDataWithCustomAttributes (Role role);
}
