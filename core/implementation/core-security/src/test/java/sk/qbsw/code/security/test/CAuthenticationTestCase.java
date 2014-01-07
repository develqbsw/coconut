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
 * Checks Authentication service.
 *
 * @autor Tomas Lauro
 * @version 1.6.0
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@TransactionConfiguration (transactionManager = "transactionManager", defaultRollback = true)
public class CAuthenticationTestCase
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
		assertNotNull("Couldnt find Authentication service", authenticationService);
	}

	/**
	 * Login user.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testLogin () throws CSecurityException
	{
		databaseDataGenerator.generateDataForAuthenticationTests();

		testLoginWithDefaultUnit();
		testLoginWithoutDefaultUnit();
		testLoginWithDefaultUnitAndRole();
	}

	/**
	 * Test login with default unit.
	 *
	 * @throws CSecurityException the security exception
	 */
	private void testLoginWithDefaultUnit () throws CSecurityException
	{
		CUser user = authenticationService.login("user_with_default_unit", "user_with_default_unit");
		assertNotNull("Authentication with login and password failed: user is null", user);
		assertNotNull("Authentication with login and password failed: user groups is null", user.getGroups());
		Assert.assertEquals("Authentication with login and password failed: number of user groups is not 3", user.getGroups().size(), 3);
	}

	/**
	 * Test login without default unit.
	 *
	 * @throws CSecurityException the security exception
	 */
	private void testLoginWithoutDefaultUnit () throws CSecurityException
	{
		CUser user = authenticationService.login("user_without_default_unit", "user_without_default_unit");
		assertNotNull("Authentication with login and password failed: user is null", user);
		assertNotNull("Authentication with login and password failed: user groups is null", user.getGroups());
		Assert.assertEquals("Authentication with login and password failed: number of user groups is not 2", user.getGroups().size(), 2);
	}

	/**
	 * Test login with default unit and role.
	 *
	 * @throws CSecurityException the security exception
	 */
	private void testLoginWithDefaultUnitAndRole () throws CSecurityException
	{
		CRole inputRole = new CRole("role_1");
		CUser user = authenticationService.login("user_with_default_unit", "user_with_default_unit", inputRole);
		assertNotNull("Authentication with login, password and role failed: user is null", user);
		assertNotNull("Authentication with login, password and role failed: user groups is null", user.getGroups());
		Assert.assertEquals("Authentication with login, password and role failed: number of user groups is not 2", user.getGroups().size(), 3);

		CRole outputRole = null;
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
		assertNotNull("Authentication with login, password and role failed: user has not a requested role " + inputRole.getCode(), outputRole);
	}
}
