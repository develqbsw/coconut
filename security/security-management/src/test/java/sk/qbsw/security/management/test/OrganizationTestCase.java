package sk.qbsw.security.management.test;

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

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.core.model.domain.COrganization;
import sk.qbsw.security.management.service.OrganizationService;
import sk.qbsw.security.management.test.util.DataGenerator;

/**
 * Checks organization service.
 *
 * @autor Tomas Lauro
 * 
 * @version 1.11.5
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@Rollback (true)
public class OrganizationTestCase
{
	/** The database data generator. */
	@Autowired
	private DataGenerator dataGenerator;

	/** The organization service. */
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
	 * Test get all units.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testGetAllByName () throws CSecurityException
	{
		initTest();

		List<COrganization> organizations = organizationService.getOrganizations(DataGenerator.ORGANIZATION_CODE);

		//asserts
		assertNotNull("List of organizations is null", organizations);
		Assert.assertSame(1, organizations.size());

		organizations = organizationService.getOrganizations("name");

		//asserts
		assertNotNull("List of organizations is null", organizations);
		Assert.assertSame(0, organizations.size());

		organizations = organizationService.getOrganizations(DataGenerator.ORGANIZATION_2_CLONE_CODE);

		//asserts
		assertNotNull("List of organizations is null", organizations);
		Assert.assertSame(2, organizations.size());
	}

	/**
	 * Inits the test.
	 */
	private void initTest ()
	{
		dataGenerator.generateDatabaseDataForDatabaseTests();
	}
}
