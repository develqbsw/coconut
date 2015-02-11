package sk.qbsw.core.security.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Filter checks the ip address from http request with ip address stored in session. If the ip addresses match the request is allowed otherwise the forbidden http code is returned.
 *
 * @author Tomas Lauro
 * 
 * @version 1.12.2
 * @since 1.12.2
 */
public class CIpFilter implements Filter
{
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CIpFilter.class);

	/** The Constant SESSION_IP_ATTRIBUTE. */
	public static final String SESSION_IP_ATTRIBUTE = "ip";

	/** The Constant HEADER_IP_ATTRIBUTE. */
	public static final String HEADER_IP_ATTRIBUTE = "X-Forwarded-For";

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init (FilterConfig filterConfig) throws ServletException
	{
		//nothing to do
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest httpRequest = ((HttpServletRequest) request);

		String sessionIpAddress = (String) httpRequest.getSession().getAttribute(SESSION_IP_ATTRIBUTE);
		String remoteAddress = httpRequest.getHeader(HEADER_IP_ATTRIBUTE);
		//String remoteAddress = httpRequest.getRemoteAddr();

		//handle ip addresses comparison
		if (sessionIpAddress != null && remoteAddress != null && remoteAddress.trim().equals(sessionIpAddress.trim()) == true)
		{
			LOGGER.debug("The ip address in session and ip address in request are equals - allow");
			chain.doFilter(httpRequest, response);
			return;
		}
		else if (sessionIpAddress == null || remoteAddress == null)
		{
			LOGGER.debug("The ip address in session or remote address is null - allow");
			chain.doFilter(httpRequest, response);
			return;
		}

		//default reject request
		if (response instanceof HttpServletResponse)
		{
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy ()
	{
		//nothing to do
	}

}
