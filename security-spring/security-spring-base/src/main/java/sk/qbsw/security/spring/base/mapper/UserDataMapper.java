package sk.qbsw.security.spring.base.mapper;

import sk.qbsw.security.spring.common.model.UserData;

/**
 * The user data mapper.
 *
 * @param <U> the user type
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public interface UserDataMapper<U>
{
	/**
	 * Map to user data user data.
	 *
	 * @param user the user
	 * @return the user data
	 */
	UserData mapToUserData (U user);
}
