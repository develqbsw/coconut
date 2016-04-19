package sk.qbsw.core.security.test.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.security.dao.IGroupDao;
import sk.qbsw.core.security.dao.IUnitDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.test.util.CDataGenerator;

/**
 * Checks group jpa dao.
 *
 * @version 1.13.0
 * @since 1.13.0
 * @autor Tomas Lauro
 */
public class CGroupJpaDaoTestCase extends ADatabaseTestCase
{
	/** The group dao. */
	@Autowired
	private IGroupDao groupDao;

	/** The unit dao. */
	@Autowired
	private IUnitDao unitDao;

	/** The user dao. */
	@Autowired
	private IUserDao userDao;

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

		List<CGroup> groups = groupDao.findByFlagSystem(true);

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

		List<CGroup> groups = groupDao.findByFlagSystem(false);

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

		List<CGroup> groups = groupDao.findAll();

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

		CGroup group = groupDao.findOneByCode(CDataGenerator.FIRST_GROUP_IN_UNIT_CODE);

		//asserts
		assertNotNull("No groups found", group);
		Assert.assertEquals("Returns invalid groups", CDataGenerator.FIRST_GROUP_IN_UNIT_CODE, group.getCode());
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
		CUnit unit = unitDao.findOneByName(CDataGenerator.DEFAULT_UNIT_CODE);

		CGroup group = groupDao.findOneByCodeAndUnit(CDataGenerator.FIRST_GROUP_IN_UNIT_CODE, unit);

		//asserts
		assertNotNull("No groups found", group);
		Assert.assertEquals("Returns invalid groups", CDataGenerator.FIRST_GROUP_IN_UNIT_CODE, group.getCode());
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

		CGroup group = groupDao.findOneByCodeAndUnit(CDataGenerator.FIRST_GROUP_NOT_IN_UNIT_CODE, null);

		//asserts
		assertNotNull("No groups found", group);
		Assert.assertEquals("Returns invalid groups", CDataGenerator.FIRST_GROUP_NOT_IN_UNIT_CODE, group.getCode());
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
		CUnit unit = unitDao.findOneByName(CDataGenerator.DEFAULT_UNIT_CODE);

		List<CGroup> groups = groupDao.findByUnit(unit);

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

		List<CGroup> groups = groupDao.findByUnit(null);

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
		CUnit unit = unitDao.findOneByName(CDataGenerator.DEFAULT_UNIT_CODE);
		CUser user = userDao.findOneByLogin(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE);

		List<CGroup> groups = groupDao.findByUnitAndUser(unit, user);

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
		CUnit unit = unitDao.findOneByName(CDataGenerator.DEFAULT_UNIT_CODE);

		List<CGroup> groups = groupDao.findByUnitAndUser(unit, null);

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
		CUser user = userDao.findOneByLogin(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE);

		List<CGroup> groups = groupDao.findByUnitAndUser(null, user);

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

		List<CGroup> groups = groupDao.findByUnitAndUser(null, null);

		//asserts
		assertNotNull("No groups found", groups);
		Assert.assertEquals("Returns invalid groups", 5, groups.size());
	}
}
