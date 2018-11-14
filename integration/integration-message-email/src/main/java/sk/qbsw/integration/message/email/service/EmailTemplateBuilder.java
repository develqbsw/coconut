package sk.qbsw.integration.message.email.service;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * The builder for template file.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
public interface EmailTemplateBuilder
{
	/**
	 * Build body.
	 *
	 * @param template the template
	 * @param params the params
	 * @return the string
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	String buildBody (InputStream template, Map<String, Object> params) throws UnsupportedEncodingException;
}
