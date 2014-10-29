package sk.qbsw.core.configuration.test;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.configuration.dao.ISystemParameterDao;
import sk.qbsw.core.configuration.model.domain.CSystemParameter;
import sk.qbsw.core.configuration.service.ISystemParameterService;

/**
 * The system parameter test.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.11.10
 * @since 1.11.10
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@TransactionConfiguration (transactionManager = "transactionManager", defaultRollback = true)
public class CSystemParameterTestCase
{
	/** The test system parameter name. */
	private final String TEST_SYSTEM_PARAMETER_NAME = "unit_test_parameter_1";

	/** The test system parameter value. */
	private final String TEST_SYSTEM_PARAMETER_VALUE = "unit_test_parameter_value_1";

	/** The test system parameter module. */
	private final String TEST_SYSTEM_PARAMETER_MODULE = "unit_test_parameter_module_1";

	/** The system parameter service. */
	@Autowired
	private ISystemParameterService systemParameterService;

	/** The system parameter dao. */
	@Autowired
	private ISystemParameterDao systemParameterDao;

	/**
	 * Get system parameter.
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void findByName ()
	{
		//create test data
		createTestParameters();

		CSystemParameter systemParameter = systemParameterService.findByName(TEST_SYSTEM_PARAMETER_NAME);

		//checks asserts
		Assert.assertNotNull("The system parameter not found", systemParameter);
	}

	/**
	 * Creates the test data.
	 */
	private void createTestParameters ()
	{
		CSystemParameter systemParameter = new CSystemParameter();
		systemParameter.setName(TEST_SYSTEM_PARAMETER_NAME);
		systemParameter.setModule(TEST_SYSTEM_PARAMETER_MODULE);
		systemParameter.setStringValue(TEST_SYSTEM_PARAMETER_VALUE);
		systemParameter.setValidFromDate(DateTime.now());
		systemParameter.setValidToDate(DateTime.now().plusDays(10));

		systemParameterDao.save(systemParameter);
	}
}
