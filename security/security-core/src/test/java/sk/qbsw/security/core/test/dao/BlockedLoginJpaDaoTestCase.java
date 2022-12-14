package sk.qbsw.security.core.test.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.security.core.dao.BlockedLoginDao;
import sk.qbsw.security.core.model.domain.BlockedLogin;
import sk.qbsw.security.core.test.util.DataGenerator;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.time.OffsetDateTime;

import static org.junit.Assert.assertNotNull;

/**
 * Checks blocked login jpa dao.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.13.0
 */
public class BlockedLoginJpaDaoTestCase extends BaseDatabaseTestCase
{
	/** The blocked login dao. */
	@Autowired
	private BlockedLoginDao blockedLoginDao;

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
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByLoginAndIpPositive () throws NonUniqueResultException, NoResultException, CSecurityException
	{
		initTest();

		BlockedLogin blockedLogin = blockedLoginDao.findOneByLoginAndIp(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, DataGenerator.TEST_IP_ONE);

		// asserts
		assertNotNull("No blocked login found", blockedLogin);
		Assert.assertEquals("Returns invalid blocked login", DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, blockedLogin.getLogin());
	}

	/**
	 * Test find one by login and ip positive without ip.
	 *
	 * @throws NonUniqueResultException the non unique result exception
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByLoginAndIpPositiveWithoutIp () throws NonUniqueResultException, NoResultException, CSecurityException
	{
		initTest();

		BlockedLogin blockedLogin = blockedLoginDao.findOneByLoginAndIp(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, null);

		// asserts
		assertNotNull("No blocked login found", blockedLogin);
		Assert.assertEquals("Returns invalid blocked login", DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, blockedLogin.getLogin());
	}

	/**
	 * Test find one by login and ip negative no login.
	 *
	 * @throws NonUniqueResultException the non unique result exception
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByLoginAndIpNegativeNoLogin () throws NonUniqueResultException, NoResultException, CSecurityException
	{
		initTest();

		blockedLoginDao.findOneByLoginAndIp(null, DataGenerator.TEST_IP_ONE);
	}

	/**
	 * Test find one by login and ip negative no result.
	 *
	 * @throws NonUniqueResultException the non unique result exception
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = NoResultException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByLoginAndIpNegativeNoResult () throws NonUniqueResultException, NoResultException, CSecurityException
	{
		initTest();

		blockedLoginDao.findOneByLoginAndIp(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE_NO_GROUP, DataGenerator.TEST_IP_ONE);
	}

	/**
	 * Test count currently blocked by login and ip positive.
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testCountCurrentlyBlockedByLoginAndIpPositive ()
	{
		initTest();

		long count = blockedLoginDao.countCurrentlyBlockedByLoginAndIp(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE_NO_GROUP, DataGenerator.TEST_IP_ONE);

		// asserts
		assertNotNull("No blocked login found", count);
		Assert.assertEquals("Returns count value", 1, count);
	}

	/**
	 * Test count currently blocked by login and ip positive without ip.
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testCountCurrentlyBlockedByLoginAndIpPositiveWithoutIp ()
	{
		initTest();

		long count = blockedLoginDao.countCurrentlyBlockedByLoginAndIp(DataGenerator.ACCOUNT_WITHOUT_PASSWORD, null);

		// asserts
		assertNotNull("No blocked login found", count);
		Assert.assertEquals("Returns count value", 1, count);
	}

	/**
	 * Test count currently blocked by login and ip positive count zero.
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testCountCurrentlyBlockedByLoginAndIpPositiveCountZero ()
	{
		initTest();

		long count = blockedLoginDao.countCurrentlyBlockedByLoginAndIp(DataGenerator.ACCOUNT_CREATED, DataGenerator.TEST_IP_ONE);

		// asserts
		assertNotNull("No blocked login found", count);
		Assert.assertEquals("Returns count value", 0, count);
	}

	/**
	 * Test count currently blocked by login and ip negative no login.
	 */
	@Test (expected = CSystemException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testCountCurrentlyBlockedByLoginAndIpNegativeNoLogin ()
	{
		initTest();

		blockedLoginDao.countCurrentlyBlockedByLoginAndIp(null, DataGenerator.TEST_IP_ONE);
	}

	/**
	 * Inits the test.
	 */
	@Override
	protected void initTest ()
	{
		super.initTest();

		BlockedLogin blockedLogin = dataGenerator.createBlockedLogin(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, DataGenerator.TEST_IP_ONE, 1, null, null);
		blockedLoginDao.update(blockedLogin);

		BlockedLogin blockedLoginWithoutIp = dataGenerator.createBlockedLogin(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, null, 1, null, null);
		blockedLoginDao.update(blockedLoginWithoutIp);

		BlockedLogin blockedLoginWithDatesOne = dataGenerator.createBlockedLogin(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE_NO_GROUP, DataGenerator.TEST_IP_ONE, 5, OffsetDateTime.now().minusDays(1), OffsetDateTime.now().plusDays(1));
		blockedLoginDao.update(blockedLoginWithDatesOne);

		BlockedLogin blockedLoginWithDatesTwo = dataGenerator.createBlockedLogin(DataGenerator.ACCOUNT_WITHOUT_PASSWORD, null, 5, OffsetDateTime.now().minusDays(1), OffsetDateTime.now().plusDays(1));
		blockedLoginDao.update(blockedLoginWithDatesTwo);

		blockedLoginDao.flush();
	}
}
