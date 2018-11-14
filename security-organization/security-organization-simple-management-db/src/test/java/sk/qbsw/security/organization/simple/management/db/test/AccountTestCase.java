package sk.qbsw.security.organization.simple.management.db.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.core.security.base.model.AccountInputData;
import sk.qbsw.security.core.dao.OrganizationDao;
import sk.qbsw.security.core.model.domain.Organization;
import sk.qbsw.security.organization.simple.management.SPOAccountManagementService;
import sk.qbsw.security.organization.simple.management.db.test.util.DataGenerator;

/**
 * Checks user service.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@Rollback
public class AccountTestCase
{
	@Autowired
	private DataGenerator dataGenerator;

	@Autowired
	private SPOAccountManagementService<AccountInputData, AccountData> accountManagementService;

	@Autowired
	private OrganizationDao organizationDao;

	/**
	 * Test get all order by organization.
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testGetAllOrderByOrganization ()
	{
		initTest();

		List<AccountData> accounts = accountManagementService.findByOrganizationCodeAndStateAndGroupCodeOrderByOrganizationNameAndLogin(null, null, null);

		// asserts
		assertNotNull("Get all accounts order by organization failed: list of accounts is null", accounts);

		// checks if there are all test accounts in the list and if they have correctly set groups.
		checksGetAllAccountsList(accounts);
	}

	/**
	 * Test get all by group.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testGetAllByGroup () throws CSecurityException
	{
		initTest();

		List<AccountData> accounts = accountManagementService.findByOrganizationCodeAndStateAndGroupCode(null, null, DataGenerator.FIRST_GROUP_IN_UNIT_CODE);

		// asserts
		assertNotNull("Get all accounts by group failed: list of accounts is null", accounts);
		Assert.assertEquals("Get all by group accounts failed: the expected count of account is 1 ", accounts.size(), 1);
	}

	/**
	 * Test get all by group order by organization.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testGetAllByGroupOrderByOrganization () throws CSecurityException
	{
		initTest();

		List<AccountData> accounts = accountManagementService.findByOrganizationCodeAndStateAndGroupCodeOrderByOrganizationNameAndLogin(null, null, DataGenerator.FIRST_GROUP_IN_UNIT_CODE);

		// asserts
		assertNotNull("Get all accounts by group failed: list of accounts is null", accounts);
		Assert.assertEquals("Get all by group accounts failed: the expected count of account is 1 ", accounts.size(), 1);
	}

	/**
	 * Test get all by name and organization.
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testGetAllByNameAndOrganization ()
	{
		initTest();

		Organization organization = organizationDao.findByName(DataGenerator.ORGANIZATION_CODE).get(0);
		Organization organization2 = organizationDao.findByName(DataGenerator.ORGANIZATION_2_CODE).get(0);
		AccountData account = accountManagementService.findOneByOrganizationCodeAndLoginAndState(organization.getCode(), DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, ActivityStates.ACTIVE);

		// asserts
		assertNotNull("Get account by name and organization failed: cannot find accounts", account);

		try
		{
			account = accountManagementService.findOneByOrganizationCodeAndLoginAndState(organization2.getCode(), DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, ActivityStates.ACTIVE);
		}
		catch (NoResultException e)
		{
			account = null;
		}

		// asserts
		assertNull("Get account by name and organization failed: found account", account);
	}

	private void checksGetAllAccountsList (List<AccountData> accounts)
	{
		boolean accountWithDefaultUnitCodePresent = false;
		boolean accountWithoutDefaultUnitCodePresent = false;
		boolean accountWithDefaultUnitCodeNoGroupPresent = false;
		boolean accountWithoutDefaultUnitCodeNoGroupPresent = false;

		for (AccountData account : accounts)
		{
			// checks if all test accounts are in list
			if (account.getLogin().equals(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE))
			{
				Assert.assertFalse("Get all accounts failed: there is more than one accountWithDefaultUnitCode ", accountWithDefaultUnitCodePresent);
				accountWithDefaultUnitCodePresent = true;
			}
			else if (account.getLogin().equals(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE))
			{
				Assert.assertFalse("Get all accounts failed: there is more than one accountWithoutDefaultUnitCode ", accountWithoutDefaultUnitCodePresent);
				accountWithoutDefaultUnitCodePresent = true;
			}
			else if (account.getLogin().equals(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE_NO_GROUP))
			{
				Assert.assertFalse("Get all accounts failed: there is more than one accountWithDefaultUnitCodeNoGroup ", accountWithDefaultUnitCodeNoGroupPresent);
				accountWithDefaultUnitCodeNoGroupPresent = true;
			}
			else if (account.getLogin().equals(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE_NO_GROUP))
			{
				Assert.assertFalse("Get all accounts failed: there is more than one accountWithoutDefaultUnitCodeNoGroup ", accountWithoutDefaultUnitCodeNoGroupPresent);
				accountWithoutDefaultUnitCodeNoGroupPresent = true;
			}

			// test if the accounts has correct groups
			checksAccountHasCorrectGroups(account);
		}

		Assert.assertTrue("Get all accounts failed: there is missing a test account", (accountWithDefaultUnitCodePresent && accountWithoutDefaultUnitCodePresent && accountWithDefaultUnitCodeNoGroupPresent && accountWithoutDefaultUnitCodeNoGroupPresent));
	}

	private void checksAccountHasCorrectGroups (AccountData account)
	{
		if (account.getLogin().equals(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE))
		{
			Assert.assertEquals("Get all accounts failed: the expected count of accounts group is 2 ", account.getGroups().size(), 2);
			for (String groupCode : account.getGroups())
			{
				Assert.assertTrue("Get all accounts failed: the account with login " + account.getLogin() + " has unexpected group with code " + groupCode, groupCode.equals(DataGenerator.FIRST_GROUP_IN_UNIT_CODE) || groupCode.equals(DataGenerator.SECOND_GROUP_IN_UNIT_CODE));
			}
		}
		else if (account.getLogin().equals(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE))
		{
			Assert.assertEquals("Get all accounts failed: the expected count of accounts group is 2 ", account.getGroups().size(), 2);
			for (String groupCode : account.getGroups())
			{
				Assert.assertTrue("Get all accounts failed: the account with login " + account.getLogin() + " has unexpected group with code " + groupCode, groupCode.equals(DataGenerator.FIRST_GROUP_NOT_IN_UNIT_CODE) || groupCode.equals(DataGenerator.SECOND_GROUP_NOT_IN_UNIT_CODE));
			}
		}
		else if (account.getLogin().equals(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE_NO_GROUP))
		{
			Assert.assertEquals("Get all accounts failed: the expected count of accounts group is 0 ", account.getGroups().size(), 0);
		}
		else if (account.getLogin().equals(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE_NO_GROUP))
		{
			Assert.assertEquals("Get all accounts failed: the expected count of accounts group is 0 ", account.getGroups().size(), 0);
		}
	}

	private void initTest ()
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
	}
}
