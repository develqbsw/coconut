package sk.qbsw.security.authentication.ldap.test.util;

import org.junit.Assert;
import org.springframework.stereotype.Component;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.authentication.service.AuthenticationService;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Role;
import sk.qbsw.security.core.model.domain.Unit;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertNotNull;

/**
 * Provides test for authentication.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.6.0
 */
@Component
public class AuthenticationTestProvider
{
	public void testLoginWithDefaultUnit (AuthenticationService authenticationService) throws CSecurityException
	{
		Account account = authenticationService.login(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE);

		assertNotNull("Authentication with login and password failed: account is null", account);
		assertNotNull("Authentication with login and password failed: account groups is null", account.getGroups());
		Assert.assertEquals("Authentication with login and password failed: number of account groups is not 2", account.getGroups().size(), 2);
	}

	public void testLoginWithDefaultUnitIncorrectPassword (AuthenticationService authenticationService) throws CSecurityException
	{
		authenticationService.login(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, "incorrectPassword");
	}

	public void testLoginWithoutDefaultUnit (AuthenticationService authenticationService) throws CSecurityException
	{
		Account account = authenticationService.login(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE);

		assertNotNull("Authentication with login and password failed: account is null", account);
		assertNotNull("Authentication with login and password failed: account groups is null", account.getGroups());
		Assert.assertEquals("Authentication with login and password failed: number of account groups is not 2", account.getGroups().size(), 2);
	}

	public void testLoginWithDefaultUnitAndRolePositive (AuthenticationService authenticationService) throws CSecurityException
	{
		Set<String> expectedGroups = new HashSet<>();
		expectedGroups.add(DataGenerator.FIRST_GROUP_IN_UNIT_CODE);
		expectedGroups.add(DataGenerator.SECOND_GROUP_IN_UNIT_CODE);
		Role inputRole = new Role();
		inputRole.setCode(DataGenerator.FIRST_ROLE_CODE);

		testLoginWithRole(authenticationService, DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, inputRole, expectedGroups);
	}

	public void testLoginWithDefaultUnitAndRoleNegative (AuthenticationService authenticationService) throws CSecurityException
	{
		Role inputRole = new Role();
		inputRole.setCode(DataGenerator.SECOND_ROLE_CODE);
		authenticationService.login(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, inputRole);
	}

	public void testLoginWithoutDefaultUnitAndRolePositive (AuthenticationService authenticationService) throws CSecurityException
	{
		Set<String> expectedGroups = new HashSet<>();
		expectedGroups.add(DataGenerator.FIRST_GROUP_NOT_IN_UNIT_CODE);
		expectedGroups.add(DataGenerator.SECOND_GROUP_NOT_IN_UNIT_CODE);
		Role inputRole = new Role();
		inputRole.setCode(DataGenerator.FIRST_ROLE_CODE);

		testLoginWithRole(authenticationService, DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, inputRole, expectedGroups);
	}

	public void testLoginWithoutDefaultUnitAndRoleNegative (AuthenticationService authenticationService) throws CSecurityException
	{
		Role inputRole = new Role();
		inputRole.setCode(DataGenerator.SECOND_ROLE_CODE);
		authenticationService.login(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, inputRole);
	}

	public void testLoginWithDefaultUnitAndUnit (AuthenticationService authenticationService) throws CSecurityException
	{
		Set<String> expectedGroups = new HashSet<>();
		expectedGroups.add(DataGenerator.SECOND_GROUP_IN_UNIT_CODE);
		expectedGroups.add(DataGenerator.THIRD_GROUP_IN_UNIT_CODE);
		String unit = DataGenerator.FIRST_UNIT_CODE;

		testLoginWithUnit(authenticationService, DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, unit, expectedGroups);
	}

	public void testLoginWithoutDefaultUnitAndUnit (AuthenticationService authenticationService) throws CSecurityException
	{
		Set<String> expectedGroups = new HashSet<>();
		expectedGroups.add(DataGenerator.SECOND_GROUP_IN_UNIT_CODE);
		String unit = DataGenerator.FIRST_UNIT_CODE;

		testLoginWithUnit(authenticationService, DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, unit, expectedGroups);
	}

	public void testLoginEnabledAccountDisabledOrganization (AuthenticationService authenticationService) throws CSecurityException
	{
		authenticationService.login(DataGenerator.ACCOUNT_ENABLED_IN_DISABLED_ORGANIZATION, DataGenerator.ACCOUNT_ENABLED_IN_DISABLED_ORGANIZATION);
	}

	public void testLoginDisabledAccountDisabledOrganization (AuthenticationService authenticationService) throws CSecurityException
	{
		authenticationService.login(DataGenerator.ACCOUNT_DISABLED_IN_DISABLED_ORGANIZATION, DataGenerator.ACCOUNT_DISABLED_IN_DISABLED_ORGANIZATION);
	}

	public void testLoginDisabledAccountEnabledOrganization (AuthenticationService authenticationService) throws CSecurityException
	{
		authenticationService.login(DataGenerator.ACCOUNT_DISABLED_IN_ENABLED_ORGANIZATION, DataGenerator.ACCOUNT_DISABLED_IN_ENABLED_ORGANIZATION);
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
