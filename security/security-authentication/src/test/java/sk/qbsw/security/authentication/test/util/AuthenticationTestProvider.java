package sk.qbsw.security.authentication.test.util;

import static org.junit.Assert.assertNotNull;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Assert;
import org.springframework.stereotype.Component;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.authentication.base.service.AuthenticationService;
import sk.qbsw.security.core.dao.OrganizationDao;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.model.domain.*;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.management.service.UserCredentialManagementService;
import sk.qbsw.security.management.service.UserManagementService;

/**
 * Provides test for authentication.
 *
 * @author Tomas Lauro
 * @version 1.18.4
 * @since 1.6.0
 */
@Component
public class AuthenticationTestProvider
{
	/**
	 * Test login with default unit.
	 *
	 * @param authenticationService the authentication service
	 * @throws CSecurityException the security exception
	 */
	public void testLoginWithDefaultUnit (AuthenticationService authenticationService) throws CSecurityException
	{
		Account user = authenticationService.login(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, DataGenerator.USER_WITH_DEFAULT_UNIT_CODE);

		assertNotNull("Authentication with login and password failed: user is null", user);
		assertNotNull("Authentication with login and password failed: user groups is null", user.getGroups());
		Assert.assertEquals("Authentication with login and password failed: number of user groups is not 2", user.getGroups().size(), 2);
	}

	/**
	 * Test login with default unit - incorrect password.
	 *
	 * @param authenticationService the authentication service
	 * @throws CSecurityException the security exception
	 */
	public void testLoginWithDefaultUnitIncorrectPassword (AuthenticationService authenticationService) throws CSecurityException
	{
		authenticationService.login(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, "incorrectPassword");
	}

	/**
	 * Test login without default unit.
	 *
	 * @param authenticationService the authentication service
	 * @throws CSecurityException the security exception
	 */
	public void testLoginWithoutDefaultUnit (AuthenticationService authenticationService) throws CSecurityException
	{
		Account user = authenticationService.login(DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE);

		assertNotNull("Authentication with login and password failed: user is null", user);
		assertNotNull("Authentication with login and password failed: user groups is null", user.getGroups());
		Assert.assertEquals("Authentication with login and password failed: number of user groups is not 2", user.getGroups().size(), 2);
	}

	/**
	 * Test login with default unit and role. The role should be found - login success.
	 *
	 * @param authenticationService the authentication service
	 * @throws CSecurityException the security exception
	 */
	public void testLoginWithDefaultUnitAndRolePositive (AuthenticationService authenticationService) throws CSecurityException
	{
		Set<String> expectedGroups = new HashSet<String>();
		expectedGroups.add(DataGenerator.FIRST_GROUP_IN_UNIT_CODE);
		expectedGroups.add(DataGenerator.SECOND_GROUP_IN_UNIT_CODE);
		Role inputRole = new Role(DataGenerator.FIRST_ROLE_CODE);

		testLoginWithRole(authenticationService, DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, inputRole, expectedGroups);
	}

	/**
	 * Test login with default unit and role. The role should not be found - login fails.
	 *
	 * @param authenticationService the authentication service
	 * @throws CSecurityException the security exception
	 */
	public void testLoginWithDefaultUnitAndRoleNegative (AuthenticationService authenticationService) throws CSecurityException
	{
		Role inputRole = new Role(DataGenerator.SECOND_ROLE_CODE);
		authenticationService.login(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, inputRole);
	}

	/**
	 * Test login without default unit and with role. The role should be found - login success.
	 *
	 * @param authenticationService the authentication service
	 * @throws CSecurityException the security exception
	 */
	public void testLoginWithoutDefaultUnitAndRolePositive (AuthenticationService authenticationService) throws CSecurityException
	{
		Set<String> expectedGroups = new HashSet<String>();
		expectedGroups.add(DataGenerator.FIRST_GROUP_NOT_IN_UNIT_CODE);
		expectedGroups.add(DataGenerator.SECOND_GROUP_NOT_IN_UNIT_CODE);
		Role inputRole = new Role(DataGenerator.FIRST_ROLE_CODE);

		testLoginWithRole(authenticationService, DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, inputRole, expectedGroups);
	}

