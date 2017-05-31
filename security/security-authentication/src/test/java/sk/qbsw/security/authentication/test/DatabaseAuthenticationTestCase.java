package sk.qbsw.security.authentication.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.security.base.exception.InvalidAuthenticationException;
import sk.qbsw.security.authentication.base.service.AuthenticationService;
import sk.qbsw.security.authentication.test.util.AuthenticationTestProvider;
import sk.qbsw.security.authentication.test.util.DataGenerator;
import sk.qbsw.security.core.dao.IOrganizationDao;
import sk.qbsw.security.core.dao.IUserDao;
import sk.qbsw.security.core.model.jmx.IAuthenticationConfigurator;
import sk.qbsw.security.management.service.UserCredentialManagementService;
import sk.qbsw.security.management.service.UserManagementService;

/**
 * Checks Authentication service for database.
 *
 * @autor Tomas Lauro
 * 
 * @version 1.15.0
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@Rollback (true)
public class DatabaseAuthenticationTestCase
{
	/** The database data generator. */
	@Autowired
	private DataGenerator dataGenerator;

	/** The authentication service. */
	@Autowired
	@Qualifier ("cLoginService")
	private AuthenticationService authenticationService;

	/** The authentication service. */
	@Autowired
	@Qualifier ("userCredentialManagementService")
	private UserCredentialManagementService authenticationModifierService;

	/** The authentication test provider. */
	@Autowired
	private AuthenticationTestProvider authenticationTestProvider;

	/** The user service. */
	@Autowired
	private UserManagementService userService;

	/** The org service. */
	@Autowired
	private IOrganizationDao orgDao;

	/** The user dao. */
	@Autowired
	private IUserDao userDao;

	/** The Authentication Configurator. */
	@Autowired
	private IAuthenticationConfigurator authenticationConfigurator;

	/**
	 * Inits the test case.
	 */
	@Before
	public void initTestCase ()
	{
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
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
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
	@Transactional (transactionManager = "transactionManager")
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
	@Transactional (transactionManager = "transactionManager")
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
	@Transactional (transactionManager = "transactionManager")
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
	@Transactional (transactionManager = "transactionManager")
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
	@Transactional (transactionManager = "transactionManager")
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
	@Transactional (transactionManager = "transactionManager")
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
	@Transactional (transactionManager = "transactionManager")
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
	@Transactional (transactionManager = "transactionManager")
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
	@Transactional (transactionManager = "transactionManager")
	public void testChangePasswordExistingUser () throws CSecurityException
	{
		initTest();

		authenticationTestProvider.testChangePasswordExistingUser(authenticationService, authenticationModifierService);
	}

	/**
	 * Test change encrypted password.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testChangeEncryptedPasswordExistingUser () throws CSecurityException
	{
		initTest();

		authenticationTestProvider.testChangeEncryptedPasswordExistingUser(authenticationService, authenticationModifierService);
	}

	/**
	 * Test change plain text password with new user.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testChangeEncryptedPasswordNewUser () throws CSecurityException
	{
		initTest();

		authenticationTestProvider.testChangeEncryptedPasswordNewUser(authenticationService, userService, userDao, orgDao, dataGenerator);
	}

	/**
	 * Test change login name of user.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testChangeLogin () throws CSecurityException
	{
		initTest();

		authenticationTestProvider.testChangeLogin(authenticationModifierService, userService);
	}

	/**
	 * Test if the database is online.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testIsOnline ()
	{
		initTest();

		authenticationTestProvider.testIsOnline(authenticationService);
	}

	/**
	 * Test login invalid from auth param.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = InvalidAuthenticationException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testLoginInvalidFromAuthParam () throws CSecurityException
	{
		initTest();

		authenticationTestProvider.testLoginInvalidFromAuthParam(authenticationService, authenticationModifierService);
	}

	/**
	 * Test login invalid to auth param.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = InvalidAuthenticationException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testLoginInvalidToAuthParam () throws CSecurityException
	{
		initTest();

		authenticationTestProvider.testLoginInvalidToAuthParam(authenticationService, authenticationModifierService);
	}

	/**
	 * Test login invalid from and to auth param.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = InvalidAuthenticationException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testLoginInvalidFromAndToAuthParam () throws CSecurityException
	{
		initTest();

		authenticationTestProvider.testLoginInvalidFromAndToAuthParam(authenticationService, authenticationModifierService);
	}

	/**
	 * Test change password null from and to auth param.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testChangePasswordNullFromAndToAuthParam () throws CSecurityException
	{
		initTest();

		authenticationTestProvider.testChangePasswordNullFromAndToAuthParam(authenticationService, authenticationModifierService);
	}

	/**
	 * Inits the test.
	 */
	private void initTest ()
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
	}
}
