package sk.qbsw.code.security.test;

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

import sk.qbsw.code.security.test.util.CDataGenerator;
import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.service.IUnitService;

/**
 * Checks unit service.
 *
 * @autor Tomas Lauro
 * @version 1.6.0
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

	/**
	 * Test initialization.
	 */
	@Test
	public void testInitialization ()
	{
		assertNotNull("Could not find unit service", unitService);
	}

	/**
	 * Test login with default unit.
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
		assertNotNull("Get all units failed: list of units is null", units);
		Assert.assertTrue("Get all units failed: list of units is empty", units.size() > 0);
	}

	/**
	 * Inits the test.
	 */
	private void initTest ()
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
	}
}
