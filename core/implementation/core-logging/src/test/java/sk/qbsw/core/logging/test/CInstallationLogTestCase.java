package sk.qbsw.core.logging.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.logging.dao.IInstallationLogDao;
import sk.qbsw.core.logging.model.domain.CInstallationLog;
import sk.qbsw.core.logging.service.IInstallationLogService;

/**
 * Checks installation log service.
 *
 * @autor Tomas Lauro
 * @version 1.9.0
 * @since 1.9.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@TransactionConfiguration (transactionManager = "transactionManager", defaultRollback = true)
public class CInstallationLogTestCase
{
	/** The installation log dao. */
	@Autowired
	private IInstallationLogDao installationLogDao;

	/** The installation log service. */
	@Autowired
	private IInstallationLogService installationLogService;

	/**
	 * Checks version with positive result.
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void checksVersionPositive ()
	{
		String versionToCheck = "unit_test_1.1.0";

		//create new version
		installationLogDao.save(createInstallationLog(versionToCheck));

		//checks version
		boolean result = installationLogService.checkVersion(versionToCheck);

		//checks result
		Assert.assertTrue("Check of installation log failed", result);
	}

	/**
	 * Checks version with negative result.
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void checksVersionNegative ()
	{
		//create new version
		installationLogDao.save(createInstallationLog("unit_test_1.1.0"));

		//checks version
		boolean result = installationLogService.checkVersion("unit_test_1.1.2");

		//checks result
		Assert.assertFalse("Check of installation log failed", result);
	}

	/**
	 * Checks versions with positive result - same list to compare.
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void checksFullVersionListPositive ()
	{
		List<String> versionsToCheck = Arrays.asList("unit_test_1.1.0", "unit_test_1.1.1");

		for (String versionToCheck : versionsToCheck)
		{
			//create new version
			installationLogDao.save(createInstallationLog(versionToCheck));
		}

		//checks version
		boolean result = installationLogService.checkVersionList(versionsToCheck);

		//checks result
		Assert.assertTrue("Check of installation log failed", result);
	}

	/**
	 * Checks versions with positive result - smaller list to compare.
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void checksLessVersionListPositive ()
	{
		List<String> versionsToSave = Arrays.asList("unit_test_1.0.0", "unit_test_1.1.0", "unit_test_1.2.0");

		for (String versionToSave : versionsToSave)
		{
			//create new version
			installationLogDao.save(createInstallationLog(versionToSave));
		}

		//checks version
		boolean result = installationLogService.checkVersionList(Arrays.asList("unit_test_1.0.0", "unit_test_1.2.0"));

		//checks result
		Assert.assertTrue("Check of installation log failed", result);
	}

	/**
	 * Checks versions with negative result - more versions as input as in list.
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void checksMoreVersionListNegative ()
	{
		List<String> versionsToSave = Arrays.asList("unit_test_1.0.0", "unit_test_1.2.0");

		for (String versionToSave : versionsToSave)
		{
			//create new version
			installationLogDao.save(createInstallationLog(versionToSave));
		}

		//checks version
		boolean result = installationLogService.checkVersionList(Arrays.asList("unit_test_1.0.0", "unit_test_1.2.0", "unit_test_1.3.0"));

		//checks result
		Assert.assertFalse("Check of installation log failed", result);
	}

	/**
	 * Checks versions with negative result - no version in DB.
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void checksNoneVersionListNegative ()
	{
		//checks version
		boolean result = installationLogService.checkVersionList(Arrays.asList("unit_test_1.0.0", "unit_test_1.2.0"));

		//checks result
		Assert.assertFalse("Check of installation log failed", result);
	}

	/**
	 * Checks versions with negative result - empty list as input.
	 */
	@Test (expected = IllegalArgumentException.class)
	@Transactional
	@Rollback (true)
	public void checksEmptyVersionListNegative ()
	{
		//checks version
		installationLogService.checkVersionList(new ArrayList<String>());
	}

	/**
	 * Creates the installation log.
	 *
	 * @param version the version
	 * @return the installation log
	 */
	private CInstallationLog createInstallationLog (String version)
	{
		CInstallationLog log = new CInstallationLog();
		log.setVersion(version);
		log.setName("Patch with version " + version);
		log.setDescription("Description of patch with version " + version);

		return log;
	}
}
