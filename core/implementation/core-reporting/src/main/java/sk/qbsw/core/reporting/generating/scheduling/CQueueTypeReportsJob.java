package sk.qbsw.core.reporting.generating.scheduling;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.reporting.generating.concurrency.dispatcher.IReportDispatcher;
import sk.qbsw.core.reporting.generating.service.IApplicationServerNodeRoleService;
import sk.qbsw.core.reporting.generating.service.IReportCreator;
import sk.qbsw.core.reporting.generating.service.IReportRequestService;
import sk.qbsw.core.reporting.generating.service.impl.ACreateReportService;

/**
 * Class for report generating. The {@link CQueueTypeReportsJob#executeInternal(JobExecutionContext)} is called in specified period.
 * {@link IReportDispatcher}, {@link IApplicationServerNodeRoleService} and nodeRole property have to be set<br>
 * For every job you have to define bean of this type<br>
 * 
 * All you have to do is to implement:<br>
 * {@link IApplicationServerNodeRoleService}<br>
 * {@link IReportRequestService}<br>
 * {@link IReportCreator} for different reports<br>
 * 
 * Overrides {@link ACreateReportService} and marks it as service 
 * @author Peter Bozik
 *
 */
@CNotAuditLogged (blockTree = true)
public class CQueueTypeReportsJob extends QuartzJobBean implements StatefulJob {

	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	private static final String logOperationName = "reporting job ";
	private String nodeRole;
	private boolean notConfiguredShown=false;
	private boolean invalidConfigurationShown=false;
	


	private IApplicationServerNodeRoleService applicationServerNodeRoleService;
	private IReportDispatcher dispatcher;



	public void setApplicationServerNodeRoleService (IApplicationServerNodeRoleService applicationServerNodeRoleService) {
		this.applicationServerNodeRoleService = applicationServerNodeRoleService;
	}


	public void setDispatcher (IReportDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}


	@Override
	protected void executeInternal (JobExecutionContext context) throws JobExecutionException {
		check();
		boolean canGenerate = this.applicationServerNodeRoleService.applicationServerNodeHasRole(nodeRole);
		if (canGenerate) {
			LOGGER.debug(CQueueTypeReportsJob.logOperationName + dispatcher.getQueueType() + " start");
			dispatcher.dispatchExecution(CQueueTypeReportsJob.logOperationName);
			LOGGER.debug(CQueueTypeReportsJob.logOperationName + dispatcher.getQueueType() + " end");
			notConfiguredShown=false;
		}
		else {
			if (!notConfiguredShown) {
				LOGGER.info("This application server node is not configured as " + nodeRole + ". Reports wont be generated.");
				notConfiguredShown=true;
			}
		}
	}
	private void check(){
		if(nodeRole==null&&!invalidConfigurationShown){
			LOGGER.error(CQueueTypeReportsJob.logOperationName + dispatcher.getQueueType() + " no node role defined");
			invalidConfigurationShown=true;
		}
		
	}



	public String getNodeRole () {
		return nodeRole;
	}


	public void setNodeRole (String nodeRole) {
		this.nodeRole = nodeRole;
	}
}
