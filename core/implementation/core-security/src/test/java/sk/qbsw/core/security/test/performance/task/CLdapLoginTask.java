package sk.qbsw.core.security.test.performance.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator;
import sk.qbsw.core.security.service.IAuthenticationService;
import sk.qbsw.core.security.test.util.CDataGenerator;

/**
 * The database login task for test.
 *
 * @author Tomas Lauro
 * 
 * @version 1.11.4
 * @since 1.7.2
 */
class CLdapLoginTask extends CLoginTask implements Runnable
{
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CLdapLoginTask.class);

	/** The login 1. */
	private static final String FIRST_LOGIN = CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE;

	/** The login 2. */
	private static final String SECOND_LOGIN = CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE_NO_GROUP;

	/** The password 1. */
	private static final String FIRST_PASSWORD = CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE;

	/** The password 2. */
	private static final String SECOND_PASSWORD = CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE_NO_GROUP;

	/** The authentication service. */
	@Autowired
	@Qualifier ("ldapAuthenticationService")
	private IAuthenticationService authenticationService;

	/** The ldap authentication configurator. */
	@Autowired
	private ILdapAuthenticationConfigurator ldapAuthenticationConfigurator;

	/* (non-Javadoc)
	 * @see sk.qbsw.code.security.test.performance.task.CLoginTask#authenticate()
	 */
	@Override
	protected CUser authenticate () throws CSecurityException
	{
		if (Math.random() > 0.5)
		{
			LOGGER.debug("Authentication service using first login");
			return authenticationService.login(FIRST_LOGIN, FIRST_PASSWORD);
		}
		else
		{
			LOGGER.debug("Authentication service using second login");
			return authenticationService.login(SECOND_LOGIN, SECOND_PASSWORD);
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.code.security.test.performance.task.CLoginTask#initResources()
	 */
	@Override
	protected void initResources ()
	{
		ldapAuthenticationConfigurator.setServerName("192.168.123.162");
		ldapAuthenticationConfigurator.setServerPort(10389);
		ldapAuthenticationConfigurator.setUserDn("cn=jozko.mrkvicka,ou=users,dc=mfsr,dc=sk");
		ldapAuthenticationConfigurator.setUserPassword("jozko.mrkvicka");
		ldapAuthenticationConfigurator.setUserSearchBaseDns("ou=system,dc=mfsr,dc=sk;;ou=users,dc=mfsr,dc=sk".split(";;"));
		ldapAuthenticationConfigurator.setUserObjectClass("inetOrgPerson");
		ldapAuthenticationConfigurator.setUserOrganizationId((long) 1);
	}
}
