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
import sk.qbsw.core.security.dao.IUnitDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.service.IGroupService;

/**
 * Checks group service.
 *
 * @autor Tomas Lauro
 * @version 1.6.0
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@TransactionConfiguration (transactionManager = "transactionManager", defaultRollback = true)
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
	@Transactional
	@Rollback (true)
	public void testGetByUnit () throws CSecurityException
	{
		initTest();

		CUnit unit = unitDao.findByName(CDataGenerator.FIRST_UNIT_CODE);
		List<CGroup> groups = groupService.getByUnit(unit);

		//asserts
		assertNotNull("Get all groups failed: list of groups is null", groups);
		Assert.assertEquals("Get all groups failed: the size of list of groups is not 2", groups.size(), 2);
	}
	
	@Test
	@Transactional
	@Rollback (true)
	public void testGetByUnitUser () throws CSecurityException
	{
		initTest();

		CUnit unit1 = unitDao.findByName(CDataGenerator.FIRST_UNIT_CODE);
		CUser user2 = userDao.findByLogin(CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE);
		
		List<CGroup> groups = groupService.getByUnitUser(unit1, user2);
		Assert.assertEquals("Get all groups failed: the size of list of groups is not 0", groups.size(), 0);
		
		CUser user1 = userDao.findByLogin(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		groups = groupService.getByUnitUser(unit1, user1);
		Assert.assertEquals("Get all groups failed: the size of list of groups is not 2", groups.size(), 2);
		
		CUnit unit2 = unitDao.findByName(CDataGenerator.SECOND_UNIT_CODE);
		groups = groupService.getByUnitUser(unit2, user1);
		Assert.assertEquals("Get all groups failed: the size of list of groups is not 2", groups.size(), 2);
	}

	/**
	 * Inits the test.
	 */
	private void initTest ()
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
	}
}
