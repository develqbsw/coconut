package sk.qbsw.core.communication.mail.service;

import java.io.File;
import java.io.InputStream;

import org.springframework.stereotype.Component;

// TODO: Auto-generated Javadoc
/**
 * The Class CTemplateBasicLoader.
 */
@Component ("TemplateBasicLoader")
public class CTemplateBasicLoader implements ITemplateLoader
{

	/** The base path. */
	private String basePath;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.communication.mail.service.ITemplateLoader#setBasePath(java.lang.String)
	 */
	@Override
	public void setBasePath (String path)
	{
		this.basePath = path;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.communication.mail.service.ITemplateLoader#getTemplate(java.lang.ClassLoader, java.lang.String, java.lang.String)
	 */
	@Override
	public InputStream getTemplate (Class<?> clas, String templateName, String locale)
	{
		return clas.getResourceAsStream(getFullFileName(templateName, locale));
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.communication.mail.service.ITemplateLoader#getTemplate(java.lang.String, java.lang.String)
	 */
	@Override
	public InputStream getTemplate (String templateName, String locale)
	{
		return getTemplate(this.getClass(), templateName, locale);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.communication.mail.service.ITemplateLoader#getFullFileName(java.lang.String, java.lang.String)
	 */
	public String getFullFileName (String templateName, String locale)
	{
		String retVal = basePath + File.separator + templateName + "_" + locale + ".vm";

		return retVal;
	}

}
