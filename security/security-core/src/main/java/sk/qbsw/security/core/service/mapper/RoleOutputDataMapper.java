package sk.qbsw.security.core.service.mapper;

import sk.qbsw.core.security.base.model.RoleOutputData;
import sk.qbsw.security.core.model.domain.Role;

/**
 * The role output data mapper.
 *
 * @author Tomas Lauro
 * @version 2.5.0
 * @since 2.5.0
 */
public interface RoleOutputDataMapper
{
	/**
	 * Map to role output data role output data.
	 *
	 * @param role the role
	 * @return the role output data
	 */
	RoleOutputData mapToRoleOutputData (Role role);
}
