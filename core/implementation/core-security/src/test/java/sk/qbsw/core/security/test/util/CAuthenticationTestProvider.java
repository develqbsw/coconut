package sk.qbsw.core.security.test.util;

import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.springframework.stereotype.Component;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.security.dao.IBlockedLoginDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.domain.CBlockedLogin;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.service.IAuthenticationService;
import sk.qbsw.core.security.service.IOrganizationService;
import sk.qbsw.core.security.service.IUserService;

/**
 * Provides test for authentication.
 *
 * @autor Tomas Lauro
 * @version 1.12.2
 * @since 1.6.0
 */
@Component
public class CAuthenticationTestProvider
{
	/**
	 * Test login with default unit.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testLoginWithDefaultUnit (IAuthenticationService authenticationService) throws CSecurityException
	{
		CUser user = authenticationService.login(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE);

		assertNotNull("Authentication with login and password failed: user is null", user);
		assertNotNull("Authentication with login and password failed: user groups is null", user.getGroups());
		Assert.assertEquals("Authentication with login and password failed: number of user groups is not 2", user.getGroups().size(), 2);
	}

	/**
	 * Test login with default unit - incorrect password.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testLoginWithDefaultUnitIncorrectPassword (IAuthenticationService authenticationService) throws CSecurityException
	{
		authenticationService.login(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, "incorrectPassword");
	}

	/**
	 * Test login without default unit.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testLoginWithoutDefaultUnit (IAuthenticationService authenticationService) throws CSecurityException
	{
		CUser user = authenticationService.login(CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE);

		assertNotNull("Authentication with login and password failed: user is null", user);
		assertNotNull("Authentication with login and password failed: user groups is null", user.getGroups());
		Assert.assertEquals("Authentication with login and password failed: number of user groups is not 2", user.getGroups().size(), 2);
	}

	/**
	 * Test login with default unit and role. The role should be found - login success.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testLoginWithDefaultUnitAndRolePositive (IAuthenticationService authenticationService) throws CSecurityException
	{
		Set<String> expectedGroups = new HashSet<String>();
		expectedGroups.add(CDataGenerator.FIRST_GROUP_IN_UNIT_CODE);
		expectedGroups.add(CDataGenerator.SECOND_GROUP_IN_UNIT_CODE);
		CRole inputRole = new CRole(CDataGenerator.FIRST_ROLE_CODE);

		testLoginWithRole(authenticationService, CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, inputRole, expectedGroups);
	}

	/**
	 * Test login with default unit and role. The role should not be found - login fails.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testLoginWithDefaultUnitAndRoleNegative (IAuthenticationService authenticationService) throws CSecurityException
	{
		CRole inputRole = new CRole(CDataGenerator.SECOND_ROLE_CODE);
		authenticationService.login(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, inputRole);
	}

	/**
	 * Test login without default unit and with role. The role should be found - login success.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testLoginWithoutDefaultUnitAndRolePositive (IAuthenticationService authenticationService) throws CSecurityException
	{
		Set<String> expectedGroups = new HashSet<String>();
		expectedGroups.add(CDataGenerator.FIRST_GROUP_NOT_IN_UNIT_CODE);
		expectedGroups.add(CDataGenerator.SECOND_GROUP_NOT_IN_UNIT_CODE);
		CRole inputRole = new CRole(CDataGenerator.FIRST_ROLE_CODE);

		testLoginWithRole(authenticationService, CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, inputRole, expectedGroups);
	}

	/**
	 * Test login without default unit and with role. The role should not be found - login fails.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testLoginWithoutDefaultUnitAndRoleNegative (IAuthenticationService authenticationService) throws CSecurityException
	{
		CRole inputRole = new CRole(CDataGenerator.SECOND_ROLE_CODE);
		authenticationService.login(CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, inputRole);
	}

	/**
	 * Test login with default unit and with unit. The user should be found - login success.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testLoginWithDefaultUnitAndUnit (IAuthenticationService authenticationService) throws CSecurityException
	{
		Set<String> expectedGroups = new HashSet<String>();
		expectedGroups.add(CDataGenerator.SECOND_GROUP_IN_UNIT_CODE);
		expectedGroups.add(CDataGenerator.THIRD_GROUP_IN_UNIT_CODE);
		String unit = CDataGenerator.FIRST_UNIT_CODE;

		testLoginWithUnit(authenticationService, CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, unit, expectedGroups);
	}

	/**
	 * Test login without default unit and with unit. The user should be found - login success.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testLoginWithoutDefaultUnitAndUnit (IAuthenticationService authenticationService) throws CSecurityException
	{
		Set<String> expectedGroups = new HashSet<String>();
		expectedGroups.add(CDataGenerator.SECOND_GROUP_IN_UNIT_CODE);
		String unit = CDataGenerator.FIRST_UNIT_CODE;

		testLoginWithUnit(authenticationService, CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, unit, expectedGroups);
	}

	/**
	 * Test change plain text password.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testChangePasswordExistingUser (IAuthenticationService authenticationService) throws CSecurityException
	{
		String newPassword = "change1Password3ExistingUser@";
		authenticationService.changePlainPassword(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE + "@qbsw.sk", newPassword);

		//test authentication
		CUser user = authenticationService.login(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, newPassword);

		assertNotNull("The plain text password change failed", user);
	}

	/**
	 * Test change encrypted password.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testChangeEncryptedPasswordExistingUser (IAuthenticationService authenticationService) throws CSecurityException
	{
		final String newPassword = "change1EncryptedPasswordExistingUser$56";
		authenticationService.changeEncryptedPassword(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, newPassword);

		//test authentication
		CUser user = authenticationService.login(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, newPassword);

		assertNotNull("The plain text password change failed", user);
	}

	/**
	 * Test change encrypted password with new user.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testChangeEncryptedPasswordNewUser (IAuthenticationService authenticationService, IUserService userService, IUserDao userDao, IOrganizationService orgService, CDataGenerator dataGenerator) throws CSecurityException
	{
		//create new user and needed objects
		CUser newUser = dataGenerator.createUser(CDataGenerator.USER_WITHOUT_PASSWORD);
		COrganization organization = orgService.getOrganizationByNameNull(CDataGenerator.ORGANIZATION_CODE);
		String newPassword = "change56%PasswordNewUser";

		//register user
		userService.registerNewUser(newUser, newPassword, organization);

		//flush and clear persistent context - the user's auth params are saved using dao 
		userDao.flush();
		userDao.clear();

		//test authentication
		assertNotNull("The encrypted password change failed", authenticationService.login(CDataGenerator.USER_WITHOUT_PASSWORD, newPassword));
	}

	/**
	 * Test change login name of user.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testChangeLogin (IAuthenticationService authenticationService, IUserService userService) throws CSecurityException
	{
		String newLogin = "new1Login#";
		CUser user = userService.getUserByLogin(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE);

		//change login
		authenticationService.changeLogin(user.getId(), newLogin);

		//test new and old login
		CUser userWithNewLogin = userService.getUserByLogin(newLogin);
		Assert.assertEquals("There login change failed", user.getId(), userWithNewLogin.getId());
	}

	/**
	 * Test if the source is online.
	 * 
	 */
	public void testIsOnline (IAuthenticationService authenticationService)
	{
		Assert.assertTrue("Checks is online failed - the source is offline", authenticationService.isOnline());
	}

