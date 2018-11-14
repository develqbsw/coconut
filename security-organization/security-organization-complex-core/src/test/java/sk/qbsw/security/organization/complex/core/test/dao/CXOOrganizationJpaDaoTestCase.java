package sk.qbsw.security.organization.complex.core.test.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.security.organization.complex.core.test.util.DataGenerator;
import sk.qbsw.security.organization.complex.core.dao.CXOOrganizationDao;
import sk.qbsw.security.organization.complex.core.model.domain.CXOOrganization;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Checks organization jpa dao.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.13.0
 */
public class CXOOrganizationJpaDaoTestCase extends CXODatabaseTestCaseBase
{
	@Autowired
	private CXOOrganizationDao organizationDao;

	/**
	 * Test initialization.
	 */
	@Before
	public void testInitialization ()
	{
		super.testInitialization();
		assertNotNull("Could not find organization dao", organizationDao);
	}

	/**
	 * Test find by name positive.
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByNamePositive ()
	{
		initTest();

		List<CXOOrganization> organizations = organizationDao.findByName(DataGenerator.ORGANIZATION_CODE);

		// asserts
		assertNotNull("No organizations found", organizations);
		Assert.assertEquals("Returns invalid organizations", 1, organizations.size());
	}

	/**
	 * Test find by name positive no name.
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByNamePositiveNoName ()
	{
		initTest();

		List<CXOOrganization> organizations = organizationDao.findByName(null);

		// asserts
		assertNotNull("No organizations found", organizations);
		Assert.assertEquals("Returns invalid organizations", 0, organizations.size());
	}

	/**
	 * Test find by name positive not found.
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByNamePositiveNotFound ()
	{
		initTest();

		List<CXOOrganization> organizations = organizationDao.findByName("not found");

		// asserts
		assertNotNull("No organizations found", organizations);
		Assert.assertEquals("Returns invalid organizations", 0, organizations.size());
	}

	/**
	 * Test find all positive.
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindAllPositive ()
	{
		initTest();

		List<CXOOrganization> organizations = organizationDao.findAll();

		// asserts
		assertNotNull("No organizations found", organizations);
		Assert.assertEquals("Returns invalid organizations", 4, organizations.size());
	}

	/**
	 * Test find all positive no result.
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindAllPositiveNoResult ()
	{
		// without init to database

		List<CXOOrganization> organizations = organizationDao.findAll();

		// asserts
		assertNotNull("No organizations found", organizations);
		Assert.assertEquals("Returns invalid organizations", 0, organizations.size());
	}
}
