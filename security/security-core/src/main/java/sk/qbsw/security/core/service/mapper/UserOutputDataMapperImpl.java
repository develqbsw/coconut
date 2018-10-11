package sk.qbsw.security.core.service.mapper;

import sk.qbsw.core.security.base.model.UserOutputData;
import sk.qbsw.security.core.model.domain.User;

/**
 * The user output data mapper implementation.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class UserOutputDataMapperImpl extends UserOutputDataMapperBase implements UserOutputDataMapper
{
	@Override
	protected UserOutputData instantiateUserOutputDataWithCustomAttributes (User user)
	{
		return new UserOutputData();
	}
}
