package sk.qbsw.security.core.test.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.UnitDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.Unit;
import sk.qbsw.security.core.test.util.DataGenerator;

import javax.persistence.NoResultException;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Checks unit dao.
 *
 * @version 1.19.0
 * @since 1.13.0
 * @author Tomas Lauro
 */
public class UnitJpaDaoTestCase extends BaseDatabaseTestCase
{
	@Autowired
	private UnitDao unitDao;

	@Autowired
	private AccountDao accountDao;

	/**
	 * Test initialization.
	 */
	@Before
	public void testInitialization ()
	{
		super.testInitialization();
		assertNotNull("Could not find unit dao", unitDao);
		assertNotNull("Could not find account dao", accountDao);
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

		Unit unit = unitDao.findOneByName(DataGenerator.FIRST_UNIT_CODE);

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
	 * Test find by account id positive.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUserIdPositive () throws CSecurityException
	{
		initTest();

		Account account = accountDao.findOneByLogin(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE);

		List<Unit> units = unitDao.findByAccountId(account.getId());

		// asserts
		assertNotNull("No units found", units);
		Assert.assertEquals("Returns invalid units", 3, units.size());
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

		List<Unit> units = unitDao.findByAccountId(89238947289347l);

		// asserts
		assertNotNull("No units found", units);
		Assert.assertEquals("Returns invalid units", 0, units.size());
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

		unitDao.findByAccountId(null);
	}
}
