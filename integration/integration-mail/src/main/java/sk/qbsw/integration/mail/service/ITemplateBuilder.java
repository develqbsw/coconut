package sk.qbsw.integration.mail.service;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Builder for template file.
 *
 * @author Tomas Lauro
 * 
 * @version 1.9.0
 * @since 1.9.0
 */
public interface ITemplateBuilder
{
	/**
	 * Builds the mail body from template.
	 *
	 * @param template the template
	 * @param params the parameters of template
	 * @return the string
	 * @throws UnsupportedEncodingException wrong encoding
	 */
	public String buildMailBody (InputStream template, Map<String, Object> params) throws UnsupportedEncodingException;
}
