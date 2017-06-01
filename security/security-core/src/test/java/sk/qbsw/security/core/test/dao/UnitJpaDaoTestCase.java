package sk.qbsw.security.core.test.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.core.dao.UnitDao;
import sk.qbsw.security.core.dao.UserDao;
import sk.qbsw.security.core.model.domain.CUnit;
import sk.qbsw.security.core.model.domain.CUser;
import sk.qbsw.security.core.test.util.DataGenerator;

/**
 * Checks unit dao.
 *
 * @version 1.13.0
 * @since 1.13.0
 * @autor Tomas Lauro
 */
public class UnitJpaDaoTestCase extends BaseDatabaseTestCase
{
	/** The unit dao. */
	@Autowired
	private UnitDao unitDao;

	/** The user dao. */
	@Autowired
	private UserDao userDao;

	/**
	 * Test initialization.
	 */
	@Before
	public void testInitialization ()
	{
		super.testInitialization();
		assertNotNull("Could not find unit dao", unitDao);
		assertNotNull("Could not find user dao", userDao);
	}

	/**
	 * Test find one by name positive.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByNamePositive () throws CSecurityException
	{
		initTest();

		CUnit unit = unitDao.findOneByName(DataGenerator.FIRST_UNIT_CODE);

		//asserts
		assertNotNull("No unit found", unit);
		Assert.assertEquals("Returns invalid unit", DataGenerator.FIRST_UNIT_CODE, unit.getName());
	}

	/**
	 * Test find one by name negative no result.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = NoResultException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByNameNegativeNoResult () throws CSecurityException
	{
		initTest();

		unitDao.findOneByName("no result");
	}

	/**
	 * Test find one by name negative no name.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByNameNegativeNoName () throws CSecurityException
	{
		initTest();

		unitDao.findOneByName(null);
	}

	/**
	 * Test find by user id positive.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUserIdPositive () throws CSecurityException
	{
		initTest();

		CUser user = userDao.findOneByLogin(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE);

		List<CUnit> units = unitDao.findByUserId(user.getId());

		//asserts
		assertNotNull("No units found", units);
		Assert.assertEquals("Returns invalid units", 3, units.size());
	}

	/**
	 * Test find by user id positive not found.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUserIdPositiveNotFound () throws CSecurityException
	{
		initTest();

		List<CUnit> units = unitDao.findByUserId(89238947289347l);

		//asserts
		assertNotNull("No units found", units);
		Assert.assertEquals("Returns invalid units", 0, units.size());
	}

	/**
	 * Test find by user id positive no user id.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUserIdPositiveNoUserId () throws CSecurityException
	{
		initTest();

		unitDao.findByUserId(null);
	}
}
