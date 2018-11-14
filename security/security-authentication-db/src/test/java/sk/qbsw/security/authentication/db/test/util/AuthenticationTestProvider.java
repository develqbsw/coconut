package sk.qbsw.security.authentication.db.test.util;

import org.junit.Assert;
import org.springframework.stereotype.Component;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.core.security.base.model.AccountDataTypes;
import sk.qbsw.core.security.base.model.AccountInputData;
import sk.qbsw.security.authentication.service.AuthenticationService;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.OrganizationDao;
import sk.qbsw.security.core.model.domain.*;
import sk.qbsw.security.management.service.AccountCredentialManagementService;
import sk.qbsw.security.management.service.AccountManagementService;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertNotNull;

/**
 * Provides test for authentication.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.6.0
 */
@Component
public class AuthenticationTestProvider
{
	/**
	 * Test login with default unit.
	 *
	 * @param authenticationService the authentication service
	 * @throws CSecurityException the c security exception
	 */
	public void testLoginWithDefaultUnit (AuthenticationService authenticationService) throws CSecurityException
	{
		Account account = authenticationService.login(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE);

		assertNotNull("Authentication with login and password failed: account is null", account);
		assertNotNull("Authentication with login and password failed: account groups is null", account.getGroups());
		Assert.assertEquals("Authentication with login and password failed: number of account groups is not 2", account.getGroups().size(), 2);
	}

	/**
	 * Test login with default unit incorrect password.
	 *
	 * @param authenticationService the authentication service
	 * @throws CSecurityException the c security exception
	 */
	public void testLoginWithDefaultUnitIncorrectPassword (AuthenticationService authenticationService) throws CSecurityException
	{
		authenticationService.login(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, "incorrectPassword");
	}

	/**
	 * Test login without default unit.
	 *
	 * @param authenticationService the authentication service
	 * @throws CSecurityException the c security exception
	 */
	public void testLoginWithoutDefaultUnit (AuthenticationService authenticationService) throws CSecurityException
	{
		Account account = authenticationService.login(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE);

		assertNotNull("Authentication with login and password failed: account is null", account);
		assertNotNull("Authentication with login and password failed: account groups is null", account.getGroups());
		Assert.assertEquals("Authentication with login and password failed: number of account groups is not 2", account.getGroups().size(), 2);
	}

	/**
	 * Test login with default unit and role positive.
	 *
	 * @param authenticationService the authentication service
	 * @throws CSecurityException the c security exception
	 */
	public void testLoginWithDefaultUnitAndRolePositive (AuthenticationService authenticationService) throws CSecurityException
	{
		Set<String> expectedGroups = new HashSet<>();
		expectedGroups.add(DataGenerator.FIRST_GROUP_IN_UNIT_CODE);
		expectedGroups.add(DataGenerator.SECOND_GROUP_IN_UNIT_CODE);
		Role inputRole = new Role();
		inputRole.setCode(DataGenerator.FIRST_ROLE_CODE);

		testLoginWithRole(authenticationService, DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, inputRole, expectedGroups);
	}

	/**
	 * Test login with default unit and role negative.
	 *
	 * @param authenticationService the authentication service
	 * @throws CSecurityException the c security exception
	 */
	public void testLoginWithDefaultUnitAndRoleNegative (AuthenticationService authenticationService) throws CSecurityException
	{
		Role inputRole = new Role();
		inputRole.setCode(DataGenerator.SECOND_ROLE_CODE);
		authenticationService.login(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, inputRole);
	}

	/**
	 * Test login without default unit and role positive.
	 *
	 * @param authenticationService the authentication service
	 * @throws CSecurityException the c security exception
	 */
	public void testLoginWithoutDefaultUnitAndRolePositive (AuthenticationService authenticationService) throws CSecurityException
	{
		Set<String> expectedGroups = new HashSet<>();
		expectedGroups.add(DataGenerator.FIRST_GROUP_NOT_IN_UNIT_CODE);
		expectedGroups.add(DataGenerator.SECOND_GROUP_NOT_IN_UNIT_CODE);
		Role inputRole = new Role();
		inputRole.setCode(DataGenerator.FIRST_ROLE_CODE);

		testLoginWithRole(authenticationService, DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, inputRole, expectedGroups);
	}

