package sk.qbsw.security.organization.complex.management.db.test.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.security.organization.complex.core.model.domain.CXOOrganization;
import sk.qbsw.security.organization.complex.management.db.test.util.DataGenerator;
import sk.qbsw.security.organization.complex.management.service.CXOOrganizationService;

/**
 * Checks organization service.
 *
 * @author Tomas Lauro
 * @author Tomas Leken
 * @version 2.0.0
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@Rollback
public class CXOOrganizationServiceTestCase
{
	@Autowired
	private DataGenerator dataGenerator;

	@Autowired
	private CXOOrganizationService organizationService;

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

		List<CXOOrganization> organizations = organizationService.findByName(DataGenerator.ORGANIZATION_CODE);

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
