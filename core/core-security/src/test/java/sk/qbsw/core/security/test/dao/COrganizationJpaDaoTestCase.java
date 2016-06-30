package sk.qbsw.core.security.test.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.security.dao.IOrganizationDao;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.test.util.CDataGenerator;

/**
 * Checks organization jpa dao.
 *
 * @version 1.13.0
 * @since 1.13.0
 * @autor Tomas Lauro
 */
public class COrganizationJpaDaoTestCase extends ADatabaseTestCase
{
	/** The organization dao. */
	@Autowired
	private IOrganizationDao organizationDao;

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

		List<COrganization> organizations = organizationDao.findByName(CDataGenerator.ORGANIZATION_CODE);

		//asserts
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

		List<COrganization> organizations = organizationDao.findByName(null);

		//asserts
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

		List<COrganization> organizations = organizationDao.findByName("not found");

		//asserts
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

		List<COrganization> organizations = organizationDao.findAll();

		//asserts
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
		//without init to database

		List<COrganization> organizations = organizationDao.findAll();

		//asserts
		assertNotNull("No organizations found", organizations);
		Assert.assertEquals("Returns invalid organizations", 0, organizations.size());
	}
}
