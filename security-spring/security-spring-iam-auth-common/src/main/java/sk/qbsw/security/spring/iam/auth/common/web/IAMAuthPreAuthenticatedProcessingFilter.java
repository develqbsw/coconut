package sk.qbsw.security.spring.iam.auth.common.web;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * The token processing filter intended to use with IAM authentication.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class IAMAuthPreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter
{
	/**
	 * The constant REQUEST_SECURITY_HEADER_TOKEN_NAME.
	 */
	protected static final String REQUEST_SECURITY_HEADER_TOKEN_NAME = "authorization-iam";

	/**
	 * Instantiates a new Firebase auth pre authenticated processing filter.
	 *
	 * @param authManager the auth manager
	 */
	public IAMAuthPreAuthenticatedProcessingFilter (AuthenticationManager authManager)
	{
		this.setAuthenticationManager(authManager);
	}

	@Override
	protected Object getPreAuthenticatedPrincipal (HttpServletRequest request)
	{
		return request.getHeader(REQUEST_SECURITY_HEADER_TOKEN_NAME);
	}

	@Override
	protected Object getPreAuthenticatedCredentials (HttpServletRequest request)
	{
		return request.getHeader(REQUEST_SECURITY_HEADER_TOKEN_NAME);
	}
}
