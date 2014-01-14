package sk.qbsw.code.security.test;

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

import sk.qbsw.code.security.test.util.CDataGenerator;
import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.service.IAuthorizationService;

/**
 * Checks Authorization service for database.
 * 
 * @author Tomas Lauro
 * @version 1.6.0
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@TransactionConfiguration (transactionManager = "transactionManager")
public class CDatabaseAuthorizationTestCase
{
	/** The database data generator. */
	@Autowired
	private CDataGenerator dataGenerator;

	/** The authorization service. */
	@Autowired
	@Qualifier ("databaseAuthorizationService")
	private IAuthorizationService authorizationService;

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
		dataGenerator.generateDataForDatabaseTests();

		CRole role = new CRole("role_1");
		authorizationService.checkAccessRights("user_with_default_unit", role, null, null);
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
		dataGenerator.generateDataForDatabaseTests();

		CRole role = new CRole("role_2");
		authorizationService.checkAccessRights("user_with_default_unit", role, null, null);
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
		dataGenerator.generateDataForDatabaseTests();

		CRole role = new CRole("role_2");
		authorizationService.checkAccessRights("user_with_default_unit", role, "unit_1", null);
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
		dataGenerator.generateDataForDatabaseTests();

		CRole role = new CRole("role_2");
		authorizationService.checkAccessRights("user_with_default_unit", role, "default_unit", null);
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
		dataGenerator.generateDataForDatabaseTests();

		CRole role = new CRole("role_1");
		authorizationService.checkAccessRights("user_with_default_unit", role, null, "category_1");
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
		dataGenerator.generateDataForDatabaseTests();

		CRole role = new CRole("role_2");
		authorizationService.checkAccessRights("user_with_default_unit", role, null, "category_1");
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
		dataGenerator.generateDataForDatabaseTests();

		CRole role = new CRole("role_2");
		authorizationService.checkAccessRights("user_with_default_unit", role, "unit_2", "category_2");
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
		dataGenerator.generateDataForDatabaseTests();

		CRole role = new CRole("role_2");
		authorizationService.checkAccessRights("user_with_default_unit", role, "unit_2", "category_1");
	}
}
