package sk.qbsw.security.integration.authentication.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.client.model.response.EmptyResponseBody;
import sk.qbsw.security.api.authentication.client.AuthenticationHeaders;
import sk.qbsw.security.api.authentication.client.AuthenticationPaths;
import sk.qbsw.security.api.authentication.client.model.CSAccountData;
import sk.qbsw.security.api.authentication.client.model.request.AuthenticationRequestBody;
import sk.qbsw.security.api.authentication.client.model.request.InvalidateRequestBody;
import sk.qbsw.security.api.authentication.client.model.request.VerifyRequestBody;
import sk.qbsw.security.api.authentication.client.model.response.AuthenticationResponseBody;
import sk.qbsw.security.api.authentication.client.model.response.ReauthenticationResponseBody;
import sk.qbsw.security.authentication.base.service.AuthenticationService;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.integration.authentication.exception.AuthenticationApiException;
import sk.qbsw.security.integration.authentication.mapping.SecurityMapper;
import sk.qbsw.security.oauth.service.AuthenticationTokenService;
import sk.qbsw.security.oauth.service.MasterTokenService;
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
@RequestMapping (value = "${custom}" + AuthenticationPaths.BASE_PATH, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class AuthenticationController
{
	private final AuthenticationService authenticationService;

	private final MasterTokenService masterTokenService;

	private final AuthenticationTokenService authenticationTokenService;

	private final SecurityMapper securityMapper;

	private final CHttpClientAddressRetriever ipAddressRetriever;

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

	/**
	 * Instantiates a new Authentication controller.
	 *
	 * @param authenticationService the authentication service
	 * @param masterTokenService the master token service
	 * @param authenticationTokenService the authentication token service
	 * @param securityMapper the security mapper
	 */
	@Autowired
	public AuthenticationController (AuthenticationService authenticationService, MasterTokenService masterTokenService, AuthenticationTokenService authenticationTokenService, SecurityMapper securityMapper)
	{
		this.authenticationService = authenticationService;
		this.masterTokenService = masterTokenService;
		this.authenticationTokenService = authenticationTokenService;
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
	@RequestMapping (value = AuthenticationPaths.AUTHENTICATE, method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public AuthenticationResponseBody authenticate (HttpServletRequest httpRequest, @NotNull @Validated @RequestBody AuthenticationRequestBody request, @NotNull @RequestHeader (value = AuthenticationHeaders.DEVICE_ID_REQUEST_HEADER) String deviceId) throws CBusinessException
	{
		String ip = ipAddressRetriever.getClientIpAddress(httpRequest);
		User user = authenticationService.login(request.getLogin(), request.getPassword());

		if (user == null)
		{
			throw new CSecurityException(ECoreErrorResponse.USER_NOT_FOUND);
		}

		// generate tokens
		String masterToken = masterTokenService.generateMasterToken(user.getId(), deviceId, ip);
		String authenticationToken = authenticationTokenService.generateAuthenticationToken(user.getId(), masterToken, deviceId, ip);

		// create response
		return securityMapper.mapToAuthenticationResponse(masterToken, authenticationToken, user);
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
	@RequestMapping (value = AuthenticationPaths.REAUTHENTICATE, method = RequestMethod.POST)
	public ReauthenticationResponseBody reauthenticate (HttpServletRequest httpRequest, @NotNull @RequestHeader (value = AuthenticationHeaders.DEVICE_ID_REQUEST_HEADER) String deviceId, @NotNull @RequestHeader (value = AuthenticationHeaders.TOKEN_REQUEST_HEADER) String masterToken) throws CBusinessException
	{
		String ip = ipAddressRetriever.getClientIpAddress(httpRequest);
		User user = masterTokenService.getUserByMasterToken(masterToken, deviceId, ip);

		if (user == null)
		{
			throw new CSecurityException(ECoreErrorResponse.USER_NOT_FOUND);
		}

		String authenticationToken = authenticationTokenService.generateAuthenticationToken(user.getId(), masterToken, deviceId, ip);

		// set response
		return securityMapper.mapToReauthenticationResponse(authenticationToken);
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
	@RequestMapping (value = AuthenticationPaths.INVALIDATE, method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public EmptyResponseBody invalidate (HttpServletRequest httpRequest, @NotNull @RequestBody InvalidateRequestBody request, @NotNull @RequestHeader (value = AuthenticationHeaders.DEVICE_ID_REQUEST_HEADER) String deviceId) throws CBusinessException
	{
		String ip = ipAddressRetriever.getClientIpAddress(httpRequest);
		User user = masterTokenService.getUserByMasterToken(request.getMasterToken(), deviceId, ip);

		if (user == null)
		{
			throw new CSecurityException(ECoreErrorResponse.USER_NOT_FOUND);
		}

		masterTokenService.revokeMasterToken(user.getId(), request.getMasterToken());
		authenticationTokenService.revokeAuthenticationToken(user.getId(), request.getAuthenticationToken());

		return EmptyResponseBody.build();
	}

	/**
	 * Verify account data.
	 *
	 * @param request the request
	 * @return the cs account data
	 * @throws CBusinessException the c business exception
	 */
	@RequestMapping (value = AuthenticationPaths.VERIFY, method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public CSAccountData verify (@NotNull @Validated @RequestBody VerifyRequestBody request) throws CBusinessException
	{
		try
		{
			User userByMasterToken = masterTokenService.getUserByMasterToken(request.getToken(), request.getDeviceId(), request.getDeviceId());
			User userByAuthenticationToken = authenticationTokenService.getUserByAuthenticationToken(request.getToken(), request.getDeviceId(), request.getIp());

			if (userByMasterToken != null)
			{
				return securityMapper.mapUserToCSAccountData(userByMasterToken);
			}
			else if (userByAuthenticationToken != null)
			{
				return securityMapper.mapUserToCSAccountData(userByAuthenticationToken);
			}
			else
			{
				return null;
			}
		}
		catch (Exception e)
		{
			LOGGER.error("The exception in token verification process");
			throw new AuthenticationApiException("The exception in token verification process", e);
		}
	}
}
