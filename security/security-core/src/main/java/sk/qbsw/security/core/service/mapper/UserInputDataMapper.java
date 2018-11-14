package sk.qbsw.security.core.service.mapper;

import sk.qbsw.core.security.base.model.UserInputData;
import sk.qbsw.security.core.model.domain.User;

/**
 * The user input data mapper.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
public interface UserInputDataMapper
{
	/**
	 * Map to user user.
	 *
	 * @param inputData the input data
	 * @return the user
	 */
	User mapToUser (UserInputData inputData);
}
