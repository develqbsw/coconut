package sk.qbsw.security.core.test.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AccountUnitGroupDao;
import sk.qbsw.security.core.dao.GroupDao;
import sk.qbsw.security.core.dao.UnitDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.AccountUnitGroup;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Unit;
import sk.qbsw.security.core.test.util.DataGenerator;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Checks cross table userUnitGroup dao.
 *
 * @author Tomas Lauro
 * @author Michal Slezák
 * @version 2.5.0
 * @since 1.13.0
 */
public class AccountUnitGroupJpaDaoTestCase extends BaseDatabaseTestCase
{
	@Autowired
	private AccountUnitGroupDao accountUnitGroupDao;

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private UnitDao unitDao;

	/**
	 * Test initialization.
	 */
	@Before
	public void testInitialization ()
	{
		super.testInitialization();
		assertNotNull("Could not find accountUnitGroupDao dao", accountUnitGroupDao);
		assertNotNull("Could not find account dao", accountDao);
		assertNotNull("Could not find unit dao", unitDao);
		assertNotNull("Could not find group dao", groupDao);

	}

	/**
	 * Test find by account and unit and group positive all.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByAccountAndUnitAndGroupPositiveAll () throws CSecurityException
	{
		initTest();

		Account account = accountDao.findOneByLogin(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE);
		Group group = groupDao.findOneByCode(DataGenerator.FIRST_GROUP_IN_UNIT_CODE);
		Unit unit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);

		List<AccountUnitGroup> xAccountUnitGroups = accountUnitGroupDao.findByAccountIdAndUnitAndGroup(account.getId(), unit, group);

		// asserts
		assertNotNull("No xAccountUnitGroups found", xAccountUnitGroups);
		Assert.assertEquals("Returns invalid xAccountUnitGroups", 1, xAccountUnitGroups.size());
	}

	/**
	 * Test find by account and unit and group positive account group.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByAccountAndUnitAndGroupPositiveAccountGroup () throws CSecurityException
	{
		initTest();

		Account account = accountDao.findOneByLogin(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE);
		Group group = groupDao.findOneByCode(DataGenerator.FIRST_GROUP_IN_UNIT_CODE);

		List<AccountUnitGroup> xAccountUnitGroups = accountUnitGroupDao.findByAccountIdAndUnitAndGroup(account.getId(), null, group);

		// asserts
		assertNotNull("No xAccountUnitGroups found", xAccountUnitGroups);
		Assert.assertEquals("Returns invalid xAccountUnitGroups", 2, xAccountUnitGroups.size());
	}

	/**
	 * Test find by account and unit and group positive account unit.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByAccountAndUnitAndGroupPositiveAccountUnit () throws CSecurityException
	{
		initTest();

		Account account = accountDao.findOneByLogin(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE);
		Unit unit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);

		List<AccountUnitGroup> xAccountUnitGroups = accountUnitGroupDao.findByAccountIdAndUnitAndGroup(account.getId(), unit, null);

		// asserts
		assertNotNull("No xAccountUnitGroups found", xAccountUnitGroups);
		Assert.assertEquals("Returns invalid xAccountUnitGroups", 1, xAccountUnitGroups.size());
	}

	/**
	 * Test find by account and unit and group positive group unit.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByAccountAndUnitAndGroupPositiveGroupUnit () throws CSecurityException
	{
		initTest();

		Group group = groupDao.findOneByCode(DataGenerator.SECOND_GROUP_IN_UNIT_CODE);
		Unit unit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);

		List<AccountUnitGroup> xAccountUnitGroups = accountUnitGroupDao.findByAccountIdAndUnitAndGroup(null, unit, group);

		// asserts
		assertNotNull("No xAccountUnitGroups found", xAccountUnitGroups);
		Assert.assertEquals("Returns invalid xAccountUnitGroups", 3, xAccountUnitGroups.size());
	}

	/**
	 * Test find by account and unit and group positive account.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByAccountAndUnitAndGroupPositiveAccount () throws CSecurityException
	{
		initTest();

		Account account = accountDao.findOneByLogin(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE);

		List<AccountUnitGroup> xAccountUnitGroups = accountUnitGroupDao.findByAccountIdAndUnitAndGroup(account.getId(), null, null);

		// asserts
		assertNotNull("No xAccountUnitGroups found", xAccountUnitGroups);
		Assert.assertEquals("Returns invalid xAccountUnitGroups", 6, xAccountUnitGroups.size());
	}

	/**
	 * Test find by account and unit and group positive group.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByAccountAndUnitAndGroupPositiveGroup () throws CSecurityException
	{
		initTest();

		Group group = groupDao.findOneByCode(DataGenerator.FIRST_GROUP_IN_UNIT_CODE);

		List<AccountUnitGroup> xAccountUnitGroups = accountUnitGroupDao.findByAccountIdAndUnitAndGroup(null, null, group);

		// asserts
		assertNotNull("No xAccountUnitGroups found", xAccountUnitGroups);
		Assert.assertEquals("Returns invalid xAccountUnitGroups", 2, xAccountUnitGroups.size());
	}

	/**
	 * Test find by account and unit and group positive unit.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByAccountAndUnitAndGroupPositiveUnit () throws CSecurityException
	{
		initTest();

		Unit unit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);

		List<AccountUnitGroup> xAccountUnitGroups = accountUnitGroupDao.findByAccountIdAndUnitAndGroup(null, unit, null);

		// asserts
		assertNotNull("No xAccountUnitGroups found", xAccountUnitGroups);
		Assert.assertEquals("Returns invalid xAccountUnitGroups", 4, xAccountUnitGroups.size());
	}

	/**
	 * Test find by account and unit and group positive none.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByAccountAndUnitAndGroupPositiveNone () throws CSecurityException
	{
		initTest();

		List<AccountUnitGroup> xAccountUnitGroups = accountUnitGroupDao.findByAccountIdAndUnitAndGroup(null, null, null);

		// asserts
		assertNotNull("No xAccountUnitGroups found", xAccountUnitGroups);
		Assert.assertEquals("Returns invalid xAccountUnitGroups", 19, xAccountUnitGroups.size());
	}
}
