package sk.qbsw.security.spring.oauth.rest.mapper;

import sk.qbsw.security.rest.oauth.client.model.CSUserData;
import sk.qbsw.security.spring.base.mapper.UserDataMapper;
import sk.qbsw.security.spring.base.mapper.UserDataMapperBase;
import sk.qbsw.security.spring.common.model.UserData;

/**
 * The user output data mapper implementation.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public class UserDataMapperImpl extends UserDataMapperBase<CSUserData> implements UserDataMapper<CSUserData>
{
	@Override
	protected UserData instantiateUserDataWithCustomAttributes (CSUserData user)
	{
		UserData userData = new UserData();
		userData.setId(user.getId());

		return userData;
	}
}
