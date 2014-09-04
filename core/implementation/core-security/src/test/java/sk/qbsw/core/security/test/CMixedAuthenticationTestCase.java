package sk.qbsw.core.security.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.exception.CUserDisabledException;
import sk.qbsw.core.security.model.jmx.IAuthenticationConfigurator;
import sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator;
import sk.qbsw.core.security.service.IAuthenticationService;
import sk.qbsw.core.security.service.IUserService;
import sk.qbsw.core.security.service.ldap.CLdapProvider;
import sk.qbsw.core.security.test.util.CAuthenticationTestProvider;
import sk.qbsw.core.security.test.util.CDataGenerator;
import sk.qbsw.core.testing.mock.IMockHelper;

/**
 * Checks Authentication service for mixed auth.
 *
 * @autor Tomas Lauro
 * @version 1.10.5
 * @since 1.10.5
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-ldap-context.xml"})
@TransactionConfiguration (transactionManager = "transactionManager", defaultRollback = true)
public class CMixedAuthenticationTestCase
{
	/** The database data generator. */
	@Autowired
	private CDataGenerator dataGenerator;

	/** The mixed service. */
	@Autowired
	@Qualifier ("mixedAuthenticationService")
	private IAuthenticationService authenticationService;

	/** The authentication service. */
	@Autowired
	@Qualifier ("ldapAuthenticationService")
	private IAuthenticationService ldapAuthenticationService;

	/** The authentication test provider. */
	@Autowired
	private CAuthenticationTestProvider authenticationTestProvider;

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

	/** The ldap provider. */
	@Autowired
	@Qualifier ("ldapProviderMock")
	private CLdapProvider ldapProvider;

	/**
	 * Inits the test case.
	 */
	@Before
	public void initTestCase ()
	{
		ldapConfigurator.setServerName("192.168.123.78");
		ldapConfigurator.setServerPort(10389);
		ldapConfigurator.setUserDn("cn=jozko.mrkvicka,ou=users,dc=mfsr,dc=sk");
		ldapConfigurator.setUserPassword("jozko.mrkvicka");
		ldapConfigurator.setUserSearchBaseDns(new String[] {"ou=system,dc=mfsr,dc=sk", "ou=users,dc=mfsr,dc=sk"});
		ldapConfigurator.setUserObjectClass("inetOrgPerson");
		ldapConfigurator.setUserOrganizationId((long) 1);
		authenticationConfigurator.setPasswordPattern("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,40})");
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
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testLoginWithDefaultUnit () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginWithDefaultUnit(authenticationService);
	}

	/**
	 * Test login with default unit - incorrect password.
	 * IGNORED - because the ldap mock is always successful
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional
	@Rollback (true)
	@Ignore
	public void testLoginWithDefaultUnitIncorrectPassword () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginWithDefaultUnitIncorrectPassword(authenticationService);
	}

	/**
	 * Test login without default unit.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testLoginWithoutDefaultUnit () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginWithoutDefaultUnit(authenticationService);
	}

	/**
	 * Test login with default unit and role. The role should be found - login success.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testLoginWithDefaultUnitAndRolePositive () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginWithDefaultUnitAndRolePositive(authenticationService);
	}

	/**
	 * Test login with default unit and role. The role should not be found - login fails.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional
	@Rollback (true)
	public void testLoginWithDefaultUnitAndRoleNegative () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginWithDefaultUnitAndRoleNegative(authenticationService);
	}

	/**
	 * Test login without default unit and with role. The role should be found - login success.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testLoginWithoutDefaultUnitAndRolePositive () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginWithoutDefaultUnitAndRolePositive(authenticationService);
	}

	/**
	 * Test login without default unit and with role. The role should not be found - login fails.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional
	@Rollback (true)
	public void testLoginWithoutDefaultUnitAndRoleNegative () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginWithoutDefaultUnitAndRoleNegative(authenticationService);
	}

	/**
	 * Test login with default unit and with unit. The user should be found - login success.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testLoginWithDefaultUnitAndUnit () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginWithDefaultUnitAndUnit(authenticationService);
	}

	/**
	 * Test login without default unit and with unit. The user should be found - login success.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testLoginWithoutDefaultUnitAndUnit () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginWithoutDefaultUnitAndUnit(authenticationService);
	}

	/**
	 * Test login enabled user in disabled organization.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test (expected = CUserDisabledException.class)
	@Transactional (readOnly = true)
	@Rollback (true)
	public void testLoginEnabledUserDisabledOrganization () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginEnabledUserDisabledOrganization(authenticationService, userService);
	}

	/**
	 * Test login disabled user in disabled organization.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test (expected = CUserDisabledException.class)
	@Transactional (readOnly = true)
	@Rollback (true)
	public void testLoginDisabledUserDisabledOrganization () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginDisabledUserDisabledOrganization(authenticationService, userService);
	}

	/**
	 * Test login disabled user in enabled organization.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test (expected = CUserDisabledException.class)
	@Transactional (readOnly = true)
	@Rollback (true)
	public void testLoginDisabledUserEnabledOrganization () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginDisabledUserEnabledOrganization(authenticationService, userService);
	}

	/**
	 * Inits the test.
	 */
	private void initTest () throws Exception
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
		ReflectionTestUtils.setField(mockHelper.unwrapSpringProxyObject(ldapAuthenticationService), "ldapProvider", ldapProvider);
	}
}
