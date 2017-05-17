package sk.qbsw.reporting.base.generating.service;

import java.util.List;

import sk.qbsw.reporting.base.generating.model.IReportRequest;

/**
 * Interface for service for {@link IReportRequest}
 * @author Peter Bozik
 *
 */
public interface IReportRequestService
{
	/**
	 * Make sure new Transaction scope is created
	 * @param report
	 * @param newState
	 * @return
	 */
	IReportRequest changeReportStateLimitExceeded (IReportRequest report);
	/**
	 * Make sure new Transaction scope is created
	 * @param report
	 * @param newState
	 * @return
	 */
	IReportRequest changeReportStateErrorProcessing (IReportRequest report);
	
	void changeReportStateDone(IReportRequest report);
	
	/**
	 * Find all suitable requests to process and mark them as currently processing
	 * @param queueType
	 * @param reportTypes
	 * @param limit
	 * @return
	 */
	List<IReportRequest> findNewestReportsWithLockAndMarkAsProcessing (String queueType, String[] reportTypes, Long limit);
	
	/**
	 * Find the report and marks it as processing 
	 * @param id
	 * @return
	 */
	IReportRequest findByIdAndMarkAsProcessing (Long id);

}
