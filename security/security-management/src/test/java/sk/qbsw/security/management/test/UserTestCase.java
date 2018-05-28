package sk.qbsw.security.management.test;

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
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.core.dao.GroupDao;
import sk.qbsw.security.core.dao.UnitDao;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AccountUnitGroupDao;
import sk.qbsw.security.core.model.domain.*;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.configuration.SecurityCoreConfigurator;
import sk.qbsw.security.core.model.order.OrderModel;
import sk.qbsw.security.core.model.order.OrderSpecification;
import sk.qbsw.security.core.model.order.OrderSpecifiers;
import sk.qbsw.security.core.model.order.UserOrderByAttributeSpecifiers;
import sk.qbsw.security.core.model.order.OrderByAttributeSpecifier;
import sk.qbsw.security.management.service.OrganizationService;
import sk.qbsw.security.management.service.UserManagementService;
import sk.qbsw.security.management.service.UserPermissionManagementService;
import sk.qbsw.security.management.test.util.DataGenerator;

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
@Rollback (true)
public class UserTestCase
{
	/** The database data generator. */
	@Autowired
	private DataGenerator dataGenerator;

	/** The unit service. */
	@Autowired
	@Qualifier ("userManagementService")
	private UserManagementService userManagementService;

	@Autowired
	@Qualifier ("userPermissionManagementService")
	private UserPermissionManagementService userPermissionManagementService;

	@Autowired
	private AccountDao userDao;

	/** The group dao. */
	@Autowired
	private GroupDao groupDao;

	@Autowired
	private OrganizationService orgService;

	@Autowired
	private UnitDao unitDao;

	/** The cross user unit group dao. */
	@Autowired
	private AccountUnitGroupDao crossUserUnitGroupDao;

	/** The Authentication Configurator. */
	@Autowired
	private SecurityCoreConfigurator securityCoreConfigurator;

	/**
	 * Test initialization.
	 */
	@Before
	public void testInitialization ()
	{
		assertNotNull("Could not find user service", userManagementService);
		securityCoreConfigurator.setPasswordPattern("((?=.*[a-z])(?=.*[@#$%_]).{6,40})");
	}

