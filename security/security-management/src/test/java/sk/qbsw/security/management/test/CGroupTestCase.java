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
import sk.qbsw.security.core.dao.IUnitDao;
import sk.qbsw.security.core.dao.IUserDao;
import sk.qbsw.security.core.model.domain.CGroup;
import sk.qbsw.security.core.model.domain.CUnit;
import sk.qbsw.security.core.model.domain.CUser;
import sk.qbsw.security.management.service.IGroupService;
import sk.qbsw.security.management.test.util.CDataGenerator;

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
public class CGroupTestCase
{
	/** The database data generator. */
	@Autowired
	private CDataGenerator dataGenerator;

	/** The group service. */
	@Autowired
	@Qualifier ("cGroupService")
	private IGroupService groupService;

	/** Unit dao. */
	@Autowired
	private IUnitDao unitDao;

	@Autowired
	private IUserDao userDao;

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

		CUnit unit = unitDao.findOneByName(CDataGenerator.FIRST_UNIT_CODE);
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

		CUnit unit1 = unitDao.findOneByName(CDataGenerator.SECOND_UNIT_CODE);
		CUser user2 = userDao.findOneByLogin(CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE);

		List<CGroup> groups = groupService.getByUnitUser(unit1, user2);
		Assert.assertEquals("Get all groups failed: the size of list of groups is not 0", groups.size(), 0);

		CUser user1 = userDao.findOneByLogin(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		groups = groupService.getByUnitUser(unit1, user1);
		Assert.assertEquals("Get all groups failed: the size of list of groups is not 2", groups.size(), 2);

		CUnit unit2 = unitDao.findOneByName(CDataGenerator.SECOND_UNIT_CODE);
		groups = groupService.getByUnitUser(unit2, user1);
		Assert.assertEquals("Get all groups failed: the size of list of groups is not 2", groups.size(), 2);
	}

	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testGetByCodeAndUnit () throws CSecurityException
	{
		initTest();

		CUnit unit = unitDao.findOneByName(CDataGenerator.SECOND_UNIT_CODE);

		List<CGroup> groups = groupService.getByCodeAndUnit(CDataGenerator.FIRST_GROUP_IN_UNIT_CODE, unit);
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
