package sk.qbsw.reporting.base.generating.concurrency.impl;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.reporting.base.generating.concurrency.IReportCreatorExecutor;
import sk.qbsw.reporting.base.generating.concurrency.IReportCreatorWorkerTask;
import sk.qbsw.reporting.base.generating.model.IReportRequest;
import sk.qbsw.reporting.base.generating.service.ICreateReportService;
import sk.qbsw.reporting.model.CArrayUtils;

/**
 * Executor for threads that create reports. Only one configuration is permitted
 * @author Peter Bozik
 *
 */
@Component
@Scope (ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CReportCreatorExecutor implements IReportCreatorExecutor {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
	private ICreateReportService reportService;

	/**
	 * Thread pool executor
	 */
	private ThreadPoolExecutor executor;
	/**
	 * Array of possible report types
	 */
	protected String[] reportTypes;
	/**
	 * Maximal pool size
	 */
	private Integer poolSize;
	/**
	 * Report Queue type
	 */
	protected String queueType;
	/**
	 * configuration flag
	 */
	private boolean configured=false;


	/**
	 * Only one configuration is permitted
	 */
	@Override
	public void configure (String[] reportTypes, Integer poolSize, String queueType) {
		if(configured){
			throw new IllegalStateException("Executor already configured");
		}
		this.reportTypes = reportTypes;
		this.poolSize = poolSize;
		this.queueType = queueType;
		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(this.poolSize);
		configured=true;
	}

	/**
	 * look for available resources (free thread space in {@link ThreadPoolExecutor}).
	 */
	@Override
	public synchronized void execute () {
		if(!configured){
			throw new IllegalStateException("Executor is not configured");
		}
		int currentCount = executor.getActiveCount();
		int availableCount = poolSize - currentCount;// how many reports can handle this executor in given time
		if (availableCount > 0) {
			List<IReportRequest> reportRequests;
			try
			{
				reportRequests = reportService.getReportsForGenerating(null, queueType, reportTypes, new Long(availableCount));
				LOGGER.debug("executor: processing " + queueType + " (" + CArrayUtils.arrayToString(reportTypes) + ") available resources " + availableCount + " going to process: " + reportRequests.size());
				for (IReportRequest reportRequest : reportRequests) {
					executeNew(reportRequest);
				}
			}
			catch (CBusinessException e)
			{
				LOGGER.error("error retrieving report requests",e);
			}
			
		}
		else {
			LOGGER.debug("executor: processing " + queueType + " (" + CArrayUtils.arrayToString(reportTypes) + ") available resources " + availableCount + ".");

		}



	}


	/**
	 * execute new thread
	 */
	private synchronized void executeNew (IReportRequest reportRequest) {
		if (executor.getActiveCount() < poolSize) {
			executor.execute(getThread(reportRequest));
		}
	}

	/**
	 * create new thread to process report
	 * @param reportRequest report request {@link IReportRequest}
	 * @return
	 */
	protected IReportCreatorWorkerTask getThread (IReportRequest reportRequest) {
		IReportCreatorWorkerTask task = appContext.getBean(IReportCreatorWorkerTask.class);
		task.configure(reportRequest, null);
		return task;
	}
}
