package sk.qbsw.reporting.base.generating.concurrency.impl;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.reporting.base.generating.concurrency.IReportCreatorWorkerTask;
import sk.qbsw.reporting.base.generating.exception.CReporProcessingLimitExceededException;
import sk.qbsw.reporting.base.generating.model.IReportRequest;
import sk.qbsw.reporting.base.generating.service.ICreateReportService;
import sk.qbsw.reporting.base.generating.service.IReportRequestService;

/**
 * Thread to generate reports. Report processing is aware of {@link CReporProcessingLimitExceededException} and based on this {@link IReportRequestService#changeReportStateLimitExceeded(IReportRequest)} is called
 * @author Peter Bozik
 *
 */
@Component
@Scope (ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CNotAuditLogged (blockTree = true)
public class CReportCreationWorkerThread extends AReportCreatorTask implements IReportCreatorWorkerTask
{

	@Autowired
	private ICreateReportService serviceInst;

	@Autowired
	private IReportRequestService reportService;


	private CountDownLatch latch;
	private IReportRequest reportRequest;

	@Override
	public void configure (IReportRequest reportRequest, CountDownLatch latch)
	{
		this.reportRequest = reportRequest;
		this.latch = latch;
	}

	@Override
	public void run ()
	{
		LOGGER.debug("WorkerThread: Waiting for report generation " + Thread.currentThread().getId());
		try
		{
			generateReports();
		}
		catch (Exception e)
		{
			LOGGER.error("WorkerThread: CBusinessException : " + e.getMessage(), e);
		}
		finally
		{
			if (latch != null)
			{
				latch.countDown();
			}
		}
		LOGGER.debug("WorkerThread: End of Report generation " + Thread.currentThread().getId());
	}

	private void generateReports () throws CBusinessException
	{
		LOGGER.debug("SYNC: Begin of Report processing at thread " + Thread.currentThread().getId());
		if (reportRequest != null)
		{
			try
			{
				serviceInst.createReport(reportRequest);
			}
			catch (CBusinessException e)
			{
				LOGGER.error("SYNC: Latest report ID:" + reportRequest.getIdentificator() + "  is marked as ERROR_PROCESSING", e);
				reportService.changeReportStateLimitExceeded(reportRequest);

				throw e;
			}
			catch (Exception e)
			{
				LOGGER.error("SYNC: Latest report ID:" + reportRequest.getIdentificator() + "  is marked as ERROR_PROCESSING", e);
				reportService.changeReportStateLimitExceeded(reportRequest);
				throw new CBusinessException("Error report generating", e);
			}
		}
		else
		{
			LOGGER.debug("SYNC: Suitable report not found at thread" + Thread.currentThread().getId());
		}
		LOGGER.debug("SYNC: End of Report processing at thread " + Thread.currentThread().getId());
	}



}
