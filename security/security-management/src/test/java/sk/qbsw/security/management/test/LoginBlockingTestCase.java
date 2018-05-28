package sk.qbsw.security.management.test;

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
import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.security.core.dao.BlockedLoginDao;
import sk.qbsw.security.core.configuration.SecurityCoreConfigurator;
import sk.qbsw.security.management.service.LoginBlockingService;
import sk.qbsw.security.management.service.UserCredentialManagementService;
import sk.qbsw.security.management.test.util.DataGenerator;
import sk.qbsw.security.management.test.util.LoginBlockingTestProvider;

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
public class LoginBlockingTestCase
{
	/** The database data generator. */
	@Autowired
	private DataGenerator dataGenerator;

	/** The authentication service. */
	@Autowired
	@Qualifier ("userCredentialManagementService")
	private UserCredentialManagementService authenticationModifierService;

	/** The login blocking service. */
	@Autowired
	private LoginBlockingService loginBlockingService;

	/** The authentication test provider. */
	@Autowired
	private LoginBlockingTestProvider authenticationTestProvider;

	/** The blocked login jpa dao. */
	@Autowired
	private BlockedLoginDao blockedLoginJpaDao;

	/** The Authentication Configurator. */
	@Autowired
	private SecurityCoreConfigurator securityCoreConfigurator;

	/**
	 * Inits the test case.
	 */
	@Before
	public void initTestCase ()
	{
		securityCoreConfigurator.setPasswordPattern("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%_]).{6,40})");
	}

	/**
	 * Test blocked login without blocked.
	 *
	 * @throws CSystemException the c system exception
	 * @throws CSecurityException the c security exception
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testBlockedLoginWithoutBlocked () throws CSystemException, CSecurityException, InterruptedException
	{
		initTest();

		authenticationTestProvider.testBlockedLoginWithoutBlocked(loginBlockingService, blockedLoginJpaDao);
	}

	/**
	 * Test blocked login without blocked check ip null.
	 *
	 * @throws CSystemException the c system exception
	 * @throws CSecurityException the c security exception
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testBlockedLoginWithoutBlockedCheckIpNull () throws CSystemException, CSecurityException, InterruptedException
	{
		initTest();

		authenticationTestProvider.testBlockedLoginWithoutBlockedCheckIpNull(loginBlockingService, blockedLoginJpaDao);
	}

	/**
	 * Test blocked login without blocked set ip null.
	 *
	 * @throws CSystemException the c system exception
	 * @throws CSecurityException the c security exception
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testBlockedLoginWithoutBlockedSetIpNull () throws CSystemException, CSecurityException, InterruptedException
	{
		initTest();

		authenticationTestProvider.testBlockedLoginWithoutBlockedSetIpNull(loginBlockingService, blockedLoginJpaDao);
	}

	/**
	 * Test blocked login with blocked.
	 *
	 * @throws CSystemException the c system exception
	 * @throws CSecurityException the c security exception
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testBlockedLoginWithBlocked () throws CSystemException, CSecurityException, InterruptedException
	{
		initTest();

		authenticationTestProvider.testBlockedLoginWithBlocked(loginBlockingService, blockedLoginJpaDao);
	}

	/**
	 * Test blocked login with blocked check ip null.
	 *
	 * @throws CSystemException the c system exception
	 * @throws CSecurityException the c security exception
	 * @throws InterruptedException 
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testBlockedLoginWithBlockedCheckIpNull () throws CSystemException, CSecurityException, InterruptedException
	{
		initTest();

		authenticationTestProvider.testBlockedLoginWithBlockedCheckIpNull(loginBlockingService, blockedLoginJpaDao);
	}

	/**
	 * Test blocked login with blocked set ip null.
	 *
	 * @throws CSystemException the c system exception
	 * @throws CSecurityException the c security exception
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testBlockedLoginWithBlockedSetIpNull () throws CSystemException, CSecurityException, InterruptedException
	{
		initTest();

		authenticationTestProvider.testBlockedLoginWithBlockedSetIpNull(loginBlockingService, blockedLoginJpaDao);
	}

	/**
	 * Inits the test.
	 */
	private void initTest ()
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
	}
}
