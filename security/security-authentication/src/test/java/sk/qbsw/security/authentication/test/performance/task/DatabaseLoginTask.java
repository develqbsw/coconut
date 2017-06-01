package sk.qbsw.security.authentication.test.performance.task;

import org.springframework.beans.factory.annotation.Autowired;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.authentication.base.service.AuthenticationService;
import sk.qbsw.security.authentication.test.util.DataGenerator;
import sk.qbsw.security.core.model.domain.User;

/**
 * The database login task for test.
 *
 * @author Tomas Lauro
 * 
 * @version 1.11.4
 * @since 1.7.2
 */
class DatabaseLoginTask extends LoginTask implements Runnable
{
	/** The login. */
	private final String LOGIN = DataGenerator.USER_WITH_DEFAULT_UNIT_CODE;

	/** The password. */
	private final String PASSWORD = DataGenerator.USER_WITH_DEFAULT_UNIT_CODE;

	/** The authentication service. */
	@Autowired
	private AuthenticationService authenticationService;

	/* (non-Javadoc)
	 * @see sk.qbsw.code.security.test.performance.task.CLoginTask#authenticate()
	 */
	@Override
	protected User authenticate () throws CSecurityException
	{
		return authenticationService.login(LOGIN, PASSWORD);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.code.security.test.performance.task.CLoginTask#initResources()
	 */
	@Override
	protected void initResources ()
	{
		//nothing
	}
}
