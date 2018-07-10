package sk.qbsw.security.organization.complex.management.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.organization.core.model.domain.User;
import sk.qbsw.organization.management.service.UserService;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AccountUnitGroupDao;
import sk.qbsw.security.core.dao.GroupDao;
import sk.qbsw.security.core.dao.UnitDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.AccountTypes;
import sk.qbsw.security.core.model.domain.Organization;
import sk.qbsw.security.management.service.AccountManagementService;
import sk.qbsw.security.management.service.AccountPermissionManagementService;
import sk.qbsw.security.management.service.OrganizationService;
import sk.qbsw.security.organization.complex.management.service.UserAccountService;
import sk.qbsw.security.organization.complex.management.test.util.DataGenerator;

/**
 * Checks user service.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@Rollback
@Ignore
public class UserAccountTestCase
{
	@Autowired
	private DataGenerator dataGenerator;


	@Autowired
	private AccountManagementService accountManagementService;

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private UserService userService;

	@Autowired
	private AccountPermissionManagementService accountPermissionManagementService;

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private UnitDao unitDao;

	@Autowired
	private AccountUnitGroupDao accountUnitGroupDao;

	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testCreateUserAccountWithPassword () throws CSecurityException
	{
		initTest();

		Organization organization = organizationService.findByName(DataGenerator.ORGANIZATION_CODE).get(0);

//		Account user = new Account();
//		user.setLogin(DataGenerator.ACCOUNT_CREATED);
//
//		accountManagementService.create(user, DataGenerator.ACCOUNT_CREATED, organization);
		User user = userService.findAll().get(0);

		userAccountService.create(DataGenerator.ACCOUNT_CREATED, null, AccountTypes.PERSONAL, user, DataGenerator.ACCOUNT_CREATED, organization);

		Account queryAccount = accountManagementService.findByByLogin(DataGenerator.ACCOUNT_CREATED);

		// asserts
		assertNotNull("Account has not been created", queryAccount);
	}

	/**
	 * Test create account without password.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testCreateUserAccountWithoutPassword () throws CSecurityException
	{
		initTest();

		Organization organization = organizationService.findByName(DataGenerator.ORGANIZATION_CODE).get(0);

//		Account account = new Account();
//		account.setLogin(DataGenerator.ACCOUNT_CREATED);
//
//		accountManagementService.register(account, organization);
		User user = userService.findAll().get(0);

		userAccountService.create(DataGenerator.ACCOUNT_CREATED, null, AccountTypes.PERSONAL, user, organization);

		Account queryAccount = accountManagementService.findByByLogin(DataGenerator.ACCOUNT_CREATED);

		// asserts
		assertNotNull("Account has not been created", queryAccount);
	}

//	/**
//	 * Test get all.
//	 */
//	@Test
//	@Transactional (transactionManager = "transactionManager")
//	public void testGetAll ()
//	{
//		initTest();
//
//		List<Account> accounts = accountManagementService.findAll();
//
//		// asserts
//		assertNotNull("Get all accounts failed: list of accounts is null", accounts);
//
//		// checks if there are all test accounts in the list and if they have correctly set groups.
//		checksGetAllAccountsList(accounts);
//	}

