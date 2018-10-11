package sk.qbsw.security.core.service.mapper;

import sk.qbsw.core.security.base.model.UserInputData;
import sk.qbsw.security.core.model.domain.User;

/**
 * The user input data mapper implementation.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class UserInputDataMapperImpl extends UserInputDataMapperBase implements UserInputDataMapper
{
	@Override
	protected User instantiateUserWithCustomAttributes (UserInputData inputData)
	{
		return new User();
	}
}
