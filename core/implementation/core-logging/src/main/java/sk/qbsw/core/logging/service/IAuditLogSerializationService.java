package sk.qbsw.core.logging.service;

import java.util.List;


public interface IAuditLogSerializationService
{
	/**
	 * serialize method parameters to xml
	 * @param pojoObject
	 */
	public String toXml(List<Object> pojoObject);
}
