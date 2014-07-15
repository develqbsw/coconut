package sk.qbsw.core.security.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.jmx.IAuthenticationConfigurator;
import sk.qbsw.core.security.service.IAuthenticationService;
import sk.qbsw.core.security.service.IUserService;
import sk.qbsw.core.security.test.util.CAuthenticationTestProvider;
import sk.qbsw.core.security.test.util.CDataGenerator;

/**
 * Checks Authentication service for database.
 *
 * @autor Tomas Lauro
 * @version 1.10.2
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@TransactionConfiguration (transactionManager = "transactionManager", defaultRollback = true)
public class CDatabaseAuthenticationTestCase
{
	/** The database data generator. */
	@Autowired
	private CDataGenerator dataGenerator;

	/** The authentication service. */
	@Autowired
	@Qualifier ("cLoginService")
	private IAuthenticationService authenticationService;

	/** The authentication test provider. */
	@Autowired
	private CAuthenticationTestProvider authenticationTestProvider;

	/** The user service. */
	@Autowired
	private IUserService userService;

	/** The user dao. */
	@Autowired
	private IUserDao userDao;

	/** The authentication configurator. */
	@Autowired
	private IAuthenticationConfigurator authenticationConfigurator;

	/**
	 * Inits the test case.
	 */
	@Before
	public void initTestCase ()
	{
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
	public void testLoginWithDefaultUnit () throws CSecurityException
	{
		initTest();

		authenticationTestProvider.testLoginWithDefaultUnit(authenticationService);
	}

	/**
	 * Test login with default unit - incorrect password.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional
	@Rollback (true)
	public void testLoginWithDefaultUnitIncorrectPassword () throws CSecurityException
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
	public void testLoginWithoutDefaultUnit () throws CSecurityException
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
	public void testLoginWithDefaultUnitAndRolePositive () throws CSecurityException
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
	public void testLoginWithDefaultUnitAndRoleNegative () throws CSecurityException
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
	public void testLoginWithoutDefaultUnitAndRolePositive () throws CSecurityException
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
	public void testLoginWithoutDefaultUnitAndRoleNegative () throws CSecurityException
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
	public void testLoginWithDefaultUnitAndUnit () throws CSecurityException
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
	public void testLoginWithoutDefaultUnitAndUnit () throws CSecurityException
	{
		initTest();

		authenticationTestProvider.testLoginWithoutDefaultUnitAndUnit(authenticationService);
	}

	/**
	 * Test change plain text password.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testChangePasswordExistingUser () throws CSecurityException
	{
		initTest();

		authenticationTestProvider.testChangePasswordExistingUser(authenticationService);
	}

	/**
	 * Test change encrypted password.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testChangeEncryptedPasswordExistingUser () throws CSecurityException
	{
		initTest();

		authenticationTestProvider.testChangeEncryptedPasswordExistingUser(authenticationService);
	}

	/**
	 * Test change plain text password with new user.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testChangeEncryptedPasswordNewUser () throws CSecurityException
	{
		initTest();

		authenticationTestProvider.testChangeEncryptedPasswordNewUser(authenticationService, userService, userDao, dataGenerator);
	}

	/**
	 * Test change login name of user.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testChangeLogin () throws CSecurityException
	{
		initTest();

		authenticationTestProvider.testChangeLogin(authenticationService, userService);
	}

	/**
	 * Test if the database is online.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional (readOnly = true)
	@Rollback (true)
	public void testIsOnline ()
	{
		initTest();

		authenticationTestProvider.testIsOnline(authenticationService);
	}

	/**
	 * Inits the test.
	 */
	private void initTest ()
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
	}
}
