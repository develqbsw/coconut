package sk.qbsw.sgwt.winnetou.server.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * FIlter for setting cache control on resources
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CResponseChangeFilter implements Filter
{
	/**
	 * expiration date (filter parameter)
	 */
	private String expires = null;
	/**
	 * last modification date (filter parameter)
	 */
	private String lastModified = null;

	private String maxAge = null;

	public void destroy()
	{
	}

	/**
	 * Adds cache control to response
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.setHeader("Cache-Control", "public");

		if (lastModified != null)
		{
			resp.setHeader("Last-Modified", lastModified);
		}
		if (expires != null)
		{
			resp.setHeader("Expires", expires);
		}
		if (maxAge != null)
		{
			resp.setHeader("Max-Age", maxAge);
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException
	{
		this.expires = arg0.getInitParameter("expires");
		this.lastModified = arg0.getInitParameter("lastModified");
		this.maxAge = arg0.getInitParameter("maxAge");
	}
}
