package sk.qbsw.security.core.service.mapper;

import sk.qbsw.core.security.base.model.UserOutputData;
import sk.qbsw.security.core.model.domain.User;

/**
 * The user output data mapper.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
public interface UserOutputDataMapper
{
	/**
	 * Map to user output data user output data.
	 *
	 * @param user the user
	 * @return the user output data
	 */
	UserOutputData mapToUserOutputData (User user);
}
