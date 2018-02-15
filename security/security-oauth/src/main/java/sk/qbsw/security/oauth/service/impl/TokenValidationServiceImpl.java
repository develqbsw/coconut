package sk.qbsw.security.oauth.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.qbsw.security.oauth.model.domain.AuthenticationToken;
import sk.qbsw.security.oauth.model.domain.MasterToken;
import sk.qbsw.security.oauth.model.jmx.OauthConfigurator;
import sk.qbsw.security.oauth.service.CheckTokenStrategy;
import sk.qbsw.security.oauth.service.TokenValidationService;

/**
 * The token validation service.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.18.2
 */
@Service
public class TokenValidationServiceImpl implements TokenValidationService
{
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(TokenValidationServiceImpl.class);

	/** The check token strategy. */
	@Autowired
	private CheckTokenStrategy checkTokenStrategy;

	/** The configurator. */
	@Autowired
	private OauthConfigurator oauthConfigurator;

	@Override
	public boolean isMasterTokenIpValid (MasterToken token, String ip)
	{
		if (!oauthConfigurator.isIpIgnored())
		{
			if ( (token.getIp() != null && !token.getIp().equals(ip)) || (token.getIp() == null && ip != null))
			{
				LOGGER.warn("The master token {} for user {} and device {} deleted because of invalid ip", token.getToken(), token.getUser().getLogin(), token.getDeviceId());
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean isAuthenticationTokenIpValid (AuthenticationToken token, String ip)
	{
		if (!oauthConfigurator.isAuthIpIgnored())
		{
			if ( (token.getIp() != null && !token.getIp().equals(ip)) || (token.getIp() == null && ip != null))
			{
				LOGGER.warn("The authentication token {} for user {} and device {} deleted because of invalid ip", token.getToken(), token.getUser().getLogin(), token.getDeviceId());
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean isMasterTokenExpired (MasterToken token)
	{
		if (checkTokenStrategy.isMasterTokenExpired(token))
		{
			LOGGER.warn("The master token {} has expired", token.getToken());
			return true;
		}

		if (checkTokenStrategy.isMasterTokenInactivityReached(token))
		{
			LOGGER.warn("The master token {} inactivity limit reached", token.getToken());
			return true;
		}

		return false;
	}

	@Override
	public boolean isAuthenticationTokenExpired (AuthenticationToken token)
	{
		if (checkTokenStrategy.isAuthenticationTokenExpired(token))
		{
			LOGGER.warn("The authentication token {} has expired", token.getToken());
			return true;
		}

		if (checkTokenStrategy.isAuthenticationTokenInactivityReached(token))
		{
			LOGGER.warn("The authentication token {} inactivity limit reached", token.getToken());
			return true;
		}

		return false;
	}
}
