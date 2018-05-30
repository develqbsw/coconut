package sk.qbsw.reporting.base.generating.mock.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sk.qbsw.reporting.base.generating.exception.CReportCreationException;
import sk.qbsw.reporting.base.generating.model.IReportRequest;
import sk.qbsw.reporting.base.generating.service.IReportCreator;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CReportCreatorServiceMock implements IReportCreator
{
    private static volatile int NUMBER_OF_INSTANCES = 0;

    private static final Logger LOGGER = LoggerFactory.getLogger(CReportCreatorServiceMock.class);

    @Autowired
    private CReportCreatorForMockService service;

    private IReportRequest report;

    public CReportCreatorServiceMock()
    {
        CReportCreatorServiceMock.NUMBER_OF_INSTANCES++;
    }

    public static int getCountOfInstances()
    {
        return CReportCreatorServiceMock.NUMBER_OF_INSTANCES;
    }

    @Override
    public void setObject(IReportRequest report)
    {
        this.report = report;
    }

    @Override
    public void execute() throws CReportCreationException
    {
        if (report == null)
        {
            throw new IllegalStateException("no report defined");
        }
        service.call();
        LOGGER.info("creating report file for " + report.getIdentificator());
    }
}
