package sk.qbsw.security.spring.oauth.common.web;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import sk.qbsw.security.spring.base.web.BaseTokenProcessingFilter;
import sk.qbsw.security.spring.oauth.common.model.OAuthWebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * The abstract token processing filter intended to use directly with service layer.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.13.0
 */
public abstract class BaseOAuthTokenProcessingFilter extends BaseTokenProcessingFilter
{
	public BaseOAuthTokenProcessingFilter (AuthenticationManager authManager)
	{
		super(authManager);
	}

	@Override
	public Authentication createAuthenticationToken (String token, String deviceId, String ip, HttpServletRequest request)
	{
		final PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken = new PreAuthenticatedAuthenticationToken(token, token);
		preAuthenticatedAuthenticationToken.setDetails(new OAuthWebAuthenticationDetails(request, deviceId, ip));

		return preAuthenticatedAuthenticationToken;
	}
}