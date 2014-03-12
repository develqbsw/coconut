package sk.qbsw.code.security.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.code.security.test.util.CAuthenticationTestProvider;
import sk.qbsw.code.security.test.util.CDataGenerator;
import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator;
import sk.qbsw.core.security.service.IAuthenticationService;
import sk.qbsw.core.security.service.ldap.CLdapProvider;

/**
 * Checks Authentication service for ldap.
 *
 * @autor Tomas Lauro
 * @version 1.7.1
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-ldap-context.xml"})
@TransactionConfiguration (transactionManager = "transactionManager", defaultRollback = true)
public class CLdapAuthenticationTestCase
{
	/** The database data generator. */
	@Autowired
	private CDataGenerator dataGenerator;

	/** The authentication service. */
	@Autowired
	@Qualifier ("ldapAuthenticationService")
	private IAuthenticationService authenticationService;

	/** The authentication test provider. */
	@Autowired
	private CAuthenticationTestProvider authenticationTestProvider;

	/** The ldap provider. */
	@Autowired
	@Qualifier ("ldapProviderMock")
	private CLdapProvider ldapProvider;

	/** The ldap configurator. */
	@Autowired
	private ILdapAuthenticationConfigurator ldapConfigurator;

	/**
	 * Inits the test case.
	 */
	@Before
	public void initTestCase ()
	{
		ldapConfigurator.setServerName("talos.qbsw.local");
		ldapConfigurator.setServerPort(10389);
		ldapConfigurator.setUserDn("cn=ekolkyRW,ou=users,ou=system");
		ldapConfigurator.setUserPassword("ZiAvGJDxcqUOkyQ");
		ldapConfigurator.setUserSearchBaseDn("ou=users,dc=mfsr,dc=sk");
		ldapConfigurator.setUserObjectClass("inetOrgPerson");
		ldapConfigurator.setUserOrganizationId((long) 1);
	}

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
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testLoginWithDefaultUnit () throws Exception
	{
		initTestUserWithDefaultUnit();

		authenticationTestProvider.testLoginWithDefaultUnit(authenticationService);
	}

	/**
	 * Test login without default unit.
	 * @throws Exception 
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testLoginWithoutDefaultUnit () throws Exception
	{
		initTestUserWithoutDefaultUnit();

		authenticationTestProvider.testLoginWithoutDefaultUnit(authenticationService);
	}

	/**
	 * Test login with default unit and role. The role should be found - login success.
	 * @throws Exception 
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testLoginWithDefaultUnitAndRolePositive () throws Exception
	{
		initTestUserWithDefaultUnit();

		authenticationTestProvider.testLoginWithDefaultUnitAndRolePositive(authenticationService);
	}

	/**
	 * Test login with default unit and role. The role should not be found - login fails.
	 * @throws Exception 
	 */
	@Test (expected = CSecurityException.class)
	@Transactional
	@Rollback (true)
	public void testLoginWithDefaultUnitAndRoleNegative () throws Exception
	{
		initTestUserWithDefaultUnit();

		authenticationTestProvider.testLoginWithDefaultUnitAndRoleNegative(authenticationService);
	}

	/**
	 * Test login without default unit and with role. The role should be found - login success.
	 * @throws Exception 
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testLoginWithoutDefaultUnitAndRolePositive () throws Exception
	{
		initTestUserWithoutDefaultUnit();

		authenticationTestProvider.testLoginWithoutDefaultUnitAndRolePositive(authenticationService);
	}

	/**
	 * Test login without default unit and with role. The role should not be found - login fails.
	 * @throws Exception 
	 */
	@Test (expected = CSecurityException.class)
	@Transactional
	@Rollback (true)
	public void testLoginWithoutDefaultUnitAndRoleNegative () throws Exception
	{
		initTestUserWithoutDefaultUnit();

		authenticationTestProvider.testLoginWithoutDefaultUnitAndRoleNegative(authenticationService);
	}

	/**
	 * Test login with default unit and with unit. The user should be found - login success.
	 * @throws Exception 
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testLoginWithDefaultUnitAndUnit () throws Exception
	{
		initTestUserWithDefaultUnit();

		authenticationTestProvider.testLoginWithDefaultUnitAndUnit(authenticationService);
	}

	/**
	 * Test login without default unit and with unit. The user should be found - login success.
	 * @throws Exception 
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testLoginWithoutDefaultUnitAndUnit () throws Exception
	{
		initTestUserWithoutDefaultUnit();

		authenticationTestProvider.testLoginWithoutDefaultUnitAndUnit(authenticationService);
	}

	/**
	 * Test if the ldap is online.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional (readOnly = true)
	@Rollback (true)
	public void testIsOnline ()
	{
		authenticationTestProvider.testIsOnline(authenticationService);
	}

	/**
	 * Inits the test user with default unit.
	 *
	 * @throws Exception the exception
	 */
	private void initTestUserWithDefaultUnit () throws Exception
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
		ReflectionTestUtils.setField(unwrapSpringProxyObject(authenticationService), "ldapProvider", ldapProvider);
	}

	/**
	 * Inits the test user without default unit.
	 *
	 * @throws Exception the exception
	 */
	private void initTestUserWithoutDefaultUnit () throws Exception
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
		ReflectionTestUtils.setField(unwrapSpringProxyObject(authenticationService), "ldapProvider", ldapProvider);
	}

	/**
	 * Unwrap spring proxy object.
	 *
	 * @param <T> the generic type
	 * @param object the object
	 * @return the unwrapped object
	 * @throws Exception the exception
	 */
	@SuppressWarnings ("unchecked")
	private <T>T unwrapSpringProxyObject (T object) throws Exception
	{
		if (AopUtils.isAopProxy(object) && object instanceof Advised)
		{
			return (T) ((Advised) object).getTargetSource().getTarget();
		}
		else
		{
			throw new Exception("The object is not a aop advise proxy object");
		}
	}
}
