package sk.qbsw.reporting.base.generating.mock.model;

import sk.qbsw.reporting.base.generating.mock.service.CReportCreatorServiceMock;
import sk.qbsw.reporting.base.generating.model.IReportRequest;

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
