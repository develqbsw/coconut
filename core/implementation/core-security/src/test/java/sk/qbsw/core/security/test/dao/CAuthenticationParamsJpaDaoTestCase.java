package sk.qbsw.core.security.test.dao;

import static org.junit.Assert.assertNotNull;

import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.security.dao.IAuthenticationParamsDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.model.domain.CAuthenticationParams;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.test.util.CDataGenerator;

/**
 * Checks authentication params jpa dao.
 *
 * @autor Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.13.0
 */
public class CAuthenticationParamsJpaDaoTestCase extends ADatabaseTestCase
{
	/** The role jpa dao. */
	@Autowired
	private IAuthenticationParamsDao authenticationParamsDao;

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
		assertNotNull("Could not find authentication params dao", authenticationParamsDao);
		assertNotNull("Could not find user dao", userDao);
	}

	/**
	 * Test find one by user id positive.
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testFindOneByUserIdPositive ()
	{
		initTest();

		CUser user = userDao.findOneByLogin(CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE);
		CAuthenticationParams authenticationParams = authenticationParamsDao.findOneByUserId(user.getId());

		//asserts
		assertNotNull("No authentication params found", authenticationParams);
		Assert.assertEquals("Returns invalid role", CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, authenticationParams.getPassword());
	}

	/**
	 * Test find one by user id negative.
	 */
	@Test (expected = NoResultException.class)
	@Transactional
	@Rollback (true)
	public void testFindOneByUserIdNegative ()
	{
		initTest();

		//expecting an exception
		authenticationParamsDao.findOneByUserId(92010l);
	}

	/**
	 * Test find one valid by user id positive.
	 * TODO: maybe create a negative scenario
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testFindOneValidByUserIdPositive ()
	{
		initTest();

		CUser user = userDao.findOneByLogin(CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE_NO_GROUP);
		CAuthenticationParams authenticationParams = authenticationParamsDao.findOneValidByUserId(user.getId());

		//asserts
		assertNotNull("No authentication params found", authenticationParams);
		Assert.assertEquals("Returns invalid role", CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE_NO_GROUP, authenticationParams.getPassword());
	}
}