	/**
	 * Test login without default unit and role negative.
	 *
	 * @param authenticationService the authentication service
	 * @throws CSecurityException the c security exception
	 */
	public void testLoginWithoutDefaultUnitAndRoleNegative (AuthenticationService authenticationService) throws CSecurityException
	{
		Role inputRole = new Role();
		inputRole.setCode(DataGenerator.SECOND_ROLE_CODE);
		authenticationService.login(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, inputRole);
	}

	/**
	 * Test login with default unit and unit.
	 *
	 * @param authenticationService the authentication service
	 * @throws CSecurityException the c security exception
	 */
	public void testLoginWithDefaultUnitAndUnit (AuthenticationService authenticationService) throws CSecurityException
	{
		Set<String> expectedGroups = new HashSet<>();
		expectedGroups.add(DataGenerator.SECOND_GROUP_IN_UNIT_CODE);
		expectedGroups.add(DataGenerator.THIRD_GROUP_IN_UNIT_CODE);
		String unit = DataGenerator.FIRST_UNIT_CODE;

		testLoginWithUnit(authenticationService, DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, unit, expectedGroups);
	}

	/**
	 * Test login without default unit and unit.
	 *
	 * @param authenticationService the authentication service
	 * @throws CSecurityException the c security exception
	 */
	public void testLoginWithoutDefaultUnitAndUnit (AuthenticationService authenticationService) throws CSecurityException
	{
		Set<String> expectedGroups = new HashSet<>();
		expectedGroups.add(DataGenerator.SECOND_GROUP_IN_UNIT_CODE);
		String unit = DataGenerator.FIRST_UNIT_CODE;

		testLoginWithUnit(authenticationService, DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, unit, expectedGroups);
	}

	/**
	 * Test change password existing account.
	 *
	 * @param authenticationService the authentication service
	 * @param modifierService the modifier service
	 * @throws CSecurityException the c security exception
	 */
	public void testChangePasswordExistingAccount (AuthenticationService authenticationService, AccountCredentialManagementService modifierService) throws CSecurityException
	{
		String newPassword = "change1Password3ExistingAccount@";
		modifierService.changePlainPassword(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE + "@qbsw.sk", newPassword);

		// test authentication
		Account account = authenticationService.login(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, newPassword);

		assertNotNull("The plain text password change failed", account);
	}

	/**
	 * Test change password with password validation existing account.
	 *
	 * @param authenticationService the authentication service
	 * @param modifierService the modifier service
	 * @throws CSecurityException the c security exception
	 */
	public void testChangePasswordWithPasswordValidationExistingAccount (AuthenticationService authenticationService, AccountCredentialManagementService modifierService) throws CSecurityException
	{
		final String newPassword = "change1EncryptedPasswordExistingAccount$56";
		modifierService.changePlainPassword(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE + "@qbsw.sk", DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, newPassword);

		// test authentication
		Account account = authenticationService.login(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, newPassword);

		assertNotNull("The plain text password change failed", account);
	}

	/**
	 * Test change password with password validation existing account fail.
	 *
	 * @param authenticationService the authentication service
	 * @param modifierService the modifier service
	 * @throws CSecurityException the c security exception
	 */
	public void testChangePasswordWithPasswordValidationExistingAccountFail (AuthenticationService authenticationService, AccountCredentialManagementService modifierService) throws CSecurityException
	{
		final String newPassword = "change1EncryptedPasswordExistingAccount$56";
		modifierService.changePlainPassword(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE + "@qbsw.sk", "fail", newPassword);

		// test authentication
		Account account = authenticationService.login(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, newPassword);

		assertNotNull("The plain text password change failed", account);
	}

	/**
	 * Test change encrypted password existing account.
	 *
	 * @param authenticationService the authentication service
	 * @param modifierService the modifier service
	 * @throws CSecurityException the c security exception
	 */
	public void testChangeEncryptedPasswordExistingAccount (AuthenticationService authenticationService, AccountCredentialManagementService modifierService) throws CSecurityException
	{
		final String newPassword = "change1EncryptedPasswordExistingAccount$56";
		modifierService.changeEncryptedPassword(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, newPassword);

		// test authentication
		Account account = authenticationService.login(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, newPassword);

		assertNotNull("The plain text password change failed", account);
	}

