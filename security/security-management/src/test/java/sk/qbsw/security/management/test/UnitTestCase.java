package sk.qbsw.security.management.test;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.Unit;
import sk.qbsw.security.management.service.UnitService;
import sk.qbsw.security.management.service.UserManagementService;
import sk.qbsw.security.management.test.util.DataGenerator;

/**
 * Checks unit service.
 *
 * @autor Tomas Lauro
 * @version 1.7.1
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@Rollback (true)
public class UnitTestCase
{
	/** The database data generator. */
	@Autowired
	private DataGenerator dataGenerator;

	/** The unit service. */
	@Autowired
	@Qualifier ("unitService")
	private UnitService unitService;

	/** The user service. */
	@Autowired
	private UserManagementService userService;

	/**
	 * Test initialization.
	 */
	@Test
	public void testInitialization ()
	{
		assertNotNull("Could not find unit service", unitService);
	}

	/**
	 * Test get all units.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testGetAll () throws CSecurityException
	{
		initTest();

		List<Unit> units = unitService.getAll();

		//asserts
		assertNotNull("List of units is null", units);
		Assert.assertTrue("List of units is empty", units.size() > 0);
	}

	/**
	 * Test get all units by user.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testGetAllByUser () throws CSecurityException
	{
		initTest();

		Account userWithDefaultUnit = userService.getUserByLogin(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		Account userWithoutDefaultUnit = userService.getUserByLogin(DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE);

		//first user
		List<Unit> units = unitService.getAll(userWithDefaultUnit);

		//asserts
		assertNotNull("List of units is null", units);
		Assert.assertEquals("The expected count of units in list is 3 ", units.size(), 3);

		//second user
		units = unitService.getAll(userWithoutDefaultUnit);

		//asserts
		assertNotNull("List of units is null", units);
		Assert.assertEquals("The expected count of units in list is  2", units.size(), 2);
	}

	/**
	 * Inits the test.
	 */
	private void initTest ()
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
	}
}
