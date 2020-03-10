package sk.qbsw.security.core.service.mapper;

import sk.qbsw.core.security.base.model.GroupDataTypes;
import sk.qbsw.core.security.base.model.GroupOutputData;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.GroupTypes;

/**
 * The group output data mapper.
 *
 * @param <O> the output data type
 * @param <G> the group type
 * @author Tomas Lauro
 * @version 2.5.0
 * @since 2.5.0
 */
public interface GroupOutputDataMapper<O extends GroupOutputData, G extends Group>
{
	/**
	 * Map to group output data o.
	 *
	 * @param group the group
	 * @return the o
	 */
	O mapToGroupOutputData (G group);
}
