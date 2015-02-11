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

import sk.qbsw.core.security.web.CHttpClientAddressRetriever;
import sk.qbsw.core.security.web.listener.CSessionIpAppenderListener;

/**
 * Filter checks the ip address from http request with ip address stored in session. If the ip addresses match the request is allowed otherwise the forbidden http code is returned.
 *
 * @author Tomas Lauro
 * @author Dalibor Rak
 * 
 * @version 1.12.2
 * @since 1.12.2
 */
public class CIpValidationFilter implements Filter
{

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CIpValidationFilter.class);

	/** The address retriever. */
	private CHttpClientAddressRetriever addressRetriever = new CHttpClientAddressRetriever();

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		//nothing to do
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest httpRequest = ((HttpServletRequest) request);

		String clientSessionAddress = (String) httpRequest.getSession().getAttribute(CSessionIpAppenderListener.SESSION_IP_ATTRIBUTE);
		String clientRequestAddress = addressRetriever.getClientIpAddress(httpRequest);

		//handle ip addresses comparison
		if (clientSessionAddress != null && clientRequestAddress != null && clientRequestAddress.trim().equals(clientSessionAddress.trim()) == true)
		{
			LOGGER.debug("The ip address in session and ip address in request are equals - allow");
			chain.doFilter(httpRequest, response);
			return;
		}
		else if (clientSessionAddress == null || clientRequestAddress == null)
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
	public void destroy()
	{
		//nothing to do
	}
}
