package sk.qbsw.core.reporting.generating.mock.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import sk.qbsw.core.reporting.generating.model.IReportRequest;
import sk.qbsw.core.reporting.generating.service.IReportRequestService;

@Service
public class CReportRequestServiceMock implements IReportRequestService
{
	private List<IReportRequest> requests=new ArrayList<>();
	private List<IReportRequest> doneRequests=new ArrayList<>();
	public void addReportRequest(IReportRequest request){
		requests.add(request);
	}
	public void clearReportRequests(){
		requests.clear();
		doneRequests.clear();
	}
	@Override
	public IReportRequest changeReportStateLimitExceeded (IReportRequest report)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IReportRequest changeReportStateErrorProcessing (IReportRequest report)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changeReportStateDone (IReportRequest report)
	{
		doneRequests.add(report);
		
	}

	@Override
	public List<IReportRequest> findNewestReportsWithLockAndMarkAsProcessing (String queueType, String[] reportTypes, Long limit)
	{
		return requests;
	}

	@Override
	public IReportRequest findByIdAndMarkAsProcessing (Long id)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean isReportDone(IReportRequest report){
		return doneRequests.contains(report);
	}

}
