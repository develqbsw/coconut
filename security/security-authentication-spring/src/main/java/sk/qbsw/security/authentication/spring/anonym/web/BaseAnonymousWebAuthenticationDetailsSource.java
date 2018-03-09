package sk.qbsw.security.authentication.spring.anonym.web;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import sk.qbsw.security.authentication.spring.anonym.model.AnonymousWebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * The base anonymous web authentication details source.
 *
 * @author Tomas Lauro
 * @version 1.18.4
 * @since 1.18.4
 */
public abstract class BaseAnonymousWebAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, AnonymousWebAuthenticationDetails>
{
	public AnonymousWebAuthenticationDetails buildDetails (HttpServletRequest context)
	{
		return new AnonymousWebAuthenticationDetails(context, parseOrganizationCode(context));
	}

	/**
	 * Parse organization code string.
	 *
	 * @param request the request
	 * @return the string
	 */
	protected abstract String parseOrganizationCode (HttpServletRequest request);
}
