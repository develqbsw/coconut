package sk.qbsw.code.security.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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

import sk.qbsw.code.security.test.util.CAuthorizationTestProvider;
import sk.qbsw.code.security.test.util.CDataGenerator;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator;
import sk.qbsw.core.security.service.IAuthorizationService;

/**
 * Checks Authorization service for database.
 * 
 * @author Tomas Lauro
 * @version 1.6.0
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@TransactionConfiguration (transactionManager = "transactionManager")
public class CLdapAuthorizationTestCase
{
	/** The database data generator. */
	@Autowired
	private CDataGenerator dataGenerator;

	/** The authorization service. */
	@Autowired
	@Qualifier ("ldapAuthorizationService")
	private IAuthorizationService authorizationService;

	/** The authorization test provider. */
	@Autowired
	private CAuthorizationTestProvider authorizationTestProvider;

	/** The user ldap dao. */
	@Autowired
	@Qualifier ("ldapUserDaoMock")
	private IUserDao userLdapDao;

	/** The ldap configurator. */
	@Autowired
	private ILdapAuthenticationConfigurator ldapConfigurator;

	/**
	 * Inits the test case.
	 */
	@Before
	public void initTestCase ()
	{
		ldapConfigurator.setServerName("lauro8");
		ldapConfigurator.setServerPort(10389);
		ldapConfigurator.setUserDn("uid=admin,ou=system");
		ldapConfigurator.setUserPassword("secret");
		ldapConfigurator.setGroupSearchBaseDn("ou=groups,dc=mfsr,dc=sk");
		ldapConfigurator.setUserSearchBaseDn("ou=users,dc=mfsr,dc=sk");
		ldapConfigurator.setUserOrganizationId((long) 1);
	}

	/**
	 * Test initialization.
	 */
	@Test
	public void testInitialization ()
	{
		assertNotNull("Could not find Authorization service", authorizationService);
	}

	/**
	 * Test successful authorization of user with default unit.
	 * @throws Exception 
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testAuthorizationWithDefaultUnitPositive () throws Exception
	{
		initTestUserWithDefaultUnit();

		authorizationTestProvider.testAuthorizationWithDefaultUnitPositive(authorizationService);
	}

	/**
	 * Test unsuccessful authorization of user with default unit.
	 * @throws Exception 
	 */
	@Test (expected = CSecurityException.class)
	@Transactional
	@Rollback (true)
	public void testAuthorizationWithDefaultUnitNegative () throws Exception
	{
		initTestUserWithDefaultUnit();

		authorizationTestProvider.testAuthorizationWithDefaultUnitNegative(authorizationService);
	}

	/**
	 * Test successful authorization of user with unit.
	 * @throws Exception 
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testAuthorizationWithUnitPositive () throws Exception
	{
		initTestUserWithDefaultUnit();

		authorizationTestProvider.testAuthorizationWithUnitPositive(authorizationService);
	}

	/**
	 * Test unsuccessful authorization of user with unit.
	 * @throws Exception 
	 */
	@Test (expected = CSecurityException.class)
	@Transactional
	@Rollback (true)
	public void testAuthorizationWithUnitNegative () throws Exception
	{
		initTestUserWithDefaultUnit();

		authorizationTestProvider.testAuthorizationWithUnitNegative(authorizationService);
	}

	/**
	 * Test successful authorization of user with category.
	 * @throws Exception 
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testAuthorizationWithCategoryPositive () throws Exception
	{
		initTestUserWithDefaultUnit();

		authorizationTestProvider.testAuthorizationWithCategoryPositive(authorizationService);
	}

	/**
	 * Test unsuccessful authorization of user with category.
	 * @throws Exception 
	 */
	@Test (expected = CSecurityException.class)
	@Transactional
	@Rollback (true)
	public void testAuthorizationWithCategoryNegative () throws Exception
	{
		initTestUserWithDefaultUnit();

		authorizationTestProvider.testAuthorizationWithCategoryNegative(authorizationService);
	}

	/**
	 * Test successful authorization of user with unit and category.
	 * @throws Exception 
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testAuthorizationWithUnitAndCategoryPositive () throws Exception
	{
		initTestUserWithDefaultUnit();

		authorizationTestProvider.testAuthorizationWithUnitAndCategoryPositive(authorizationService);
	}

	/**
	 * Test unsuccessful authorization of user with unit and category.
	 * @throws Exception 
	 */
	@Test (expected = CSecurityException.class)
	@Transactional
	@Rollback (true)
	public void testAuthorizationWithUnitAndCategoryNegative () throws Exception
	{
		initTestUserWithDefaultUnit();

		authorizationTestProvider.testAuthorizationWithUnitAndCategoryNegative(authorizationService);
	}

	/**
	 * Inits the test user with default unit.
	 *
	 * @throws Exception the exception
	 */
	private void initTestUserWithDefaultUnit () throws Exception
	{
		dataGenerator.generateDatabaseDataForLdapTests();
		CUser user = dataGenerator.generateUserWithDefaultUnitForLdapTests();

		//init mocks
		Mockito.when(userLdapDao.findByLogin(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE)).thenReturn(user);

		//replace autowired variables
		ReflectionTestUtils.setField(unwrapSpringProxyObject(authorizationService), "userLdapDao", userLdapDao);
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
