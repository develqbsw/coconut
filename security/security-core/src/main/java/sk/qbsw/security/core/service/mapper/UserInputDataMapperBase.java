package sk.qbsw.security.core.service.mapper;

import sk.qbsw.core.security.base.model.UserInputData;
import sk.qbsw.security.core.model.domain.User;

/**
 * The user input data mapper base.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public abstract class UserInputDataMapperBase implements UserInputDataMapper
{
	@Override
	public User mapToUser (UserInputData inputData)
	{
		if (inputData == null)
		{
			return null;
		}

		User user = instantiateUserWithCustomAttributes(inputData);
		user.setId(inputData.getId());

		return user;
	}

	/**
	 * Instantiate user with custom attributes user.
	 *
	 * @param inputData the input data
	 * @return the user
	 */
	protected abstract User instantiateUserWithCustomAttributes (UserInputData inputData);
}