	/**
	 * Test login enabled user in disabled organization.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testLoginEnabledUserDisabledOrganization (IAuthenticationService authenticationService, IUserService userService) throws CSecurityException
	{
		authenticationService.login(CDataGenerator.USER_ENABLED_IN_DISABLED_ORGANIZATION, CDataGenerator.USER_ENABLED_IN_DISABLED_ORGANIZATION);
	}

	/**
	 * Test login disabled user in disabled organization.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testLoginDisabledUserDisabledOrganization (IAuthenticationService authenticationService, IUserService userService) throws CSecurityException
	{
		authenticationService.login(CDataGenerator.USER_DISABLED_IN_DISABLED_ORGANIZATION, CDataGenerator.USER_DISABLED_IN_DISABLED_ORGANIZATION);
	}

	/**
	 * Test login disabled user in enabled organization.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testLoginDisabledUserEnabledOrganization (IAuthenticationService authenticationService, IUserService userService) throws CSecurityException
	{
		authenticationService.login(CDataGenerator.USER_DISABLED_IN_ENABLED_ORGANIZATION, CDataGenerator.USER_DISABLED_IN_ENABLED_ORGANIZATION);
	}

	/**
	 * Test blocked login without blocked.
	 *
	 * @param authenticationService the authentication service
	 * @param blockedLoginJpaDao the blocked login jpa dao
	 * @throws CSystemException the c system exception
	 * @throws CSecurityException the c security exception
	 */
	public void testBlockedLoginWithoutBlocked (IAuthenticationService authenticationService, IBlockedLoginDao blockedLoginJpaDao) throws CSystemException, CSecurityException
	{
		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		//flush 
		blockedLoginJpaDao.flush();

		CBlockedLogin recordIpOne = blockedLoginJpaDao.findByLoginAndIp(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		CBlockedLogin recordIpTwo = blockedLoginJpaDao.findByLoginAndIp(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);

		boolean isBlockedIpOne = authenticationService.isLoginBlocked(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		boolean isBlockedIpTwo = authenticationService.isLoginBlocked(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);

		Assert.assertNotNull(recordIpOne);
		Assert.assertEquals(4, recordIpOne.getInvalidLoginCount());
		Assert.assertFalse(isBlockedIpOne);

		Assert.assertNotNull(recordIpTwo);
		Assert.assertEquals(3, recordIpTwo.getInvalidLoginCount());
		Assert.assertFalse(isBlockedIpTwo);
	}

	/**
	 * Test blocked login without blocked check ip null.
	 *
	 * @param authenticationService the authentication service
	 * @param blockedLoginJpaDao the blocked login jpa dao
	 * @throws CSystemException the c system exception
	 * @throws CSecurityException the c security exception
	 */
	public void testBlockedLoginWithoutBlockedCheckIpNull (IAuthenticationService authenticationService, IBlockedLoginDao blockedLoginJpaDao) throws CSystemException, CSecurityException
	{
		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		//flush 
		blockedLoginJpaDao.flush();

		CBlockedLogin recordIpOne = blockedLoginJpaDao.findByLoginAndIp(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		CBlockedLogin recordIpTwo = blockedLoginJpaDao.findByLoginAndIp(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);

		boolean isBlocked = authenticationService.isLoginBlocked(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);

		Assert.assertNotNull(recordIpOne);
		Assert.assertEquals(4, recordIpOne.getInvalidLoginCount());

		Assert.assertNotNull(recordIpTwo);
		Assert.assertEquals(3, recordIpTwo.getInvalidLoginCount());

		Assert.assertFalse(isBlocked);
	}

	/**
	 * Test blocked login without blocked set ip null.
	 *
	 * @param authenticationService the authentication service
	 * @param blockedLoginJpaDao the blocked login jpa dao
	 * @throws CSystemException the c system exception
	 * @throws CSecurityException the c security exception
	 */
	public void testBlockedLoginWithoutBlockedSetIpNull (IAuthenticationService authenticationService, IBlockedLoginDao blockedLoginJpaDao) throws CSystemException, CSecurityException
	{
		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);
		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);
		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);
		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);
		//flush 
		blockedLoginJpaDao.flush();

