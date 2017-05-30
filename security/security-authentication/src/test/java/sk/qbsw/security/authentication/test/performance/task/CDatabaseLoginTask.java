package sk.qbsw.security.authentication.test.performance.task;

import org.springframework.beans.factory.annotation.Autowired;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.authentication.base.service.IAuthenticationService;
import sk.qbsw.security.authentication.test.util.CDataGenerator;
import sk.qbsw.security.core.model.domain.CUser;

/**
 * The database login task for test.
 *
 * @author Tomas Lauro
 * 
 * @version 1.11.4
 * @since 1.7.2
 */
class CDatabaseLoginTask extends CLoginTask implements Runnable
{
	/** The login. */
	private final String LOGIN = CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE;

	/** The password. */
	private final String PASSWORD = CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE;

	/** The authentication service. */
	@Autowired
	private IAuthenticationService authenticationService;

	/* (non-Javadoc)
	 * @see sk.qbsw.code.security.test.performance.task.CLoginTask#authenticate()
	 */
	@Override
	protected CUser authenticate () throws CSecurityException
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
