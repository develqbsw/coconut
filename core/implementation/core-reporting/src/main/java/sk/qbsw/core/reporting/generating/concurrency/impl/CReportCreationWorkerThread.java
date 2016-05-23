package sk.qbsw.core.reporting.generating.concurrency.impl;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.reporting.generating.concurrency.IReportCreatorWorkerTask;
import sk.qbsw.core.reporting.generating.exception.CReporProcessingLimitExceededException;
import sk.qbsw.core.reporting.generating.model.IReportRequest;
import sk.qbsw.core.reporting.generating.service.ICreateReportService;
import sk.qbsw.core.reporting.generating.service.IReportRequestService;

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
			LOGGER.error("WorkerThread: CBusinessException : " + e.getMessage());
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
			catch (Exception e)
			{
				LOGGER.error("SYNC: Leatest report ID:" + reportRequest.getIdentificator() + "  is marked as ERROR_PROCESSING", e);
				if (e instanceof CReporProcessingLimitExceededException)
				{
					reportService.changeReportStateLimitExceeded(reportRequest);
				}
				else
				{
					reportService.changeReportStateErrorProcessing(reportRequest);
				}
				if (e instanceof CBusinessException)
				{
					throw e;
				}
				throw new CBusinessException("error report generating", e);
			}
		}
		else
		{
			LOGGER.debug("SYNC: Suitable report not found at thread" + Thread.currentThread().getId());
		}
		LOGGER.debug("SYNC: End of Report processing at thread " + Thread.currentThread().getId());
	}



}
