package sk.qbsw.security.organization.complex.management.db.test.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.organization.complex.core.model.domain.CXOUnit;
import sk.qbsw.security.organization.complex.core.model.domain.CXOUser;
import sk.qbsw.security.organization.complex.management.db.test.util.DataGenerator;
import sk.qbsw.security.organization.complex.management.service.CXOUnitService;
import sk.qbsw.security.organization.complex.management.service.CXOUserService;

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
public class CXOUnitServiceTestCase
{
	@Autowired
	private DataGenerator dataGenerator;

	@Autowired
	private CXOUnitService unitService;

	@Autowired
	private CXOUserService userService;

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

		List<CXOUnit> units = unitService.findAll();

		// asserts
		assertNotNull("List of units is null", units);
		Assert.assertTrue("List of units is empty", units.size() > 0);
	}

	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testGetAllByUser () throws CSecurityException
	{
		initTest();

		List<CXOUser> users = userService.findAll();
		CXOUser userWithDefaultUnit;
		CXOUser userWithoutDefaultUnit;
		userWithDefaultUnit = users.stream().filter(user -> user.getUnits().stream().anyMatch(unit -> DataGenerator.DEFAULT_UNIT_CODE.equals(unit.getCode()))).findFirst().get();
		userWithoutDefaultUnit = users.stream().filter(user -> user.getUnits().stream().noneMatch(unit -> DataGenerator.DEFAULT_UNIT_CODE.equals(unit.getCode()))).findFirst().get();

		// first user
		List<CXOUnit> units = unitService.findByUser(userWithDefaultUnit);

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
