package sk.qbsw.reporting.base.generating.concurrency;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import sk.qbsw.reporting.base.generating.concurrency.IReportCreatorWorkerTask;
import sk.qbsw.reporting.base.generating.mock.ASpringTest;
import sk.qbsw.reporting.base.generating.mock.model.CReportRequestMock;
import sk.qbsw.reporting.base.generating.mock.service.CReportCreatorForMockService;
import sk.qbsw.reporting.base.generating.mock.service.CReportCreatorServiceMock;

public class CReportCreationWorkerThreadTest extends ASpringTest
{
	@Autowired
	private CReportCreatorForMockService service;
	
	/**
	 * Tests if bean configuration is goot, if the totoal count of executions is not greater or lower as it should be
	 */
	@Test
	public void testBeanConfiguration ()
	{
		int cycleCount=5;
		IReportCreatorWorkerTask thread =appContext.getBean(IReportCreatorWorkerTask.class);
		
		CReportRequestMock mockedRequest=new CReportRequestMock();
		thread.configure(mockedRequest, null);
		for(int i=0;i<cycleCount;i++){
			thread.run();
		}
		Assert.assertEquals(cycleCount, CReportCreatorServiceMock.NUMBER_OF_INSTANCES);
		Assert.assertEquals(cycleCount, service.getCalls());
	}
}
