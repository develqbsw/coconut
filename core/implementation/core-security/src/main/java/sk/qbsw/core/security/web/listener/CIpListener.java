package sk.qbsw.core.security.web.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listener read ip from http header and set it to session.
 *
 * @author Tomas Lauro
 * 
 * @version 1.12.2
 * @since 1.12.2
 */
public class CIpListener implements ServletRequestListener
{
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CIpListener.class);

	/** The Constant SESSION_IP_ATTRIBUTE. */
	public static final String SESSION_IP_ATTRIBUTE = "ip";

	/** The Constant HEADER_IP_ATTRIBUTE. */
	public static final String HEADER_IP_ATTRIBUTE = "X-Forwarded-For";

	/* (non-Javadoc)
	 * @see javax.servlet.ServletRequestListener#requestInitialized(javax.servlet.ServletRequestEvent)
	 */
	@Override
	public void requestInitialized (ServletRequestEvent sre)
	{
		if (sre != null)
		{
			HttpServletRequest httpRequest = (HttpServletRequest) sre.getServletRequest();

			//String remoteAddress = httpRequest.getRemoteAddr();
			String remoteAddress = httpRequest.getHeader(HEADER_IP_ATTRIBUTE);
			HttpSession session = httpRequest.getSession();

			if (session != null && remoteAddress != null)
			{
				if (session.getAttribute(SESSION_IP_ATTRIBUTE) == null)
				{
					session.setAttribute(SESSION_IP_ATTRIBUTE, remoteAddress);
					LOGGER.debug("Remote IP {} was set to session", remoteAddress);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletRequestListener#requestDestroyed(javax.servlet.ServletRequestEvent)
	 */
	@Override
	public void requestDestroyed (ServletRequestEvent sre)
	{
		//nothing to do
	}
}
