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
 * @version 1.7.0
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

		for (CUser user : users)
		{
			checksUserHasCorrectGroups(user);
		}
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

		for (CUser user : users)
		{
			checksUserHasCorrectGroups(user);
		}
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
		List<CUser> oneUser = userService.getUsers(null, null, null, null, CDataGenerator.FIRST_GROUP_NOT_IN_UNIT_CODE.substring(0, 20));

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
	 * Test get user by id.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testGet () throws CSecurityException
	{
		initTest();

		CUser userByLogin = userService.getUserByLogin(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		CUser userById = userService.get(userByLogin.getPkId());

		//asserts
		assertNotNull("Get user by id failed: cannot find user with login " + userByLogin.getLogin(), userByLogin);
		assertNotNull("Get user by id failed: the id of user with login " + userByLogin.getLogin() + " is incorrect", userById);
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

		List<CUser> users = userDao.findAllUsers(unit, groups1.get(0));
		Assert.assertTrue("User is null", users.size() == 0);

		List<CGroup> groups3 = groupDao.findByCode(CDataGenerator.THIRD_GROUP_IN_UNIT_CODE);
		users = userDao.findAllUsers(unit, groups3.get(0));
		Assert.assertTrue("User is null", users.size() == 1);
	}

	/**
	 * Checks user has correct groups.
	 *
	 * @param user the user
	 */
	private void checksUserHasCorrectGroups (CUser user)
	{
		if (user.getLogin().equals(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE))
		{
			Assert.assertEquals("Get all users failed: the expected count of users group is 2 ", user.getGroups().size(), 2);
			for (CGroup group : user.getGroups())
			{
				Assert.assertTrue("Get all users failed: the user with login " + user.getLogin() + " has unexpected group with code " + group.getCode(), group.getCode().equals(CDataGenerator.FIRST_GROUP_IN_UNIT_CODE) || group.getCode().equals(CDataGenerator.SECOND_GROUP_IN_UNIT_CODE));
			}
		}
		else if (user.getLogin().equals(CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE))
		{
			Assert.assertEquals("Get all users failed: the expected count of users group is 2 ", user.getGroups().size(), 2);
			for (CGroup group : user.getGroups())
			{
				Assert.assertTrue("Get all users failed: the user with login " + user.getLogin() + " has unexpected group with code " + group.getCode(), group.getCode().equals(CDataGenerator.FIRST_GROUP_NOT_IN_UNIT_CODE) || group.getCode().equals(CDataGenerator.SECOND_GROUP_NOT_IN_UNIT_CODE));
			}
		}
	}

	/**
	 * Inits the test.
	 */
	private void initTest ()
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
	}
}
