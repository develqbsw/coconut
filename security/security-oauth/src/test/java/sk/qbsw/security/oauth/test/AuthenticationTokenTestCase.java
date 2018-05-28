package sk.qbsw.security.oauth.test;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.oauth.model.GeneratedTokenData;
import sk.qbsw.security.oauth.service.AuthenticationTokenService;
import sk.qbsw.security.oauth.test.util.DataGenerator;

/**
 * The authentication token service test case.
 *
 * @autor Tomas Lauro
 * @version 1.13.1
 * @since 1.13.1
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@Rollback (true)
public class AuthenticationTokenTestCase
{
	private static final boolean IP_IGNORED = false;

	/** The user dao. */
	@Autowired
	private AccountDao userDao;

	/** The authentication token service. */
	@Autowired
	private AuthenticationTokenService authenticationTokenService;

	/** The data generator. */
	@Autowired
	private DataGenerator dataGenerator;

	/**
	 * Generate authentication token test.
	 *
	 * @throws CBusinessException the c business exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void generateAuthenticationTokenTest () throws CBusinessException
	{
		initTest();

		Account user = userDao.findOneByLogin(DataGenerator.FIRST_USER);
		GeneratedTokenData tokenData = authenticationTokenService.generateAuthenticationToken(user.getId(), DataGenerator.MASTER_TOKEN, DataGenerator.DEVICE_ID, DataGenerator.TEST_IP_ONE, IP_IGNORED);

		Assert.assertNotNull(tokenData);
		Assert.assertNotNull(tokenData.getGeneratedToken());
	}

	/**
	 * Generate authentication token invalid parameters test.
	 *
	 * @throws CBusinessException the c business exception
	 */
	@Test (expected = CBusinessException.class)
	@Transactional (transactionManager = "transactionManager")
	public void generateAuthenticationTokenInvalidParametersTest () throws CBusinessException
	{
		initTest();

		authenticationTokenService.generateAuthenticationToken(null, null, null, null, IP_IGNORED);
	}

	/**
	 * Generate authentication token user not found test.
	 *
	 * @throws CBusinessException the c business exception
	 */
	@Test (expected = CBusinessException.class)
	@Transactional (transactionManager = "transactionManager")
	public void generateAuthenticationTokenUserNotFoundTest () throws CBusinessException
	{
		initTest();

		authenticationTokenService.generateAuthenticationToken(123461321467L, DataGenerator.MASTER_TOKEN, DataGenerator.DEVICE_ID, DataGenerator.TEST_IP_ONE, IP_IGNORED);
	}

	/**
	 * Generate authentication token token exists test.
	 *
	 * @throws CBusinessException the c business exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void generateAuthenticationTokenTokenExistsTest () throws CBusinessException
	{
		initTest();

		Account user = userDao.findOneByLogin(DataGenerator.FIRST_USER);
		GeneratedTokenData tokenData = authenticationTokenService.generateAuthenticationToken(user.getId(), DataGenerator.MASTER_TOKEN, DataGenerator.DEVICE_ID, DataGenerator.TEST_IP_ONE, IP_IGNORED);

		Assert.assertNotNull(tokenData);
		Assert.assertNotNull(tokenData.getGeneratedToken());
		Assert.assertNotNull(tokenData.getInvalidatedToken());
		Assert.assertTrue(tokenData.getGeneratedToken().equals(DataGenerator.AUTHENTICATION_TOKEN) == false);
	}

	/**
	 * Generate authentication token master token not found test.
	 *
	 * @throws CBusinessException the c business exception
	 */
	@Test (expected = CBusinessException.class)
	@Transactional (transactionManager = "transactionManager")
	public void generateAuthenticationTokenMasterTokenNotFoundTest () throws CBusinessException
	{
		initTest();

		Account user = userDao.findOneByLogin(DataGenerator.FIRST_USER);
		authenticationTokenService.generateAuthenticationToken(user.getId(), "123456", DataGenerator.DEVICE_ID, DataGenerator.TEST_IP_ONE, IP_IGNORED);
	}

	/**
	 * Generate authentication token master token invalid test.
	 *
	 * @throws CBusinessException the c business exception
	 */
	@Test (expected = CBusinessException.class)
	@Transactional (transactionManager = "transactionManager")
	public void generateAuthenticationTokenMasterTokenInvalidTest () throws CBusinessException
	{
		initTest();

		Account user = userDao.findOneByLogin(DataGenerator.FIRST_USER);
		authenticationTokenService.generateAuthenticationToken(user.getId(), DataGenerator.MASTER_TOKEN, DataGenerator.DEVICE_ID, "123456", IP_IGNORED);
	}

	/**
	 * Revoke authentication token test.
	 *
	 * @throws CBusinessException the c business exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void revokeAuthenticationTokenTest () throws CBusinessException
	{
		initTest();

		Account user = userDao.findOneByLogin(DataGenerator.FIRST_USER);
		authenticationTokenService.revokeAuthenticationToken(user.getId(), DataGenerator.AUTHENTICATION_TOKEN);
	}

	/**
	 * Revoke authentication token token not found test.
	 *
	 * @throws CBusinessException the c business exception
	 */
	@Test (expected = CBusinessException.class)
	@Transactional (transactionManager = "transactionManager")
	public void revokeAuthenticationTokenTokenNotFoundTest () throws CBusinessException
	{
		initTest();

		Account user = userDao.findOneByLogin(DataGenerator.FIRST_USER);
		authenticationTokenService.revokeAuthenticationToken(user.getId(), "123456");
	}

	/**
	 * Gets the user by authentication token test.
	 *
	 * @return the user by authentication token test
	 * @throws CBusinessException the c business exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void getUserByAuthenticationTokenTest () throws CBusinessException
	{
		initTest();

		Account user = authenticationTokenService.getUserByAuthenticationToken(DataGenerator.AUTHENTICATION_TOKEN, DataGenerator.DEVICE_ID, DataGenerator.TEST_IP_ONE, IP_IGNORED);

		Assert.assertNotNull(user);
	}

	/**
	 * Gets the user by authentication token invalid ip test.
	 *
	 * @return the user by authentication token invalid ip test
	 * @throws CBusinessException the c business exception
	 */
	@Test (expected = CBusinessException.class)
	@Transactional (transactionManager = "transactionManager")
	public void getUserByAuthenticationTokenInvalidIpTest () throws CBusinessException
	{
		initTest();

		authenticationTokenService.getUserByAuthenticationToken(DataGenerator.AUTHENTICATION_TOKEN, DataGenerator.DEVICE_ID, "123", IP_IGNORED);
	}

	/**
	 * Gets the user by authentication token invalid ip null test.
	 *
	 * @return the user by authentication token invalid ip null test
	 * @throws CBusinessException the c business exception
	 */
	@Test (expected = CBusinessException.class)
	@Transactional (transactionManager = "transactionManager")
	public void getUserByAuthenticationTokenInvalidIpNullTest () throws CBusinessException
	{
		initTest();

		authenticationTokenService.getUserByAuthenticationToken(DataGenerator.AUTHENTICATION_TOKEN, DataGenerator.DEVICE_ID, null, IP_IGNORED);
	}

	/**
	 * Inits the test.
	 */
	private void initTest ()
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
	}
}
