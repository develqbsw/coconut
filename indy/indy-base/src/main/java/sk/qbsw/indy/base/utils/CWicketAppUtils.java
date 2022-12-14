package sk.qbsw.indy.base.utils;

import javax.servlet.ServletContext;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;

/**
 * Wicket application utilities.
 *
 * @author Dalibor Rak
 * @version 1.3.0
 * @since 1.3.0
 */
public class CWicketAppUtils
{

	/**
	 * Gets app context name.
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


	/**
	 * Gets the base URL of the application.
	 *
	 * @return the base URL
	 */
	public static String getBaseURL ()
	{
		final Url url = Url.parse("/");
		String applicationUrl = RequestCycle.get().getUrlRenderer().renderFullUrl(url);
		return applicationUrl;
	}
}
