package sk.qbsw.core.logging.service;

import java.util.List;

import sk.qbsw.core.logging.model.domain.EOperationResult;

/**
 * The Interface IAuditLogService.
 */
public interface IAuditLogService {

	/**
	 * serialize method parameters to xml.
	 *
	 * @param operationCode
	 *            the operation code
	 * @param requestData
	 *            the request data
	 * @param result
	 *            the result
	 * @param resultDescription
	 *            the result description
	 */
	public void doLog(String operationCode, List<Object> requestData, EOperationResult result, String resultDescription);


	/**
	 * serialize method parameters to xml
	 * @param pojoObject
	 */
	public void doLog (String operationCode, List<Object> requestData, EOperationResult result, String resultDescription, String uuid);
}
