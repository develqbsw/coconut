package sk.qbsw.core.logging.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import sk.qbsw.core.logging.service.IAuditLogSerializationService;

import com.thoughtworks.xstream.XStream;

/**
 * Service xml serialziation
 *
 * @author Michal Lacko
 * 
 * @version 1.8.0
 * @since 1.8.0
 */
@Service
public class CXMLAuditLogSerializationService implements IAuditLogSerializationService
{

	public String serializeForAuditLog (List<Object> pojoObject)
	{
		CParametersPojoList<Object> pojoListToSerialize = new CParametersPojoList<Object>();
		pojoListToSerialize.setList(pojoObject);

		XStream xstream = new XStream();
		xstream.alias("parameter", Object.class);
		xstream.alias("parameters", CParametersPojoList.class);
		xstream.addImplicitCollection(CParametersPojoList.class, "list");
		
		return xstream.toXML(pojoListToSerialize);
	}
}
