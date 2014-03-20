package sk.qbsw.code.security.test.performance.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import sk.qbsw.code.security.test.util.CDataGenerator;
import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator;
import sk.qbsw.core.security.service.IAuthenticationService;

/**
 * The database login task for test.
 *
 * @author Tomas Lauro
 * 
 * @version 1.7.2
 * @since 1.7.2
 */
public class CLdapLoginTask extends CLoginTask implements Runnable
{
	/** The login. */
	private final String LOGIN = CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE;

	/** The password. */
	private final String PASSWORD = "password123";

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
		return authenticationService.login(LOGIN, PASSWORD);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.code.security.test.performance.task.CLoginTask#initResources()
	 */
	@Override
	protected void initResources ()
	{
		ldapAuthenticationConfigurator.setServerName("talos.qbsw.local");
		ldapAuthenticationConfigurator.setServerPort(10389);
		ldapAuthenticationConfigurator.setUserDn("cn=ekolkyRW,ou=users,ou=system");
		ldapAuthenticationConfigurator.setUserPassword("ZiAvGJDxcqUOkyQ");
		ldapAuthenticationConfigurator.setUserSearchBaseDn("ou=users,dc=mfsr,dc=sk");
		ldapAuthenticationConfigurator.setUserObjectClass("inetOrgPerson");
		ldapAuthenticationConfigurator.setUserOrganizationId((long) 1);
	}
}
