package sk.qbsw.organization.complex.core.test.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.organization.complex.core.dao.UnitDao;
import sk.qbsw.organization.complex.core.dao.UserDao;
import sk.qbsw.organization.complex.core.model.domain.Unit;
import sk.qbsw.organization.complex.core.model.domain.User;
import sk.qbsw.organization.complex.core.test.util.DataGenerator;

import javax.persistence.NoResultException;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class UnitJpaDaoTestCase extends BaseDatabaseTestCase
{
	@Autowired
	private UnitDao unitDao;

	@Autowired
	private UserDao userDao;

	/**
	 * Test initialization.
	 */
	@Before
	public void testInitialization ()
	{
		super.testInitialization();
		assertNotNull("Could not find organization entity dao", unitDao);
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

		Unit unit = unitDao.findOneByCode(DataGenerator.FIRST_UNIT_CODE);

		// asserts
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
	public void testFindOneByCodeNegativeNoResult () throws CSecurityException
	{
		initTest();

		unitDao.findOneByCode("no result");
	}

	/**
	 * Test find one by name negative no name.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByCodeNegativeNoName () throws CSecurityException
	{
		initTest();

		unitDao.findOneByCode(null);
	}

	/**
	 * Test find by account id positive.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUserIdPositive () throws CSecurityException
	{
		initTest();

		List<User> users = userDao.findAll();

		List<Unit> organizationEntities = unitDao.findByUserId(users.get(0).getId());

		// asserts
		assertNotNull("No organizationEntities found", organizationEntities);
		Assert.assertEquals("Returns invalid organizationEntities", 2, organizationEntities.size());
	}

	/**
	 * Test find by account id positive not found.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUserIdPositiveNotFound () throws CSecurityException
	{
		initTest();

		List<Unit> organizationEntities = unitDao.findByUserId(89238947289347L);

		// asserts
		assertNotNull("No organizationEntities found", organizationEntities);
		Assert.assertEquals("Returns invalid organizationEntities", 0, organizationEntities.size());
	}

	/**
	 * Test find by account id positive no account id.
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
