package sk.qbsw.security.management.test.util;

import org.junit.Assert;
import org.springframework.stereotype.Component;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.security.core.dao.IBlockedLoginDao;
import sk.qbsw.security.core.model.domain.CBlockedLogin;
import sk.qbsw.security.management.service.ILoginBlockingService;

/**
 * Provides test for authentication.
 *
 * @autor Tomas Lauro
 * @version 1.12.2
 * @since 1.6.0
 */
@Component
public class CLoginBlockingTestProvider
{

	/**
	 * Test blocked login without blocked.
	 *
	 * @param loginBlockingService the authentication service
	 * @param blockedLoginJpaDao the blocked login jpa dao
	 * @throws CSystemException the c system exception
	 * @throws CSecurityException the c security exception
	 * @throws InterruptedException the interrupted exception
	 */
	public void testBlockedLoginWithoutBlocked (ILoginBlockingService loginBlockingService, IBlockedLoginDao blockedLoginJpaDao) throws CSystemException, CSecurityException, InterruptedException
	{
		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		//flush 
		blockedLoginJpaDao.flush();

		CBlockedLogin recordIpOne = blockedLoginJpaDao.findOneByLoginAndIp(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		CBlockedLogin recordIpTwo = blockedLoginJpaDao.findOneByLoginAndIp(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);

		//sleep for 1 second to be able to validate blocked login (there is a comparison to dateTime.now() inside)
		Thread.sleep(1000);

		boolean isBlockedIpOne = loginBlockingService.isLoginBlocked(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		boolean isBlockedIpTwo = loginBlockingService.isLoginBlocked(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);

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
	 * @param loginBlockingService the authentication service
	 * @param blockedLoginJpaDao the blocked login jpa dao
	 * @throws CSystemException the c system exception
	 * @throws CSecurityException the c security exception
	 * @throws InterruptedException the interrupted exception
	 */
	public void testBlockedLoginWithoutBlockedCheckIpNull (ILoginBlockingService loginBlockingService, IBlockedLoginDao blockedLoginJpaDao) throws CSystemException, CSecurityException, InterruptedException
	{
		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		//flush 
		blockedLoginJpaDao.flush();

		CBlockedLogin recordIpOne = blockedLoginJpaDao.findOneByLoginAndIp(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		CBlockedLogin recordIpTwo = blockedLoginJpaDao.findOneByLoginAndIp(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);

		//sleep for 1 second to be able to validate blocked login (there is a comparison to dateTime.now() inside)
		Thread.sleep(1000);
		boolean isBlocked = loginBlockingService.isLoginBlocked(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);

		Assert.assertNotNull(recordIpOne);
		Assert.assertEquals(4, recordIpOne.getInvalidLoginCount());

		Assert.assertNotNull(recordIpTwo);
		Assert.assertEquals(3, recordIpTwo.getInvalidLoginCount());

		Assert.assertFalse(isBlocked);
	}

	/**
	 * Test blocked login without blocked set ip null.
	 *
	 * @param loginBlockingService the authentication service
	 * @param blockedLoginJpaDao the blocked login jpa dao
	 * @throws CSystemException the c system exception
	 * @throws CSecurityException the c security exception
	 * @throws InterruptedException the interrupted exception
	 */
	public void testBlockedLoginWithoutBlockedSetIpNull (ILoginBlockingService loginBlockingService, IBlockedLoginDao blockedLoginJpaDao) throws CSystemException, CSecurityException, InterruptedException
	{
		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);
		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);
		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);
		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);
		//flush 
		blockedLoginJpaDao.flush();

		CBlockedLogin recordIpNull = blockedLoginJpaDao.findOneByLoginAndIp(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);
		CBlockedLogin recordIpTwo = blockedLoginJpaDao.findOneByLoginAndIp(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);

		//sleep for 1 second to be able to validate blocked login (there is a comparison to dateTime.now() inside)
		Thread.sleep(1000);
		boolean isBlockedIpNull = loginBlockingService.isLoginBlocked(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);
		boolean isBlockedIpTwo = loginBlockingService.isLoginBlocked(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);

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
	 * @param loginBlockingService the authentication service
	 * @param blockedLoginJpaDao the blocked login jpa dao
	 * @throws CSystemException the c system exception
	 * @throws CSecurityException the c security exception
	 * @throws InterruptedException the interrupted exception
	 */
	public void testBlockedLoginWithBlocked (ILoginBlockingService loginBlockingService, IBlockedLoginDao blockedLoginJpaDao) throws CSystemException, CSecurityException, InterruptedException
	{
		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		loginBlockingService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		//flush 
		blockedLoginJpaDao.flush();

		CBlockedLogin recordIpOne = blockedLoginJpaDao.findOneByLoginAndIp(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		CBlockedLogin recordIpTwo = blockedLoginJpaDao.findOneByLoginAndIp(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);

		//sleep for 1 second to be able to validate blocked login (there is a comparison to dateTime.now() inside)
		Thread.sleep(1000);
		boolean isBlockedIpOne = loginBlockingService.isLoginBlocked(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		boolean isBlockedIpTwo = loginBlockingService.isLoginBlocked(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);

		Assert.assertNotNull("The record from ip one is null", recordIpOne);
		Assert.assertEquals("The expected count of records from ip one is invalid", 5, recordIpOne.getInvalidLoginCount());
		Assert.assertTrue("The record from ip one is blocked", isBlockedIpOne);

		Assert.assertNotNull("The record from ip two is null", recordIpTwo);
		Assert.assertEquals("The expected count of records from ip one is invalid", 4, recordIpTwo.getInvalidLoginCount());
		Assert.assertFalse("The record from ip one is blocked", isBlockedIpTwo);
	}

	/**
	 * Test blocked login with blocked check ip null.
	 *
	 * @param blockingLoginService the authentication service
	 * @param blockedLoginJpaDao the blocked login jpa dao
	 * @throws CSystemException the c system exception
	 * @throws CSecurityException the c security exception
	 * @throws InterruptedException 
	 */
	public void testBlockedLoginWithBlockedCheckIpNull (ILoginBlockingService blockingLoginService, IBlockedLoginDao blockedLoginJpaDao) throws CSystemException, CSecurityException, InterruptedException
	{
		blockingLoginService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		blockingLoginService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		blockingLoginService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		blockingLoginService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		blockingLoginService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		blockingLoginService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		blockingLoginService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		blockingLoginService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		blockingLoginService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		//flush 
		blockedLoginJpaDao.flush();

		CBlockedLogin recordIpOne = blockedLoginJpaDao.findOneByLoginAndIp(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);
		CBlockedLogin recordIpTwo = blockedLoginJpaDao.findOneByLoginAndIp(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);

		//sleep for 1 second to be able to validate blocked login (there is a comparison to dateTime.now() inside)
		Thread.sleep(1000);
		boolean isBlocked = blockingLoginService.isLoginBlocked(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);

		Assert.assertNotNull(recordIpOne);
		Assert.assertEquals(5, recordIpOne.getInvalidLoginCount());

		Assert.assertNotNull(recordIpTwo);
		Assert.assertEquals(4, recordIpTwo.getInvalidLoginCount());

		Assert.assertTrue(isBlocked);
	}

	/**
	 * Test blocked login with blocked set ip null.
	 *
	 * @param blockingLoginService the authentication service
	 * @param blockedLoginJpaDao the blocked login jpa dao
	 * @throws CSystemException the c system exception
	 * @throws CSecurityException the c security exception
	 * @throws InterruptedException the interrupted exception
	 */
	public void testBlockedLoginWithBlockedSetIpNull (ILoginBlockingService blockingLoginService, IBlockedLoginDao blockedLoginJpaDao) throws CSystemException, CSecurityException, InterruptedException
	{
		blockingLoginService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);
		blockingLoginService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		blockingLoginService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);
		blockingLoginService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		blockingLoginService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);
		blockingLoginService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		blockingLoginService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);
		blockingLoginService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);
		//flush 
		blockedLoginJpaDao.flush();

		blockingLoginService.increaseInvalidLoginCounter(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);
		//flush 
		blockedLoginJpaDao.flush();

		CBlockedLogin recordIpNull = blockedLoginJpaDao.findOneByLoginAndIp(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);
		CBlockedLogin recordIpTwo = blockedLoginJpaDao.findOneByLoginAndIp(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);

		//sleep for 1 second to be able to validate blocked login (there is a comparison to dateTime.now() inside)
		Thread.sleep(1000);
		boolean isBlockedIpNull = blockingLoginService.isLoginBlocked(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, null);
		boolean isBlockedIpTwo = blockingLoginService.isLoginBlocked(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_TWO);

		Assert.assertNotNull(recordIpNull);
		Assert.assertEquals(5, recordIpNull.getInvalidLoginCount());
		Assert.assertTrue(isBlockedIpNull);

		Assert.assertNotNull(recordIpTwo);
		Assert.assertEquals(4, recordIpTwo.getInvalidLoginCount());
		Assert.assertFalse(isBlockedIpTwo);
	}

}