package sk.qbsw.core.security.test.dao;

import static org.junit.Assert.assertNotNull;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.security.dao.IBlockedLoginDao;
import sk.qbsw.core.security.model.domain.CBlockedLogin;
import sk.qbsw.core.security.test.util.CDataGenerator;

/**
 * Checks blocked login jpa dao.
 *
 * @autor Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.13.0
 */
public class CBlockedLoginJpaDaoTestCase extends ADatabaseTestCase
{
	/** The blocked login dao. */
	@Autowired
	private IBlockedLoginDao blockedLoginDao;

	/**
	 * Test initialization.
	 */
	@Before
	public void testInitialization ()
	{
		super.testInitialization();
		assertNotNull("Could not find blocked login dao", blockedLoginDao);
	}

	/**
	 * Test find one by login and ip positive.
	 *
	 * @throws NonUniqueResultException the non unique result exception
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testFindOneByLoginAndIpPositive () throws NonUniqueResultException, NoResultException, CSecurityException
	{
		initTest();

		CBlockedLogin blockedLogin = blockedLoginDao.findOneByLoginAndIp(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE);

		//asserts
		assertNotNull("No blocked login found", blockedLogin);
		Assert.assertEquals("Returns invalid blocked login", CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, blockedLogin.getLogin());
	}

	/**
	 * Test find one by login and ip positive without ip.
	 *
	 * @throws NonUniqueResultException the non unique result exception
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testFindOneByLoginAndIpPositiveWithoutIp () throws NonUniqueResultException, NoResultException, CSecurityException
	{
		initTest();

		CBlockedLogin blockedLogin = blockedLoginDao.findOneByLoginAndIp(CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, null);

		//asserts
		assertNotNull("No blocked login found", blockedLogin);
		Assert.assertEquals("Returns invalid blocked login", CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, blockedLogin.getLogin());
	}

	/**
	 * Test find one by login and ip negative no login.
	 *
	 * @throws NonUniqueResultException the non unique result exception
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional
	@Rollback (true)
	public void testFindOneByLoginAndIpNegativeNoLogin () throws NonUniqueResultException, NoResultException, CSecurityException
	{
		initTest();

		blockedLoginDao.findOneByLoginAndIp(null, CDataGenerator.TEST_IP_ONE);
	}

	/**
	 * Test find one by login and ip negative no result.
	 *
	 * @throws NonUniqueResultException the non unique result exception
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = NoResultException.class)
	@Transactional
	@Rollback (true)
	public void testFindOneByLoginAndIpNegativeNoResult () throws NonUniqueResultException, NoResultException, CSecurityException
	{
		initTest();

		blockedLoginDao.findOneByLoginAndIp(CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE_NO_GROUP, CDataGenerator.TEST_IP_ONE);
	}

	/**
	 * Test count currently blocked by login and ip positive.
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testCountCurrentlyBlockedByLoginAndIpPositive ()
	{
		initTest();

		long count = blockedLoginDao.countCurrentlyBlockedByLoginAndIp(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE_NO_GROUP, CDataGenerator.TEST_IP_ONE);

		//asserts
		assertNotNull("No blocked login found", count);
		Assert.assertEquals("Returns count value", 1, count);
	}

	/**
	 * Test count currently blocked by login and ip positive without ip.
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testCountCurrentlyBlockedByLoginAndIpPositiveWithoutIp ()
	{
		initTest();

		long count = blockedLoginDao.countCurrentlyBlockedByLoginAndIp(CDataGenerator.USER_WITHOUT_PASSWORD, null);

		//asserts
		assertNotNull("No blocked login found", count);
		Assert.assertEquals("Returns count value", 1, count);
	}

	/**
	 * Test count currently blocked by login and ip positive count zero.
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testCountCurrentlyBlockedByLoginAndIpPositiveCountZero ()
	{
		initTest();

		long count = blockedLoginDao.countCurrentlyBlockedByLoginAndIp(CDataGenerator.USER_CREATED, CDataGenerator.TEST_IP_ONE);

		//asserts
		assertNotNull("No blocked login found", count);
		Assert.assertEquals("Returns count value", 0, count);
	}

	/**
	 * Test count currently blocked by login and ip negative no login.
	 */
	@Test (expected = CSystemException.class)
	@Transactional
	@Rollback (true)
	public void testCountCurrentlyBlockedByLoginAndIpNegativeNoLogin ()
	{
		initTest();

		blockedLoginDao.countCurrentlyBlockedByLoginAndIp(null, CDataGenerator.TEST_IP_ONE);
	}

	/**
	 * Inits the test.
	 */
	@Override
	protected void initTest ()
	{
		super.initTest();

		CBlockedLogin blockedLogin = dataGenerator.createBlockedLogin(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, CDataGenerator.TEST_IP_ONE, 1, null, null);
		blockedLoginDao.update(blockedLogin);

		CBlockedLogin blockedLoginWithoutIp = dataGenerator.createBlockedLogin(CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, null, 1, null, null);
		blockedLoginDao.update(blockedLoginWithoutIp);

		CBlockedLogin blockedLoginWithDatesOne = dataGenerator.createBlockedLogin(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE_NO_GROUP, CDataGenerator.TEST_IP_ONE, 5, DateTime.now().minusDays(1), DateTime.now().plusDays(1));
		blockedLoginDao.update(blockedLoginWithDatesOne);

		CBlockedLogin blockedLoginWithDatesTwo = dataGenerator.createBlockedLogin(CDataGenerator.USER_WITHOUT_PASSWORD, null, 5, DateTime.now().minusDays(1), DateTime.now().plusDays(1));
		blockedLoginDao.update(blockedLoginWithDatesTwo);

		blockedLoginDao.flush();
	}
}
