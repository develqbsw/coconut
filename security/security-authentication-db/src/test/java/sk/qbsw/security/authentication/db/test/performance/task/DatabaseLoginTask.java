package sk.qbsw.security.authentication.db.test.performance.task;

import org.springframework.beans.factory.annotation.Autowired;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.authentication.service.AuthenticationService;
import sk.qbsw.security.authentication.db.test.util.DataGenerator;
import sk.qbsw.security.core.model.domain.Account;

/**
 * The database login task for test.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.7.2
 */
class DatabaseLoginTask extends LoginTask implements Runnable
{
	private static final String LOGIN = DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE;

	private static final String PASSWORD = DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE;

	@Autowired
	private AuthenticationService authenticationService;

	@Override
	protected Account authenticate () throws CSecurityException
	{
		return authenticationService.login(LOGIN, PASSWORD);
	}

	@Override
	protected void initResources ()
	{
		// nothing
	}
}
