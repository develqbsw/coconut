package sk.qbsw.core.security.test.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.security.dao.IRoleDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.test.util.CDataGenerator;

/**
 * Checks role dao.
 *
 * @version 1.13.0
 * @since 1.13.0
 * @autor Tomas Lauro
 */
public class CRoleJpaDaoTestCase extends ADatabaseTestCase
{
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
		super.testInitialization();
		assertNotNull("Could not find role dao", roleDao);
		assertNotNull("Could not find user dao", userDao);
	}

	/**
	 * Test find by user positive.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testFindByUserPositive () throws CSecurityException
	{
		initTest();

		CUser user = userDao.findOneByLogin(CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE);
		List<CRole> roles = roleDao.findByUser(user);

		//asserts
		assertNotNull("No roles found", roles);
		Assert.assertEquals("Returns invalid roles count", 1, roles.size());
		Assert.assertEquals("Returns invalid role", CDataGenerator.FIRST_ROLE_CODE, roles.get(0).getCode());
	}

	/**
	 * Test find by user negative no user.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional
	@Rollback (true)
	public void testFindByUserNegativeNoUser () throws CSecurityException
	{
		initTest();

		roleDao.findByUser(null);
	}

	/**
	 * Test find one by code positive.
	 *
	 * @throws NonUniqueResultException the non unique result exception
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testFindOneByCodePositive () throws NonUniqueResultException, NoResultException, CSecurityException
	{
		initTest();

		CRole role = roleDao.findOneByCode(CDataGenerator.SECOND_ROLE_CODE);

		//asserts
		assertNotNull("No roles found", role);
		Assert.assertEquals("Returns invalid role", CDataGenerator.SECOND_ROLE_CODE, role.getCode());
	}

	/**
	 * Test find one by code no result.
	 *
	 * @throws NonUniqueResultException the non unique result exception
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = NoResultException.class)
	@Transactional
	@Rollback (true)
	public void testFindOneByCodeNoResult () throws NonUniqueResultException, NoResultException, CSecurityException
	{
		initTest();

		roleDao.findOneByCode("no result");
	}

	/**
	 * Test find one by code no code.
	 *
	 * @throws NonUniqueResultException the non unique result exception
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional
	@Rollback (true)
	public void testFindOneByCodeNoCode () throws NonUniqueResultException, NoResultException, CSecurityException
	{
		initTest();

		roleDao.findOneByCode(null);
	}
}
