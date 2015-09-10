package sk.qbsw.core.reporting.generating.concurrency.dispatcher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.base.monitoring.IMonitored;
import sk.qbsw.core.reporting.generating.concurrency.IExecutor;
import sk.qbsw.core.reporting.generating.concurrency.IReportCreatorExecutor;
import sk.qbsw.core.reporting.generating.model.IServerNodeRoleEntity;
import sk.qbsw.core.reporting.generating.service.IApplicationServerNodeRoleService;

/**
 * Dispatcher for report generation. Contains several executors ( {@link IExecutor} )
 * @author Peter Bozik
 *
 */
@CNotAuditLogged(blockTree=true)
public class CReportDispatcher implements IReportDispatcher,InitializingBean, IMonitored {
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected ApplicationContext context;
	
	@Autowired
	protected IApplicationServerNodeRoleService roleService;
	

	
	private String queueType;
	private String role;
	private List<IExecutor> executors = null;
	private boolean messageShown = false;
	
	/**
	 * dispatch execution of executors
	 * @param callerIdentificator used for logging
	 */
	public void dispatchExecution (String callerIdentificator) {
		if (!messageShown && (executors == null || executors.size() == 0)) {
			messageShown = true;
			LOGGER.warn(callerIdentificator + " " + queueType + " there are no defined executers");
			return;
		}
		for (IExecutor executor : executors) {
			executor.execute();
		}
	}

	private void initialize () {
		executors = createExecutors();
	}



	@Override
	public void afterPropertiesSet () throws Exception {
		initialize();
	}

	private List<IExecutor> createExecutors () {
		List<IExecutor> result = new ArrayList<IExecutor>();
		List<? extends IServerNodeRoleEntity> configurations = roleService.getApplicationServerNodeRolesComplete();
		if (configurations != null && configurations.size() > 0) {
			for (IServerNodeRoleEntity cApplicationServerNodeRole : configurations) {
				IExecutor executor = createExecutor(cApplicationServerNodeRole);
				if (executor != null) {
					result.add(executor);
				}
			}
		}
		return result;
	}

	/**
	 * create executor for given configuration
	 * @param conf Configuration fo type {@link IServerNodeRoleEntity}
	 * @return
	 */
	private IExecutor createExecutor (IServerNodeRoleEntity conf) {
		boolean error = false;
		if (!conf.getServerRole().equals(role)) {
			return null;
		}
		String reportTypesS = conf.getReportTypes();
		Set<String> typesSet = new HashSet<String>();
		if (StringUtils.isNotBlank(reportTypesS)) {
			String[] typesA = reportTypesS.split(",");
			for (String typeS : typesA) {
				typesSet.add(typeS.trim());
			}
		}
		else {
			LOGGER.error("blank report types configuration for node role with id: " + conf.getIdentificator());
			error = true;
		}
		if (conf.getThreadResources() == null || conf.getThreadResources() == 0l) {
			error = true;
			LOGGER.error("wrong resources number configuration for node role with id: " + conf.getIdentificator());
		}
		if (!error) {
			IReportCreatorExecutor executor=context.getBean(IReportCreatorExecutor.class);
			executor.configure(typesSet.toArray(new String[typesSet.size()]), conf.getThreadResources(), queueType);
			LOGGER.info("executor for configuration with id " + conf.getIdentificator()+" created");
			return executor;
		}

		return null;
	}

	public String getQueueType () {
		return queueType;
	}

	public void setQueueType (String queueType) {
		this.queueType = queueType;
	}

	public String getRole () {
		return role;
	}

	public void setRole (String role) {
		this.role = role;
	}


}
