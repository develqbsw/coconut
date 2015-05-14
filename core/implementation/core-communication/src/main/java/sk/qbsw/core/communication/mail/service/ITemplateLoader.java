package sk.qbsw.core.communication.mail.service;

import java.io.InputStream;

/**
 * Loader for template file.
 *
 * @author Dalibor Rak
 * 
 * @version 1.6.0
 * @since 1.6.0
 */
public interface ITemplateLoader
{
	/**
	 * Sets the base path.
	 *
	 * @param path the new base path
	 */
	public void setBasePath (String path);

	/**
	 * Gets the template.
	 *
	 * @param classLoader the class loader
	 * @param templateName the template name
	 * @param locale the locale
	 * @return the template
	 */
	public InputStream getTemplate (Class<?> clas, String templateName, String locale);

	/**
	 * Gets the template.
	 *
	 * @param templateName the template name
	 * @param locale the locale
	 * @return the template
	 */
	public InputStream getTemplate (String templateName, String locale);

	/**
	 * Gets the full file name.
	 *
	 * @param templateName the template name
	 * @param locale the locale
	 * @return the full file name
	 */
	public String getFullFileName (String templateName, String locale);
}
