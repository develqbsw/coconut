package sk.qbsw.security.test.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.security.dao.ILicenseDao;
import sk.qbsw.security.dao.IOrganizationDao;
import sk.qbsw.security.model.domain.CLicense;
import sk.qbsw.security.model.domain.COrganization;
import sk.qbsw.security.test.util.CDataGenerator;

/**
 * Checks license jpa dao.
 *
 * @version 1.13.0
 * @since 1.13.0
 * @autor Tomas Lauro
 */
public class CLicenseJpaDaoTestCase extends ADatabaseTestCase
{
	/** The license dao. */
	@Autowired
	private ILicenseDao licenseDao;

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

		List<COrganization> organizations = organizationDao.findByName(CDataGenerator.ORGANIZATION_CODE);

		List<CLicense<?>> licenses = licenseDao.findByOrganizationId(organizations.get(0).getId());

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

		List<COrganization> organizations = organizationDao.findByName(CDataGenerator.ORGANIZATION_DISABLED_CODE);

		List<CLicense<?>> licenses = licenseDao.findByOrganizationId(organizations.get(0).getId());

		//asserts
		assertNotNull("No licenses found", licenses);
		Assert.assertEquals("Returns invalid licenses", 0, licenses.size());
	}
}
