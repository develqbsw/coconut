package sk.qbsw.core.security.test;

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
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.service.IUnitService;
import sk.qbsw.core.security.service.IUserService;
import sk.qbsw.core.security.test.util.CDataGenerator;

/**
 * Checks unit service.
 *
 * @autor Tomas Lauro
 * @version 1.7.1
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@TransactionConfiguration (transactionManager = "transactionManager", defaultRollback = true)
public class CUnitTestCase
{
	/** The database data generator. */
	@Autowired
	private CDataGenerator dataGenerator;

	/** The unit service. */
	@Autowired
	@Qualifier ("unitService")
	private IUnitService unitService;

	/** The user service. */
	@Autowired
	private IUserService userService;

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
	@Transactional
	@Rollback (true)
	public void testGetAll () throws CSecurityException
	{
		initTest();

		List<CUnit> units = unitService.getAll();

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
	@Transactional
	@Rollback (true)
	public void testGetAllByUser () throws CSecurityException
	{
		initTest();

		CUser userWithDefaultUnit = userService.getUserByLogin(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		CUser userWithoutDefaultUnit = userService.getUserByLogin(CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE);

		//first user
		List<CUnit> units = unitService.getAll(userWithDefaultUnit);

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
