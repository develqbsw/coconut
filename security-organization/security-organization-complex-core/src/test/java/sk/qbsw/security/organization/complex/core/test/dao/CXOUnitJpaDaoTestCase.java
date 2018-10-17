package sk.qbsw.security.organization.complex.core.test.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.organization.complex.core.dao.CXOUnitDao;
import sk.qbsw.security.organization.complex.core.dao.CXOUserDao;
import sk.qbsw.security.organization.complex.core.model.domain.CXOUnit;
import sk.qbsw.security.organization.complex.core.model.domain.CXOUser;
import sk.qbsw.security.organization.complex.core.test.util.DataGenerator;

import java.util.List;

import static org.junit.Assert.assertNotNull;

public class CXOUnitJpaDaoTestCase extends CXODatabaseTestCaseBase
{
	@Autowired
	private CXOUnitDao unitDao;

	@Autowired
	private CXOUserDao userDao;

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

		CXOUnit unit = unitDao.findOneByCode(DataGenerator.FIRST_UNIT_CODE);

		// asserts
		assertNotNull("No unit found", unit);
		Assert.assertEquals("Returns invalid unit", DataGenerator.FIRST_UNIT_CODE, unit.getName());
	}

	/**
	 * Test find one by name negative no result.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByCodeNegativeNoResult () throws CSecurityException
	{
		initTest();

		CXOUnit result = unitDao.findOneByCode("no result");

		Assert.assertNull(result);
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

		List<CXOUser> users = userDao.findAll();

		List<CXOUnit> organizationEntities = unitDao.findByUserId(users.get(0).getId());

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

		List<CXOUnit> organizationEntities = unitDao.findByUserId(89238947289347L);

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
