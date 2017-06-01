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
import sk.qbsw.security.core.dao.GroupDao;
import sk.qbsw.security.core.dao.UnitDao;
import sk.qbsw.security.core.dao.UserDao;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Unit;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.core.test.util.DataGenerator;

/**
 * Checks group jpa dao.
 *
 * @version 1.13.0
 * @since 1.13.0
 * @autor Tomas Lauro
 */
public class GroupJpaDaoTestCase extends BaseDatabaseTestCase
{
	/** The group dao. */
	@Autowired
	private GroupDao groupDao;

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
		assertNotNull("Could not find group dao", groupDao);
		assertNotNull("Could not find unit dao", unitDao);
		assertNotNull("Could not find user dao", userDao);
	}

	/**
	 * Test find by flag system positive flag true.
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByFlagSystemPositiveFlagTrue ()
	{
		initTest();

		List<Group> groups = groupDao.findByFlagSystem(true);

		//asserts
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

		List<Group> groups = groupDao.findByFlagSystem(false);

		//asserts
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

		//asserts
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

		//asserts
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

		//get unit
		Unit unit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);

		Group group = groupDao.findOneByCodeAndUnit(DataGenerator.FIRST_GROUP_IN_UNIT_CODE, unit);

		//asserts
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

		//asserts
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

		//get unit
		Unit unit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);

		List<Group> groups = groupDao.findByUnit(unit);

		//asserts
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

		//asserts
		assertNotNull("No groups found", groups);
		Assert.assertEquals("Returns invalid groups", 5, groups.size());
	}

	/**
	 * Test find by unit and user positive.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUnitAndUserPositive () throws CSecurityException
	{
		initTest();

		//get unit
		Unit unit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);
		User user = userDao.findOneByLogin(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE);

		List<Group> groups = groupDao.findByUnitAndUser(unit, user);

		//asserts
		assertNotNull("No groups found", groups);
		Assert.assertEquals("Returns invalid groups", 2, groups.size());
	}

	/**
	 * Test find by unit and user positive only unit.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUnitAndUserPositiveOnlyUnit () throws CSecurityException
	{
		initTest();

		//get unit
		Unit unit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);

		List<Group> groups = groupDao.findByUnitAndUser(unit, null);

		//asserts
		assertNotNull("No groups found", groups);
		Assert.assertEquals("Returns invalid groups", 2, groups.size());
	}

	/**
	 * Test find by unit and user positive only user.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUnitAndUserPositiveOnlyUser () throws CSecurityException
	{
		initTest();

		//get unit
		User user = userDao.findOneByLogin(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE);

		List<Group> groups = groupDao.findByUnitAndUser(null, user);

		//asserts
		assertNotNull("No groups found", groups);
		Assert.assertEquals("Returns invalid groups", 3, groups.size());
	}

	/**
	 * Test find by unit and user positive without user and unit.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUnitAndUserPositiveWithoutUserAndUnit () throws CSecurityException
	{
		initTest();

		List<Group> groups = groupDao.findByUnitAndUser(null, null);

		//asserts
		assertNotNull("No groups found", groups);
		Assert.assertEquals("Returns invalid groups", 5, groups.size());
	}
}
