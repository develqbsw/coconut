package sk.qbsw.security.spring.oauth.common.web;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import sk.qbsw.security.spring.common.web.TokenProcessingFilterBase;
import sk.qbsw.security.spring.oauth.common.configuration.OAuthHeaderConfiguration;
import sk.qbsw.security.spring.oauth.common.model.OAuthRequestSecurityHeader;
import sk.qbsw.security.spring.oauth.common.model.OAuthWebAuthenticationDetails;
import sk.qbsw.security.web.CHttpClientAddressRetriever;

import javax.servlet.http.HttpServletRequest;

/**
 * The token processing filter intended to use directly with service layer.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 1.13.0
 */
public class OAuthTokenProcessingFilter extends TokenProcessingFilterBase<OAuthRequestSecurityHeader>
{
	private final CHttpClientAddressRetriever ipRetriever;

	/**
	 * Instantiates a new O auth token processing filter.
	 *
	 * @param authManager the auth manager
	 * @param oAuthHeaderConfiguration the o auth header configuration
	 */
	public OAuthTokenProcessingFilter (AuthenticationManager authManager, OAuthHeaderConfiguration oAuthHeaderConfiguration)
	{
		super(authManager, oAuthHeaderConfiguration);
		this.ipRetriever = new CHttpClientAddressRetriever();
	}

	@Override
	protected OAuthRequestSecurityHeader parseSecurityHeader (HttpServletRequest request)
	{
		return new OAuthRequestSecurityHeader(request.getHeader(authHeaderConfiguration.getRequestSecurityHeaderTokenName()), request.getHeader( ((OAuthHeaderConfiguration) authHeaderConfiguration).getRequestSecurityHeaderDeviceIdName()), ipRetriever.getClientIpAddress(request));
	}

	@Override
	protected boolean validateSecurityHeader (OAuthRequestSecurityHeader securityHeader)
	{
		return securityHeader != null && !StringUtils.isEmpty(securityHeader.getToken()) && !StringUtils.isEmpty(securityHeader.getDeviceId());
	}

	@Override
	public Authentication createAuthenticationToken (OAuthRequestSecurityHeader securityHeader, HttpServletRequest request)
	{
		final PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken = new PreAuthenticatedAuthenticationToken(securityHeader.getToken(), securityHeader.getToken());
		preAuthenticatedAuthenticationToken.setDetails(new OAuthWebAuthenticationDetails(request, securityHeader.getDeviceId(), securityHeader.getIp()));

		return preAuthenticatedAuthenticationToken;
	}
}