	/**
	 * Test login without default unit and with role. The role should not be found - login fails.
	 *
	 * @param authenticationService the authentication service
	 * @throws CSecurityException the security exception
	 */
	public void testLoginWithoutDefaultUnitAndRoleNegative (AuthenticationService authenticationService) throws CSecurityException
	{
		Role inputRole = new Role(DataGenerator.SECOND_ROLE_CODE);
		authenticationService.login(DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, inputRole);
	}

	/**
	 * Test login with default unit and with unit. The user should be found - login success.
	 *
	 * @param authenticationService the authentication service
	 * @throws CSecurityException the security exception
	 */
	public void testLoginWithDefaultUnitAndUnit (AuthenticationService authenticationService) throws CSecurityException
	{
		Set<String> expectedGroups = new HashSet<String>();
		expectedGroups.add(DataGenerator.SECOND_GROUP_IN_UNIT_CODE);
		expectedGroups.add(DataGenerator.THIRD_GROUP_IN_UNIT_CODE);
		String unit = DataGenerator.FIRST_UNIT_CODE;

		testLoginWithUnit(authenticationService, DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, unit, expectedGroups);
	}

	/**
	 * Test login without default unit and with unit. The user should be found - login success.
	 *
	 * @param authenticationService the authentication service
	 * @throws CSecurityException the security exception
	 */
	public void testLoginWithoutDefaultUnitAndUnit (AuthenticationService authenticationService) throws CSecurityException
	{
		Set<String> expectedGroups = new HashSet<String>();
		expectedGroups.add(DataGenerator.SECOND_GROUP_IN_UNIT_CODE);
		String unit = DataGenerator.FIRST_UNIT_CODE;

		testLoginWithUnit(authenticationService, DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, unit, expectedGroups);
	}

	/**
	 * Test change plain text password.
	 *
	 * @param authenticationService the authentication service
	 * @param modifierService the modifier service
	 * @throws CSecurityException the security exception
	 */
	public void testChangePasswordExistingUser (AuthenticationService authenticationService, UserCredentialManagementService modifierService) throws CSecurityException
	{
		String newPassword = "change1Password3ExistingUser@";
		modifierService.changePlainPassword(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, DataGenerator.USER_WITH_DEFAULT_UNIT_CODE + "@qbsw.sk", newPassword);

		// test authentication
		Account user = authenticationService.login(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, newPassword);

		assertNotNull("The plain text password change failed", user);
	}

	/**
	 * Test change password with password validation existing user.
	 *
	 * @param authenticationService the authentication service
	 * @param modifierService the modifier service
	 * @throws CSecurityException the c security exception
	 */
	public void testChangePasswordWithPasswordValidationExistingUser (AuthenticationService authenticationService, UserCredentialManagementService modifierService) throws CSecurityException
	{
		final String newPassword = "change1EncryptedPasswordExistingUser$56";
		modifierService.changePlainPassword(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, DataGenerator.USER_WITH_DEFAULT_UNIT_CODE + "@qbsw.sk", DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, newPassword);

		// test authentication
		Account user = authenticationService.login(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, newPassword);

		assertNotNull("The plain text password change failed", user);
	}

	/**
	 * Test change password with password validation existing user fail.
	 *
	 * @param authenticationService the authentication service
	 * @param modifierService the modifier service
	 * @throws CSecurityException the c security exception
	 */
	public void testChangePasswordWithPasswordValidationExistingUserFail (AuthenticationService authenticationService, UserCredentialManagementService modifierService) throws CSecurityException
	{
		final String newPassword = "change1EncryptedPasswordExistingUser$56";
		modifierService.changePlainPassword(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, DataGenerator.USER_WITH_DEFAULT_UNIT_CODE + "@qbsw.sk", "fail", newPassword);

		// test authentication
		Account user = authenticationService.login(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, newPassword);

		assertNotNull("The plain text password change failed", user);
	}

	/**
	 * Test change encrypted password.
	 *
	 * @param authenticationService the authentication service
	 * @param modifierService the modifier service
	 * @throws CSecurityException the security exception
	 */
	public void testChangeEncryptedPasswordExistingUser (AuthenticationService authenticationService, UserCredentialManagementService modifierService) throws CSecurityException
	{
		final String newPassword = "change1EncryptedPasswordExistingUser$56";
		modifierService.changeEncryptedPassword(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, newPassword);

		// test authentication
		Account user = authenticationService.login(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, newPassword);

		assertNotNull("The plain text password change failed", user);
	}

