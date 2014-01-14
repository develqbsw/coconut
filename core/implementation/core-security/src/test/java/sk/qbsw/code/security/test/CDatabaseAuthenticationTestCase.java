package sk.qbsw.code.security.test;

import static org.junit.Assert.assertNotNull;

import java.util.Iterator;

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

import sk.qbsw.code.security.test.util.CDatabaseDataGenerator;
import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.service.IAuthenticationService;

/**
 * Checks Authentication service for database.
 *
 * @autor Tomas Lauro
 * @version 1.6.0
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@TransactionConfiguration (transactionManager = "transactionManager", defaultRollback = true)
public class CDatabaseAuthenticationTestCase
{
	/** The database data generator. */
	@Autowired
	private CDatabaseDataGenerator databaseDataGenerator;

	/** The authentication service. */
	@Autowired
	@Qualifier ("cLoginService")
	private IAuthenticationService authenticationService;

	/**
	 * Test initialization.
	 */
	@Test
	public void testInitialization ()
	{
		assertNotNull("Could not find Authentication service", authenticationService);
	}

	/**
	 * Test login with default unit.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testLoginWithDefaultUnit () throws CSecurityException
	{
		databaseDataGenerator.generateDataForAuthenticationTests();

		CUser user = authenticationService.login("user_with_default_unit", "user_with_default_unit");
		assertNotNull("Authentication with login and password failed: user is null", user);
		assertNotNull("Authentication with login and password failed: user groups is null", user.getGroups());
		Assert.assertEquals("Authentication with login and password failed: number of user groups is not 2", user.getGroups().size(), 2);
	}

	/**
	 * Test login without default unit.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testLoginWithoutDefaultUnit () throws CSecurityException
	{
		databaseDataGenerator.generateDataForAuthenticationTests();

		CUser user = authenticationService.login("user_without_default_unit", "user_without_default_unit");
		assertNotNull("Authentication with login and password failed: user is null", user);
		assertNotNull("Authentication with login and password failed: user groups is null", user.getGroups());
		Assert.assertEquals("Authentication with login and password failed: number of user groups is not 2", user.getGroups().size(), 2);
	}

	/**
	 * Test login with default unit and role. The role should be found - login success.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testLoginWithDefaultUnitAndRolePositive () throws CSecurityException
	{
		databaseDataGenerator.generateDataForAuthenticationTests();

		CRole inputRole = new CRole("role_1");
		CRole outputRole = testLoginWithDefaultUnitAndRole(inputRole);

		assertNotNull("Authentication with login, password and role failed: user has not a requested role " + inputRole.getCode(), outputRole);
	}

	/**
	 * Test login with default unit and role. The role should not be found - login fails.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional
	@Rollback (true)
	public void testLoginWithDefaultUnitAndRoleNegative () throws CSecurityException
	{
		databaseDataGenerator.generateDataForAuthenticationTests();

		CRole inputRole = new CRole("role_2");
		authenticationService.login("user_with_default_unit", "user_with_default_unit", inputRole);
	}

	/**
	 * Test login with default unit and role. Return role if found else null.
	 *
	 * @param inputRole the input role - the role to find for user.
	 * @return the role or null if not found
	 * @throws CSecurityException the security exception
	 */
	private CRole testLoginWithDefaultUnitAndRole (CRole inputRole) throws CSecurityException
	{
		//define output role - found in user
		CRole outputRole = null;

		//login user
		CUser user = authenticationService.login("user_with_default_unit", "user_with_default_unit", inputRole);
		assertNotNull("Authentication with login, password and role failed: user is null", user);
		assertNotNull("Authentication with login, password and role failed: user groups is null", user.getGroups());
		Assert.assertEquals("Authentication with login, password and role failed: number of user groups is not 2", user.getGroups().size(), 2);

		//checks if user has input role
		Iterator<CGroup> groupIterator = user.getGroups().iterator();
		while (groupIterator.hasNext())
		{
			CGroup group = (CGroup) groupIterator.next();
			assertNotNull("Authentication with login, password and role failed: user roles in group " + group.getCode() + " is null", group.getRoles());

			Iterator<CRole> roleIterator = group.getRoles().iterator();
			while (roleIterator.hasNext())
			{
				CRole tempOutputRole = (CRole) roleIterator.next();
				if (tempOutputRole.getCode().equals(inputRole.getCode()))
				{
					outputRole = tempOutputRole;
					break;
				}

			}

			if (outputRole != null)
			{
				break;
			}

		}

		return outputRole;
	}
}
