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
import sk.qbsw.security.dao.IUserDao;
import sk.qbsw.security.model.domain.CUser;
import sk.qbsw.security.oauth.service.IMasterTokenService;
import sk.qbsw.security.oauth.test.util.CDataGenerator;

/**
 * The master token service test case.
 *
 * @autor Tomas Lauro
 *
 * @version 1.13.1
 * @since 1.13.1
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@Rollback (true)
public class CMasterTokenTestCase
{
	/** The user dao. */
	@Autowired
	private IUserDao userDao;

	/** The master token service. */
	@Autowired
	private IMasterTokenService masterTokenService;

	/** The data generator. */
	@Autowired
	private CDataGenerator dataGenerator;

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

		CUser user = userDao.findOneByLogin(CDataGenerator.SECOND_USER);
		String token = masterTokenService.generateMasterToken(user.getId(), CDataGenerator.DEVICE_ID, CDataGenerator.TEST_IP_ONE);

		Assert.assertNotNull(token);
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

		masterTokenService.generateMasterToken(5689894L, CDataGenerator.DEVICE_ID, null);
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

		CUser user = userDao.findOneByLogin(CDataGenerator.FIRST_USER);
		String token = masterTokenService.generateMasterToken(user.getId(), CDataGenerator.DEVICE_ID, CDataGenerator.TEST_IP_ONE);

		Assert.assertNotNull(token);
		Assert.assertTrue(token.equals(CDataGenerator.MASTER_TOKEN) == false);
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

		CUser user = userDao.findOneByLogin(CDataGenerator.FIRST_USER);
		masterTokenService.revokeMasterToken(user.getId(), CDataGenerator.MASTER_TOKEN);
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

		CUser user = userDao.findOneByLogin(CDataGenerator.FIRST_USER);
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

		CUser user = masterTokenService.getUserByMasterToken(CDataGenerator.MASTER_TOKEN, CDataGenerator.DEVICE_ID, CDataGenerator.TEST_IP_ONE);

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

		masterTokenService.getUserByMasterToken(CDataGenerator.MASTER_TOKEN, CDataGenerator.DEVICE_ID, "123");
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

		masterTokenService.getUserByMasterToken(CDataGenerator.MASTER_TOKEN, CDataGenerator.DEVICE_ID, null);
	}

	/**
	 * Inits the test.
	 */
	private void initTest ()
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
	}
}
