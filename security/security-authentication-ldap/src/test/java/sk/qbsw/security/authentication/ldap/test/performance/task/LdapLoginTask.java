package sk.qbsw.security.authentication.ldap.test.performance.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.authentication.service.AuthenticationService;
import sk.qbsw.security.authentication.ldap.test.util.DataGenerator;
import sk.qbsw.security.core.model.domain.Account;

/**
 * The database login task for test.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.7.2
 */
class LdapLoginTask extends LoginTask implements Runnable
{
	private static final Logger LOGGER = LoggerFactory.getLogger(LdapLoginTask.class);

	private static final String FIRST_LOGIN = DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE;

	private static final String SECOND_LOGIN = DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE_NO_GROUP;

	private static final String FIRST_PASSWORD = DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE;

	private static final String SECOND_PASSWORD = DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE_NO_GROUP;

	private final AuthenticationService ldapAuthenticationService;

	/**
	 * Instantiates a new Ldap login task.
	 *
	 * @param ldapAuthenticationService the ldap authentication service
	 */
	@Autowired
	LdapLoginTask (AuthenticationService ldapAuthenticationService)
	{
		this.ldapAuthenticationService = ldapAuthenticationService;
	}

	@Override
	protected Account authenticate () throws CSecurityException
	{
		if (Math.random() > 0.5)
		{
			LOGGER.debug("Authentication service using first login");
			return ldapAuthenticationService.login(FIRST_LOGIN, FIRST_PASSWORD);
		}
		else
		{
			LOGGER.debug("Authentication service using second login");
			return ldapAuthenticationService.login(SECOND_LOGIN, SECOND_PASSWORD);
		}
	}
}
