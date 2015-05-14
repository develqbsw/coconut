package sk.qbsw.core.logging.test;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.logging.model.domain.EOperationResult;
import sk.qbsw.core.logging.service.IAuditLogService;

/**
 * Checks logging service.
 *
 * @autor Michal Lacko
 * @version 1.8.0
 * @since 1.8.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@TransactionConfiguration (transactionManager = "transactionManager")
public class CAuditLogTestCase
{


	/** The user service. */
	@Autowired
	private IAuditLogService auditLogService;

	@Autowired
	private Authentication authentification;

	@Before
	public void initialise ()
	{
		SecurityContextHolder.getContext().setAuthentication(authentification);
	}

	/**
	 * Test get all units by user.
	 *
	 * @throws CSecurityException the security exception
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void storeLog ()
	{
		List<Object> testDataList = new ArrayList<Object>();
		testDataList.add(createTestData());

		auditLogService.doLog("OP-TEST-1", testDataList, EOperationResult.OK, "testDescription");
	}

	private CTestData createTestData ()
	{
		CTestData data = new CTestData();
		data.setAtributeObjectDouble(new Double(123456789));
		data.setAttributeBytes(new byte[] {1, 2, 3, 4, 5, 6, 7, 8, 9});
		data.setAttributeDateTime(new DateTime());
		data.setAttributeSmallDouble(123456789D);
		data.setAttributeString("test data");
		return data;
	}
}
