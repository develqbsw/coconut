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
import sk.qbsw.security.core.dao.IBlockedLoginDao;
import sk.qbsw.security.core.model.jmx.IAuthenticationConfigurator;
import sk.qbsw.security.management.service.ILoginBlockingService;
import sk.qbsw.security.management.service.IUserCredentialManagementService;
import sk.qbsw.security.management.test.util.CDataGenerator;
import sk.qbsw.security.management.test.util.CLoginBlockingTestProvider;

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
public class CLoginBlockingTestCase
{
	/** The database data generator. */
	@Autowired
	private CDataGenerator dataGenerator;

	/** The authentication service. */
	@Autowired
	@Qualifier ("userCredentialManagementService")
	private IUserCredentialManagementService authenticationModifierService;

	/** The login blocking service. */
	@Autowired
	private ILoginBlockingService loginBlockingService;

	/** The authentication test provider. */
	@Autowired
	private CLoginBlockingTestProvider authenticationTestProvider;

	/** The blocked login jpa dao. */
	@Autowired
	private IBlockedLoginDao blockedLoginJpaDao;

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
