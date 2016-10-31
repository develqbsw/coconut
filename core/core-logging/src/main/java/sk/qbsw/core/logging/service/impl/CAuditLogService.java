package sk.qbsw.core.logging.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.logging.dao.IAuditLogDao;
import sk.qbsw.core.logging.model.domain.CAuditLog;
import sk.qbsw.core.logging.model.domain.EOperationResult;
import sk.qbsw.core.logging.service.IAuditLogEnabler;
import sk.qbsw.core.logging.service.IAuditLogSerializationService;
import sk.qbsw.core.logging.service.IAuditLogService;

// TODO: Auto-generated Javadoc
/**
 * Service xml serialziation.
 *
 * @author Michal Lacko
 * @author Dalibor Rak
 * @version 1.13.0
 * @since 1.8.0
 */
@Service
public class CAuditLogService implements IAuditLogService {

	/** The audit log serialization service. */
	@Autowired
	private IAuditLogSerializationService auditLogSerializationService;

	/** The enabler. */
	@Autowired
	private IAuditLogEnabler enabler;

	/** The audit log dao. */
	@Autowired
	private IAuditLogDao auditLogDao;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.logging.service.IAuditLogService#doLog(java.lang.String, java.util.List, sk.qbsw.core.logging.model.domain.EOperationResult, java.lang.String)
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void doLog(String operationCode, List<Object> requestData, EOperationResult result, String resultDescription) {
		doLog(operationCode, requestData, result, resultDescription, null);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.logging.service.IAuditLogService#doLog(java.lang.String, java.util.List, sk.qbsw.core.logging.model.domain.EOperationResult, java.lang.String, java.lang.String)
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void doLog(String operationCode, List<Object> requestData, EOperationResult result, String resultDescription, String uuid) {
		if (enabler.isAuditLogEnabled()) {
			String principal = null;
			SecurityContext securityContext = SecurityContextHolder.getContext();
			Authentication authentication = securityContext.getAuthentication();
			if (authentication != null) {
				principal = (String) authentication.getName();
			}

			CAuditLog auditLog = new CAuditLog();
			auditLog.setOperationCode(operationCode);
			auditLog.setRequestData(auditLogSerializationService.serializeForAuditLog(requestData));
			auditLog.setUserIdentifier(principal);
			auditLog.setOperationResult(result);
			auditLog.setResultDescription(resultDescription);
			auditLog.setUuid(uuid);

			auditLogDao.create(auditLog);
		}
	}
}
