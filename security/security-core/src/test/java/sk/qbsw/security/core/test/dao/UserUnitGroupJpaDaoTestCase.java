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
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Unit;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.core.model.domain.UserUnitGroup;
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

		User user = userDao.findOneByLogin(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		Group group = groupDao.findOneByCode(DataGenerator.FIRST_GROUP_IN_UNIT_CODE);
		Unit unit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);

		List<UserUnitGroup> xUserUnitGroups = xUserUnitGroupDao.findByUserAndUnitAndGroup(user, unit, group);

		//asserts
		assertNotNull("No xUserUnitGroups found", xUserUnitGroups);
		Assert.assertEquals("Returns invalid xUserUnitGroups", 1, xUserUnitGroups.size());
	}

	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUserAndUnitAndGroupPositiveUserGroup () throws CSecurityException
	{
		initTest();

		User user = userDao.findOneByLogin(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		Group group = groupDao.findOneByCode(DataGenerator.FIRST_GROUP_IN_UNIT_CODE);

		List<UserUnitGroup> xUserUnitGroups = xUserUnitGroupDao.findByUserAndUnitAndGroup(user, null, group);

		//asserts
		assertNotNull("No xUserUnitGroups found", xUserUnitGroups);
		Assert.assertEquals("Returns invalid xUserUnitGroups", 2, xUserUnitGroups.size());
	}

	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUserAndUnitAndGroupPositiveUserUnit () throws CSecurityException
	{
		initTest();

		User user = userDao.findOneByLogin(DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE);
		Unit unit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);

		List<UserUnitGroup> xUserUnitGroups = xUserUnitGroupDao.findByUserAndUnitAndGroup(user, unit, null);

		//asserts
		assertNotNull("No xUserUnitGroups found", xUserUnitGroups);
		Assert.assertEquals("Returns invalid xUserUnitGroups", 1, xUserUnitGroups.size());
	}

	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUserAndUnitAndGroupPositiveGroupUnit () throws CSecurityException
	{
		initTest();

		Group group = groupDao.findOneByCode(DataGenerator.SECOND_GROUP_IN_UNIT_CODE);
		Unit unit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);

		List<UserUnitGroup> xUserUnitGroups = xUserUnitGroupDao.findByUserAndUnitAndGroup(null, unit, group);

		//asserts
		assertNotNull("No xUserUnitGroups found", xUserUnitGroups);
		Assert.assertEquals("Returns invalid xUserUnitGroups", 3, xUserUnitGroups.size());
	}

	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUserAndUnitAndGroupPositiveUser () throws CSecurityException
	{
		initTest();

		User user = userDao.findOneByLogin(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE);

		List<UserUnitGroup> xUserUnitGroups = xUserUnitGroupDao.findByUserAndUnitAndGroup(user, null, null);

		//asserts
		assertNotNull("No xUserUnitGroups found", xUserUnitGroups);
		Assert.assertEquals("Returns invalid xUserUnitGroups", 6, xUserUnitGroups.size());
	}

	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUserAndUnitAndGroupPositiveGroup () throws CSecurityException
	{
		initTest();

		Group group = groupDao.findOneByCode(DataGenerator.FIRST_GROUP_IN_UNIT_CODE);

		List<UserUnitGroup> xUserUnitGroups = xUserUnitGroupDao.findByUserAndUnitAndGroup(null, null, group);

		//asserts
		assertNotNull("No xUserUnitGroups found", xUserUnitGroups);
		Assert.assertEquals("Returns invalid xUserUnitGroups", 2, xUserUnitGroups.size());
	}

	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUserAndUnitAndGroupPositiveUnit () throws CSecurityException
	{
		initTest();

		Unit unit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);

		List<UserUnitGroup> xUserUnitGroups = xUserUnitGroupDao.findByUserAndUnitAndGroup(null, unit, null);

		//asserts
		assertNotNull("No xUserUnitGroups found", xUserUnitGroups);
		Assert.assertEquals("Returns invalid xUserUnitGroups", 4, xUserUnitGroups.size());
	}

	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUserAndUnitAndGroupPositiveNone () throws CSecurityException
	{
		initTest();

		List<UserUnitGroup> xUserUnitGroups = xUserUnitGroupDao.findByUserAndUnitAndGroup(null, null, null);

		//asserts
		assertNotNull("No xUserUnitGroups found", xUserUnitGroups);
		Assert.assertEquals("Returns invalid xUserUnitGroups", 17, xUserUnitGroups.size());
	}
}
