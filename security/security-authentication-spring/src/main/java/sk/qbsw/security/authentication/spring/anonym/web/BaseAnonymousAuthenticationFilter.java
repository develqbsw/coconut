package sk.qbsw.security.authentication.spring.anonym.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import sk.qbsw.security.authentication.spring.anonym.model.AnonymousAuthenticationToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * The base anonymous authentication filter.
 *
 * @author Tomas Lauro
 * @version 1.18.5
 * @since 1.18.4
 */
public abstract class BaseAnonymousAuthenticationFilter extends AnonymousAuthenticationFilter
{
	protected static final Logger LOGGER = LoggerFactory.getLogger(BaseAnonymousAuthenticationFilter.class);

	private static final String DEFAULT_KEY = "anonymousKey";

	private static final String DEFAULT_PRINCIPAL = "anonymousUser";

	private static final String DEFAULT_ROLE = DEFAULT_PRINCIPAL;

	private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource;

	public BaseAnonymousAuthenticationFilter (AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource)
	{
		super(DEFAULT_KEY, DEFAULT_PRINCIPAL, AuthorityUtils.createAuthorityList(DEFAULT_ROLE));
		this.authenticationDetailsSource = authenticationDetailsSource;
	}

	public BaseAnonymousAuthenticationFilter (Object principal, AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource)
	{
		super(DEFAULT_KEY, principal, AuthorityUtils.createAuthorityList(DEFAULT_ROLE));
		this.authenticationDetailsSource = authenticationDetailsSource;
	}

	@Override
	public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		if (SecurityContextHolder.getContext().getAuthentication() == null)
		{
			final HttpServletRequest httpRequest = (HttpServletRequest) request;
			final String organizationCode = parseOrganizationCode(httpRequest); // can be null

			LOGGER.info("Received anonymous request with organization code {}.", organizationCode);

			SecurityContextHolder.getContext().setAuthentication(createAuthentication((HttpServletRequest) request));
			if (LOGGER.isDebugEnabled())
			{
				LOGGER.debug("Populated SecurityContextHolder with anonymous token: {}", SecurityContextHolder.getContext().getAuthentication());
			}
		}
		else
		{
			if (LOGGER.isDebugEnabled())
			{
				LOGGER.debug("SecurityContextHolder not populated with anonymous token, as it already contained: {}", SecurityContextHolder.getContext().getAuthentication());
			}
		}

		chain.doFilter(request, response);
	}

	@Override
	protected Authentication createAuthentication (HttpServletRequest request)
	{
		AnonymousAuthenticationToken auth = new AnonymousAuthenticationToken(DEFAULT_KEY, super.getPrincipal(), AuthorityUtils.createAuthorityList(DEFAULT_ROLE), false);
		auth.setDetails(authenticationDetailsSource.buildDetails(request));

		return auth;
	}

	protected abstract String parseOrganizationCode (HttpServletRequest request);
}
