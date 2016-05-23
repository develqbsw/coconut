package sk.qbsw.core.reporting.generating.mock.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import sk.qbsw.core.reporting.generating.exception.CReportCreationException;
import sk.qbsw.core.reporting.generating.model.IReportRequest;
import sk.qbsw.core.reporting.generating.service.IReportCreator;
import sk.qbsw.core.reporting.generating.service.impl.ACreateReportService;

@Component
@Scope (ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CReportCreatorServiceMock implements IReportCreator
{
	public static int NUMBER_OF_INSTANCES = 0;
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ACreateReportService.class);

	@Autowired
	private CReportCreatorForMockService service;
	private IReportRequest report;

	public CReportCreatorServiceMock ()
	{
		CReportCreatorServiceMock.NUMBER_OF_INSTANCES++;
	}


	@Override
	public void setObject (IReportRequest report)
	{
		this.report = report;

	}

	@Override
	public void execute () throws CReportCreationException
	{
		if (report == null)
		{
			throw new IllegalStateException("no report defined");
		}
		service.call();
		LOGGER.info("creating report file for " + report.getIdentificator());
	}


}
