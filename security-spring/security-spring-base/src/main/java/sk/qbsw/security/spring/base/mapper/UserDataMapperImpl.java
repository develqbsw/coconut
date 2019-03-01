package sk.qbsw.security.spring.base.mapper;

import sk.qbsw.core.security.base.model.UserOutputData;
import sk.qbsw.security.spring.common.model.UserData;

/**
 * The user output data mapper implementation.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public class UserDataMapperImpl extends UserDataMapperBase<UserOutputData> implements UserDataMapper<UserOutputData>
{
	@Override
	protected UserData instantiateUserDataWithCustomAttributes (UserOutputData user)
	{
		UserData userData = new UserData();
		userData.setId(user.getId());

		return userData;
	}
}
