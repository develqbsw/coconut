package sk.qbsw.security.authorization.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.authorization.service.AuthorizationService;
import sk.qbsw.security.authorization.test.util.AuthorizationTestProvider;
import sk.qbsw.security.authorization.test.util.DataGenerator;

import static org.junit.Assert.assertNotNull;

/**
 * Checks Authorization service for database.
 * 
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@Rollback
public class DatabaseAuthorizationTestCase
{
	@Autowired
	private DataGenerator dataGenerator;

	@Autowired
	private AuthorizationService authorizationService;

	@Autowired
	private AuthorizationTestProvider authorizationTestProvider;

	@Test
	public void testInitialization ()
	{
		assertNotNull("Could not find Authorization service", authorizationService);
	}

	/**
	 * Test successful authorization of account with default unit.
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
	 * Test unsuccessful authorization of account with default unit.
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
	 * Test successful authorization of account with unit.
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
	 * Test unsuccessful authorization of account with unit.
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
	 * Test successful authorization of account with category.
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
	 * Test unsuccessful authorization of account with category.
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
	 * Test successful authorization of account with unit and category.
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
	 * Test unsuccessful authorization of account with unit and category.
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
