package sk.qbsw.security.management.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.core.security.base.model.AccountInputData;
import sk.qbsw.security.core.model.domain.Unit;
import sk.qbsw.security.management.db.service.UnitService;
import sk.qbsw.security.management.service.AccountManagementService;
import sk.qbsw.security.management.test.util.DataGenerator;

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
	private AccountManagementService<AccountInputData, AccountData> accountManagementService;

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

	/**
	 * Test read all units by account.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testGetAllByAccount () throws CSecurityException
	{
		initTest();

		AccountData accountWithDefaultUnit = accountManagementService.findOneByLogin(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE);
		AccountData accountWithoutDefaultUnit = accountManagementService.findOneByLogin(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE);

		// first account
		List<Unit> units = unitService.findByAccountId(accountWithDefaultUnit.getId());

		// asserts
		assertNotNull("List of units is null", units);
		Assert.assertEquals("The expected count of units in list is 3 ", units.size(), 3);

		// second account
		units = unitService.findByAccountId(accountWithoutDefaultUnit.getId());

		// asserts
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
