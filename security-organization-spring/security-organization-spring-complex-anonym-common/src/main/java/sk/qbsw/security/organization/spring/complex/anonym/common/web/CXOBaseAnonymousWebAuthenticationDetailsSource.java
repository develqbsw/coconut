package sk.qbsw.security.organization.spring.complex.anonym.common.web;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import sk.qbsw.security.organization.spring.complex.anonym.common.model.CXOAnonymousWebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * The base anonymous web authentication details source.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 1.18.4
 */
public abstract class CXOBaseAnonymousWebAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, CXOAnonymousWebAuthenticationDetails>
{
	public CXOAnonymousWebAuthenticationDetails buildDetails (HttpServletRequest context)
	{
		return new CXOAnonymousWebAuthenticationDetails(context, parseOrganizationCode(context));
	}

	/**
	 * Parse organization code string.
	 *
	 * @param request the request
	 * @return the string
	 */
	protected abstract String parseOrganizationCode (HttpServletRequest request);
}
