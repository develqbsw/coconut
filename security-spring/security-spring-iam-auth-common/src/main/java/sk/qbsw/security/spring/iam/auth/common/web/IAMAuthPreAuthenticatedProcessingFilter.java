package sk.qbsw.security.spring.iam.auth.common.web;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import sk.qbsw.security.spring.common.configuration.AuthHeaderConfiguration;

import javax.servlet.http.HttpServletRequest;

/**
 * The token processing filter intended to use with IAM authentication.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
public class IAMAuthPreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter
{
	protected final AuthHeaderConfiguration iamAuthHeaderConfiguration;

	/**
	 * Instantiates a new Firebase auth pre authenticated processing filter.
	 *
	 * @param authManager the auth manager
	 * @param iamAuthHeaderConfiguration the iam auth header configuration
	 */
	public IAMAuthPreAuthenticatedProcessingFilter (AuthenticationManager authManager, AuthHeaderConfiguration iamAuthHeaderConfiguration)
	{
		this.iamAuthHeaderConfiguration = iamAuthHeaderConfiguration;
		this.setAuthenticationManager(authManager);
	}

	@Override
	protected Object getPreAuthenticatedPrincipal (HttpServletRequest request)
	{
		return request.getHeader(iamAuthHeaderConfiguration.getRequestSecurityHeaderTokenName());
	}

	@Override
	protected Object getPreAuthenticatedCredentials (HttpServletRequest request)
	{
		return request.getHeader(iamAuthHeaderConfiguration.getRequestSecurityHeaderTokenName());
	}
}
