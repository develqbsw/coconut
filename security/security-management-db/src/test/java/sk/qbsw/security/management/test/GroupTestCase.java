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
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.UnitDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Unit;
import sk.qbsw.security.management.db.service.GroupService;
import sk.qbsw.security.management.test.util.DataGenerator;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Checks group service.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@Rollback
public class GroupTestCase
{
	@Autowired
	private DataGenerator dataGenerator;

	@Autowired
	private GroupService groupService;

	@Autowired
	private UnitDao unitDao;

	@Autowired
	private AccountDao accountDao;

	/**
	 * Test initialization.
	 */
	@Test
	public void testInitialization ()
	{
		assertNotNull("Could not find group service", groupService);
	}

	/**
	 * Test get by unit.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testGetByUnit () throws CSecurityException
	{
		initTest();

		Unit unit = unitDao.findOneByName(DataGenerator.FIRST_UNIT_CODE);
		List<Group> groups = groupService.findByUnit(unit);

		// asserts
		assertNotNull("Get all groups failed: list of groups is null", groups);
		Assert.assertEquals("Get all groups failed: the size of list of groups is not 2", groups.size(), 2);
	}

	/**
	 * Test get by unit account.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testGetByUnitAccount () throws CSecurityException
	{
		initTest();

		Unit unit1 = unitDao.findOneByName(DataGenerator.SECOND_UNIT_CODE);
		Account account2 = accountDao.findOneByLogin(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE);

		List<Group> groups = groupService.findByUnitAndAccount(unit1, account2);
		Assert.assertEquals("Get all groups failed: the size of list of groups is not 0", groups.size(), 0);

		Account account1 = accountDao.findOneByLogin(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE);
		groups = groupService.findByUnitAndAccount(unit1, account1);
		Assert.assertEquals("Get all groups failed: the size of list of groups is not 2", groups.size(), 2);

		Unit unit2 = unitDao.findOneByName(DataGenerator.SECOND_UNIT_CODE);
		groups = groupService.findByUnitAndAccount(unit2, account1);
		Assert.assertEquals("Get all groups failed: the size of list of groups is not 2", groups.size(), 2);
	}

	/**
	 * Test get by code and unit.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testGetByCodeAndUnit () throws CSecurityException
	{
		initTest();

		Unit unit = unitDao.findOneByName(DataGenerator.SECOND_UNIT_CODE);

		Group group = groupService.findByCodeAndUnit(DataGenerator.FIRST_GROUP_IN_UNIT_CODE, unit);
		Assert.assertNotNull(group);
	}

	private void initTest ()
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
	}
}
