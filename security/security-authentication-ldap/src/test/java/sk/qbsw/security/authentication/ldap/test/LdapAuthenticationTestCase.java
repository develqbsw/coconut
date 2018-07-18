package sk.qbsw.security.authentication.ldap.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.security.base.exception.AccountDisabledException;
import sk.qbsw.core.testing.mock.IMockHelper;
import sk.qbsw.security.authentication.service.AuthenticationService;
import sk.qbsw.security.authentication.ldap.provider.LdapProvider;
import sk.qbsw.security.authentication.ldap.test.util.AuthenticationTestProvider;
import sk.qbsw.security.authentication.ldap.test.util.DataGenerator;

import static org.junit.Assert.assertNotNull;

/**
 * Checks Authentication service for ldap.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-ldap-context.xml"})
@Rollback
public class LdapAuthenticationTestCase
{
	@Autowired
	private DataGenerator dataGenerator;

	@Autowired
	private AuthenticationService ldapAuthenticationService;

	@Autowired
	private AuthenticationTestProvider authenticationTestProvider;

	@Autowired
	private LdapProvider ldapProvider;

	@Autowired
	private IMockHelper mockHelper;

	/**
	 * Test initialization.
	 */
	@Test
	public void testInitialization ()
	{
		assertNotNull("Could not find Authentication service", ldapAuthenticationService);
	}

	/**
	 * Test login with default unit.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testLoginWithDefaultUnit () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginWithDefaultUnit(ldapAuthenticationService);
	}

	/**
	 * Test login without default unit.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testLoginWithoutDefaultUnit () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginWithoutDefaultUnit(ldapAuthenticationService);
	}

	/**
	 * Test login with default unit and role positive.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testLoginWithDefaultUnitAndRolePositive () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginWithDefaultUnitAndRolePositive(ldapAuthenticationService);
	}

	/**
	 * Test login with default unit and role negative.
	 *
	 * @throws Exception the exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testLoginWithDefaultUnitAndRoleNegative () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginWithDefaultUnitAndRoleNegative(ldapAuthenticationService);
	}

	/**
	 * Test login without default unit and role positive.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testLoginWithoutDefaultUnitAndRolePositive () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginWithoutDefaultUnitAndRolePositive(ldapAuthenticationService);
	}

	/**
	 * Test login without default unit and role negative.
	 *
	 * @throws Exception the exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testLoginWithoutDefaultUnitAndRoleNegative () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginWithoutDefaultUnitAndRoleNegative(ldapAuthenticationService);
	}

	/**
	 * Test login with default unit and unit.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testLoginWithDefaultUnitAndUnit () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginWithDefaultUnitAndUnit(ldapAuthenticationService);
	}

	/**
	 * Test login without default unit and unit.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testLoginWithoutDefaultUnitAndUnit () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginWithoutDefaultUnitAndUnit(ldapAuthenticationService);
	}

	/**
	 * Test login enabled account disabled organization.
	 *
	 * @throws Exception the exception
	 */
	@Test (expected = AccountDisabledException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testLoginEnabledAccountDisabledOrganization () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginEnabledAccountDisabledOrganization(ldapAuthenticationService);
	}

	/**
	 * Test login disabled account disabled organization.
	 *
	 * @throws Exception the exception
	 */
	@Test (expected = AccountDisabledException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testLoginDisabledAccountDisabledOrganization () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginDisabledAccountDisabledOrganization(ldapAuthenticationService);
	}

	/**
	 * Test login disabled account enabled organization.
	 *
	 * @throws Exception the exception
	 */
	@Test (expected = AccountDisabledException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testLoginDisabledAccountEnabledOrganization () throws Exception
	{
		initTest();

		authenticationTestProvider.testLoginDisabledAccountEnabledOrganization(ldapAuthenticationService);
	}

	private void initTest ()
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
		ReflectionTestUtils.setField(mockHelper.unwrapSpringProxyObject(ldapAuthenticationService), "ldapProvider", ldapProvider);
	}
}
