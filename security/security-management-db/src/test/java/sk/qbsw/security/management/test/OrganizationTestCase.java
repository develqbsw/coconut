package sk.qbsw.security.management.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.security.core.model.domain.Organization;
import sk.qbsw.security.management.db.service.OrganizationService;
import sk.qbsw.security.management.test.util.DataGenerator;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Checks organization service.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@Rollback
public class OrganizationTestCase
{
	@Autowired
	private DataGenerator dataGenerator;

	@Autowired
	private OrganizationService organizationService;

	/**
	 * Test initialization.
	 */
	@Test
	public void testInitialization ()
	{
		assertNotNull("Could not find unit service", organizationService);
	}

	/**
	 * Test read all units.
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testGetAllByName ()
	{
		initTest();

		List<Organization> organizations = organizationService.findByName(DataGenerator.ORGANIZATION_CODE);

		// asserts
		assertNotNull("List of organizations is null", organizations);
		Assert.assertSame(1, organizations.size());

		organizations = organizationService.findByName("name");

		// asserts
		assertNotNull("List of organizations is null", organizations);
		Assert.assertSame(0, organizations.size());

		organizations = organizationService.findByName(DataGenerator.ORGANIZATION_2_CLONE_CODE);

		// asserts
		assertNotNull("List of organizations is null", organizations);
		Assert.assertSame(1, organizations.size());
	}

	/**
	 * Inits the test.
	 */
	private void initTest ()
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
	}
}
