package sk.qbsw.core.reporting.generating.scheduling;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import sk.qbsw.core.reporting.generating.concurrency.dispatcher.CReportDispatcher;
import sk.qbsw.core.reporting.generating.mock.ASpringTest;
import sk.qbsw.core.reporting.generating.mock.model.CReportRequestMock;
import sk.qbsw.core.reporting.generating.mock.service.CReportRequestServiceMock;

public class CDispatcherTest extends ASpringTest
{

	@Autowired
	private CReportRequestServiceMock service;
	
	/**
	 * Tests report processing. If the report is marked as processed
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception{
		CReportRequestMock request=new CReportRequestMock(); 
		service.addReportRequest(request);
		
		String queueType="TestQueue";
		String role="testRole";
		CReportDispatcher dispatcher=new CReportDispatcher();
		dispatcher.setQueueType(queueType);
		dispatcher.setRole(role);
		beanFactory.autowireBean(dispatcher);
		dispatcher.afterPropertiesSet();
		dispatcher.dispatchExecution("test sign");
		Thread.sleep(1000);//need to wait for execution
		Assert.assertTrue(service.isReportDone(request));
	}
	
	@After
	public void after(){
		service.clearReportRequests();
	}
}
