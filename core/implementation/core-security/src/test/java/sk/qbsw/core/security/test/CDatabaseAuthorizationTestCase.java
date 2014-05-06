package sk.qbsw.core.security.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.service.IAuthorizationService;
import sk.qbsw.core.security.test.util.CAuthorizationTestProvider;
import sk.qbsw.core.security.test.util.CDataGenerator;

/**
 * Checks Authorization service for database.
 * 
 * @author Tomas Lauro
 * @version 1.6.0
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@TransactionConfiguration (transactionManager = "transactionManager", defaultRollback = true)
public class CDatabaseAuthorizationTestCase
{
	/** The database data generator. */
	@Autowired
	private CDataGenerator dataGenerator;

	/** The authorization service. */
	@Autowired
	@Qualifier ("authorizationService")
	private IAuthorizationService authorizationService;

	/** The authorization test provider. */
	@Autowired
	private CAuthorizationTestProvider authorizationTestProvider;

	/**
	 * Test initialization.
	 */
	@Test
	public void testInitialization ()
	{
		assertNotNull("Could not find Authorization service", authorizationService);
	}

	/**
	 * Test successful authorization of user with default unit.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testAuthorizationWithDefaultUnitPositive () throws CSecurityException
	{
		initTest();

		authorizationTestProvider.testAuthorizationWithDefaultUnitPositive(authorizationService);
	}

	/**
	 * Test unsuccessful authorization of user with default unit.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional
	@Rollback (true)
	public void testAuthorizationWithDefaultUnitNegative () throws CSecurityException
	{
		initTest();

		authorizationTestProvider.testAuthorizationWithDefaultUnitNegative(authorizationService);
	}

	/**
	 * Test successful authorization of user with unit.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testAuthorizationWithUnitPositive () throws CSecurityException
	{
		initTest();

		authorizationTestProvider.testAuthorizationWithUnitPositive(authorizationService);
	}

	/**
	 * Test unsuccessful authorization of user with unit.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional
	@Rollback (true)
	public void testAuthorizationWithUnitNegative () throws CSecurityException
	{
		initTest();

		authorizationTestProvider.testAuthorizationWithUnitNegative(authorizationService);
	}

	/**
	 * Test successful authorization of user with category.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testAuthorizationWithCategoryPositive () throws CSecurityException
	{
		initTest();

		authorizationTestProvider.testAuthorizationWithCategoryPositive(authorizationService);
	}

	/**
	 * Test unsuccessful authorization of user with category.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional
	@Rollback (true)
	public void testAuthorizationWithCategoryNegative () throws CSecurityException
	{
		initTest();

		authorizationTestProvider.testAuthorizationWithCategoryNegative(authorizationService);
	}

	/**
	 * Test successful authorization of user with unit and category.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testAuthorizationWithUnitAndCategoryPositive () throws CSecurityException
	{
		initTest();

		authorizationTestProvider.testAuthorizationWithUnitAndCategoryPositive(authorizationService);
	}

	/**
	 * Test unsuccessful authorization of user with unit and category.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional
	@Rollback (true)
	public void testAuthorizationWithUnitAndCategoryNegative () throws CSecurityException
	{
		initTest();

		authorizationTestProvider.testAuthorizationWithUnitAndCategoryNegative(authorizationService);
	}

	/**
	 * Inits the test.
	 */
	private void initTest ()
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
	}
}
