package sk.qbsw.security.integration.authentication.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.security.authentication.base.service.AuthenticationService;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.integration.authentication.core.exception.AccessDeniedException;
import sk.qbsw.security.integration.authentication.core.exception.AuthenticationException;
import sk.qbsw.security.integration.authentication.core.model.AuthenticationData;
import sk.qbsw.security.integration.authentication.core.model.VerificationData;
import sk.qbsw.security.oauth.service.AuthenticationTokenService;
import sk.qbsw.security.oauth.service.MasterTokenService;

import java.util.HashMap;
import java.util.Map;

/**
 * The integration authentication service.
 *
 * @author Tomas Lauro
 * @author Roman Farkaš
 * @version 1.0.0
 * @since 1.0.0
 */
public class IntegrationAuthenticationServiceImpl implements IntegrationAuthenticationService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(IntegrationAuthenticationServiceImpl.class);

	private final AuthenticationService authenticationService;

	private final MasterTokenService masterTokenService;

	private final AuthenticationTokenService authenticationTokenService;


	/**
	 * Instantiates a new Authentication controller.
	 *
	 * @param authenticationService the authentication service
	 * @param masterTokenService the master token service
	 * @param authenticationTokenService the authentication token service
	 */
	@Autowired
	public IntegrationAuthenticationServiceImpl (AuthenticationService authenticationService, MasterTokenService masterTokenService, AuthenticationTokenService authenticationTokenService)
	{
		this.authenticationService = authenticationService;
		this.masterTokenService = masterTokenService;
		this.authenticationTokenService = authenticationTokenService;
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
		String masterToken = masterTokenService.generateMasterToken(user.getId(), deviceId, ip);
		String authenticationToken = authenticationTokenService.generateAuthenticationToken(user.getId(), masterToken, deviceId, ip);

		// create response
		return new AuthenticationData(masterToken, authenticationToken, user);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public String reauthenticate (String masterToken, String deviceId, String ip) throws CBusinessException
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
				return new VerificationData(userByMasterToken, createAdditionalInformation(userByMasterToken.getId()));
			}
			else if (userByAuthenticationToken != null)
			{
				return new VerificationData(userByAuthenticationToken, createAdditionalInformation(userByAuthenticationToken.getId()));
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

	/**
	 * Create additional information map.
	 *
	 * @param userId the user id
	 * @return the map
	 */
	protected Map<String, Object> createAdditionalInformation (Long userId)
	{
		return new HashMap<>();
	}
}
