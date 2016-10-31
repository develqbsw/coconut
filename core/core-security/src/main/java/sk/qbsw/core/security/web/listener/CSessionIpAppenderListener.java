package sk.qbsw.core.security.web.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sk.qbsw.core.security.web.CHttpClientAddressRetriever;

/**
 * Listener read ip from http header and set it to session.
 *
 * @author Tomas Lauro
 * @author Dalibor Rak
 * 
 * @version 1.12.2
 * @since 1.12.2
 */
public class CSessionIpAppenderListener implements ServletRequestListener
{

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CSessionIpAppenderListener.class);

	/** The Constant SESSION_IP_ATTRIBUTE. */
	public static final String SESSION_IP_ATTRIBUTE = "SESSION_IP_ATTRIBUTE";

	/** The address retriever. */
	private CHttpClientAddressRetriever addressRetriever = new CHttpClientAddressRetriever();

	/* (non-Javadoc)
	 * @see javax.servlet.ServletRequestListener#requestInitialized(javax.servlet.ServletRequestEvent)
	 */
	@Override
	public void requestInitialized(ServletRequestEvent sre)
	{
		if (sre != null)
		{
			HttpServletRequest httpRequest = (HttpServletRequest) sre.getServletRequest();
			String clientAddress = addressRetriever.getClientIpAddress(httpRequest);

			HttpSession session = httpRequest.getSession();
			if (session != null && clientAddress != null)
			{
				if (session.getAttribute(SESSION_IP_ATTRIBUTE) == null)
				{
					session.setAttribute(SESSION_IP_ATTRIBUTE, clientAddress);
					LOGGER.debug("Remote IP {} was set to session", clientAddress);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletRequestListener#requestDestroyed(javax.servlet.ServletRequestEvent)
	 */
	@Override
	public void requestDestroyed(ServletRequestEvent sre)
	{
		//nothing to do
	}
}
