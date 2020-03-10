package sk.qbsw.security.management.test.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.security.management.db.dao.GroupManageableDao;
import sk.qbsw.security.management.db.model.domain.GroupManageable;
import sk.qbsw.security.management.test.util.DataGenerator;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Checks group manageable jpa dao.
 *
 * @author Michal Slezák
 * @version 2.5.0
 * @since 2.5.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@Rollback
public class GroupManageableJpaDaoTestCase extends BaseDatabaseTestCase
{
	@Autowired
	private GroupManageableDao groupManageableDao;

	/**
	 * The Data generator.
	 */
	@Autowired
	protected DataGenerator dataGenerator;

	/**
	 * Test initialization.
	 */
	@Before
	public void testInitialization ()
	{
		assertNotNull("Could not find group manageable dao", groupManageableDao);
	}

	/**
	 * Init test.
	 */
	protected void initTest ()
	{
		dataGenerator.generateDatabaseDataWithGroupManageableForDatabaseTests();
	}

	/**
	 * Test find all positive.
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindAllPositive ()
	{
		initTest();

		List<GroupManageable> groupManageables = groupManageableDao.findAll();

		// asserts
		Assert.assertEquals("Returns invalid groups", 1, groupManageables.size());
	}
}
