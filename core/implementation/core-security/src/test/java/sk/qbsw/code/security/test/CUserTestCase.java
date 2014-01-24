package sk.qbsw.code.security.test;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.code.security.test.util.CDataGenerator;
import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.service.IUserService;

/**
 * Checks user service.
 *
 * @autor Tomas Lauro
 * @version 1.6.0
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@TransactionConfiguration (transactionManager = "transactionManager", defaultRollback = true)
public class CUserTestCase
{
	/** The database data generator. */
	@Autowired
	private CDataGenerator dataGenerator;

	/** The unit service. */
	@Autowired
	@Qualifier ("cUserService")
	private IUserService userService;

	/**
	 * Test initialization.
	 */
	@Test
	public void testInitialization ()
	{
		assertNotNull("Could not find user service", userService);
	}

	/**
	 * Test get all users.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testGetAll () throws CSecurityException
	{
		initTest();

		List<CUser> users = userService.getUsers();

		//asserts
		assertNotNull("Get all users failed: list of users is null", users);
		Assert.assertTrue("Get all users failed: list of users is empty", users.size() > 0);
	}

	/**
	 * Test get all users.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testGetAllByGroupCodePrefix () throws CSecurityException
	{
		initTest();

		List<CUser> twoUsers = userService.getUsers(null, null, null, null, CDataGenerator.SECOND_GROUP_IN_UNIT_CODE.substring(0, 12));
		List<CUser> oneUser = userService.getUsers(null, null, null, null, CDataGenerator.THIRD_GROUP_IN_UNIT_CODE);

		//asserts
		assertNotNull("Get all users failed: list of users is null", twoUsers);
		assertNotNull("Get all users failed: list of users is null", oneUser);
		Assert.assertEquals("Get all users failed: the expected count of user is 2 ", twoUsers.size(), 2);
		Assert.assertEquals("Get all users failed: the expected count of user is 1 ", oneUser.size(), 1);
	}

	/**
	 * Inits the test.
	 */
	private void initTest ()
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
	}
}
