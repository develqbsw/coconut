package sk.qbsw.core.configuration.test;


import java.time.OffsetDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.configuration.dao.ISystemParameterDao;
import sk.qbsw.core.configuration.model.domain.CSystemParameter;
import sk.qbsw.core.configuration.service.ISystemParameterService;

/**
 * The system parameter test.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.12.0
 * @since 1.11.10
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@Rollback (true)
public class CSystemParameterTestCase
{

	/** The test system parameter name. */
	private final String TEST_SYSTEM_PARAMETER_NAME = "unit_test_parameter_1";

	/** The test system parameter value. */
	private final String TEST_SYSTEM_PARAMETER_VALUE = "unit_test_parameter_value_1";

	/** The test system parameter encrypted value. */
	private final String TEST_SYSTEM_PARAMETER_ENCRYPTED_VALUE = "unit_test_parameter_encrypted_value_1";

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
	@Transactional (transactionManager = "transactionManager")
	public void findByName ()
	{
		//create test data
		createTestParameters();

		CSystemParameter systemParameter = systemParameterService.findByName(TEST_SYSTEM_PARAMETER_NAME);

		//checks asserts
		Assert.assertNotNull("The system parameter not found", systemParameter);
	}

	/**
	 * Get encrypted system parameter.
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testGetEncryptedParameter ()
	{
		//create test data
		createTestParameters();

		CSystemParameter systemParameter = systemParameterService.findByName(TEST_SYSTEM_PARAMETER_NAME);

		//checks asserts
		Assert.assertNotNull("The system parameter not found", systemParameter);
		Assert.assertEquals("The system parameter encrypted value does not equals", systemParameter.getEncryptedValue(), TEST_SYSTEM_PARAMETER_ENCRYPTED_VALUE);
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
		systemParameter.setEncryptedValue(TEST_SYSTEM_PARAMETER_ENCRYPTED_VALUE);
		systemParameter.setValidFromDate(OffsetDateTime.now().minusDays(10));
		systemParameter.setValidToDate(OffsetDateTime.now().plusDays(10));

		systemParameterDao.update(systemParameter);
		systemParameterDao.flush();
	}
}
