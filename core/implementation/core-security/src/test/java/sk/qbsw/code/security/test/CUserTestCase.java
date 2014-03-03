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
import sk.qbsw.core.security.dao.IGroupDao;
import sk.qbsw.core.security.dao.IUnitDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.service.IOrganizationService;
import sk.qbsw.core.security.service.IUserService;

/**
 * Checks user service.
 *
 * @autor Tomas Lauro
 * @version 1.6.0
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@TransactionConfiguration (transactionManager = "transactionManager", defaultRollback = true)
public class CUserTestCase
{
	/** The database data generator. */
	@Autowired
	private CDataGenerator dataGenerator;

	/** The unit service. */
	@Autowired
	@Qualifier ("cUserService")
	private IUserService userService;
	
	@Autowired
	private IUserDao userDao;

	/** The group dao. */
	@Autowired
	private IGroupDao groupDao;

	@Autowired
	private IOrganizationService orgService;
	
	@Autowired
	private IUnitDao unitDao;

	/**
	 * Test initialization.
	 */
	@Test
	public void testInitialization ()
	{
		assertNotNull("Could not find user service", userService);
	}

	/**
	 * Test get all users.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testGetAll () throws CSecurityException
	{
		initTest();

		List<CUser> users = userService.getUsers();

		//asserts
		assertNotNull("Get all users failed: list of users is null", users);
		Assert.assertTrue("Get all users failed: list of users is empty", users.size() > 0);
	}

	/**
	 * Test get all users.
	 *
	 * @throws CSecurityException the security exception
	 */

	@Test
	@Transactional
	@Rollback (true)
	public void testGetAllOrderByOrganization () throws CSecurityException
	{
		initTest();

		List<CUser> users = userService.getUsersOrderByOrganization(null, null, null);

		//asserts
		assertNotNull("Get all users order by organization failed: list of users is null", users);
		Assert.assertTrue("Get all users order by organization failed: list of users is empty", users.size() > 0);
	}

	/**
	 * Test get all users.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testGetAllByGroupCodePrefix () throws CSecurityException
	{
		initTest();

		List<CUser> twoUsers = userService.getUsers(null, null, null, null, CDataGenerator.SECOND_GROUP_IN_UNIT_CODE.substring(0, 12));
		List<CUser> oneUser = userService.getUsers(null, null, null, null, CDataGenerator.THIRD_GROUP_IN_UNIT_CODE);

		//asserts
		assertNotNull("Get all users failed: list of users is null", twoUsers);
		assertNotNull("Get all users failed: list of users is null", oneUser);
		Assert.assertEquals("Get all users failed: the expected count of user is 2 ", twoUsers.size(), 2);
		Assert.assertEquals("Get all users failed: the expected count of user is 1 ", oneUser.size(), 1);
	}

	/**
	 * Test get all users.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testGetAllByGroup () throws CSecurityException
	{
		initTest();

		List<CGroup> groups = groupDao.findByCode(CDataGenerator.FIRST_GROUP_IN_UNIT_CODE);

		List<CUser> users = userService.getUsers(null, null, groups.get(0));

		//asserts
		assertNotNull("Get all users by group failed: list of users is null", users);
		Assert.assertEquals("Get all by group users failed: the expected count of user is 1 ", users.size(), 1);
	}

	/**
	 * Test get all users.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testGetAllByGroupOrderByOrganization () throws CSecurityException
	{
		initTest();

		List<CGroup> groups = groupDao.findByCode(CDataGenerator.FIRST_GROUP_IN_UNIT_CODE);

		List<CUser> users = userService.getUsersOrderByOrganization(null, null, groups.get(0));

		//asserts
		assertNotNull("Get all users by group failed: list of users is null", users);
		Assert.assertEquals("Get all by group users failed: the expected count of user is 1 ", users.size(), 1);
	}
	
	/**
	 * 
	 * @throws CSecurityException
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testGetAllByGroupAndUnit () throws CSecurityException
	{
		initTest();

		List<CGroup> groups1 = groupDao.findByCode(CDataGenerator.FIRST_GROUP_IN_UNIT_CODE);
		Assert.assertTrue("No groups found", groups1.size() > 0);
		
		CUnit unit = unitDao.findByName(CDataGenerator.FIRST_UNIT_CODE);
		
		List<CUser> users = userDao.findByUnitGroup(unit, groups1.get(0));
		Assert.assertTrue("User is null", users.size() == 0);
		
		List<CGroup> groups3 = groupDao.findByCode(CDataGenerator.THIRD_GROUP_IN_UNIT_CODE);
		users = userDao.findByUnitGroup(unit, groups3.get(0));
		Assert.assertTrue("User is null", users.size() == 1);
	}

	/**
	 * Inits the test.
	 */
	private void initTest ()
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
	}
}
