package sk.qbsw.security.ldap.test;

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
import sk.qbsw.core.security.base.exception.CUserDisabledException;
import sk.qbsw.core.testing.mock.IMockHelper;
import sk.qbsw.security.dao.IUserDao;
import sk.qbsw.security.ldap.configuration.ILdapAuthenticationConfigurator;
import sk.qbsw.security.ldap.service.ILdapProvider;
import sk.qbsw.security.ldap.test.util.CAuthenticationTestProvider;
import sk.qbsw.security.ldap.test.util.CDataGenerator;
import sk.qbsw.security.model.jmx.IAuthenticationConfigurator;
import sk.qbsw.security.service.IAuthenticationService;
import sk.qbsw.security.service.IUserService;

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
public class CLdapAuthenticationTestCase
{
	/** The database data generator. */
	@Autowired
	private CDataGenerator dataGenerator;

	/** The authentication service. */
	@Autowired
	@Qualifier ("ldapAuthenticationService")
	private IAuthenticationService authenticationService;

	/** The authentication test provider. */
	@Autowired
	private CAuthenticationTestProvider authenticationTestProvider;

	/** The ldap provider. */
	@Autowired
	@Qualifier ("ldapProviderMock")
	private ILdapProvider ldapProvider;

	/** The ldap configurator. */
	@Autowired
	private ILdapAuthenticationConfigurator ldapConfigurator;

	/** The user service. */
	@Autowired
	private IUserService userService;

	/** The user dao. */
	@Autowired
	private IUserDao userDao;

	/** The authentication configurator. */
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
	@Test (expected = CUserDisabledException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testLoginEnabledUserDisabledOrganization () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginEnabledUserDisabledOrganization(authenticationService, userService);
	}

	/**
	 * Test login disabled user in disabled organization.
	 * @throws Exception 
	 */
	@Test (expected = CUserDisabledException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testLoginDisabledUserDisabledOrganization () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginDisabledUserDisabledOrganization(authenticationService, userService);
	}

	/**
	 * Test login disabled user in enabled organization.
	 * @throws Exception 
	 */
	@Test (expected = CUserDisabledException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testLoginDisabledUserEnabledOrganization () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginDisabledUserEnabledOrganization(authenticationService, userService);
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