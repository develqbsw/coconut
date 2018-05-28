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
import sk.qbsw.security.oauth.service.MasterTokenService;
import sk.qbsw.security.oauth.test.util.DataGenerator;

/**
 * The master token service test case.
 *
 * @autor Tomas Lauro
 * @version 1.13.1
 * @since 1.13.1
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@Rollback (true)
public class MasterTokenTestCase
{
	private static final boolean IP_IGNORED = false;

	/** The user dao. */
	@Autowired
	private AccountDao userDao;

	/** The master token service. */
	@Autowired
	private MasterTokenService masterTokenService;

	/** The data generator. */
	@Autowired
	private DataGenerator dataGenerator;

	/**
	 * Generate master token test.
	 *
	 * @throws CBusinessException the c business exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void generateMasterTokenTest () throws CBusinessException
	{
		initTest();

		Account user = userDao.findOneByLogin(DataGenerator.SECOND_USER);
		GeneratedTokenData tokenData = masterTokenService.generateMasterToken(user.getId(), DataGenerator.DEVICE_ID, DataGenerator.TEST_IP_ONE);

		Assert.assertNotNull(tokenData);
	}

	/**
	 * Generate master token invalid parameters test.
	 *
	 * @throws CBusinessException the c business exception
	 */
	@Test (expected = CBusinessException.class)
	@Transactional (transactionManager = "transactionManager")
	public void generateMasterTokenInvalidParametersTest () throws CBusinessException
	{
		initTest();

		masterTokenService.generateMasterToken(null, null, null);
	}

	/**
	 * Generate master token user not found test.
	 *
	 * @throws CBusinessException the c business exception
	 */
	@Test (expected = CBusinessException.class)
	@Transactional (transactionManager = "transactionManager")
	public void generateMasterTokenUserNotFoundTest () throws CBusinessException
	{
		initTest();

		masterTokenService.generateMasterToken(5689894L, DataGenerator.DEVICE_ID, null);
	}

	/**
	 * Generate master token token exists test.
	 *
	 * @throws CBusinessException the c business exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void generateMasterTokenTokenExistsTest () throws CBusinessException
	{
		initTest();

		Account user = userDao.findOneByLogin(DataGenerator.FIRST_USER);
		GeneratedTokenData tokenData = masterTokenService.generateMasterToken(user.getId(), DataGenerator.DEVICE_ID, DataGenerator.TEST_IP_ONE);

		Assert.assertNotNull(tokenData);
		Assert.assertNotNull(tokenData.getGeneratedToken());
		Assert.assertNotNull(tokenData.getInvalidatedToken());
		Assert.assertTrue(tokenData.getGeneratedToken().equals(DataGenerator.MASTER_TOKEN) == false);
	}

	/**
	 * Revoke master token test.
	 *
	 * @throws CBusinessException the c business exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void revokeMasterTokenTest () throws CBusinessException
	{
		initTest();

		Account user = userDao.findOneByLogin(DataGenerator.FIRST_USER);
		masterTokenService.revokeMasterToken(user.getId(), DataGenerator.MASTER_TOKEN);
	}

	/**
	 * Revoke master token token not found test.
	 *
	 * @throws CBusinessException the c business exception
	 */
	@Test (expected = CBusinessException.class)
	@Transactional (transactionManager = "transactionManager")
	public void revokeMasterTokenTokenNotFoundTest () throws CBusinessException
	{
		initTest();

		Account user = userDao.findOneByLogin(DataGenerator.FIRST_USER);
		masterTokenService.revokeMasterToken(user.getId(), "123456789");
	}

	/**
	 * Gets the user by master token test.
	 *
	 * @return the user by master token test
	 * @throws CBusinessException the c business exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void getUserByMasterTokenTest () throws CBusinessException
	{
		initTest();

		Account user = masterTokenService.getUserByMasterToken(DataGenerator.MASTER_TOKEN, DataGenerator.DEVICE_ID, DataGenerator.TEST_IP_ONE, IP_IGNORED);

		Assert.assertNotNull(user);
	}

	/**
	 * Gets the user by master token invalid ip test.
	 *
	 * @return the user by master token invalid ip test
	 * @throws CBusinessException the c business exception
	 */
	@Test (expected = CBusinessException.class)
	@Transactional (transactionManager = "transactionManager")
	public void getUserByMasterTokenInvalidIpTest () throws CBusinessException
	{
		initTest();

		masterTokenService.getUserByMasterToken(DataGenerator.MASTER_TOKEN, DataGenerator.DEVICE_ID, "123", IP_IGNORED);
	}

	/**
	 * Gets the user by master token invalid ip null test.
	 *
	 * @return the user by master token invalid ip null test
	 * @throws CBusinessException the c business exception
	 */
	@Test (expected = CBusinessException.class)
	@Transactional (transactionManager = "transactionManager")
	public void getUserByMasterTokenInvalidIpNullTest () throws CBusinessException
	{
		initTest();

		masterTokenService.getUserByMasterToken(DataGenerator.MASTER_TOKEN, DataGenerator.DEVICE_ID, null, IP_IGNORED);
	}

	/**
	 * Inits the test.
	 */
	private void initTest ()
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
	}
}
