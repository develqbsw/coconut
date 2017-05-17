package sk.qbsw.reporting.base.generating.service;

import java.util.List;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.reporting.base.generating.model.IReportRequest;

/**
 * 
 * @author podmajersky
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public interface ICreateReportService {



	/**
	 * create report from input parameter
	 * @param report
	 * @throws CCrpBussinesException
	 */
	void createReport (IReportRequest report) throws CBusinessException;



	/**
	 * find report by id, or the newest by queType and type.
	 * @param id id of the report to be generated. It is an optional parameter
	 * @param queueType acceptable queue type of reports
	 * @param type acceptable type of reports
	 * @param limit of the reports to be returned
	 * @return
	 */
	public List<IReportRequest> getReportsForGenerating (Long id, String queueType, String[] type, Long limit)throws CBusinessException;

}
