package sk.qbsw.security.authorization.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.authorization.service.IAuthorizationService;
import sk.qbsw.security.authorization.test.util.CAuthorizationTestProvider;
import sk.qbsw.security.authorization.test.util.CDataGenerator;

/**
 * Checks Authorization service for database.
 * 
 * @author Tomas Lauro
 * @version 1.6.0
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@Rollback (true)
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
	@Transactional (transactionManager = "transactionManager")
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
	@Transactional (transactionManager = "transactionManager")
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
	@Transactional (transactionManager = "transactionManager")
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
	@Transactional (transactionManager = "transactionManager")
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
	@Transactional (transactionManager = "transactionManager")
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
	@Transactional (transactionManager = "transactionManager")
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
	@Transactional (transactionManager = "transactionManager")
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
	@Transactional (transactionManager = "transactionManager")
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
