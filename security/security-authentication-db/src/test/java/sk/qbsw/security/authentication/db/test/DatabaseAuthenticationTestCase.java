package sk.qbsw.security.authentication.db.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.security.base.exception.InvalidAuthenticationException;
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.core.security.base.model.AccountInputData;
import sk.qbsw.security.authentication.db.test.util.AuthenticationTestProvider;
import sk.qbsw.security.authentication.db.test.util.DataGenerator;
import sk.qbsw.security.authentication.service.AuthenticationService;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.OrganizationDao;
import sk.qbsw.security.management.service.AccountCredentialManagementService;
import sk.qbsw.security.management.service.AccountManagementService;

import static org.junit.Assert.assertNotNull;

/**
 * Checks Authentication service for database.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@Rollback
public class DatabaseAuthenticationTestCase
{
	@Autowired
	private DataGenerator dataGenerator;

	@Autowired
	private AuthenticationService databaseAuthenticationService;

	@Autowired
	private AccountCredentialManagementService authenticationModifierService;

	@Autowired
	private AuthenticationTestProvider authenticationTestProvider;

	@Autowired
	private AccountManagementService<AccountInputData, AccountData> accountService;

	@Autowired
	private OrganizationDao organizationDao;

	@Autowired
	private AccountDao accountDao;

	// @Before
	// public void initTestCase ()
	// {
	// securityCoreConfigurator.setPasswordPattern("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%_]).{6,40})");
	// }

	/**
	 * Test initialization.
	 */
	@Test
	public void testInitialization ()
	{
		assertNotNull("Could not find Authentication service", databaseAuthenticationService);
	}

	/**
	 * Test login with default unit.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testLoginWithDefaultUnit () throws CSecurityException
	{
		initTest();

		authenticationTestProvider.testLoginWithDefaultUnit(databaseAuthenticationService);
	}

	/**
	 * Test login with default unit incorrect password.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testLoginWithDefaultUnitIncorrectPassword () throws CSecurityException
	{
		initTest();

		authenticationTestProvider.testLoginWithDefaultUnitIncorrectPassword(databaseAuthenticationService);
	}

	/**
	 * Test login without default unit.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testLoginWithoutDefaultUnit () throws CSecurityException
	{
		initTest();

		authenticationTestProvider.testLoginWithoutDefaultUnit(databaseAuthenticationService);
	}


	/**
	 * Test login with default unit and role positive.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testLoginWithDefaultUnitAndRolePositive () throws CSecurityException
	{
		initTest();

		authenticationTestProvider.testLoginWithDefaultUnitAndRolePositive(databaseAuthenticationService);
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

		authenticationTestProvider.testLoginWithDefaultUnitAndRoleNegative(databaseAuthenticationService);
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

		authenticationTestProvider.testLoginWithoutDefaultUnitAndRolePositive(databaseAuthenticationService);
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

		authenticationTestProvider.testLoginWithoutDefaultUnitAndRoleNegative(databaseAuthenticationService);
	}

	/**
	 * Test login with default unit and with unit. The account should be found - login success.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testLoginWithDefaultUnitAndUnit () throws CSecurityException
	{
		initTest();

		authenticationTestProvider.testLoginWithDefaultUnitAndUnit(databaseAuthenticationService);
	}

	/**
	 * Test login without default unit and with unit. The account should be found - login success.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testLoginWithoutDefaultUnitAndUnit () throws CSecurityException
	{
		initTest();

		authenticationTestProvider.testLoginWithoutDefaultUnitAndUnit(databaseAuthenticationService);
	}

	/**
	 * Test change plain text password.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testChangePasswordExistingAccount () throws CSecurityException
	{
		initTest();

		authenticationTestProvider.testChangePasswordExistingAccount(databaseAuthenticationService, authenticationModifierService);
	}

	/**
	 * Test change password with password validation existing account.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testChangePasswordWithPasswordValidationExistingAccount () throws CSecurityException
	{
		initTest();

		authenticationTestProvider.testChangePasswordWithPasswordValidationExistingAccount(databaseAuthenticationService, authenticationModifierService);
	}

	/**
	 * Test change password with password validation existing account fail.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testChangePasswordWithPasswordValidationExistingAccountFail () throws CSecurityException
	{
		initTest();

		authenticationTestProvider.testChangePasswordWithPasswordValidationExistingAccountFail(databaseAuthenticationService, authenticationModifierService);
	}

	/**
	 * Test change encrypted password.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testChangeEncryptedPasswordExistingAccount () throws CSecurityException
	{
		initTest();

		authenticationTestProvider.testChangeEncryptedPasswordExistingAccount(databaseAuthenticationService, authenticationModifierService);
	}

	/**
	 * Test change encrypted password with password validation existing account.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testChangeEncryptedPasswordWithPasswordValidationExistingAccount () throws CSecurityException
	{
		initTest();

		authenticationTestProvider.testChangeEncryptedPasswordWithPasswordValidationExistingAccount(databaseAuthenticationService, authenticationModifierService);
	}

	/**
	 * Test change encrypted password with password validation existing account fail.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testChangeEncryptedPasswordWithPasswordValidationExistingAccountFail () throws CSecurityException
	{
		initTest();

		authenticationTestProvider.testChangeEncryptedPasswordWithPasswordValidationExistingAccountFail(databaseAuthenticationService, authenticationModifierService);
	}

	/**
	 * Test change plain text password with new account.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testChangeEncryptedPasswordNewAccount () throws CBusinessException
	{
		initTest();

		authenticationTestProvider.testChangeEncryptedPasswordNewAccount(databaseAuthenticationService, accountService, accountDao, organizationDao, dataGenerator);
	}

	/**
	 * Test change login name of account.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testChangeLogin () throws CSecurityException
	{
		initTest();

		authenticationTestProvider.testChangeLogin(authenticationModifierService, accountService);
	}

	/**
	 * Test if the database is online.
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testIsOnline ()
	{
		initTest();

		authenticationTestProvider.testIsOnline(databaseAuthenticationService);
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

		authenticationTestProvider.testLoginInvalidFromAuthParam(databaseAuthenticationService, authenticationModifierService);
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

		authenticationTestProvider.testLoginInvalidToAuthParam(databaseAuthenticationService, authenticationModifierService);
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

		authenticationTestProvider.testLoginInvalidFromAndToAuthParam(databaseAuthenticationService, authenticationModifierService);
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

		authenticationTestProvider.testChangePasswordNullFromAndToAuthParam(databaseAuthenticationService, authenticationModifierService);
	}

	/**
	 * Inits the test.
	 */
	private void initTest ()
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
	}
}