	/**
	 * Test change encrypted password with password validation existing user.
	 *
	 * @param authenticationService the authentication service
	 * @param modifierService the modifier service
	 * @throws CSecurityException the c security exception
	 */
	public void testChangeEncryptedPasswordWithPasswordValidationExistingUser (AuthenticationService authenticationService, UserCredentialManagementService modifierService) throws CSecurityException
	{
		final String newTemporaryPassword = "change1EncryptedPasswordExistingUser$567";

		// set digested password
		modifierService.changeEncryptedPassword(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, newTemporaryPassword);

		final String newPassword = "change1EncryptedPasswordExistingUser$56";
		modifierService.changeEncryptedPassword(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, newTemporaryPassword, newPassword);

		// test authentication
		Account user = authenticationService.login(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, newPassword);

		assertNotNull("The plain text password change failed", user);
	}

	/**
	 * Test change encrypted password with password validation existing user fail.
	 *
	 * @param authenticationService the authentication service
	 * @param modifierService the modifier service
	 * @throws CSecurityException the c security exception
	 */
	public void testChangeEncryptedPasswordWithPasswordValidationExistingUserFail (AuthenticationService authenticationService, UserCredentialManagementService modifierService) throws CSecurityException
	{
		final String newTemporaryPassword = "change1EncryptedPasswordExistingUser$567";

		// set digested password
		modifierService.changeEncryptedPassword(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, newTemporaryPassword);

		final String newPassword = "change1EncryptedPasswordExistingUser$56";
		modifierService.changeEncryptedPassword(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, "fail", newPassword);

		// test authentication
		Account user = authenticationService.login(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, newPassword);

		assertNotNull("The plain text password change failed", user);
	}

	/**
	 * Test change encrypted password with new user.
	 *
	 * @param authenticationService the authentication service
	 * @param userService the user service
	 * @param userDao the user dao
	 * @param orgDao the org dao
	 * @param dataGenerator the data generator
	 * @throws CSecurityException the security exception
	 */
	public void testChangeEncryptedPasswordNewUser (AuthenticationService authenticationService, UserManagementService userService, AccountDao userDao, OrganizationDao orgDao, DataGenerator dataGenerator) throws CSecurityException
	{
		// create new user and needed objects
		Account newUser = dataGenerator.createUser(DataGenerator.USER_WITHOUT_PASSWORD);
		Organization organization = orgDao.findOneByName(DataGenerator.ORGANIZATION_CODE);
		String newPassword = "change56%PasswordNewUser";

		// register user
		userService.registerNewUser(newUser, newPassword, organization);

		// flush and clear persistent context - the user's auth params are saved using dao
		userDao.flush();
		userDao.clear();

		// test authentication
		assertNotNull("The encrypted password change failed", authenticationService.login(DataGenerator.USER_WITHOUT_PASSWORD, newPassword));
	}

	/**
	 * Test change login name of user.
	 *
	 * @param authenticationService the authentication service
	 * @param userService the user service
	 * @throws CSecurityException the security exception
	 */
	public void testChangeLogin (UserCredentialManagementService authenticationService, UserManagementService userService) throws CSecurityException
	{
		String newLogin = "new1Login#";
		Account user = userService.getUserByLogin(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE);

		// change login
		authenticationService.changeLogin(user.getId(), newLogin);

		// test new and old login
		Account userWithNewLogin = userService.getUserByLogin(newLogin);
		Assert.assertEquals("There login change failed", user.getId(), userWithNewLogin.getId());
	}

	/**
	 * Test if the source is online.
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
	public void testLoginInvalidFromAuthParam (AuthenticationService authenticationService, UserCredentialManagementService modifierService) throws CSecurityException
	{
		String newPassword = "new1Login#";
		modifierService.changeEncryptedPassword(DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, newPassword, OffsetDateTime.now().plusHours(2), null);

		authenticationService.login(DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, newPassword);
	}

	/**
	 * Test login invalid to auth param.
	 *
	 * @param authenticationService the authentication service
	 * @param modifierService the modifier service
	 * @throws CSecurityException the c security exception
	 */
	public void testLoginInvalidToAuthParam (AuthenticationService authenticationService, UserCredentialManagementService modifierService) throws CSecurityException
	{
		String newPassword = "new1Login#";
		modifierService.changeEncryptedPassword(DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, newPassword, null, OffsetDateTime.now().minusHours(1));

		authenticationService.login(DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, newPassword);
	}

