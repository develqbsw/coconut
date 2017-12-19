package sk.qbsw.security.oauth.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.oauth.model.AccountData;
import sk.qbsw.security.oauth.service.AuthenticationTokenService;
import sk.qbsw.security.oauth.service.MasterTokenService;
import sk.qbsw.security.oauth.service.OAuthService;

import java.util.HashMap;
import java.util.Map;

/**
 * The oauth service implementation.
 *
 * @author Tomas Lauro
 * @version 1.18.1
 * @since 1.18.1
 */
public class OAuthServiceImpl implements OAuthService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(OAuthServiceImpl.class);

	private final MasterTokenService masterTokenService;

	private final AuthenticationTokenService authenticationTokenService;

	public OAuthServiceImpl (MasterTokenService masterTokenService, AuthenticationTokenService authenticationTokenService)
	{
		this.masterTokenService = masterTokenService;
		this.authenticationTokenService = authenticationTokenService;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public AccountData getUserByOAuthToken (String token, String deviceId, String ip)
	{
		try
		{
			User userByMasterToken = masterTokenService.getUserByMasterToken(token, deviceId, ip);
			User userByAuthenticationToken = authenticationTokenService.getUserByAuthenticationToken(token, deviceId, ip);

			if (userByMasterToken != null)
			{
				return new AccountData(userByMasterToken, createAdditionalInformation(userByMasterToken.getId()));
			}
			else if (userByAuthenticationToken != null)
			{
				return new AccountData(userByAuthenticationToken, createAdditionalInformation(userByAuthenticationToken.getId()));
			}
			else
			{
				LOGGER.error("User for user token {} and device id {} not found", token, deviceId);
				throw new UsernameNotFoundException("User for user token " + token + " and device id " + deviceId + " not found");
			}
		}
		catch (Exception ex)
		{
			LOGGER.error("Error by fetching user with user token {} and device id {} not found", token, deviceId, ex);
			throw new AuthenticationServiceException("Error by fetching user with user token " + token + " and device id " + deviceId + " not found", ex);
		}
	}

	protected Map<String, Object> createAdditionalInformation (Long userId)
	{
		return new HashMap<>();
	}
}
