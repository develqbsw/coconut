package sk.qbsw.organization.complex.management.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.organization.complex.core.model.domain.Unit;
import sk.qbsw.organization.complex.core.model.domain.User;
import sk.qbsw.organization.complex.management.service.UnitService;
import sk.qbsw.organization.complex.management.service.UserService;
import sk.qbsw.organization.complex.management.test.util.DataGenerator;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Checks unit service.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@Rollback
public class UnitTestCase
{
	@Autowired
	private DataGenerator dataGenerator;

	@Autowired
	private UnitService unitService;

	@Autowired
	private UserService userService;

	/**
	 * Test initialization.
	 */
	@Test
	public void testInitialization ()
	{
		assertNotNull("Could not find unit service", unitService);
	}

	/**
	 * Test read all units.
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testGetAll ()
	{
		initTest();

		List<Unit> units = unitService.findAll();

		// asserts
		assertNotNull("List of units is null", units);
		Assert.assertTrue("List of units is empty", units.size() > 0);
	}

	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testGetAllByUser () throws CSecurityException
	{
		initTest();

		List<User> users = userService.findAll();
		User userWithDefaultUnit;
		User userWithoutDefaultUnit;
		userWithDefaultUnit = users.stream().filter(user -> user.getUnits().stream().anyMatch(unit -> DataGenerator.DEFAULT_UNIT_CODE.equals(unit.getCode()))).findFirst().get();
		userWithoutDefaultUnit = users.stream().filter(user -> user.getUnits().stream().noneMatch(unit -> DataGenerator.DEFAULT_UNIT_CODE.equals(unit.getCode()))).findFirst().get();

		// first user
		List<Unit> units = unitService.findByUser(userWithDefaultUnit);

		// asserts
		assertNotNull("List of units is null", units);
		Assert.assertEquals("The expected count of units in list is 2 ", units.size(), 2);

		// second user
		units = unitService.findByUser(userWithoutDefaultUnit);

		// asserts
		assertNotNull("List of units is null", units);
		Assert.assertEquals("The expected count of units in list is  1", units.size(), 1);
	}

	/**
	 * Inits the test.
	 */
	private void initTest ()
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
	}
}
