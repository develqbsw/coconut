package sk.qbsw.core.logging.service;

import java.util.List;

/**
 * The Interface IAuditLogSerializationService.
 * 
 * @version 1.12.4
 * @since 1.11.0
 */
public interface IAuditLogSerializationService {

	/**
	 * serialize method parameters to appropriate type.
	 *
	 * @param pojoObject
	 *            the pojo object
	 * @return the string
	 */
	public String serializeForAuditLog(List<Object> pojoObject);
}
