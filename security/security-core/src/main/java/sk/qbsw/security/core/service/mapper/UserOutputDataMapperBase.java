package sk.qbsw.security.core.service.mapper;

import sk.qbsw.core.security.base.model.UserOutputData;
import sk.qbsw.security.core.model.domain.User;

/**
 * The user output data mapper base.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
public abstract class UserOutputDataMapperBase extends OutputDataMapperBase implements UserOutputDataMapper
{
	@Override
	public UserOutputData mapToUserOutputData (User user)
	{
		if (user == null)
		{
			return null;
		}

		UserOutputData userOutputData = instantiateUserOutputDataWithCustomAttributes(user);
		userOutputData.setId(user.getId());

		return userOutputData;
	}

	/**
	 * Instantiate user output data with custom attributes user output data.
	 *
	 * @param user the user
	 * @return the user output data
	 */
	protected abstract UserOutputData instantiateUserOutputDataWithCustomAttributes (User user);
}
