package sk.qbsw.core.logging.service;

import java.util.List;

import sk.qbsw.core.logging.model.domain.EOperationResult;


public interface IAuditLogService
{
	/**
	 * serialize method parameters to xml
	 * @param pojoObject
	 */
	public void doLog (String operationCode, List<Object> requestData, EOperationResult result, String resultDescription);

	/**
	 * serialize method parameters to xml
	 * @param pojoObject
	 */
	public void doLog (String operationCode, List<Object> requestData, EOperationResult result, String resultDescription, String uuid);
}
