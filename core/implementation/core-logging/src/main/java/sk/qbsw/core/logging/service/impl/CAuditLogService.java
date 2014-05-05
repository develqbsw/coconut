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
import sk.qbsw.core.logging.service.IAuditLogSerializationService;
import sk.qbsw.core.logging.service.IAuditLogService;

/**
 * Service xml serialziation
 *
 * @author Michal Lacko
 * 
 * @version 1.8.0
 * @since 1.8.0
 */
@Service
public class CAuditLogService implements IAuditLogService
{

	@Autowired
	private IAuditLogSerializationService auditLogSerializationService;
	
	@Autowired
	private IAuditLogDao auditLogDao;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void doLog (String operationCode, List<Object> requestData, EOperationResult result, String resultDescription)
	{
		String principal = null;
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		if (authentication != null)
		{
			principal = (String) authentication.getPrincipal();
		}

		CAuditLog auditLog = new CAuditLog();
		auditLog.setOperationCode(operationCode);
		auditLog.setRequestData(auditLogSerializationService.toXml(requestData));
		auditLog.setUserIdentifier(principal);
		auditLog.setOperationResult(result);
		auditLog.setResultDescription(resultDescription);
		
		auditLogDao.save(auditLog);
	}

}