	/**
	 * Test create user with password.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testCreateUserWithPassword () throws CSecurityException
	{
		initTest();

		Organization organization = orgService.getOrganizationByName(DataGenerator.ORGANIZATION_CODE).get(0);

		Account user = new Account();
		user.setLogin(DataGenerator.USER_CREATED);
		user.setName(DataGenerator.USER_CREATED);

		userManagementService.registerNewUser(user, DataGenerator.USER_CREATED, organization);

		Account queryUser = userManagementService.getUserByLogin(DataGenerator.USER_CREATED);

		//asserts
		assertNotNull("User has not been created", queryUser);
	}

	/**
	 * Test create user without password.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testCreateUserWithoutPassword () throws CSecurityException
	{
		initTest();

		Organization organization = orgService.getOrganizationByName(DataGenerator.ORGANIZATION_CODE).get(0);

		Account user = new Account();
		user.setLogin(DataGenerator.USER_CREATED);
		user.setName(DataGenerator.USER_CREATED);

		userManagementService.registerNewUser(user, organization);

		Account queryUser = userManagementService.getUserByLogin(DataGenerator.USER_CREATED);

		//asserts
		assertNotNull("User has not been created", queryUser);
	}

	/**
	 * Test get all users.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testGetAll () throws CSecurityException
	{
		initTest();

		List<Account> users = userManagementService.getUsers();

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
	@Transactional (transactionManager = "transactionManager")
	public void testGetAllOrderByOrganization () throws CSecurityException
	{
		initTest();

		List<Account> users = userManagementService.getUsersOrderByOrganization(null, null, null);

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
	@Transactional (transactionManager = "transactionManager")
	public void testGetAllByGroupCodePrefix () throws CSecurityException
	{
		initTest();

		List<Account> usersInGroupInUnit = userManagementService.getUsers(null, null, null, null, DataGenerator.SECOND_GROUP_IN_UNIT_CODE.substring(0, 12));
		List<Account> usersInGroupNotInUnit = userManagementService.getUsers(null, null, null, null, DataGenerator.FIRST_GROUP_NOT_IN_UNIT_CODE.substring(0, 20));

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
	@Transactional (transactionManager = "transactionManager")
	public void testGetAllByGroup () throws CSecurityException
	{
		initTest();

		List<Group> groups = groupDao.findByCode(DataGenerator.FIRST_GROUP_IN_UNIT_CODE);

		List<Account> users = userManagementService.getUsers(null, null, groups.get(0));

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
	@Transactional (transactionManager = "transactionManager")
	public void testGetAllByGroupOrderByOrganization () throws CSecurityException
	{
		initTest();

		List<Group> groups = groupDao.findByCode(DataGenerator.FIRST_GROUP_IN_UNIT_CODE);

		List<Account> users = userManagementService.getUsersOrderByOrganization(null, null, groups.get(0));

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
	@Transactional (transactionManager = "transactionManager")
	public void testGet () throws CSecurityException
	{
		initTest();

		Account userByLogin = userManagementService.getUserByLogin(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		Account userById = userManagementService.get(userByLogin.getId());

		//asserts
		assertNotNull("Get user by id failed: cannot find user with login " + userByLogin.getLogin(), userByLogin);
		assertNotNull("Get user by id failed: the id of user with login " + userByLogin.getLogin() + " is incorrect", userById);
	}

	/**
	 * 
	 * @throws CSecurityException
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testGetAllByGroupAndUnit () throws CSecurityException
	{
		initTest();

		//preparation
		Unit firstUnit = unitDao.findOneByName(DataGenerator.FIRST_UNIT_CODE);

		List<Group> firstGroupInUnit = groupDao.findByCode(DataGenerator.FIRST_GROUP_IN_UNIT_CODE);
		Assert.assertTrue("The group firstGroupInUnit not found", firstGroupInUnit.size() > 0);

		List<Group> thirdGroupInUnit = groupDao.findByCode(DataGenerator.THIRD_GROUP_IN_UNIT_CODE);
		Assert.assertTrue("The group thirdGroupInUnit not found", firstGroupInUnit.size() > 0);

		OrderModel<UserOrderByAttributeSpecifiers> orderModel = new OrderModel<UserOrderByAttributeSpecifiers>();
		orderModel.getOrderSpecification().add(new OrderSpecification<OrderByAttributeSpecifier>(UserOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		//tests
		List<Account> users = userDao.findByUnitAndGroup(firstUnit, firstGroupInUnit.get(0), orderModel);
		Assert.assertEquals("The expected count of users with firstGroupInUnit is 0 ", users.size(), 0);

		users = userDao.findByUnitAndGroup(firstUnit, thirdGroupInUnit.get(0), orderModel);
		Assert.assertEquals("The expected count of users with thirdGroupInUnit is 2 ", users.size(), 2);
	}

	/**
	 * Test get user by email.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testGetAllByEmail () throws CSecurityException
	{
		initTest();

		String email = DataGenerator.USER_WITH_DEFAULT_UNIT_CODE + "@qbsw.sk";
		List<Account> users = userManagementService.getUsers(email);

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
	@Transactional (transactionManager = "transactionManager")
	public void testGetAllByNameAndOrganization () throws CSecurityException
	{
		initTest();

		Organization organization = orgService.getOrganizationByName(DataGenerator.ORGANIZATION_CODE).get(0);
		Organization organization2 = orgService.getOrganizationByName(DataGenerator.ORGANIZATION_2_CODE).get(0);
		List<Account> users = userManagementService.getUsers(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null, null, true, organization);

		//asserts
		assertNotNull("Get user by name and organization failed: cannot find users", users);
		Assert.assertEquals("Get user by name and organization failed: the number of users is not 1", users.size(), 1);

		users = userManagementService.getUsers(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null, null, true, organization2);

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
	@Transactional (transactionManager = "transactionManager")
	public void testUnsetGroupWithUnit () throws CSecurityException
	{
		initTest();

		//test data
		Account testUser = userDao.findOneByLogin(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		Group testGroup = groupDao.findByCode(DataGenerator.FIRST_GROUP_IN_UNIT_CODE).get(0);
		Unit testUnit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);

		//unset group
		userPermissionManagementService.unsetUserFromGroup(testUser, testGroup, testUnit);

		List<AccountUnitGroup> result = crossUserUnitGroupDao.findByUserAndUnitAndGroup(testUser, testUnit, testGroup);

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
	@Transactional (transactionManager = "transactionManager")
	public void testSetGroupWithUnit () throws CSecurityException, CBusinessException
	{
		initTest();

		//test data
		Account testUser = userDao.findOneByLogin(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		Group testGroup = groupDao.findByCode(DataGenerator.THIRD_GROUP_IN_UNIT_CODE).get(0);
		Unit testUnit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);

		//set group
		userPermissionManagementService.setUserToGroup(testUser, testGroup, testUnit);

		List<AccountUnitGroup> result = crossUserUnitGroupDao.findByUserAndUnitAndGroup(testUser, testUnit, testGroup);

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
	@Transactional (transactionManager = "transactionManager")
	public void testSetGroupWithUnitAlreadySet () throws CSecurityException, CBusinessException
	{
		initTest();

		//test data
		Account testUser = userDao.findOneByLogin(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		Group testGroup = groupDao.findByCode(DataGenerator.FIRST_GROUP_IN_UNIT_CODE).get(0);
		Unit testUnit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);

		//set group
		userPermissionManagementService.setUserToGroup(testUser, testGroup, testUnit);
	}

	/**
	 * Test get users by pin.
	 *
	 * @throws CBusinessException the c business exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testGetUsersByPin () throws CBusinessException
	{
		initTest();

		List<Account> users = userDao.findByPinCode("1111");

		//asserts
		assertNotNull(users);
		Assert.assertSame(1, users.size());
	}

	/**
	 * Checks list of users has correct count of test users.
	 *
	 * @param user the user
	 */
	private void checksGetAllUsersList (List<Account> users)
	{
		boolean userWithDefaulUnitCodePresent = false;
		boolean userWithoutDefaulUnitCodePresent = false;
		boolean userWithDefaulUnitCodeNoGroupPresent = false;
		boolean userWithoutDefaulUnitCodeNoGroupPresent = false;

		for (Account user : users)
		{
			//checks if all test users are in list
			if (user.getLogin().equals(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE))
			{
				Assert.assertFalse("Get all users failed: there is more than one userWithDefaulUnitCode ", userWithDefaulUnitCodePresent);
				userWithDefaulUnitCodePresent = true;
			}
			else if (user.getLogin().equals(DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE))
			{
				Assert.assertFalse("Get all users failed: there is more than one userWithoutDefaulUnitCode ", userWithoutDefaulUnitCodePresent);
				userWithoutDefaulUnitCodePresent = true;
			}
			else if (user.getLogin().equals(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE_NO_GROUP))
			{
				Assert.assertFalse("Get all users failed: there is more than one userWithDefaulUnitCodeNoGroup ", userWithDefaulUnitCodeNoGroupPresent);
				userWithDefaulUnitCodeNoGroupPresent = true;
			}
			else if (user.getLogin().equals(DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE_NO_GROUP))
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
	private void checksUserHasCorrectGroups (Account user)
	{
		if (user.getLogin().equals(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE))
		{
			Assert.assertEquals("Get all users failed: the expected count of users group is 2 ", user.getGroups().size(), 2);
			for (Group group : user.getGroups())
			{
				Assert.assertTrue("Get all users failed: the user with login " + user.getLogin() + " has unexpected group with code " + group.getCode(), group.getCode().equals(DataGenerator.FIRST_GROUP_IN_UNIT_CODE) || group.getCode().equals(DataGenerator.SECOND_GROUP_IN_UNIT_CODE));
			}
		}
		else if (user.getLogin().equals(DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE))
		{
			Assert.assertEquals("Get all users failed: the expected count of users group is 2 ", user.getGroups().size(), 2);
			for (Group group : user.getGroups())
			{
				Assert.assertTrue("Get all users failed: the user with login " + user.getLogin() + " has unexpected group with code " + group.getCode(), group.getCode().equals(DataGenerator.FIRST_GROUP_NOT_IN_UNIT_CODE) || group.getCode().equals(DataGenerator.SECOND_GROUP_NOT_IN_UNIT_CODE));
			}
		}
		else if (user.getLogin().equals(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE_NO_GROUP))
		{
			Assert.assertEquals("Get all users failed: the expected count of users group is 0 ", user.getGroups().size(), 0);
		}
		else if (user.getLogin().equals(DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE_NO_GROUP))
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
