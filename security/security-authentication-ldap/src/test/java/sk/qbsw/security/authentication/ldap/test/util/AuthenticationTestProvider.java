package sk.qbsw.security.authentication.ldap.test.util;

import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Assert;
import org.springframework.stereotype.Component;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.authentication.base.service.AuthenticationService;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Role;
import sk.qbsw.security.core.model.domain.Unit;
import sk.qbsw.security.management.service.UserCredentialManagementService;

/**
 * Provides test for authentication.
 *
 * @autor Tomas Lauro
 * @version 1.12.2
 * @since 1.6.0
 */
@Component
public class AuthenticationTestProvider
{

	/**
	 * Test login with default unit.
	 *
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
	 * @throws CSecurityException the security exception
	 */
	public void testLoginWithDefaultUnitIncorrectPassword (AuthenticationService authenticationService) throws CSecurityException
	{
		authenticationService.login(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, "incorrectPassword");
	}

	/**
	 * Test login without default unit.
	 *
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
	 * @throws CSecurityException the security exception
	 */
	public void testChangePasswordExistingUser (AuthenticationService authenticationService, UserCredentialManagementService modifierService) throws CSecurityException
	{
		String newPassword = "change1Password3ExistingUser@";
		modifierService.changePlainPassword(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, DataGenerator.USER_WITH_DEFAULT_UNIT_CODE + "@qbsw.sk", newPassword);

		//test authentication
		Account user = authenticationService.login(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, newPassword);

		assertNotNull("The plain text password change failed", user);
	}

	/**
	 * Test login enabled user in disabled organization.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testLoginEnabledUserDisabledOrganization (AuthenticationService authenticationService) throws CSecurityException
	{
		authenticationService.login(DataGenerator.USER_ENABLED_IN_DISABLED_ORGANIZATION, DataGenerator.USER_ENABLED_IN_DISABLED_ORGANIZATION);
	}

	/**
	 * Test login disabled user in disabled organization.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testLoginDisabledUserDisabledOrganization (AuthenticationService authenticationService) throws CSecurityException
	{
		authenticationService.login(DataGenerator.USER_DISABLED_IN_DISABLED_ORGANIZATION, DataGenerator.USER_DISABLED_IN_DISABLED_ORGANIZATION);
	}

	/**
	 * Test login disabled user in enabled organization.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testLoginDisabledUserEnabledOrganization (AuthenticationService authenticationService) throws CSecurityException
	{
		authenticationService.login(DataGenerator.USER_DISABLED_IN_ENABLED_ORGANIZATION, DataGenerator.USER_DISABLED_IN_ENABLED_ORGANIZATION);
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
		//define output role - found in user
		Role outputRole = null;

		//login user
		Account user = authenticationService.login(login, password, inputRole);
		assertNotNull("Authentication with login, password and role failed: user is null", user);
		assertNotNull("Authentication with login, password and role failed: user groups is null", user.getGroups());
		Assert.assertEquals("Authentication with login, password and role failed: number of user groups is not " + expectedGroups.size(), user.getGroups().size(), expectedGroups.size());

		//checks if user has input role
		Iterator<Group> groupIterator = user.getGroups().iterator();
		while (groupIterator.hasNext())
		{
			Group group = (Group) groupIterator.next();
			assertNotNull("Authentication with login, password and role failed: user roles in group " + group.getCode() + " is null", group.getRoles());

			//check groups
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

		//login user
		Account user = authenticationService.login(login, password, unit);
		assertNotNull("Authentication with login, password and unit failed: user is null", user);
		assertNotNull("Authentication with login, password and unit failed: user groups is null", user.getGroups());
		Assert.assertEquals("Authentication with login, password and unit failed: number of user groups is not " + expectedGroups.size(), user.getGroups().size(), expectedGroups.size());

		//checks if user has input role
		Iterator<Group> groupIterator = user.getGroups().iterator();
		while (groupIterator.hasNext())
		{
			Group group = (Group) groupIterator.next();
			assertNotNull("Authentication with login, password and unit failed: units in group " + group.getCode() + " is null", group.getUnits());

			//check groups
			if (expectedGroups.contains(group.getCode()) == false)
			{
				Assert.fail("Authentication with login, password and unit failed: unexpected group with code " + group.getCode());
			}

			//checks units
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
