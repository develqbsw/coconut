package sk.qbsw.security.integration.authentication.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sk.qbsw.core.api.model.response.EmptyResponse;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.security.api.authentication.client.configuration.AuthenticationApiConfiguration;
import sk.qbsw.security.api.authentication.client.configuration.AuthenticationPathConfiguration;
import sk.qbsw.security.api.authentication.client.model.request.AuthenticationRequest;
import sk.qbsw.security.api.authentication.client.model.request.InvalidateRequest;
import sk.qbsw.security.api.authentication.client.model.response.AuthenticationResponse;
import sk.qbsw.security.api.authentication.client.model.response.ReauthenticationResponse;
import sk.qbsw.security.authentication.base.service.AuthenticationService;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.integration.authentication.mapping.SecurityMapper;
import sk.qbsw.security.oauth.service.AuthenticationTokenService;
import sk.qbsw.security.oauth.service.MasterTokenService;
import sk.qbsw.security.web.CHttpClientAddressRetriever;

/**
 * The security controller.
 * 
 * @author Tomas Lauro
 * @author Roman Farkaš
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping (produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class AuthenticationController
{
	/** The auth service. */
	@Autowired
	private AuthenticationService authenticationService;

	/** The master token service. */
	@Autowired
	private MasterTokenService masterTokenService;

	/** The authentication token service. */
	@Autowired
	private AuthenticationTokenService authenticationTokenService;

	/** The security mapper. */
	@Autowired
	private SecurityMapper securityMapper;

	/** The ip address retriever. */
	private CHttpClientAddressRetriever ipAddressRetriever = new CHttpClientAddressRetriever();

	/**
	 * Authenticate.
	 *
	 * @param httpRequest the http request
	 * @param request the request
	 * @param deviceId the device id
	 * @return the authentication response
	 * @throws CBusinessException the c business exception
	 */
	@RequestMapping (value = AuthenticationPathConfiguration.AUTHENTICATE, method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public AuthenticationResponse authenticate (HttpServletRequest httpRequest, @NotNull @Validated @RequestBody (required = true) AuthenticationRequest request, @NotNull @RequestHeader (value = AuthenticationApiConfiguration.DEVICE_ID_REQUEST_HEADER, required = true) String deviceId) throws CBusinessException
	{
		String ip = ipAddressRetriever.getClientIpAddress(httpRequest);
		User user = authenticationService.login(request.getLogin(), request.getPassword());

		if (user == null)
		{
			throw new CSecurityException(ECoreErrorResponse.USER_NOT_FOUND);
		}

		//generate tokens
		String masterToken = masterTokenService.generateMasterToken(user.getId(), deviceId, ip);
		String authenticationToken = authenticationTokenService.generateAuthenticationToken(user.getId(), masterToken, deviceId, ip);

		//create response
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
	@RequestMapping (value = AuthenticationPathConfiguration.REAUTHENTICATE, method = RequestMethod.POST)
	public ReauthenticationResponse reauthenticate (HttpServletRequest httpRequest, @NotNull @RequestHeader (value = AuthenticationApiConfiguration.DEVICE_ID_REQUEST_HEADER, required = true) String deviceId, @NotNull @RequestHeader (value = AuthenticationApiConfiguration.TOKEN_REQUEST_HEADER, required = true) String masterToken) throws CBusinessException
	{
		String ip = ipAddressRetriever.getClientIpAddress(httpRequest);
		User user = masterTokenService.getUserByMasterToken(masterToken, deviceId, ip);

		if (user == null)
		{
			throw new CSecurityException(ECoreErrorResponse.USER_NOT_FOUND);
		}

		String authenticationToken = authenticationTokenService.generateAuthenticationToken(user.getId(), masterToken, deviceId, ip);

		//set response
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
	@RequestMapping (value = AuthenticationPathConfiguration.INVALIDATE, method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public EmptyResponse invalidate (HttpServletRequest httpRequest, @NotNull @RequestBody (required = true) InvalidateRequest request, @NotNull @RequestHeader (value = AuthenticationApiConfiguration.DEVICE_ID_REQUEST_HEADER, required = true) String deviceId) throws CBusinessException
	{
		String ip = ipAddressRetriever.getClientIpAddress(httpRequest);
		User user = masterTokenService.getUserByMasterToken(request.getMasterToken(), deviceId, ip);

		if (user == null)
		{
			throw new CSecurityException(ECoreErrorResponse.USER_NOT_FOUND);
		}

		masterTokenService.revokeMasterToken(user.getId(), request.getMasterToken());
		authenticationTokenService.revokeAuthenticationToken(user.getId(), request.getAuthenticationToken());

		return EmptyResponse.create();
	}
}
