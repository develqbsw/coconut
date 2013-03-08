package sk.qbsw.indy.base.utils;

import org.apache.wicket.protocol.http.WebApplication;
import javax.servlet.ServletContext;

/**
 * Wicket application utilities
 * 
 * @author Dalibor Rak
 * @version 1.3.0
 * @since 1.3.0
 */
public class CWicketAppUtils
{
	/**
	 * Gets app context
	 * 
	 * @return name of the context or empty string
	 */
	public static String getRootContext ()
	{

		String rootContext = "";

		WebApplication webApplication = WebApplication.get();
		if (webApplication != null)
		{
			ServletContext servletContext = webApplication.getServletContext();
			if (servletContext != null)
			{
				rootContext = servletContext.getServletContextName();
			}
			else
			{
				// do nothing
			}
		}
		else
		{
			// do nothing
		}

		return rootContext;

	}
}
