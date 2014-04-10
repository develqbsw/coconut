package sk.qbsw.core.logging.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.logging.model.domain.EOperationResult;


public interface IAuditLogService
{
	/**
	 * serialize method parameters to xml
	 * @param pojoObject
	 */
	@Transactional
	public void doLog(String operationCode, List<Object> requestData, EOperationResult result, String resultDescription);
}
