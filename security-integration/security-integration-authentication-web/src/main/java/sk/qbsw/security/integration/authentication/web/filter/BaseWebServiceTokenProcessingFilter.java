package sk.qbsw.security.integration.authentication.web.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import sk.qbsw.security.authentication.spring.preauth.OAuthPreAuthenticatedWebAuthenticationDetails;
import sk.qbsw.security.web.filter.BaseTokenProcessingFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * The abstract token processing filter intended to use with web service integration.
 *
 * @author Tomas Lauro
 * @version 1.18.0
 * @since 1.18.0
 */
public abstract class BaseWebServiceTokenProcessingFilter extends BaseTokenProcessingFilter
{
	public BaseWebServiceTokenProcessingFilter (AuthenticationManager authenticationManager)
	{
		super(authenticationManager);
	}

	@Override
	public Authentication createAuthenticationToken (String token, String deviceId, String ip, HttpServletRequest request)
	{
		final PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken = new PreAuthenticatedAuthenticationToken(token, null);
		preAuthenticatedAuthenticationToken.setDetails(new OAuthPreAuthenticatedWebAuthenticationDetails(request, deviceId, ip));

		return preAuthenticatedAuthenticationToken;
	}
}
