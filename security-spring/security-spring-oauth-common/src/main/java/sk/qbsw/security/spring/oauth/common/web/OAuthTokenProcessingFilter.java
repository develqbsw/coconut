package sk.qbsw.security.spring.oauth.common.web;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import sk.qbsw.security.spring.oauth.common.model.OAuthRequestSecurityHeader;
import sk.qbsw.security.spring.base.web.TokenProcessingFilterBase;
import sk.qbsw.security.spring.oauth.common.model.OAuthWebAuthenticationDetails;
import sk.qbsw.security.web.CHttpClientAddressRetriever;

import javax.servlet.http.HttpServletRequest;

/**
 * The token processing filter intended to use directly with service layer.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.13.0
 */
public class OAuthTokenProcessingFilter extends TokenProcessingFilterBase<OAuthRequestSecurityHeader>
{
	/**
	 * The constant REQUEST_SECURITY_HEADER_DEVICE_ID_NAME.
	 */
	protected static final String REQUEST_SECURITY_HEADER_DEVICE_ID_NAME = "device-id";

	private final CHttpClientAddressRetriever ipRetriever;

	/**
	 * Instantiates a new O auth token processing filter.
	 *
	 * @param authManager the auth manager
	 */
	public OAuthTokenProcessingFilter (AuthenticationManager authManager)
	{
		super(authManager);
		this.ipRetriever = new CHttpClientAddressRetriever();
	}

	@Override
	protected OAuthRequestSecurityHeader parseSecurityHeader (HttpServletRequest request)
	{
		return new OAuthRequestSecurityHeader(request.getHeader(REQUEST_SECURITY_HEADER_TOKEN_NAME), request.getHeader(REQUEST_SECURITY_HEADER_DEVICE_ID_NAME), ipRetriever.getClientIpAddress(request));
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
