package sk.qbsw.security.rest.authentication.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.client.model.response.EmptyResponseBody;
import sk.qbsw.security.oauth.service.facade.OAuthServiceFacade;
import sk.qbsw.security.organization.simple.oauth.model.SimpleOrganizationAccountData;
import sk.qbsw.security.rest.authentication.api.mapper.SecurityMapper;
import sk.qbsw.security.rest.authentication.client.model.configuration.AuthenticationPaths;
import sk.qbsw.security.rest.authentication.client.model.request.AuthenticationRequestBody;
import sk.qbsw.security.rest.authentication.client.model.request.InvalidateRequestBody;
import sk.qbsw.security.rest.authentication.client.model.request.ReauthenticationRequestBody;
import sk.qbsw.security.rest.authentication.client.model.request.VerifyRequestBody;
import sk.qbsw.security.rest.authentication.client.model.response.AuthenticationResponseBody;
import sk.qbsw.security.rest.authentication.client.model.response.ReauthenticationResponseBody;
import sk.qbsw.security.rest.authentication.client.model.response.VerificationResponseBody;
import sk.qbsw.security.web.CHttpClientAddressRetriever;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

/**
 * The security controller.
 *
 * @author Tomas Lauro
 * @author Roman Farkaš
 * @version 1.19.0
 * @since 1.18.0
 */
@RestController
@RequestMapping (value = AuthenticationPaths.BASE_PATH, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class AuthenticationController
{
	private final OAuthServiceFacade<SimpleOrganizationAccountData> oAuthService;

	private final SecurityMapper securityMapper;

	private final CHttpClientAddressRetriever ipAddressRetriever;

	/**
	 * Instantiates a new Authentication controller.
	 *
	 * @param oAuthService the o auth service
	 * @param securityMapper the security authenticationTokenMapper
	 */
	@Autowired
	public AuthenticationController (OAuthServiceFacade<SimpleOrganizationAccountData> oAuthService, SecurityMapper securityMapper)
	{
		this.oAuthService = oAuthService;
		this.securityMapper = securityMapper;
		this.ipAddressRetriever = new CHttpClientAddressRetriever();
	}

	/**
	 * Authenticate.
	 *
	 * @param httpRequest the http request
	 * @param request the request
	 * @return the authentication response
	 * @throws CBusinessException the c business exception
	 */
	@RequestMapping (value = AuthenticationPaths.SECURITY_AUTHENTICATE, method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public AuthenticationResponseBody authenticate (HttpServletRequest httpRequest, @NotNull @Validated @RequestBody AuthenticationRequestBody request) throws CBusinessException
	{
		String ip = ipAddressRetriever.getClientIpAddress(httpRequest);

		return securityMapper.mapToAuthenticationResponse(oAuthService.authenticate(request.getLogin(), request.getPassword(), request.getDeviceId(), ip));
	}

	/**
	 * Reauthenticate reauthentication response body.
	 *
	 * @param httpRequest the http request
	 * @param request the request
	 * @return the reauthentication response body
	 * @throws CBusinessException the c business exception
	 */
	@RequestMapping (value = AuthenticationPaths.SECURITY_REAUTHENTICATE, method = RequestMethod.POST)
	public ReauthenticationResponseBody reauthenticate (HttpServletRequest httpRequest, @NotNull @Validated @RequestBody ReauthenticationRequestBody request) throws CBusinessException
	{
		String ip = ipAddressRetriever.getClientIpAddress(httpRequest);

		return securityMapper.mapToReauthenticationResponse(oAuthService.reauthenticate(request.getMasterToken(), request.getDeviceId(), ip));
	}

	/**
	 * Invalidate empty response body.
	 *
	 * @param httpRequest the http request
	 * @param request the request
	 * @return the empty response body
	 * @throws CBusinessException the c business exception
	 */
	@RequestMapping (value = AuthenticationPaths.SECURITY_INVALIDATE, method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public EmptyResponseBody invalidate (HttpServletRequest httpRequest, @NotNull @RequestBody InvalidateRequestBody request) throws CBusinessException
	{
		String ip = ipAddressRetriever.getClientIpAddress(httpRequest);

		oAuthService.invalidate(request.getMasterToken(), request.getAuthenticationToken(), request.getDeviceId(), ip);

		return EmptyResponseBody.build();
	}

	/**
	 * Verify verification response body.
	 *
	 * @param request the request
	 * @return the verification response body
	 * @throws CBusinessException the c business exception
	 */
	@RequestMapping (value = AuthenticationPaths.SECURITY_VERIFY, method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public VerificationResponseBody verify (@NotNull @Validated @RequestBody VerifyRequestBody request) throws CBusinessException
	{
		return securityMapper.mapToVerificationResponse(oAuthService.verify(request.getToken(), request.getDeviceId(), request.getIp()));
	}
}