		CBlockedLogin recordIpNull = blockedLoginJpaDao.findByLoginAndIp(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);
		CBlockedLogin recordIpTwo = blockedLoginJpaDao.findByLoginAndIp(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);

		boolean isBlockedIpNull = authenticationService.isLoginBlocked(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);
		boolean isBlockedIpTwo = authenticationService.isLoginBlocked(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);

		Assert.assertNotNull(recordIpNull);
		Assert.assertEquals(4, recordIpNull.getInvalidLoginCount());
		Assert.assertFalse(isBlockedIpNull);

		Assert.assertNotNull(recordIpTwo);
		Assert.assertEquals(3, recordIpTwo.getInvalidLoginCount());
		Assert.assertFalse(isBlockedIpTwo);
	}

	/**
	 * Test blocked login with blocked.
	 *
	 * @param authenticationService the authentication service
	 * @param blockedLoginJpaDao the blocked login jpa dao
	 * @throws CSystemException the c system exception
	 * @throws CSecurityException the c security exception
	 */
	public void testBlockedLoginWithBlocked (IAuthenticationService authenticationService, IBlockedLoginDao blockedLoginJpaDao) throws CSystemException, CSecurityException
	{
		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		//flush 
		blockedLoginJpaDao.flush();

		CBlockedLogin recordIpOne = blockedLoginJpaDao.findByLoginAndIp(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		CBlockedLogin recordIpTwo = blockedLoginJpaDao.findByLoginAndIp(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);

		boolean isBlockedIpOne = authenticationService.isLoginBlocked(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		boolean isBlockedIpTwo = authenticationService.isLoginBlocked(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);

		Assert.assertNotNull(recordIpOne);
		Assert.assertEquals(5, recordIpOne.getInvalidLoginCount());
		Assert.assertTrue(isBlockedIpOne);

		Assert.assertNotNull(recordIpTwo);
		Assert.assertEquals(4, recordIpTwo.getInvalidLoginCount());
		Assert.assertFalse(isBlockedIpTwo);
	}

	/**
	 * Test blocked login with blocked check ip null.
	 *
	 * @param authenticationService the authentication service
	 * @param blockedLoginJpaDao the blocked login jpa dao
	 * @throws CSystemException the c system exception
	 * @throws CSecurityException the c security exception
	 */
	public void testBlockedLoginWithBlockedCheckIpNull (IAuthenticationService authenticationService, IBlockedLoginDao blockedLoginJpaDao) throws CSystemException, CSecurityException
	{
		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		//flush 
		blockedLoginJpaDao.flush();

		CBlockedLogin recordIpOne = blockedLoginJpaDao.findByLoginAndIp(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		CBlockedLogin recordIpTwo = blockedLoginJpaDao.findByLoginAndIp(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);

		boolean isBlocked = authenticationService.isLoginBlocked(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);

		Assert.assertNotNull(recordIpOne);
		Assert.assertEquals(5, recordIpOne.getInvalidLoginCount());

		Assert.assertNotNull(recordIpTwo);
		Assert.assertEquals(4, recordIpTwo.getInvalidLoginCount());

		Assert.assertTrue(isBlocked);
	}

	/**
	 * Test blocked login with blocked set ip null.
	 *
	 * @param authenticationService the authentication service
	 * @param blockedLoginJpaDao the blocked login jpa dao
	 * @throws CSystemException the c system exception
	 * @throws CSecurityException the c security exception
	 */
	public void testBlockedLoginWithBlockedSetIpNull (IAuthenticationService authenticationService, IBlockedLoginDao blockedLoginJpaDao) throws CSystemException, CSecurityException
	{
		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);
		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);
		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);
		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);
		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		authenticationService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);
		//flush 
		blockedLoginJpaDao.flush();

		CBlockedLogin recordIpNull = blockedLoginJpaDao.findByLoginAndIp(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);
		CBlockedLogin recordIpTwo = blockedLoginJpaDao.findByLoginAndIp(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);

		boolean isBlockedIpNull = authenticationService.isLoginBlocked(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);
		boolean isBlockedIpTwo = authenticationService.isLoginBlocked(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);

		Assert.assertNotNull(recordIpNull);
		Assert.assertEquals(5, recordIpNull.getInvalidLoginCount());
		Assert.assertTrue(isBlockedIpNull);

		Assert.assertNotNull(recordIpTwo);
		Assert.assertEquals(4, recordIpTwo.getInvalidLoginCount());
		Assert.assertFalse(isBlockedIpTwo);
	}
	
	/**
	 * Test login invalid from auth param.
	 *
	 * @param authenticationService the authentication service
	 * @throws CSecurityException the c security exception
	 */
	public void testLoginInvalidFromAuthParam (IAuthenticationService authenticationService) throws CSecurityException
	{
		String newPassword = "new1Login#";
		authenticationService.changeEncryptedPassword(CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, newPassword, DateTime.now().plusHours(2), null);
		
		authenticationService.login(CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, newPassword);
	}
	
	/**
	 * Test login invalid to auth param.
	 *
	 * @param authenticationService the authentication service
	 * @throws CSecurityException the c security exception
	 */
	public void testLoginInvalidToAuthParam (IAuthenticationService authenticationService) throws CSecurityException
	{
		String newPassword = "new1Login#";
		authenticationService.changeEncryptedPassword(CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, newPassword, null, DateTime.now().minusHours(1));
		
		authenticationService.login(CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, newPassword);
	}
	
	/**
	 * Test login invalid from and to auth param.
	 *
	 * @param authenticationService the authentication service
	 * @throws CSecurityException the c security exception
	 */
	public void testLoginInvalidFromAndToAuthParam (IAuthenticationService authenticationService) throws CSecurityException
	{
		String newPassword = "new1Login#";
		authenticationService.changeEncryptedPassword(CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, newPassword, DateTime.now().minusHours(2), DateTime.now().minusHours(1));
		
		authenticationService.login(CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, newPassword);
	}
	
	/**
	 * Test change password null from and to auth param.
	 *
	 * @param authenticationService the authentication service
	 * @throws CSecurityException the c security exception
	 */
	public void testChangePasswordNullFromAndToAuthParam (IAuthenticationService authenticationService) throws CSecurityException
	{
		String newPassword = "new1Login#";
		authenticationService.changeEncryptedPassword(CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, newPassword, null, null);
		
		authenticationService.login(CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, newPassword);
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
	private void testLoginWithRole (IAuthenticationService authenticationService, String login, String password, CRole inputRole, Set<String> expectedGroups) throws CSecurityException
	{
		//define output role - found in user
		CRole outputRole = null;

		//login user
		CUser user = authenticationService.login(login, password, inputRole);
		assertNotNull("Authentication with login, password and role failed: user is null", user);
		assertNotNull("Authentication with login, password and role failed: user groups is null", user.getGroups());
		Assert.assertEquals("Authentication with login, password and role failed: number of user groups is not " + expectedGroups.size(), user.getGroups().size(), expectedGroups.size());

		//checks if user has input role
		Iterator<CGroup> groupIterator = user.getGroups().iterator();
		while (groupIterator.hasNext())
		{
			CGroup group = (CGroup) groupIterator.next();
			assertNotNull("Authentication with login, password and role failed: user roles in group " + group.getCode() + " is null", group.getRoles());

			//check groups
			if (expectedGroups.contains(group.getCode()) == false)
			{
				Assert.fail("Authentication with login, password and role failed: unexpected group with code " + group.getCode());
			}

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
	private void testLoginWithUnit (IAuthenticationService authenticationService, String login, String password, String unit, Set<String> expectedGroups) throws CSecurityException
	{
		CUnit outputUnit = null;

		//login user
		CUser user = authenticationService.login(login, password, unit);
		assertNotNull("Authentication with login, password and unit failed: user is null", user);
		assertNotNull("Authentication with login, password and unit failed: user groups is null", user.getGroups());
		Assert.assertEquals("Authentication with login, password and unit failed: number of user groups is not " + expectedGroups.size(), user.getGroups().size(), expectedGroups.size());

		//checks if user has input role
		Iterator<CGroup> groupIterator = user.getGroups().iterator();
		while (groupIterator.hasNext())
		{
			CGroup group = (CGroup) groupIterator.next();
			assertNotNull("Authentication with login, password and unit failed: units in group " + group.getCode() + " is null", group.getUnits());

			//check groups
			if (expectedGroups.contains(group.getCode()) == false)
			{
				Assert.fail("Authentication with login, password and unit failed: unexpected group with code " + group.getCode());
			}

			//checks units
			Iterator<CUnit> unitsIterator = group.getUnits().iterator();
			while (unitsIterator.hasNext())
			{
				CUnit tempOutputUnit = (CUnit) unitsIterator.next();
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
