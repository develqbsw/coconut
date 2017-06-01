package sk.qbsw.security.core.test.dao;

import static org.junit.Assert.assertNotNull;

import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.core.dao.AuthenticationParamsDao;
import sk.qbsw.security.core.dao.UserDao;
import sk.qbsw.security.core.model.domain.AuthenticationParams;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.core.test.util.DataGenerator;

/**
 * Checks authentication params jpa dao.
 *
 * @autor Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.13.0
 */
public class CAuthenticationParamsJpaDaoTestCase extends BaseDatabaseTestCase
{
	/** The role jpa dao. */
	@Autowired
	private AuthenticationParamsDao authenticationParamsDao;

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
		assertNotNull("Could not find authentication params dao", authenticationParamsDao);
		assertNotNull("Could not find user dao", userDao);
	}

	/**
	 * Test find one by user id positive.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByUserIdPositive () throws NoResultException, CSecurityException
	{
		initTest();

		User user = userDao.findOneByLogin(DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE);
		AuthenticationParams authenticationParams = authenticationParamsDao.findOneByUserId(user.getId());

		//asserts
		assertNotNull("No authentication params found", authenticationParams);
		Assert.assertEquals("Returns invalid role", DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, authenticationParams.getPassword());
	}

	/**
	 * Test find one by user id negative.
	 */
	@Test (expected = NoResultException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByUserIdNegative ()
	{
		initTest();

		//expecting an exception
		authenticationParamsDao.findOneByUserId(92010l);
	}

	/**
	 * Test find one valid by user id positive.
	 * TODO: maybe create a negative scenario
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneValidByUserIdPositive () throws NoResultException, CSecurityException
	{
		initTest();

		User user = userDao.findOneByLogin(DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE_NO_GROUP);
		AuthenticationParams authenticationParams = authenticationParamsDao.findOneValidByUserId(user.getId());

		//asserts
		assertNotNull("No authentication params found", authenticationParams);
		Assert.assertEquals("Returns invalid role", DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE_NO_GROUP, authenticationParams.getPassword());
	}
}
