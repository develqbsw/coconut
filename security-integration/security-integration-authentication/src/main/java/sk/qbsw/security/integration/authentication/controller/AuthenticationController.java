package sk.qbsw.security.integration.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.client.model.response.EmptyResponseBody;
import sk.qbsw.security.api.authentication.client.AuthenticationHeaders;
import sk.qbsw.security.api.authentication.client.AuthenticationPaths;
import sk.qbsw.security.api.authentication.client.model.CSAccountData;
import sk.qbsw.security.api.authentication.client.model.request.AuthenticationRequestBody;
import sk.qbsw.security.api.authentication.client.model.request.InvalidateRequestBody;
import sk.qbsw.security.api.authentication.client.model.request.VerifyRequestBody;
import sk.qbsw.security.api.authentication.client.model.response.AuthenticationResponseBody;
import sk.qbsw.security.api.authentication.client.model.response.ReauthenticationResponseBody;
import sk.qbsw.security.integration.authentication.core.service.IntegrationAuthenticationService;
import sk.qbsw.security.integration.authentication.mapping.SecurityMapper;
import sk.qbsw.security.web.CHttpClientAddressRetriever;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

/**
 * The security controller.
 *
 * @author Tomas Lauro
 * @author Roman Farkaš
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping (value = AuthenticationPaths.BASE_PATH, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class AuthenticationController
{
	private final IntegrationAuthenticationService integrationAuthenticationService;

	private final SecurityMapper securityMapper;

	private final CHttpClientAddressRetriever ipAddressRetriever;

	@Autowired
	public AuthenticationController (IntegrationAuthenticationService integrationAuthenticationService, SecurityMapper securityMapper)
	{
		this.integrationAuthenticationService = integrationAuthenticationService;
		this.securityMapper = securityMapper;
		this.ipAddressRetriever = new CHttpClientAddressRetriever();
	}

	/**
	 * Authenticate.
	 *
	 * @param httpRequest the http request
	 * @param request the request
	 * @param deviceId the device id
	 * @return the authentication response
	 * @throws CBusinessException the c business exception
	 */
	@RequestMapping (value = AuthenticationPaths.SECURITY_AUTHENTICATE, method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public AuthenticationResponseBody authenticate (HttpServletRequest httpRequest, @NotNull @Validated @RequestBody AuthenticationRequestBody request, @NotNull @RequestHeader (value = AuthenticationHeaders.DEVICE_ID_REQUEST_HEADER) String deviceId) throws CBusinessException
	{
		String ip = ipAddressRetriever.getClientIpAddress(httpRequest);

		return securityMapper.mapToAuthenticationResponse(integrationAuthenticationService.authenticate(request.getLogin(), request.getPassword(), deviceId, ip));
	}

	/**
	 * Internal reauthenticate.
	 *
	 * @param httpRequest the http request
	 * @param deviceId the device id
	 * @param masterToken the master token
	 * @return the reauthentication response
	 * @throws CBusinessException the c business exception
	 */
	@RequestMapping (value = AuthenticationPaths.SECURITY_REAUTHENTICATE, method = RequestMethod.POST)
	public ReauthenticationResponseBody reauthenticate (HttpServletRequest httpRequest, @NotNull @RequestHeader (value = AuthenticationHeaders.DEVICE_ID_REQUEST_HEADER) String deviceId, @NotNull @RequestHeader (value = AuthenticationHeaders.TOKEN_REQUEST_HEADER) String masterToken) throws CBusinessException
	{
		String ip = ipAddressRetriever.getClientIpAddress(httpRequest);

		return securityMapper.mapToReauthenticationResponse(integrationAuthenticationService.reauthenticate(masterToken, deviceId, ip));
	}

	/**
	 * Invalidate.
	 *
	 * @param httpRequest the http request
	 * @param request the request
	 * @param deviceId the device id
	 * @return the empty response
	 * @throws CBusinessException the c business exception
	 */
	@RequestMapping (value = AuthenticationPaths.SECURITY_INVALIDATE, method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public EmptyResponseBody invalidate (HttpServletRequest httpRequest, @NotNull @RequestBody InvalidateRequestBody request, @NotNull @RequestHeader (value = AuthenticationHeaders.DEVICE_ID_REQUEST_HEADER) String deviceId) throws CBusinessException
	{
		String ip = ipAddressRetriever.getClientIpAddress(httpRequest);

		integrationAuthenticationService.invalidate(request.getMasterToken(), request.getAuthenticationToken(), deviceId, ip);

		return EmptyResponseBody.build();
	}

	/**
	 * Verify account data.
	 *
	 * @param request the request
	 * @return the cs account data
	 * @throws CBusinessException the c business exception
	 */
	@RequestMapping (value = AuthenticationPaths.SECURITY_VERIFY, method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public CSAccountData verify (@NotNull @Validated @RequestBody VerifyRequestBody request) throws CBusinessException
	{
		return securityMapper.mapUserToCSAccountData(integrationAuthenticationService.verify(request.getToken(), request.getDeviceId(), request.getIp()));
	}
}
