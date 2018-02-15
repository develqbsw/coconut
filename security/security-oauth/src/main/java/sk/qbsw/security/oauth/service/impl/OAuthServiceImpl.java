package sk.qbsw.security.oauth.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.security.base.exception.AccessDeniedException;
import sk.qbsw.core.security.base.exception.AuthenticationException;
import sk.qbsw.security.authentication.base.service.AuthenticationService;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.oauth.model.*;
import sk.qbsw.security.oauth.service.AuthenticationTokenService;
import sk.qbsw.security.oauth.service.MasterTokenService;
import sk.qbsw.security.oauth.service.OAuthService;

import java.util.HashMap;
import java.util.Map;

/**
 * The oauth service implementation.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.18.1
 */
public class OAuthServiceImpl implements OAuthService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(OAuthServiceImpl.class);

	private final MasterTokenService masterTokenService;

	private final AuthenticationTokenService authenticationTokenService;

	private final AuthenticationService authenticationService;

	public OAuthServiceImpl (MasterTokenService masterTokenService, AuthenticationTokenService authenticationTokenService, AuthenticationService authenticationService)
	{
		this.masterTokenService = masterTokenService;
		this.authenticationTokenService = authenticationTokenService;
		this.authenticationService = authenticationService;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public AuthenticationData authenticate (String login, String password, String deviceId, String ip) throws CBusinessException
	{
		User user = authenticationService.login(login, password);

		if (user == null)
		{
			throw new CSecurityException(ECoreErrorResponse.USER_NOT_FOUND);
		}

		// generate tokens
		GeneratedTokenData masterTokenData = masterTokenService.generateMasterToken(user.getId(), deviceId, ip);
		GeneratedTokenData authenticationTokenData = authenticationTokenService.generateAuthenticationToken(user.getId(), masterTokenData.getGeneratedToken(), deviceId, ip);

		// create response
		return new AuthenticationData(masterTokenData, authenticationTokenData, user, createAdditionalInformation(user.getId()));
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public GeneratedTokenData reauthenticate (String masterToken, String deviceId, String ip) throws CBusinessException
	{
		User user = masterTokenService.getUserByMasterToken(masterToken, deviceId, ip);

		if (user == null)
		{
			throw new CSecurityException(ECoreErrorResponse.USER_NOT_FOUND);
		}

		return authenticationTokenService.generateAuthenticationToken(user.getId(), masterToken, deviceId, ip);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void invalidate (String masterToken, String authenticationToken, String deviceId, String ip) throws CBusinessException
	{
		User user = masterTokenService.getUserByMasterToken(masterToken, deviceId, ip);

		if (user == null)
		{
			throw new CSecurityException(ECoreErrorResponse.USER_NOT_FOUND);
		}

		masterTokenService.revokeMasterToken(user.getId(), masterToken);
		authenticationTokenService.revokeAuthenticationToken(user.getId(), authenticationToken);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public VerificationData verify (String token, String deviceId, String ip) throws CBusinessException
	{
		try
		{
			User userByMasterToken = masterTokenService.getUserByMasterToken(token, deviceId, ip);
			User userByAuthenticationToken = authenticationTokenService.getUserByAuthenticationToken(token, deviceId, ip);

			if (userByMasterToken != null)
			{
				return new VerificationData(userByMasterToken, createAdditionalInformation(userByMasterToken.getId()), VerificationTypes.MASTER_TOKEN_VERIFICATION);
			}
			else if (userByAuthenticationToken != null)
			{
				return new VerificationData(userByAuthenticationToken, createAdditionalInformation(userByAuthenticationToken.getId()), VerificationTypes.AUTHENTICATION_TOKEN_VERIFICATION);
			}
			else
			{
				throw new AccessDeniedException("The exception in token verification process", ECoreErrorResponse.ACCESS_DENIED);
			}
		}
		catch (Exception e)
		{
			LOGGER.error("The exception in token verification process");
			throw new AuthenticationException("The exception in token verification process", e, ECoreErrorResponse.ACCESS_DENIED);
		}
	}

	protected Map<String, Object> createAdditionalInformation (Long userId)
	{
		return new HashMap<>();
	}
}
