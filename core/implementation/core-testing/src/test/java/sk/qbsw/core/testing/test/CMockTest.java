package sk.qbsw.core.testing.test;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import sk.qbsw.core.testing.test.dao.ITestDao;
import sk.qbsw.core.testing.test.service.ITestService;

/**
 * Checks mock objects.
 *
 * @autor Tomas Lauro
 * @version 1.9.0
 * @since 1.9.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
public class CMockTest
{
	/** The test service. */
	@Autowired
	@Qualifier ("testService")
	private ITestService testService;

	/** The test dao. */
	@Autowired
	@Qualifier ("mockTestDao")
	private ITestDao testDao;

	/**
	 * Test mocking.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testMocking () throws Exception
	{
		int expectedResult = 25;
		//mockito rules
		Mockito.when(testDao.find()).thenReturn(expectedResult);
		//set mock dao to service
		ReflectionTestUtils.setField(testService, "testDao", testDao);

		int result = testService.doSomething();

		//checks result
		Assert.assertEquals("Mock testing failed", expectedResult, result);
	}
}
