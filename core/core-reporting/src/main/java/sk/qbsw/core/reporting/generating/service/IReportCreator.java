package sk.qbsw.core.reporting.generating.service;

import sk.qbsw.core.reporting.generating.exception.CReportCreationException;
import sk.qbsw.core.reporting.generating.model.IReportRequest;

/**
 * Implementation of the report creator should be stateless
 * @author Peter Bozik
 *
 */
public interface IReportCreator
{
	/**
	 * Sets the report to the creator
	 * @param report
	 */
	void setObject (IReportRequest report);

	/**
	 * Execute report creation for given report
	 * @throws CReportCreationException
	 */
	void execute () throws CReportCreationException;

}
