package sk.qbsw.core.reporting.generating.mock.model;

import sk.qbsw.core.reporting.generating.mock.service.CReportCreatorServiceMock;
import sk.qbsw.core.reporting.generating.model.IReportRequest;

public class CReportRequestMock implements IReportRequest
{

	@Override
	public String getIdentificator ()
	{
		return "test";
	}

	@Override
	public String getReportCreatorClassName ()
	{
		return CReportCreatorServiceMock.class.getName();
	}

}
