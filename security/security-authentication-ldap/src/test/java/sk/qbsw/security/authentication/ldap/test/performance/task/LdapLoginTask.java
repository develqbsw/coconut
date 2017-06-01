package sk.qbsw.security.authentication.ldap.test.performance.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.authentication.base.service.AuthenticationService;
import sk.qbsw.security.authentication.ldap.configuration.LdapAuthenticationConfigurator;
import sk.qbsw.security.authentication.ldap.test.util.DataGenerator;
import sk.qbsw.security.core.model.domain.User;

/**
 * The database login task for test.
 *
 * @author Tomas Lauro
 * 
 * @version 1.11.4
 * @since 1.7.2
 */
class LdapLoginTask extends LoginTask implements Runnable
{
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(LdapLoginTask.class);

	/** The login 1. */
	private static final String FIRST_LOGIN = DataGenerator.USER_WITH_DEFAULT_UNIT_CODE;

	/** The login 2. */
	private static final String SECOND_LOGIN = DataGenerator.USER_WITH_DEFAULT_UNIT_CODE_NO_GROUP;

	/** The password 1. */
	private static final String FIRST_PASSWORD = DataGenerator.USER_WITH_DEFAULT_UNIT_CODE;

	/** The password 2. */
	private static final String SECOND_PASSWORD = DataGenerator.USER_WITH_DEFAULT_UNIT_CODE_NO_GROUP;

	/** The authentication service. */
	@Autowired
	@Qualifier ("ldapAuthenticationService")
	private AuthenticationService authenticationService;

	/** The ldap authentication configurator. */
	@Autowired
	private LdapAuthenticationConfigurator ldapAuthenticationConfigurator;

	/* (non-Javadoc)
	 * @see sk.qbsw.code.security.test.performance.task.CLoginTask#authenticate()
	 */
	@Override
	protected User authenticate () throws CSecurityException
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
		ldapAuthenticationConfigurator.setSecondaryServerName("192.168.123.162");
		ldapAuthenticationConfigurator.setSecondaryServerPort(10390);
		ldapAuthenticationConfigurator.setUserDn("cn=jozko.mrkvicka,ou=users,dc=coconut,dc=sk");
		ldapAuthenticationConfigurator.setUserPassword("jozko.mrkvicka");
		ldapAuthenticationConfigurator.setUserSearchBaseDns("ou=system,dc=coconut,dc=sk;;ou=users,dc=coconut,dc=sk".split(";;"));
		ldapAuthenticationConfigurator.setUserSearchFilter("(&(cn=%s))");
		ldapAuthenticationConfigurator.setUseSslFlag(false);
		ldapAuthenticationConfigurator.setUserObjectClass("inetOrgPerson");
		ldapAuthenticationConfigurator.setUserOrganizationId((long) 1);
		ldapAuthenticationConfigurator.setPoolMaxActive(10);
	}
}