	/**
	 * Test change encrypted password with password validation existing account.
	 *
	 * @param authenticationService the authentication service
	 * @param modifierService the modifier service
	 * @throws CSecurityException the c security exception
	 */
	public void testChangeEncryptedPasswordWithPasswordValidationExistingAccount (AuthenticationService authenticationService, AccountCredentialManagementService modifierService) throws CSecurityException
	{
		final String newTemporaryPassword = "change1EncryptedPasswordExistingAccount$567";

		// set digested password
		modifierService.changeEncryptedPassword(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, newTemporaryPassword);

		final String newPassword = "change1EncryptedPasswordExistingAccount$56";
		modifierService.changeEncryptedPassword(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, newTemporaryPassword, newPassword);

		// test authentication
		Account account = authenticationService.login(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, newPassword);

		assertNotNull("The plain text password change failed", account);
	}

	/**
	 * Test change encrypted password with password validation existing account fail.
	 *
	 * @param authenticationService the authentication service
	 * @param modifierService the modifier service
	 * @throws CSecurityException the c security exception
	 */
	public void testChangeEncryptedPasswordWithPasswordValidationExistingAccountFail (AuthenticationService authenticationService, AccountCredentialManagementService modifierService) throws CSecurityException
	{
		final String newTemporaryPassword = "change1EncryptedPasswordExistingAccount$567";

		// set digested password
		modifierService.changeEncryptedPassword(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, newTemporaryPassword);

		final String newPassword = "change1EncryptedPasswordExistingAccount$56";
		modifierService.changeEncryptedPassword(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, "fail", newPassword);

		// test authentication
		Account account = authenticationService.login(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, newPassword);

		assertNotNull("The plain text password change failed", account);
	}

	/**
	 * Test change encrypted password new account.
	 *
	 * @param authenticationService the authentication service
	 * @param accountService the account service
	 * @param accountDao the account dao
	 * @param orgDao the org dao
	 * @param dataGenerator the data generator
	 * @throws CSecurityException the c security exception
	 */
	public void testChangeEncryptedPasswordNewAccount (AuthenticationService authenticationService, AccountManagementService<AccountInputData, AccountData> accountService, AccountDao accountDao, OrganizationDao orgDao, DataGenerator dataGenerator) throws CBusinessException
	{
		// create new account and needed objects
		Organization organization = orgDao.findOneByCode(DataGenerator.ORGANIZATION_CODE);

		AccountInputData accountInputData = new AccountInputData();
		accountInputData.setLogin(DataGenerator.ACCOUNT_WITHOUT_PASSWORD);
		accountInputData.setEmail(DataGenerator.ACCOUNT_WITHOUT_PASSWORD + "@qbsw.sk");
		accountInputData.setType(AccountDataTypes.PERSONAL);
		accountInputData.setOrganizationId(organization.getId());

		String newPassword = "change56%PasswordNewAccount";

		// register account
		accountService.register(accountInputData, newPassword);

		// flush and clear persistent context - the account's auth params are saved using dao
		accountDao.flush();
		accountDao.clear();

		// test authentication
		assertNotNull("The encrypted password change failed", authenticationService.login(DataGenerator.ACCOUNT_WITHOUT_PASSWORD, newPassword));
	}

	/**
	 * Test change login.
	 *
	 * @param authenticationService the authentication service
	 * @param accountService the account service
	 * @throws CSecurityException the c security exception
	 */
	public void testChangeLogin (AccountCredentialManagementService authenticationService, AccountManagementService<AccountInputData, AccountData> accountService) throws CSecurityException
	{
		String newLogin = "new1Login#";
		AccountData accountData = accountService.findOneByLogin(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE);

		// change login
		authenticationService.changeLogin(accountData.getId(), newLogin);

		// test new and old login
		AccountData accountWithNewLogin = accountService.findOneByLogin(newLogin);
		Assert.assertEquals("There login change failed", accountData.getId(), accountWithNewLogin.getId());
	}

	/**
	 * Test is online.
	 *
	 * @param authenticationService the authentication service
	 */
	public void testIsOnline (AuthenticationService authenticationService)
	{
		Assert.assertTrue("Checks is online failed - the source is offline", authenticationService.isOnline());
	}

	/**
	 * Test login invalid from auth param.
	 *
	 * @param authenticationService the authentication service
	 * @param modifierService the modifier service
	 * @throws CSecurityException the c security exception
	 */
	public void testLoginInvalidFromAuthParam (AuthenticationService authenticationService, AccountCredentialManagementService modifierService) throws CSecurityException
	{
		String newPassword = "new1Login#";
		modifierService.changeEncryptedPassword(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, newPassword, OffsetDateTime.now().plusHours(2), null);

		authenticationService.login(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, newPassword);
	}

