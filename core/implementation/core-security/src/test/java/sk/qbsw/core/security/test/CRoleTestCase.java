package sk.qbsw.core.security.test;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.security.dao.IRoleDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.test.util.CDataGenerator;

/**
 * Checks role dao.
 *
 * @autor Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@TransactionConfiguration (transactionManager = "transactionManager", defaultRollback = true)
public class CRoleTestCase
{
	/** The database data generator. */
	@Autowired
	private CDataGenerator dataGenerator;

	/** The role jpa dao. */
	@Autowired
	private IRoleDao roleDao;

	/** The user dao. */
	@Autowired
	private IUserDao userDao;

	/**
	 * Test initialization.
	 */
	@Before
	public void testInitialization ()
	{
		assertNotNull("Could not find data generator", dataGenerator);
		assertNotNull("Could not find role dao", roleDao);
		assertNotNull("Could not find user dao", userDao);
	}

	@Test
	@Transactional
	@Rollback (true)
	public void testFindByUser ()
	{
		initTest();

		CUser user = userDao.findByLogin(CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE);
		List<CRole> roles = roleDao.findByUser(user);

		//asserts
		assertNotNull("No roles found", roles);
		Assert.assertEquals("Returns invalid roles count", 1, roles.size());
		Assert.assertEquals("Returns invalid role", CDataGenerator.FIRST_ROLE_CODE, roles.get(0).getCode());
	}

	/**
	 * Test find by code.
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testFindByCode ()
	{
		initTest();

		@SuppressWarnings ("deprecation")
		List<CRole> roles = roleDao.findByCode(CDataGenerator.SECOND_ROLE_CODE);

		//asserts
		assertNotNull("No roles found", roles);
		Assert.assertEquals("Returns invalid roles count", 1, roles.size());
		Assert.assertEquals("Returns invalid role", CDataGenerator.SECOND_ROLE_CODE, roles.get(0).getCode());
	}

	/**
	 * Test find one by code.
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testFindOneByCode ()
	{
		initTest();

		CRole role = roleDao.findOneByCode(CDataGenerator.SECOND_ROLE_CODE);

		//asserts
		assertNotNull("No roles found", role);
		Assert.assertEquals("Returns invalid role", CDataGenerator.SECOND_ROLE_CODE, role.getCode());
	}

	/**
	 * Test find one by code no result.
	 */
	@Test (expected = NoResultException.class)
	@Transactional
	@Rollback (true)
	public void testFindOneByCodeNoResult ()
	{
		initTest();

		roleDao.findOneByCode("FakeCode");
	}

	/**
	 * Inits the test.
	 */
	private void initTest ()
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
	}
}