	/**
	 * Test login invalid from and to auth param.
	 *
	 * @param authenticationService the authentication service
	 * @param modifierService the modifier service
	 * @throws CSecurityException the c security exception
	 */
	public void testLoginInvalidFromAndToAuthParam (AuthenticationService authenticationService, UserCredentialManagementService modifierService) throws CSecurityException
	{
		String newPassword = "new1Login#";
		modifierService.changeEncryptedPassword(DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, newPassword, OffsetDateTime.now().minusHours(2), OffsetDateTime.now().minusHours(1));

		authenticationService.login(DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, newPassword);
	}

	/**
	 * Test change password null from and to auth param.
	 *
	 * @param authenticationService the authentication service
	 * @param modifierService the modifier service
	 * @throws CSecurityException the c security exception
	 */
	public void testChangePasswordNullFromAndToAuthParam (AuthenticationService authenticationService, UserCredentialManagementService modifierService) throws CSecurityException
	{
		String newPassword = "new1Login#";
		modifierService.changeEncryptedPassword(DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, newPassword, null, null);

		authenticationService.login(DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, newPassword);
	}

	/**
	 * Test login with login, password and role. Return role if found else null.
	 *
	 ** @param login The login
	 * @param password The password
	 * @param inputRole The input role
	 * @param expectedGroupsSize The expected size of groups for user
	 * @throws CSecurityException the security exception
	 */
	private void testLoginWithRole (AuthenticationService authenticationService, String login, String password, Role inputRole, Set<String> expectedGroups) throws CSecurityException
	{
		// define output role - found in user
		Role outputRole = null;

		// login user
		Account user = authenticationService.login(login, password, inputRole);
		assertNotNull("Authentication with login, password and role failed: user is null", user);
		assertNotNull("Authentication with login, password and role failed: user groups is null", user.getGroups());
		Assert.assertEquals("Authentication with login, password and role failed: number of user groups is not " + expectedGroups.size(), user.getGroups().size(), expectedGroups.size());

		// checks if user has input role
		Iterator<Group> groupIterator = user.getGroups().iterator();
		while (groupIterator.hasNext())
		{
			Group group = (Group) groupIterator.next();
			assertNotNull("Authentication with login, password and role failed: user roles in group " + group.getCode() + " is null", group.getRoles());

			// check groups
			if (expectedGroups.contains(group.getCode()) == false)
			{
				Assert.fail("Authentication with login, password and role failed: unexpected group with code " + group.getCode());
			}

			Iterator<Role> roleIterator = group.getRoles().iterator();
			while (roleIterator.hasNext())
			{
				Role tempOutputRole = (Role) roleIterator.next();
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

	/**
	 * Test login with login, password and unit.
	 *
	 * @param login The login
	 * @param password The password
	 * @param unit The unit
	 * @param expectedGroupsSize The expected size of groups for user
	 * @throws CSecurityException the security exception
	 */
	private void testLoginWithUnit (AuthenticationService authenticationService, String login, String password, String unit, Set<String> expectedGroups) throws CSecurityException
	{
		Unit outputUnit = null;

		// login user
		Account user = authenticationService.login(login, password, unit);
		assertNotNull("Authentication with login, password and unit failed: user is null", user);
		assertNotNull("Authentication with login, password and unit failed: user groups is null", user.getGroups());
		Assert.assertEquals("Authentication with login, password and unit failed: number of user groups is not " + expectedGroups.size(), user.getGroups().size(), expectedGroups.size());

		// checks if user has input role
		Iterator<Group> groupIterator = user.getGroups().iterator();
		while (groupIterator.hasNext())
		{
			Group group = (Group) groupIterator.next();
			assertNotNull("Authentication with login, password and unit failed: units in group " + group.getCode() + " is null", group.getUnits());

			// check groups
			if (expectedGroups.contains(group.getCode()) == false)
			{
				Assert.fail("Authentication with login, password and unit failed: unexpected group with code " + group.getCode());
			}

			// checks units
			Iterator<Unit> unitsIterator = group.getUnits().iterator();
			while (unitsIterator.hasNext())
			{
				Unit tempOutputUnit = (Unit) unitsIterator.next();
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
