package sk.qbsw.security.oauth.db.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.oauth.db.model.MasterTokenData;
import sk.qbsw.security.oauth.db.test.util.DataGenerator;
import sk.qbsw.security.oauth.model.GeneratedTokenData;
import sk.qbsw.security.oauth.service.MasterTokenService;

/**
 * The master token service test case.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.13.1
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@Rollback
public class MasterTokenTestCase
{
	private static final boolean IP_IGNORED = false;

	@Autowired
	private AccountDao userDao;

	@Autowired
	private MasterTokenService<AccountData, MasterTokenData> masterTokenService;

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
		Assert.assertTrue(!tokenData.getGeneratedToken().equals(DataGenerator.MASTER_TOKEN));
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
	 * @throws CBusinessException the c business exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void getUserByMasterTokenTest () throws CBusinessException
	{
		initTest();

		AccountData account = masterTokenService.getAccountByMasterToken(DataGenerator.MASTER_TOKEN, DataGenerator.DEVICE_ID, DataGenerator.TEST_IP_ONE, IP_IGNORED);

		Assert.assertNotNull(account);
	}

	/**
	 * Gets the user by master token invalid ip test.
	 *
	 * @throws CBusinessException the c business exception
	 */
	@Test (expected = CBusinessException.class)
	@Transactional (transactionManager = "transactionManager")
	public void getUserByMasterTokenInvalidIpTest () throws CBusinessException
	{
		initTest();

		masterTokenService.getAccountByMasterToken(DataGenerator.MASTER_TOKEN, DataGenerator.DEVICE_ID, "123", IP_IGNORED);
	}

	/**
	 * Gets the user by master token invalid ip null test.
	 *
	 * @throws CBusinessException the c business exception
	 */
	@Test (expected = CBusinessException.class)
	@Transactional (transactionManager = "transactionManager")
	public void getUserByMasterTokenInvalidIpNullTest () throws CBusinessException
	{
		initTest();

		masterTokenService.getAccountByMasterToken(DataGenerator.MASTER_TOKEN, DataGenerator.DEVICE_ID, null, IP_IGNORED);
	}

	/**
	 * Inits the test.
	 */
	private void initTest ()
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
	}
}
