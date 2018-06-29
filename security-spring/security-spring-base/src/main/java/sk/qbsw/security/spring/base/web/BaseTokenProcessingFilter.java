package sk.qbsw.security.spring.base.web;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import sk.qbsw.security.web.CHttpClientAddressRetriever;
import sk.qbsw.security.web.filter.MDCFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * The abstract authentication token processing filter.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.0
 */
public abstract class BaseTokenProcessingFilter extends GenericFilterBean
{
	protected final Logger LOCAL_LOGGER = LoggerFactory.getLogger(this.getClass());

	private final AuthenticationManager authenticationManager;

	private final CHttpClientAddressRetriever ipRetriever;

	/**
	 * Instantiates a new base authentication token processing filter.
	 *
	 * @param authenticationManager the auth manager
	 */
	public BaseTokenProcessingFilter (AuthenticationManager authenticationManager)
	{
		this.authenticationManager = authenticationManager;
		this.ipRetriever = new CHttpClientAddressRetriever();
	}

	@Override
	public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		MDCFilter.fillMDC(null);

		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final String token = parseToken(httpRequest);
		final String ip = ipRetriever.getClientIpAddress(httpRequest);
		final String deviceId = parseDeviceId(httpRequest);

		LOCAL_LOGGER.info("Received request with token {} from ip: {}.", token, ip);

		if (!StringUtils.isEmpty(token) && !StringUtils.isEmpty(deviceId))
		{
			try
			{
				final Authentication authentication = this.authenticationManager.authenticate(createAuthenticationToken(token, deviceId, ip, httpRequest));

				MDCFilter.fillMDC(authentication);

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			catch (final Exception e)
			{
				handleExceptions(e, token, deviceId);
			}
		}

		chain.doFilter(request, response);
	}

	/**
	 * Handle exceptions.
	 *
	 * @param e the e
	 * @param token the token
	 * @param deviceId the device id
	 */
	protected void handleExceptions (Exception e, String token, String deviceId)
	{
		LOCAL_LOGGER.error("Exception was thrown when processing request with security token {} and deviceId {}. Combination was not resolved to user.", token, deviceId, e);
	}

	/**
	 * Parse token string.
	 *
	 * @param request the request
	 * @return the string
	 */
	public abstract String parseToken (HttpServletRequest request);

	/**
	 * Parse device id string.
	 *
	 * @param request the request
	 * @return the string
	 */
	public abstract String parseDeviceId (HttpServletRequest request);

	/**
	 * Create authentication token authentication.
	 *
	 * @param token the token
	 * @param deviceId the device id
	 * @param ip the ip
	 * @param request the request
	 * @return the authentication
	 */
	public abstract Authentication createAuthenticationToken (String token, String deviceId, String ip, HttpServletRequest request);
}