	/**
	 * Test login invalid to auth param.
	 *
	 * @param authenticationService the authentication service
	 * @param modifierService the modifier service
	 * @throws CSecurityException the c security exception
	 */
	public void testLoginInvalidToAuthParam (AuthenticationService authenticationService, AccountCredentialManagementService modifierService) throws CSecurityException
	{
		String newPassword = "new1Login#";
		modifierService.changeEncryptedPassword(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, newPassword, null, OffsetDateTime.now().minusHours(1));

		authenticationService.login(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, newPassword);
	}

	/**
	 * Test login invalid from and to auth param.
	 *
	 * @param authenticationService the authentication service
	 * @param modifierService the modifier service
	 * @throws CSecurityException the c security exception
	 */
	public void testLoginInvalidFromAndToAuthParam (AuthenticationService authenticationService, AccountCredentialManagementService modifierService) throws CSecurityException
	{
		String newPassword = "new1Login#";
		modifierService.changeEncryptedPassword(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, newPassword, OffsetDateTime.now().minusHours(2), OffsetDateTime.now().minusHours(1));

		authenticationService.login(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, newPassword);
	}

	/**
	 * Test change password null from and to auth param.
	 *
	 * @param authenticationService the authentication service
	 * @param modifierService the modifier service
	 * @throws CSecurityException the c security exception
	 */
	public void testChangePasswordNullFromAndToAuthParam (AuthenticationService authenticationService, AccountCredentialManagementService modifierService) throws CSecurityException
	{
		String newPassword = "new1Login#";
		modifierService.changeEncryptedPassword(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, newPassword, null, null);

		authenticationService.login(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, newPassword);
	}

	private void testLoginWithRole (AuthenticationService authenticationService, String login, String password, Role inputRole, Set<String> expectedGroups) throws CSecurityException
	{
		// define output role - found in account
		Role outputRole = null;

		// login account
		Account account = authenticationService.login(login, password, inputRole);
		assertNotNull("Authentication with login, password and role failed: account is null", account);
		assertNotNull("Authentication with login, password and role failed: account groups is null", account.getGroups());
		Assert.assertEquals("Authentication with login, password and role failed: number of account groups is not " + expectedGroups.size(), account.getGroups().size(), expectedGroups.size());

		// checks if account has input role
		for (Group group : account.getGroups())
		{
			assertNotNull("Authentication with login, password and role failed: account roles in group " + group.getCode() + " is null", group.getRoles());

			// check groups
			if (!expectedGroups.contains(group.getCode()))
			{
				Assert.fail("Authentication with login, password and role failed: unexpected group with code " + group.getCode());
			}

			for (Role tempOutputRole : group.getRoles())
			{
				if (tempOutputRole.getCode().equals(inputRole.getCode()))
				{
					outputRole = tempOutputRole;
					break;
				}

			}

			if (outputRole == null)
			{
				Assert.fail("Authentication with login, password and role failed: the group " + group.getCode() + " doesn not contain role with name " + inputRole.getCode());
			}
			else
			{
				outputRole = null;
			}

		}
	}

	private void testLoginWithUnit (AuthenticationService authenticationService, String login, String password, String unit, Set<String> expectedGroups) throws CSecurityException
	{
		Unit outputUnit = null;

		// login account
		Account account = authenticationService.login(login, password, unit);
		assertNotNull("Authentication with login, password and unit failed: account is null", account);
		assertNotNull("Authentication with login, password and unit failed: account groups is null", account.getGroups());
		Assert.assertEquals("Authentication with login, password and unit failed: number of account groups is not " + expectedGroups.size(), account.getGroups().size(), expectedGroups.size());

		// checks if account has input role
		for (Group group : account.getGroups())
		{
			assertNotNull("Authentication with login, password and unit failed: units in group " + group.getCode() + " is null", group.getUnits());

			// check groups
			if (!expectedGroups.contains(group.getCode()))
			{
				Assert.fail("Authentication with login, password and unit failed: unexpected group with code " + group.getCode());
			}

			// checks units
			for (Unit tempOutputUnit : group.getUnits())
			{
				if (tempOutputUnit.getName().equals(unit))
				{
					outputUnit = tempOutputUnit;
					break;
				}
			}

			if (outputUnit == null)
			{
				Assert.fail("Authentication with login, password and unit failed: the group " + group.getCode() + " doesn not contain unit with name " + unit);
			}
			else
			{
				outputUnit = null;
			}
		}
	}
}
