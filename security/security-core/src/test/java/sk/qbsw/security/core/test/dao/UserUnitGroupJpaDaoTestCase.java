package sk.qbsw.security.core.test.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.core.dao.GroupDao;
import sk.qbsw.security.core.dao.UnitDao;
import sk.qbsw.security.core.dao.UserDao;
import sk.qbsw.security.core.dao.UserUnitGroupDao;
import sk.qbsw.security.core.model.domain.CGroup;
import sk.qbsw.security.core.model.domain.CUnit;
import sk.qbsw.security.core.model.domain.CUser;
import sk.qbsw.security.core.model.domain.CXUserUnitGroup;
import sk.qbsw.security.core.test.util.DataGenerator;

/**
 * Checks cross table userUnitGroup dao.
 *
 * @version 1.13.0
 * @since 1.13.0
 * @autor Tomas Lauro
 */
public class UserUnitGroupJpaDaoTestCase extends BaseDatabaseTestCase
{
	/** The xUserUnitGroupDao dao. */
	@Autowired
	private UserUnitGroupDao xUserUnitGroupDao;

	/** The user dao. */
	@Autowired
	private UserDao userDao;

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
		assertNotNull("Could not find xUserUnitGroupDao dao", xUserUnitGroupDao);
		assertNotNull("Could not find user dao", userDao);
		assertNotNull("Could not find unit dao", unitDao);
		assertNotNull("Could not find group dao", groupDao);

	}

	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUserAndUnitAndGroupPositiveAll () throws CSecurityException
	{
		initTest();

		CUser user = userDao.findOneByLogin(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		CGroup group = groupDao.findOneByCode(DataGenerator.FIRST_GROUP_IN_UNIT_CODE);
		CUnit unit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);

		List<CXUserUnitGroup> xUserUnitGroups = xUserUnitGroupDao.findByUserAndUnitAndGroup(user, unit, group);

		//asserts
		assertNotNull("No xUserUnitGroups found", xUserUnitGroups);
		Assert.assertEquals("Returns invalid xUserUnitGroups", 1, xUserUnitGroups.size());
	}

	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUserAndUnitAndGroupPositiveUserGroup () throws CSecurityException
	{
		initTest();

		CUser user = userDao.findOneByLogin(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		CGroup group = groupDao.findOneByCode(DataGenerator.FIRST_GROUP_IN_UNIT_CODE);

		List<CXUserUnitGroup> xUserUnitGroups = xUserUnitGroupDao.findByUserAndUnitAndGroup(user, null, group);

		//asserts
		assertNotNull("No xUserUnitGroups found", xUserUnitGroups);
		Assert.assertEquals("Returns invalid xUserUnitGroups", 2, xUserUnitGroups.size());
	}

	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUserAndUnitAndGroupPositiveUserUnit () throws CSecurityException
	{
		initTest();

		CUser user = userDao.findOneByLogin(DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE);
		CUnit unit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);

		List<CXUserUnitGroup> xUserUnitGroups = xUserUnitGroupDao.findByUserAndUnitAndGroup(user, unit, null);

		//asserts
		assertNotNull("No xUserUnitGroups found", xUserUnitGroups);
		Assert.assertEquals("Returns invalid xUserUnitGroups", 1, xUserUnitGroups.size());
	}

	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUserAndUnitAndGroupPositiveGroupUnit () throws CSecurityException
	{
		initTest();

		CGroup group = groupDao.findOneByCode(DataGenerator.SECOND_GROUP_IN_UNIT_CODE);
		CUnit unit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);

		List<CXUserUnitGroup> xUserUnitGroups = xUserUnitGroupDao.findByUserAndUnitAndGroup(null, unit, group);

		//asserts
		assertNotNull("No xUserUnitGroups found", xUserUnitGroups);
		Assert.assertEquals("Returns invalid xUserUnitGroups", 3, xUserUnitGroups.size());
	}

	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUserAndUnitAndGroupPositiveUser () throws CSecurityException
	{
		initTest();

		CUser user = userDao.findOneByLogin(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE);

		List<CXUserUnitGroup> xUserUnitGroups = xUserUnitGroupDao.findByUserAndUnitAndGroup(user, null, null);

		//asserts
		assertNotNull("No xUserUnitGroups found", xUserUnitGroups);
		Assert.assertEquals("Returns invalid xUserUnitGroups", 6, xUserUnitGroups.size());
	}

	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUserAndUnitAndGroupPositiveGroup () throws CSecurityException
	{
		initTest();

		CGroup group = groupDao.findOneByCode(DataGenerator.FIRST_GROUP_IN_UNIT_CODE);

		List<CXUserUnitGroup> xUserUnitGroups = xUserUnitGroupDao.findByUserAndUnitAndGroup(null, null, group);

		//asserts
		assertNotNull("No xUserUnitGroups found", xUserUnitGroups);
		Assert.assertEquals("Returns invalid xUserUnitGroups", 2, xUserUnitGroups.size());
	}

	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUserAndUnitAndGroupPositiveUnit () throws CSecurityException
	{
		initTest();

		CUnit unit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);

		List<CXUserUnitGroup> xUserUnitGroups = xUserUnitGroupDao.findByUserAndUnitAndGroup(null, unit, null);

		//asserts
		assertNotNull("No xUserUnitGroups found", xUserUnitGroups);
		Assert.assertEquals("Returns invalid xUserUnitGroups", 4, xUserUnitGroups.size());
	}

	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUserAndUnitAndGroupPositiveNone () throws CSecurityException
	{
		initTest();

		List<CXUserUnitGroup> xUserUnitGroups = xUserUnitGroupDao.findByUserAndUnitAndGroup(null, null, null);

		//asserts
		assertNotNull("No xUserUnitGroups found", xUserUnitGroups);
		Assert.assertEquals("Returns invalid xUserUnitGroups", 17, xUserUnitGroups.size());
	}
}
