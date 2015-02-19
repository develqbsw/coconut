package sk.qbsw.core.security.test;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.security.dao.IGroupDao;
import sk.qbsw.core.security.dao.IUnitDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.dao.IXUserUnitGroupDao;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.domain.CXUserUnitGroup;
import sk.qbsw.core.security.model.jmx.IAuthenticationConfigurator;
import sk.qbsw.core.security.service.IOrganizationService;
import sk.qbsw.core.security.service.IUserService;
import sk.qbsw.core.security.test.util.CDataGenerator;

/**
 * Checks user service.
 *
 * @autor Tomas Lauro
 * 
 * @version 1.13.0
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

	/** The cross user unit group dao. */
	@Autowired
	private IXUserUnitGroupDao crossUserUnitGroupDao;

	/** The authentication configurator. */
	@Autowired
	private IAuthenticationConfigurator authenticationConfigurator;

	/**
	 * Test initialization.
	 */
	@Before
	public void testInitialization ()
	{
		assertNotNull("Could not find user service", userService);
		authenticationConfigurator.setPasswordPattern("((?=.*[a-z])(?=.*[@#$%_]).{6,40})");
	}

	/**
	 * Test create user with password.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testCreateUserWithPassword () throws CSecurityException
	{
		initTest();

		COrganization organization = orgService.getOrganizationByName(CDataGenerator.ORGANIZATION_CODE).get(0);

		CUser user = new CUser();
		user.setLogin(CDataGenerator.USER_CREATED);
		user.setName(CDataGenerator.USER_CREATED);

		userService.registerNewUser(user, CDataGenerator.USER_CREATED, organization);

		CUser queryUser = userService.getUserByLogin(CDataGenerator.USER_CREATED);

		//asserts
		assertNotNull("User has not been created", queryUser);
	}

	/**
	 * Test create user without password.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testCreateUserWithoutPassword () throws CSecurityException
	{
		initTest();

		COrganization organization = orgService.getOrganizationByName(CDataGenerator.ORGANIZATION_CODE).get(0);

		CUser user = new CUser();
		user.setLogin(CDataGenerator.USER_CREATED);
		user.setName(CDataGenerator.USER_CREATED);

		userService.registerNewUser(user, organization);

		CUser queryUser = userService.getUserByLogin(CDataGenerator.USER_CREATED);

		//asserts
		assertNotNull("User has not been created", queryUser);
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

		//checks if there are all test users in the list and if they have correctly set groups.
		checksGetAllUsersList(users);
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

		//checks if there are all test users in the list and if they have correctly set groups.
		checksGetAllUsersList(users);
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

		List<CUser> usersInGroupInUnit = userService.getUsers(null, null, null, null, CDataGenerator.SECOND_GROUP_IN_UNIT_CODE.substring(0, 12));
		List<CUser> usersInGroupNotInUnit = userService.getUsers(null, null, null, null, CDataGenerator.FIRST_GROUP_NOT_IN_UNIT_CODE.substring(0, 20));

		//asserts
		assertNotNull("Get all users failed: list of users is null", usersInGroupInUnit);
		assertNotNull("Get all users failed: list of users is null", usersInGroupNotInUnit);
		Assert.assertEquals("Get all users failed: the expected count of user is 5 ", 5, usersInGroupInUnit.size());
		Assert.assertEquals("Get all users failed: the expected count of user is 4 ", 4, usersInGroupNotInUnit.size());
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
		CUser userById = userService.get(userByLogin.getId());

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

		//preparation
		CUnit firstUnit = unitDao.findOneByName(CDataGenerator.FIRST_UNIT_CODE);

		List<CGroup> firstGroupInUnit = groupDao.findByCode(CDataGenerator.FIRST_GROUP_IN_UNIT_CODE);
		Assert.assertTrue("The group firstGroupInUnit not found", firstGroupInUnit.size() > 0);

		List<CGroup> thirdGroupInUnit = groupDao.findByCode(CDataGenerator.THIRD_GROUP_IN_UNIT_CODE);
		Assert.assertTrue("The group thirdGroupInUnit not found", firstGroupInUnit.size() > 0);

		//tests
		List<CUser> users = userDao.findAllUsers(firstUnit, firstGroupInUnit.get(0));
		Assert.assertEquals("The expected count of users with firstGroupInUnit is 0 ", users.size(), 0);

		users = userDao.findAllUsers(firstUnit, thirdGroupInUnit.get(0));
		Assert.assertEquals("The expected count of users with thirdGroupInUnit is 2 ", users.size(), 2);
	}

	/**
	 * Test get user by email.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testGetAllByEmail () throws CSecurityException
	{
		initTest();

		String email = CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE + "@qbsw.sk";
		List<CUser> users = userService.getUsers(email);

		//asserts
		assertNotNull("Get user by email failed: cannot find user with email " + email, users);
		Assert.assertEquals("Get user by email failed: the number of users with email " + email + " is not 1", users.size(), 1);

		//checks if the user has a correct set of groups.
		checksUserHasCorrectGroups(users.get(0));
	}

	/**
	 * Test get user by name and organization
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testGetAllByNameAndOrganization () throws CSecurityException
	{
		initTest();

		COrganization organization = orgService.getOrganizationByName(CDataGenerator.ORGANIZATION_CODE).get(0);
		COrganization organization2 = orgService.getOrganizationByName(CDataGenerator.ORGANIZATION_2_CODE).get(0);
		List<CUser> users = userService.getUsers(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null, null, true, organization);

		//asserts
		assertNotNull("Get user by name and organization failed: cannot find users", users);
		Assert.assertEquals("Get user by name and organization failed: the number of users is not 1", users.size(), 1);

		users = userService.getUsers(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null, null, true, organization2);

		//asserts
		assertNotNull("Get user by name and organization failed: cannot find users", users);
		Assert.assertEquals("Get user by name and organization failed: the number of users is not 0", users.size(), 0);
	}

	/**
	 * Test unset group with unit.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testUnsetGroupWithUnit () throws CSecurityException
	{
		initTest();

		//test data
		CUser testUser = userDao.findByLogin(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		CGroup testGroup = groupDao.findByCode(CDataGenerator.FIRST_GROUP_IN_UNIT_CODE).get(0);
		CUnit testUnit = unitDao.findOneByName(CDataGenerator.DEFAULT_UNIT_CODE);

		//unset group
		userService.unsetUserFromGroup(testUser, testGroup, testUnit);

		List<CXUserUnitGroup> result = crossUserUnitGroupDao.findByUserAndUnitAndGroup(testUser, testUnit, testGroup);

		//asserts
		assertNotNull("Test unset group failed: cannot find result ", result);
		Assert.assertTrue("Test unset group failed: the number of user groups is not 0", result.size() == 0);
	}

	/**
	 * Test set group with unit.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testSetGroupWithUnit () throws CSecurityException, CBusinessException
	{
		initTest();

		//test data
		CUser testUser = userDao.findByLogin(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		CGroup testGroup = groupDao.findByCode(CDataGenerator.THIRD_GROUP_IN_UNIT_CODE).get(0);
		CUnit testUnit = unitDao.findOneByName(CDataGenerator.DEFAULT_UNIT_CODE);

		//set group
		userService.setUserToGroup(testUser, testGroup, testUnit);

		List<CXUserUnitGroup> result = crossUserUnitGroupDao.findByUserAndUnitAndGroup(testUser, testUnit, testGroup);

		//asserts
		assertNotNull("Test unset group failed: cannot find result ", result);
		Assert.assertTrue("Test unset group failed: the number of user groups is not 1", result.size() == 1);
	}

	/**
	 * Test set group with unit - group is already set to user.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = CBusinessException.class)
	@Transactional
	@Rollback (true)
	public void testSetGroupWithUnitAlreadySet () throws CSecurityException, CBusinessException
	{
		initTest();

		//test data
		CUser testUser = userDao.findByLogin(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		CGroup testGroup = groupDao.findByCode(CDataGenerator.FIRST_GROUP_IN_UNIT_CODE).get(0);
		CUnit testUnit = unitDao.findOneByName(CDataGenerator.DEFAULT_UNIT_CODE);

		//set group
		userService.setUserToGroup(testUser, testGroup, testUnit);
	}

	/**
	 * Test get users by pin.
	 *
	 * @throws CBusinessException the c business exception
	 */
	@Test
	@Transactional (readOnly = true)
	@Rollback (true)
	public void testGetUsersByPin () throws CBusinessException
	{
		initTest();

		List<CUser> users = userDao.findByPin("1111");

		//asserts
		assertNotNull(users);
		Assert.assertSame(1, users.size());
	}

	/**
	 * Checks list of users has correct count of test users.
	 *
	 * @param user the user
	 */
	private void checksGetAllUsersList (List<CUser> users)
	{
		boolean userWithDefaulUnitCodePresent = false;
		boolean userWithoutDefaulUnitCodePresent = false;
		boolean userWithDefaulUnitCodeNoGroupPresent = false;
		boolean userWithoutDefaulUnitCodeNoGroupPresent = false;

		for (CUser user : users)
		{
			//checks if all test users are in list
			if (user.getLogin().equals(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE))
			{
				Assert.assertFalse("Get all users failed: there is more than one userWithDefaulUnitCode ", userWithDefaulUnitCodePresent);
				userWithDefaulUnitCodePresent = true;
			}
			else if (user.getLogin().equals(CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE))
			{
				Assert.assertFalse("Get all users failed: there is more than one userWithoutDefaulUnitCode ", userWithoutDefaulUnitCodePresent);
				userWithoutDefaulUnitCodePresent = true;
			}
			else if (user.getLogin().equals(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE_NO_GROUP))
			{
				Assert.assertFalse("Get all users failed: there is more than one userWithDefaulUnitCodeNoGroup ", userWithDefaulUnitCodeNoGroupPresent);
				userWithDefaulUnitCodeNoGroupPresent = true;
			}
			else if (user.getLogin().equals(CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE_NO_GROUP))
			{
				Assert.assertFalse("Get all users failed: there is more than one userWithoutDefaulUnitCodeNoGroup ", userWithoutDefaulUnitCodeNoGroupPresent);
				userWithoutDefaulUnitCodeNoGroupPresent = true;
			}

			//test if the users has correct groups
			checksUserHasCorrectGroups(user);
		}

		Assert.assertTrue("Get all users failed: there is missing a test user", (userWithDefaulUnitCodePresent == true && userWithoutDefaulUnitCodePresent == true && userWithDefaulUnitCodeNoGroupPresent == true && userWithoutDefaulUnitCodeNoGroupPresent == true));
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
		else if (user.getLogin().equals(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE_NO_GROUP))
		{
			Assert.assertEquals("Get all users failed: the expected count of users group is 0 ", user.getGroups().size(), 0);
		}
		else if (user.getLogin().equals(CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE_NO_GROUP))
		{
			Assert.assertEquals("Get all users failed: the expected count of users group is 0 ", user.getGroups().size(), 0);
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