//	/**
//	 * Test get all order by organization.
//	 */
//	@Test
//	@Transactional (transactionManager = "transactionManager")
//	public void testGetAllOrderByOrganization ()
//	{
//		initTest();
//
//		List<Account> accounts = accountManagementService.findByOrganizationAndStateAndGroupOrderByOrganizationNameAndLogin(null, null, null);
//
//		// asserts
//		assertNotNull("Get all accounts order by organization failed: list of accounts is null", accounts);
//
//		// checks if there are all test accounts in the list and if they have correctly set groups.
//		checksGetAllAccountsList(accounts);
//	}
//
//	/**
//	 * Test get all by group code prefix.
//	 */
//	@Test
//	@Transactional (transactionManager = "transactionManager")
//	public void testGetAllByGroupCodePrefix ()
//	{
//		initTest();
//
//		List<Account> accountsInGroupInUnit = accountManagementService.findByLoginAndStateAndGroupPrefix(null, null, DataGenerator.SECOND_GROUP_IN_UNIT_CODE.substring(0, 12));
//		List<Account> accountsInGroupNotInUnit = accountManagementService.findByLoginAndStateAndGroupPrefix(null, null, DataGenerator.FIRST_GROUP_NOT_IN_UNIT_CODE.substring(0, 20));
//
//		// asserts
//		assertNotNull("Get all accounts failed: list of accounts is null", accountsInGroupInUnit);
//		assertNotNull("Get all accounts failed: list of accounts is null", accountsInGroupNotInUnit);
//		Assert.assertEquals("Get all accounts failed: the expected count of account is 5 ", 5, accountsInGroupInUnit.size());
//		Assert.assertEquals("Get all accounts failed: the expected count of account is 4 ", 4, accountsInGroupNotInUnit.size());
//	}
//
//	/**
//	 * Test get all by group.
//	 *
//	 * @throws CSecurityException the c security exception
//	 */
//	@Test
//	@Transactional (transactionManager = "transactionManager")
//	public void testGetAllByGroup () throws CSecurityException
//	{
//		initTest();
//
//		Group group = groupDao.findOneByCode(DataGenerator.FIRST_GROUP_IN_UNIT_CODE);
//
//		List<Account> accounts = accountManagementService.findByOrganizationAndStateAndGroup(null, null, group);
//
//		// asserts
//		assertNotNull("Get all accounts by group failed: list of accounts is null", accounts);
//		Assert.assertEquals("Get all by group accounts failed: the expected count of account is 1 ", accounts.size(), 1);
//	}
//
//	/**
//	 * Test get all by group order by organization.
//	 *
//	 * @throws CSecurityException the c security exception
//	 */
//	@Test
//	@Transactional (transactionManager = "transactionManager")
//	public void testGetAllByGroupOrderByOrganization () throws CSecurityException
//	{
//		initTest();
//
//		Group group = groupDao.findOneByCode(DataGenerator.FIRST_GROUP_IN_UNIT_CODE);
//
//		List<Account> accounts = accountManagementService.findByOrganizationAndStateAndGroupOrderByOrganizationNameAndLogin(null, null, group);
//
//		// asserts
//		assertNotNull("Get all accounts by group failed: list of accounts is null", accounts);
//		Assert.assertEquals("Get all by group accounts failed: the expected count of account is 1 ", accounts.size(), 1);
//	}
//
//	/**
//	 * Test get.
//	 *
//	 * @throws CSecurityException the c security exception
//	 */
//	@Test
//	@Transactional (transactionManager = "transactionManager")
//	public void testGet () throws CSecurityException
//	{
//		initTest();
//
//		Account accountByLogin = accountManagementService.findByByLogin(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE);
//		Account accountById = accountManagementService.read(accountByLogin.getId());
//
//		// asserts
//		assertNotNull("Get account by id failed: cannot find account with login " + accountByLogin.getLogin(), accountByLogin);
//		assertNotNull("Get account by id failed: the id of account with login " + accountByLogin.getLogin() + " is incorrect", accountById);
//	}
//
//	/**
//	 * Test get all by group and unit.
//	 *
//	 * @throws CSecurityException the c security exception
//	 */
//	@Test
//	@Transactional (transactionManager = "transactionManager")
//	public void testGetAllByGroupAndUnit () throws CSecurityException
//	{
//		initTest();
//
//		// preparation
//		Unit firstUnit = unitDao.findOneByName(DataGenerator.FIRST_UNIT_CODE);
//
//		Group firstGroupInUnit = groupDao.findOneByCode(DataGenerator.FIRST_GROUP_IN_UNIT_CODE);
//		Assert.assertNotNull("The group firstGroupInUnit not found", firstGroupInUnit);
//
//		Group thirdGroupInUnit = groupDao.findOneByCode(DataGenerator.THIRD_GROUP_IN_UNIT_CODE);
//		Assert.assertNotNull("The group thirdGroupInUnit not found", firstGroupInUnit);
//
//		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
//		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));
//
//		// tests
//		List<Account> accounts = accountDao.findByUnitAndGroup(firstUnit, firstGroupInUnit, orderModel);
//		Assert.assertEquals("The expected count of accounts with firstGroupInUnit is 0 ", accounts.size(), 0);
//
//		accounts = accountDao.findByUnitAndGroup(firstUnit, thirdGroupInUnit, orderModel);
//		Assert.assertEquals("The expected count of accounts with thirdGroupInUnit is 2 ", accounts.size(), 2);
//	}
//
//	/**
//	 * Test get all by email.
//	 */
//	@Test
//	@Transactional (transactionManager = "transactionManager")
//	public void testGetAllByEmail ()
//	{
//		initTest();
//
//		String email = DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE + "@qbsw.sk";
//		List<Account> accounts = accountManagementService.findByEmail(email);
//
//		// asserts
//		assertNotNull("Get account by email failed: cannot find account with email " + email, accounts);
//		Assert.assertEquals("Get account by email failed: the number of accounts with email " + email + " is not 1", accounts.size(), 1);
//
//		// checks if the account has a correct set of groups.
//		checksAccountHasCorrectGroups(accounts.get(0));
//	}
//
//	/**
//	 * Test get all by name and organization.
//	 */
//	@Test
//	@Transactional (transactionManager = "transactionManager")
//	public void testGetAllByNameAndOrganization ()
//	{
//		initTest();
//
//		Organization organization = organizationService.findByName(DataGenerator.ORGANIZATION_CODE).get(0);
//		Organization organization2 = organizationService.findByName(DataGenerator.ORGANIZATION_2_CODE).get(0);
//		List<Account> accounts = accountManagementService.findByOrganizationAndLoginAndState(organization, DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, ActivityStates.ACTIVE);
//
//		// asserts
//		assertNotNull("Get account by name and organization failed: cannot find accounts", accounts);
//		Assert.assertEquals("Get account by name and organization failed: the number of accounts is not 1", accounts.size(), 1);
//
//		accounts = accountManagementService.findByOrganizationAndLoginAndState(organization2, DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, ActivityStates.ACTIVE);
//
//		// asserts
//		assertNotNull("Get account by name and organization failed: cannot find accounts", accounts);
//		Assert.assertEquals("Get account by name and organization failed: the number of accounts is not 0", accounts.size(), 0);
//	}
//
//	/**
//	 * Test unset group with unit.
//	 *
//	 * @throws CSecurityException the c security exception
//	 */
//	@Test
//	@Transactional (transactionManager = "transactionManager")
//	public void testUnsetGroupWithUnit () throws CSecurityException
//	{
//		initTest();
//
//		// test data
//		Account testAccount = accountDao.findOneByLogin(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE);
//		Group testGroup = groupDao.findOneByCode(DataGenerator.FIRST_GROUP_IN_UNIT_CODE);
//		Unit testUnit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);
//
//		// unset group
//		accountPermissionManagementService.unsetAccountFromGroup(testAccount, testGroup, testUnit);
//
//		List<AccountUnitGroup> result = accountUnitGroupDao.findByAccountAndUnitAndGroup(testAccount, testUnit, testGroup);
//
//		// asserts
//		assertNotNull("Test unset group failed: cannot find result ", result);
//		Assert.assertEquals("Test unset group failed: the number of account groups is not 0", 0, result.size());
//	}
//
//	/**
//	 * Test set group with unit.
//	 *
//	 * @throws CBusinessException the c business exception
//	 */
//	@Test
//	@Transactional (transactionManager = "transactionManager")
//	public void testSetGroupWithUnit () throws CBusinessException
//	{
//		initTest();
//
//		// test data
//		Account testAccount = accountDao.findOneByLogin(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE);
//		Group testGroup = groupDao.findOneByCode(DataGenerator.THIRD_GROUP_IN_UNIT_CODE);
//		Unit testUnit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);
//
//		// set group
//		accountPermissionManagementService.setAccountToGroup(testAccount, testGroup, testUnit);
//
//		List<AccountUnitGroup> result = accountUnitGroupDao.findByAccountAndUnitAndGroup(testAccount, testUnit, testGroup);
//
//		// asserts
//		assertNotNull("Test unset group failed: cannot find result ", result);
//		Assert.assertEquals("Test unset group failed: the number of account groups is not 1", 1, result.size());
//	}
//
//	/**
//	 * Test set group with unit already set.
//	 *
//	 * @throws CBusinessException the c business exception
//	 */
//	@Test (expected = CBusinessException.class)
//	@Transactional (transactionManager = "transactionManager")
//	public void testSetGroupWithUnitAlreadySet () throws CBusinessException
//	{
//		initTest();
//
//		// test data
//		Account testAccount = accountDao.findOneByLogin(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE);
//		Group testGroup = groupDao.findOneByCode(DataGenerator.FIRST_GROUP_IN_UNIT_CODE);
//		Unit testUnit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);
//
//		// set group
//		accountPermissionManagementService.setAccountToGroup(testAccount, testGroup, testUnit);
//	}
//
//	/**
//	 * Test get accounts by pin.
//	 *
//	 * @throws CBusinessException the c business exception
//	 */
//	@Test
//	@Transactional (transactionManager = "transactionManager")
//	public void testGetAccountsByPin () throws CBusinessException
//	{
//		initTest();
//
//		List<Account> accounts = accountDao.findByPinCode("1111");
//
//		// asserts
//		assertNotNull(accounts);
//		Assert.assertSame(1, accounts.size());
//	}
//
//	private void checksGetAllAccountsList (List<Account> accounts)
//	{
//		boolean accountWithDefaultUnitCodePresent = false;
//		boolean accountWithoutDefaultUnitCodePresent = false;
//		boolean accountWithDefaultUnitCodeNoGroupPresent = false;
//		boolean accountWithoutDefaultUnitCodeNoGroupPresent = false;
//
//		for (Account account : accounts)
//		{
//			// checks if all test accounts are in list
//			if (account.getLogin().equals(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE))
//			{
//				Assert.assertFalse("Get all accounts failed: there is more than one accountWithDefaultUnitCode ", accountWithDefaultUnitCodePresent);
//				accountWithDefaultUnitCodePresent = true;
//			}
//			else if (account.getLogin().equals(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE))
//			{
//				Assert.assertFalse("Get all accounts failed: there is more than one accountWithoutDefaultUnitCode ", accountWithoutDefaultUnitCodePresent);
//				accountWithoutDefaultUnitCodePresent = true;
//			}
//			else if (account.getLogin().equals(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE_NO_GROUP))
//			{
//				Assert.assertFalse("Get all accounts failed: there is more than one accountWithDefaultUnitCodeNoGroup ", accountWithDefaultUnitCodeNoGroupPresent);
//				accountWithDefaultUnitCodeNoGroupPresent = true;
//			}
//			else if (account.getLogin().equals(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE_NO_GROUP))
//			{
//				Assert.assertFalse("Get all accounts failed: there is more than one accountWithoutDefaultUnitCodeNoGroup ", accountWithoutDefaultUnitCodeNoGroupPresent);
//				accountWithoutDefaultUnitCodeNoGroupPresent = true;
//			}
//
//			// test if the accounts has correct groups
//			checksAccountHasCorrectGroups(account);
//		}
//
//		Assert.assertTrue("Get all accounts failed: there is missing a test account", (accountWithDefaultUnitCodePresent && accountWithoutDefaultUnitCodePresent && accountWithDefaultUnitCodeNoGroupPresent && accountWithoutDefaultUnitCodeNoGroupPresent));
//	}
//
//	private void checksAccountHasCorrectGroups (Account account)
//	{
//		if (account.getLogin().equals(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE))
//		{
//			Assert.assertEquals("Get all accounts failed: the expected count of accounts group is 2 ", account.getGroups().size(), 2);
//			for (Group group : account.getGroups())
//			{
//				Assert.assertTrue("Get all accounts failed: the account with login " + account.getLogin() + " has unexpected group with code " + group.getCode(), group.getCode().equals(DataGenerator.FIRST_GROUP_IN_UNIT_CODE) || group.getCode().equals(DataGenerator.SECOND_GROUP_IN_UNIT_CODE));
//			}
//		}
//		else if (account.getLogin().equals(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE))
//		{
//			Assert.assertEquals("Get all accounts failed: the expected count of accounts group is 2 ", account.getGroups().size(), 2);
//			for (Group group : account.getGroups())
//			{
//				Assert.assertTrue("Get all accounts failed: the account with login " + account.getLogin() + " has unexpected group with code " + group.getCode(), group.getCode().equals(DataGenerator.FIRST_GROUP_NOT_IN_UNIT_CODE) || group.getCode().equals(DataGenerator.SECOND_GROUP_NOT_IN_UNIT_CODE));
//			}
//		}
//		else if (account.getLogin().equals(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE_NO_GROUP))
//		{
//			Assert.assertEquals("Get all accounts failed: the expected count of accounts group is 0 ", account.getGroups().size(), 0);
//		}
//		else if (account.getLogin().equals(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE_NO_GROUP))
//		{
//			Assert.assertEquals("Get all accounts failed: the expected count of accounts group is 0 ", account.getGroups().size(), 0);
//		}
//	}

	private void initTest ()
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
	}
}
