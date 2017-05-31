package sk.qbsw.security.authentication.ldap.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.security.base.exception.UserDisabledException;
import sk.qbsw.core.testing.mock.IMockHelper;
import sk.qbsw.security.authentication.base.service.AuthenticationService;
import sk.qbsw.security.authentication.ldap.configuration.LdapAuthenticationConfigurator;
import sk.qbsw.security.authentication.ldap.provider.LdapProvider;
import sk.qbsw.security.authentication.ldap.test.util.AuthenticationTestProvider;
import sk.qbsw.security.authentication.ldap.test.util.DataGenerator;
import sk.qbsw.security.core.model.jmx.IAuthenticationConfigurator;

/**
 * Checks Authentication service for ldap.
 *
 * @autor Tomas Lauro
 * 
 * @version 1.11.10
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-ldap-context.xml"})
@Rollback (true)
public class LdapAuthenticationTestCase
{
	/** The database data generator. */
	@Autowired
	private DataGenerator dataGenerator;

	/** The authentication service. */
	@Autowired
	@Qualifier ("ldapAuthenticationService")
	private AuthenticationService authenticationService;

	/** The authentication test provider. */
	@Autowired
	private AuthenticationTestProvider authenticationTestProvider;

	/** The ldap provider. */
	@Autowired
	@Qualifier ("ldapProviderMock")
	private LdapProvider ldapProvider;

	/** The ldap configurator. */
	@Autowired
	private LdapAuthenticationConfigurator ldapConfigurator;

	/** The Authentication Configurator. */
	@Autowired
	private IAuthenticationConfigurator authenticationConfigurator;

	/** The mock helper. */
	@Autowired
	private IMockHelper mockHelper;

	/**
	 * Inits the test case.
	 */
	@Before
	public void initTestCase ()
	{
		ldapConfigurator.setServerName("192.168.123.162");
		ldapConfigurator.setServerPort(10389);
		ldapConfigurator.setUserDn("cn=jozko.mrkvicka,ou=users,dc=mfsr,dc=sk");
		ldapConfigurator.setUserPassword("jozko.mrkvicka");
		ldapConfigurator.setUserSearchBaseDns("ou=system,dc=mfsr,dc=sk;;ou=users,dc=mfsr,dc=sk".split(";;"));
		ldapConfigurator.setUserObjectClass("inetOrgPerson");
		ldapConfigurator.setUserOrganizationId((long) 1);
		authenticationConfigurator.setPasswordPattern("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%_]).{6,40})");
	}

	/**
	 * Test initialization.
	 */
	@Test
	public void testInitialization ()
	{
		assertNotNull("Could not find Authentication service", authenticationService);
	}

	/**
	 * Test login with default unit.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testLoginWithDefaultUnit () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginWithDefaultUnit(authenticationService);
	}

	/**
	 * Test login without default unit.
	 * @throws Exception 
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testLoginWithoutDefaultUnit () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginWithoutDefaultUnit(authenticationService);
	}

	/**
	 * Test login with default unit and role. The role should be found - login success.
	 * @throws Exception 
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testLoginWithDefaultUnitAndRolePositive () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginWithDefaultUnitAndRolePositive(authenticationService);
	}

	/**
	 * Test login with default unit and role. The role should not be found - login fails.
	 * @throws Exception 
	 */
	@Test (expected = CSecurityException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testLoginWithDefaultUnitAndRoleNegative () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginWithDefaultUnitAndRoleNegative(authenticationService);
	}

	/**
	 * Test login without default unit and with role. The role should be found - login success.
	 * @throws Exception 
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testLoginWithoutDefaultUnitAndRolePositive () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginWithoutDefaultUnitAndRolePositive(authenticationService);
	}

	/**
	 * Test login without default unit and with role. The role should not be found - login fails.
	 * @throws Exception 
	 */
	@Test (expected = CSecurityException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testLoginWithoutDefaultUnitAndRoleNegative () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginWithoutDefaultUnitAndRoleNegative(authenticationService);
	}

	/**
	 * Test login with default unit and with unit. The user should be found - login success.
	 * @throws Exception 
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testLoginWithDefaultUnitAndUnit () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginWithDefaultUnitAndUnit(authenticationService);
	}

	/**
	 * Test login without default unit and with unit. The user should be found - login success.
	 * @throws Exception 
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testLoginWithoutDefaultUnitAndUnit () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginWithoutDefaultUnitAndUnit(authenticationService);
	}

	/**
	 * Test login enabled user in disabled organization.
	 * @throws Exception 
	 */
	@Test (expected = UserDisabledException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testLoginEnabledUserDisabledOrganization () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginEnabledUserDisabledOrganization(authenticationService);
	}

	/**
	 * Test login disabled user in disabled organization.
	 * @throws Exception 
	 */
	@Test (expected = UserDisabledException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testLoginDisabledUserDisabledOrganization () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginDisabledUserDisabledOrganization(authenticationService);
	}

	/**
	 * Test login disabled user in enabled organization.
	 * @throws Exception 
	 */
	@Test (expected = UserDisabledException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testLoginDisabledUserEnabledOrganization () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginDisabledUserEnabledOrganization(authenticationService);
	}

	/**
	 * Inits the test user with default unit.
	 *
	 * @throws Exception the exception
	 */
	private void initTest () throws Exception
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
		ReflectionTestUtils.setField(mockHelper.unwrapSpringProxyObject(authenticationService), "ldapProvider", ldapProvider);
	}
}
