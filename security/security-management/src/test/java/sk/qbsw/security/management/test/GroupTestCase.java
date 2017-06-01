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
import sk.qbsw.security.core.dao.UnitDao;
import sk.qbsw.security.core.dao.UserDao;
import sk.qbsw.security.core.model.domain.CGroup;
import sk.qbsw.security.core.model.domain.CUnit;
import sk.qbsw.security.core.model.domain.CUser;
import sk.qbsw.security.management.service.GroupService;
import sk.qbsw.security.management.test.util.DataGenerator;

/**
 * Checks group service.
 *
 * @autor Tomas Lauro
 * @version 1.7.0
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@Rollback (true)
public class GroupTestCase
{
	/** The database data generator. */
	@Autowired
	private DataGenerator dataGenerator;

	/** The group service. */
	@Autowired
	@Qualifier ("cGroupService")
	private GroupService groupService;

	/** Unit dao. */
	@Autowired
	private UnitDao unitDao;

	@Autowired
	private UserDao userDao;

	/**
	 * Test initialization.
	 */
	@Test
	public void testInitialization ()
	{
		assertNotNull("Could not find group service", groupService);
	}

	/**
	 * Test get groups by unit.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testGetByUnit () throws CSecurityException
	{
		initTest();

		CUnit unit = unitDao.findOneByName(DataGenerator.FIRST_UNIT_CODE);
		List<CGroup> groups = groupService.getByUnit(unit);

		//asserts
		assertNotNull("Get all groups failed: list of groups is null", groups);
		Assert.assertEquals("Get all groups failed: the size of list of groups is not 2", groups.size(), 2);
	}

	/**
	 * Test get by unit user.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testGetByUnitUser () throws CSecurityException
	{
		initTest();

		CUnit unit1 = unitDao.findOneByName(DataGenerator.SECOND_UNIT_CODE);
		CUser user2 = userDao.findOneByLogin(DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE);

		List<CGroup> groups = groupService.getByUnitUser(unit1, user2);
		Assert.assertEquals("Get all groups failed: the size of list of groups is not 0", groups.size(), 0);

		CUser user1 = userDao.findOneByLogin(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		groups = groupService.getByUnitUser(unit1, user1);
		Assert.assertEquals("Get all groups failed: the size of list of groups is not 2", groups.size(), 2);

		CUnit unit2 = unitDao.findOneByName(DataGenerator.SECOND_UNIT_CODE);
		groups = groupService.getByUnitUser(unit2, user1);
		Assert.assertEquals("Get all groups failed: the size of list of groups is not 2", groups.size(), 2);
	}

	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testGetByCodeAndUnit () throws CSecurityException
	{
		initTest();

		CUnit unit = unitDao.findOneByName(DataGenerator.SECOND_UNIT_CODE);

		List<CGroup> groups = groupService.getByCodeAndUnit(DataGenerator.FIRST_GROUP_IN_UNIT_CODE, unit);
		Assert.assertNotNull(groups);
		Assert.assertEquals(1, groups.size());
	}

	/**
	 * Inits the test.
	 */
	private void initTest ()
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
	}
}
