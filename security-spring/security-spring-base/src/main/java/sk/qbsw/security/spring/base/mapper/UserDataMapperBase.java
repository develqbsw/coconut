package sk.qbsw.security.spring.base.mapper;

import sk.qbsw.security.spring.common.model.UserData;

/**
 * The user data mapper base.
 *
 * @param <U> the user type
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public abstract class UserDataMapperBase<U> implements UserDataMapper<U>
{
	@Override
	public UserData mapToUserData (U user)
	{
		if (user == null)
		{
			return null;
		}

		return instantiateUserDataWithCustomAttributes(user);
	}

	/**
	 * Instantiate user data with custom attributes user data.
	 *
	 * @param user the user
	 * @return the user data
	 */
	protected abstract UserData instantiateUserDataWithCustomAttributes (U user);
}
