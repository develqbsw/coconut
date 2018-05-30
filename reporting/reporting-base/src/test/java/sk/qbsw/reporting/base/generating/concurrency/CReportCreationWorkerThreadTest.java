package sk.qbsw.reporting.base.generating.concurrency;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sk.qbsw.reporting.base.generating.mock.ASpringTest;
import sk.qbsw.reporting.base.generating.mock.model.CReportRequestMock;
import sk.qbsw.reporting.base.generating.mock.service.CReportCreatorForMockService;
import sk.qbsw.reporting.base.generating.mock.service.CReportCreatorServiceMock;

public class CReportCreationWorkerThreadTest extends ASpringTest
{
    @Autowired
    private CReportCreatorForMockService service;

    /**
     * Tests if bean configuration is good, if the total count of executions is not greater or lower as it should be
     */
    //TODO: fix and enable this test case - there is a problem with count of instances on bamboo: th result is 1002 instances
    @Test
    @Ignore
    public void testBeanConfiguration()
    {
        int cycleCount = 1000;
        IReportCreatorWorkerTask thread = appContext.getBean(IReportCreatorWorkerTask.class);
        CReportRequestMock mockedRequest = new CReportRequestMock();

        thread.configure(mockedRequest, null);
        for (int i = 0; i < cycleCount; i++)
        {
            thread.run();
        }
        Assert.assertEquals("Count of instances is not matching.", cycleCount, CReportCreatorServiceMock.getCountOfInstances());
        Assert.assertEquals("Count of calls is not matching.", cycleCount, service.getCalls());
    }
}
