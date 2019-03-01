package sk.qbsw.security.spring.common.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import sk.qbsw.security.spring.common.model.RequestSecurityHeader;
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
 * @version 2.1.0
 * @since 1.18.0
 */
public abstract class TokenProcessingFilterBase<H extends RequestSecurityHeader>extends GenericFilterBean
{
	protected static final String REQUEST_SECURITY_HEADER_TOKEN_NAME = "token";

	protected final Logger LOCAL_LOGGER = LoggerFactory.getLogger(this.getClass());

	private final AuthenticationManager authenticationManager;

	public TokenProcessingFilterBase (AuthenticationManager authenticationManager)
	{
		this.authenticationManager = authenticationManager;
	}

	@Override
	public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		MDCFilter.fillMDC(null);

		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final H securityHeader = parseSecurityHeader(httpRequest);

		LOCAL_LOGGER.info("Received request with security data: {}.", securityHeader);

		if (validateSecurityHeader(securityHeader))
		{
			try
			{
				final Authentication authentication = this.authenticationManager.authenticate(createAuthenticationToken(securityHeader, httpRequest));

				MDCFilter.fillMDC(authentication);

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			catch (final Exception e)
			{
				handleExceptions(e, securityHeader);
			}
		}

		chain.doFilter(request, response);
	}

	protected void handleExceptions (Exception e, H securityHeader)
	{
		LOCAL_LOGGER.error("Exception was thrown when processing request with security data: {}.", securityHeader, e);
	}

	protected abstract H parseSecurityHeader (HttpServletRequest request);

	protected abstract boolean validateSecurityHeader (H securityHeader);

	public abstract Authentication createAuthenticationToken (H securityHeader, HttpServletRequest request);
}
