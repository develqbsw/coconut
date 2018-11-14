package sk.qbsw.security.core.test.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AuthenticationParamsDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.AuthenticationParams;
import sk.qbsw.security.core.test.util.DataGenerator;

import javax.persistence.NoResultException;

import static org.junit.Assert.assertNotNull;

/**
 * Checks authentication params jpa dao.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.13.0
 */
public class AuthenticationParamsJpaDaoTestCase extends BaseDatabaseTestCase
{
	@Autowired
	private AuthenticationParamsDao authenticationParamsDao;

	@Autowired
	private AccountDao accountDao;

	/**
	 * Test initialization.
	 */
	@Before
	public void testInitialization ()
	{
		super.testInitialization();
		assertNotNull("Could not find authentication params dao", authenticationParamsDao);
		assertNotNull("Could not find account dao", accountDao);
	}

	/**
	 * Test find one by account id positive.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByAccountIdPositive () throws NoResultException, CSecurityException
	{
		initTest();

		Account account = accountDao.findOneByLogin(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE);
		AuthenticationParams authenticationParams = authenticationParamsDao.findOneByAccountId(account.getId());

		// asserts
		assertNotNull("No authentication params found", authenticationParams);
		Assert.assertEquals("Returns invalid role", DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, authenticationParams.getPassword());
	}

	/**
	 * Test find one by account id negative.
	 */
	@Test (expected = NoResultException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByAccountIdNegative ()
	{
		initTest();

		// expecting an exception
		authenticationParamsDao.findOneByAccountId(92010l);
	}

	/**
	 * Test find one valid by account id positive.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneValidByAccountIdPositive () throws NoResultException, CSecurityException
	{
		initTest();

		Account account = accountDao.findOneByLogin(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE_NO_GROUP);
		AuthenticationParams authenticationParams = authenticationParamsDao.findOneValidByAccountId(account.getId());

		// asserts
		assertNotNull("No authentication params found", authenticationParams);
		Assert.assertEquals("Returns invalid role", DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE_NO_GROUP, authenticationParams.getPassword());
	}
}
