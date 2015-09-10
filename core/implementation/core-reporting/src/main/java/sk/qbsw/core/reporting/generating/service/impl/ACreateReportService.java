package sk.qbsw.core.reporting.generating.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.monitoring.IMonitored;
import sk.qbsw.core.reporting.generating.model.IReportRequest;
import sk.qbsw.core.reporting.generating.service.ICreateReportService;
import sk.qbsw.core.reporting.generating.service.IReportCreator;
import sk.qbsw.core.reporting.generating.service.IReportRequestService;

/**
 * The Class CReportService. Needs to be overridden and marked as {@link Service}
 *
 * @author Peter Bozik
 */
public abstract class ACreateReportService implements ICreateReportService, IMonitored {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ACreateReportService.class);

	/** The report dao. */
	@Autowired
	private IReportRequestService reportService;


	@Autowired
	private ApplicationContext appContext;

	@PostConstruct
	public void init () {

	}

	/**
	 * overriding method should be transactional
	 */
	@Override
	public List<IReportRequest> getReportsForGenerating (Long id, String queueType, String[] type, Long limit) throws CBusinessException
	{
		List<IReportRequest> reports;
		if (limit == 0) {
			return new ArrayList<IReportRequest>();
		}
		if (id == null) {
			reports = this.reportService.findNewestReportsWithLockAndMarkAsProcessing(queueType, type, limit);
		}
		else {
			reports = new ArrayList<IReportRequest>();
			IReportRequest report = this.reportService.findByIdAndMarkAsProcessing(id);
			reports.add(report);
			return reports;
		}
		return reports;
	}

	/**
	 * Create report for given model. Overriding method should be transactional with proper timeout (@Transactional (readOnly = false, timeout = 300))
	 */
	@Override
	public void createReport (IReportRequest report) throws CBusinessException {
		createReportInner(report);
	}


	private void createReportInner (IReportRequest report) throws CBusinessException {
		Long time = System.currentTimeMillis();
		try {
			IReportCreator reportCreator = this.getReportCreatorByReportType(report);
			ACreateReportService.LOGGER.info("Begin report execution");
			reportCreator.setObject(report);
			reportCreator.execute();
			ACreateReportService.LOGGER.info("End report execution");
			this.reportService.changeReportStateDone(report);
			ACreateReportService.LOGGER.info("SYNC: Leatest report ID:" + report.getIdentificator() + "  is marked as PROCESSED. process duration: " + (System.currentTimeMillis() - time));
		}
		catch (Throwable e) {
			LOGGER.error("chyba pri generovani reportu: " + (report != null ? report.getIdentificator() : "null") + " process duration: " + (System.currentTimeMillis() - time), e);
			if (e instanceof CBusinessException) {
				throw (CBusinessException)e;
			}
			throw new CBusinessException("erro generating",e);
		}
	}

	/**
	 * Returns suitable {@link IReportCreator} based on {@link IReportRequest#getReportCreatorClassName()}. The instance of the class is found in spring app context 
	 * @param report
	 * @return {@link IReportCreator}
	 * @throws ClassNotFoundException 
	 * @throws BeansException 
	 * @throws Exception
	 */
	private IReportCreator getReportCreatorByReportType (IReportRequest report) throws BeansException, ClassNotFoundException {
		return (IReportCreator)appContext.getBean(Class.forName(report.getReportCreatorClassName()));
	}




}
