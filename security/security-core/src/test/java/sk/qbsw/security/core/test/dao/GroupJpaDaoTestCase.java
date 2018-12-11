package sk.qbsw.security.core.test.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.GroupDao;
import sk.qbsw.security.core.dao.UnitDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.GroupTypes;
import sk.qbsw.security.core.model.domain.Unit;
import sk.qbsw.security.core.test.util.DataGenerator;

import javax.persistence.NoResultException;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Checks group jpa dao.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.13.0
 */
public class GroupJpaDaoTestCase extends BaseDatabaseTestCase
{
	@Autowired
	private GroupDao groupDao;

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
		assertNotNull("Could not find group dao", groupDao);
		assertNotNull("Could not find unit dao", unitDao);
		assertNotNull("Could not find account dao", accountDao);
	}

	/**
	 * Test find by flag system positive flag true.
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByFlagSystemPositiveFlagTrue ()
	{
		initTest();

		List<Group> groups = groupDao.findByType(GroupTypes.TECHNICAL);

		// asserts
		assertNotNull("No groups found", groups);
		Assert.assertEquals("Returns invalid groups", 2, groups.size());
	}

	/**
	 * Test find by flag system positive flag false.
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByFlagSystemPositiveFlagFalse ()
	{
		initTest();

		List<Group> groups = groupDao.findByType(GroupTypes.STANDARD);

		// asserts
		assertNotNull("No groups found", groups);
		Assert.assertEquals("Returns invalid groups", 3, groups.size());
	}

	/**
	 * Test find all positive.
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindAllPositive ()
	{
		initTest();

		List<Group> groups = groupDao.findAll();

		// asserts
		assertNotNull("No groups found", groups);
		Assert.assertEquals("Returns invalid groups", 5, groups.size());
	}

	/**
	 * Test find one by code positive.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByCodePositive () throws CSecurityException
	{
		initTest();

		Group group = groupDao.findOneByCode(DataGenerator.FIRST_GROUP_IN_UNIT_CODE);

		// asserts
		assertNotNull("No groups found", group);
		Assert.assertEquals("Returns invalid groups", DataGenerator.FIRST_GROUP_IN_UNIT_CODE, group.getCode());
	}

	/**
	 * Test find one by code negative no result.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = NoResultException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByCodeNegativeNoResult () throws CSecurityException
	{
		initTest();

		groupDao.findOneByCode("no result");
	}

	/**
	 * Test find one by code negative no code.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByCodeNegativeNoCode () throws CSecurityException
	{
		initTest();

		groupDao.findOneByCode(null);
	}

	/**
	 * Test find one by code and unit positive.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByCodeAndUnitPositive () throws CSecurityException
	{
		initTest();

		// get unit
		Unit unit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);

		Group group = groupDao.findOneByCodeAndUnit(DataGenerator.FIRST_GROUP_IN_UNIT_CODE, unit);

		// asserts
		assertNotNull("No groups found", group);
		Assert.assertEquals("Returns invalid groups", DataGenerator.FIRST_GROUP_IN_UNIT_CODE, group.getCode());
	}

	/**
	 * Test find one by code and unit positive empty unit.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByCodeAndUnitPositiveEmptyUnit () throws CSecurityException
	{
		initTest();

		Group group = groupDao.findOneByCodeAndUnit(DataGenerator.FIRST_GROUP_NOT_IN_UNIT_CODE, null);

		// asserts
		assertNotNull("No groups found", group);
		Assert.assertEquals("Returns invalid groups", DataGenerator.FIRST_GROUP_NOT_IN_UNIT_CODE, group.getCode());
	}

	/**
	 * Test find one by code and unit negative no result.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = NoResultException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByCodeAndUnitNegativeNoResult () throws CSecurityException
	{
		initTest();

		groupDao.findOneByCodeAndUnit("no result", null);
	}

	/**
	 * Test find one by code and unit negative no code.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByCodeAndUnitNegativeNoCode () throws CSecurityException
	{
		initTest();

		groupDao.findOneByCodeAndUnit(null, null);
	}

	/**
	 * Test find by unit positive.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUnitPositive () throws CSecurityException
	{
		initTest();

		// get unit
		Unit unit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);

		List<Group> groups = groupDao.findByUnit(unit);

		// asserts
		assertNotNull("No groups found", groups);
		Assert.assertEquals("Returns invalid groups", 2, groups.size());
	}

	/**
	 * Test find by unit positive no unit.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUnitPositiveNoUnit () throws CSecurityException
	{
		initTest();

		List<Group> groups = groupDao.findByUnit(null);

		// asserts
		assertNotNull("No groups found", groups);
		Assert.assertEquals("Returns invalid groups", 5, groups.size());
	}

	/**
	 * Test find by unit and account positive.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUnitAndAccountPositive () throws CSecurityException
	{
		initTest();

		// get unit
		Unit unit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);
		Account account = accountDao.findOneByLogin(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE);

		List<Group> groups = groupDao.findByUnitAndAccountId(unit, account.getId());

		// asserts
		assertNotNull("No groups found", groups);
		Assert.assertEquals("Returns invalid groups", 2, groups.size());
	}

	/**
	 * Test find by unit and account positive only unit.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUnitAndAccountPositiveOnlyUnit () throws CSecurityException
	{
		initTest();

		// get unit
		Unit unit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);

		List<Group> groups = groupDao.findByUnitAndAccountId(unit, null);

		// asserts
		assertNotNull("No groups found", groups);
		Assert.assertEquals("Returns invalid groups", 2, groups.size());
	}

	/**
	 * Test find by unit and account positive only account.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUnitAndAccountPositiveOnlyAccount () throws CSecurityException
	{
		initTest();

		// get unit
		Account account = accountDao.findOneByLogin(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE);

		List<Group> groups = groupDao.findByUnitAndAccountId(null, account.getId());

		// asserts
		assertNotNull("No groups found", groups);
		Assert.assertEquals("Returns invalid groups", 3, groups.size());
	}

	/**
	 * Test find by unit and account positive without account and unit.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUnitAndAccountPositiveWithoutAccountAndUnit () throws CSecurityException
	{
		initTest();

		List<Group> groups = groupDao.findByUnitAndAccountId(null, null);

		// asserts
		assertNotNull("No groups found", groups);
		Assert.assertEquals("Returns invalid groups", 5, groups.size());
	}
}
