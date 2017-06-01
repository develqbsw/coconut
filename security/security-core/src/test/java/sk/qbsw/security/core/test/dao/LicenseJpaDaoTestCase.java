package sk.qbsw.security.core.test.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.security.core.dao.LicenseDao;
import sk.qbsw.security.core.dao.OrganizationDao;
import sk.qbsw.security.core.model.domain.License;
import sk.qbsw.security.core.model.domain.Organization;
import sk.qbsw.security.core.test.util.DataGenerator;

/**
 * Checks license jpa dao.
 *
 * @version 1.13.0
 * @since 1.13.0
 * @autor Tomas Lauro
 */
public class LicenseJpaDaoTestCase extends BaseDatabaseTestCase
{
	/** The license dao. */
	@Autowired
	private LicenseDao licenseDao;

	/** The organization dao. */
	@Autowired
	private OrganizationDao organizationDao;

	/**
	 * Test initialization.
	 */
	@Before
	public void testInitialization ()
	{
		super.testInitialization();
		assertNotNull("Could not find license dao", licenseDao);
		assertNotNull("Could not find organization dao", organizationDao);
	}

	/**
	 * Test find by organization id positive.
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByOrganizationIdPositive ()
	{
		initTest();

		List<Organization> organizations = organizationDao.findByName(DataGenerator.ORGANIZATION_CODE);

		List<License<?>> licenses = licenseDao.findByOrganizationId(organizations.get(0).getId());

		//asserts
		assertNotNull("No licenses found", licenses);
		Assert.assertEquals("Returns invalid licenses", 1, licenses.size());
	}

	/**
	 * Test find by organization id positive no license found.
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByOrganizationIdPositiveNoLicenseFound ()
	{
		initTest();

		List<Organization> organizations = organizationDao.findByName(DataGenerator.ORGANIZATION_DISABLED_CODE);

		List<License<?>> licenses = licenseDao.findByOrganizationId(organizations.get(0).getId());

		//asserts
		assertNotNull("No licenses found", licenses);
		Assert.assertEquals("Returns invalid licenses", 0, licenses.size());
	}
}
