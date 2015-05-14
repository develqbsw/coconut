package sk.qbsw.core.security.oauth.web;

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
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;

import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.oauth.service.ISecurityTokenService;
import sk.qbsw.core.security.web.CHttpClientAddressRetriever;
import sk.qbsw.core.security.web.filter.CMDCFilter;

/**
 * The Class AAuthenticationTokenProcessingFilter.
 *
 * @author podmajersky
 * @version 1.3.0
 * @since 1.1.0
 */
public abstract class AAuthenticationTokenProcessingFilter extends GenericFilterBean {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AAuthenticationTokenProcessingFilter.class);

	/** The authentication manager. */
	private final AuthenticationManager authenticationManager;

	/** The p. */
	@Autowired
	private AuthenticationEntryPoint p;

	/** The token service. */
	@Autowired
	private ISecurityTokenService tokenService;

	/** The ip retriever. */
	private CHttpClientAddressRetriever ipRetriever = new CHttpClientAddressRetriever();

	/**
	 * Instantiates a new a authentication token processing filter.
	 *
	 * @param authManager the auth manager
	 */
	public AAuthenticationTokenProcessingFilter(AuthenticationManager authManager) {
		this.authenticationManager = authManager;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		CMDCFilter.fillMDC(null);

		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final String token = getToken(httpRequest);
		final String ip = ipRetriever.getClientIpAddress(httpRequest);

		LOGGER.info("Received request with token {} from ip: {}.", token, ip);

		if (!StringUtils.isEmpty(token)) {
			CUser user = this.tokenService.findByToken(token, ip);
			if (user != null) {
				final String login = user.getLogin();

				if (!StringUtils.isEmpty(login)) {

					LOGGER.info("Combination was resolved to user login {}. Going to preauthenticate user.", login);

					final PreAuthenticatedAuthenticationToken authenticationToken = new PreAuthenticatedAuthenticationToken(login, "N/A", null);

					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));

					try {
						final Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

						CMDCFilter.fillMDC(authentication);

						SecurityContextHolder.getContext().setAuthentication(authentication);
					} catch (final AuthenticationException e) {
						LOGGER.error("AuthenticationException was thrown when processing request with security token " + token + ". Combination was resolved to user login " + login + ".", e);
					}
				}
			}
		}

		chain.doFilter(request, response);
	}

	/**
	 * Gets the token.
	 *
	 * @param request the request
	 * @return the token
	 */
	public abstract String getToken(HttpServletRequest request);
}
