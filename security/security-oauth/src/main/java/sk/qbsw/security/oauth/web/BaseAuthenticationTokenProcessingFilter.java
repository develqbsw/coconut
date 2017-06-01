package sk.qbsw.security.oauth.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;

import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.oauth.service.AuthenticationTokenService;
import sk.qbsw.security.oauth.service.MasterTokenService;
import sk.qbsw.security.web.CHttpClientAddressRetriever;
import sk.qbsw.security.web.filter.MDCFilter;

/**
 * The abstract authentication token processing filter.
 *
 * @author Tomas Lauro
 * 
 * @version 1.13.1
 * @since 1.13.0
 */
public abstract class BaseAuthenticationTokenProcessingFilter extends GenericFilterBean
{
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseAuthenticationTokenProcessingFilter.class);

	/** The authentication manager. */
	private final AuthenticationManager authenticationManager;

	/** The master token service. */
	@Autowired
	private MasterTokenService masterTokenService;

	/** The authentication token service. */
	@Autowired
	private AuthenticationTokenService authenticationTokenService;

	/** The ip retriever. */
	private CHttpClientAddressRetriever ipRetriever = new CHttpClientAddressRetriever();

	/**
	 * Instantiates a new a authentication token processing filter.
	 *
	 * @param authManager the auth manager
	 */
	public BaseAuthenticationTokenProcessingFilter (AuthenticationManager authManager)
	{
		this.authenticationManager = authManager;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		MDCFilter.fillMDC(null);

		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final String token = getToken(httpRequest);
		final String ip = ipRetriever.getClientIpAddress(httpRequest);
		final String deviceId = getDeviceId(httpRequest);

		LOGGER.info("Received request with token {} from ip: {}.", token, ip);

		if (!StringUtils.isEmpty(token) && !StringUtils.isEmpty(deviceId))
		{
			User user = getUser(token, deviceId, ip);

			final PreAuthenticatedAuthenticationToken authenticationToken = new PreAuthenticatedAuthenticationToken(user, token, null);
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));

			try
			{
				final Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

				MDCFilter.fillMDC(authentication);

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			catch (final AuthenticationException e)
			{
				LOGGER.error("AuthenticationException was thrown when processing request with security token " + token + ". Combination was resolved to user.", e);
			}
		}

		chain.doFilter(request, response);
	}

	/**
	 * Choose token.
	 *
	 * @param token the token
	 * @param deviceId the ip
	 * @return the string
	 */
	private User getUser (String token, String deviceId, String ip)
	{
		try
		{
			User userBymasterToken = masterTokenService.getUserByMasterToken(token, deviceId, ip);
			User userByAuthenticationToken = authenticationTokenService.getUserByAuthenticationToken(token, deviceId, ip);

			if (userBymasterToken != null)
			{
				return userBymasterToken;
			}
			else if (userByAuthenticationToken != null)
			{
				return userByAuthenticationToken;
			}
			else
			{
				return null;
			}
		}
		catch (Exception e)
		{
			LOGGER.error("The exception in token processing filter", e);
			return null;
		}
	}

	/**
	 * Gets the token.
	 *
	 * @param request the request
	 * @return the token
	 */
	public abstract String getToken (HttpServletRequest request);

	/**
	 * Gets the device id.
	 *
	 * @param request the request
	 * @return the device id
	 */
	public abstract String getDeviceId (HttpServletRequest request);
}
