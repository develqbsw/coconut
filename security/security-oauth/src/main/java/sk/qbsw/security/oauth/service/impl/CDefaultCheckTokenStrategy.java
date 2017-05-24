package sk.qbsw.security.oauth.service.impl;

import java.time.OffsetDateTime;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ESystemParameters.ECoreSystemParameter;
import sk.qbsw.core.configuration.model.domain.CSystemParameter;
import sk.qbsw.core.configuration.service.ISystemParameterService;
import sk.qbsw.security.oauth.model.domain.CAuthenticationToken;
import sk.qbsw.security.oauth.model.domain.CMasterToken;
import sk.qbsw.security.oauth.model.domain.CSecurityToken;
import sk.qbsw.security.oauth.service.ICheckTokenStrategy;

/**
 * The check token expiration's limits strategy.
 *
 * @author Tomas Lauro
 * 
 * @version 1.13.1
 * @since 1.13.0
 */
@Component
public class CDefaultCheckTokenStrategy implements ICheckTokenStrategy
{
	/** The log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CDefaultCheckTokenStrategy.class);

	/** The system parameter service. */
	@Autowired
	private ISystemParameterService systemParameterService;

	/** The master token expire limit in hours. */
	private Integer masterTokenExpireLimit;

	/** The authentication token expire limit in hours. */
	private Integer authenticationTokenExpireLimit;

	/** The change limit in hours - ak je < 1, tak ignorovat, beriem to tak, ze je to vypnute lebo to niekto chcel vypnut. */
	private Integer masterTokenChangeLimit;

	/** The authentication token change limit. */
	private Integer authenticationTokenChangeLimit;

	/**
	 * Inits the properties from DB.
	 */
	@PostConstruct
	public void initProperties ()
	{
		CSystemParameter masterTokenExpireLimitParam = systemParameterService.findByName(ECoreSystemParameter.MASTER_TOKEN_EXPIRE_LIMIT.getParameterName());
		if (masterTokenExpireLimitParam == null || masterTokenExpireLimitParam.getIntegerValue() == null || masterTokenExpireLimitParam.getIntegerValue() < 1)
		{
			masterTokenExpireLimit = null;
		}
		else
		{
			masterTokenExpireLimit = masterTokenExpireLimitParam.getIntegerValue();
		}

		CSystemParameter authenticationTokenExpireLimitParam = systemParameterService.findByName(ECoreSystemParameter.AUTHENTICATION_TOKEN_EXPIRE_LIMIT.getParameterName());
		if (authenticationTokenExpireLimitParam == null || authenticationTokenExpireLimitParam.getIntegerValue() == null || authenticationTokenExpireLimitParam.getIntegerValue() < 1)
		{
			authenticationTokenExpireLimit = null;
		}
		else
		{
			authenticationTokenExpireLimit = authenticationTokenExpireLimitParam.getIntegerValue();
		}

		CSystemParameter masterTokenChangeLimitParam = systemParameterService.findByName(ECoreSystemParameter.MASTER_TOKEN_CHANGE_LIMIT.getParameterName());
		if (masterTokenChangeLimitParam == null || masterTokenChangeLimitParam.getIntegerValue() == null)
		{
			masterTokenChangeLimit = null;
		}
		else
		{
			masterTokenChangeLimit = masterTokenChangeLimitParam.getIntegerValue();
		}

		CSystemParameter authenticationTokenChangeLimitParam = systemParameterService.findByName(ECoreSystemParameter.AUTHENTICATION_TOKEN_CHANGE_LIMIT.getParameterName());
		if (authenticationTokenChangeLimitParam == null || authenticationTokenChangeLimitParam.getIntegerValue() == null)
		{
			authenticationTokenChangeLimit = null;
		}
		else
		{
			authenticationTokenChangeLimit = authenticationTokenChangeLimitParam.getIntegerValue();
		}
	}

	/**
	 * Checks if is master token expired.
	 *
	 * @param token the token
	 * @return true, if is master token expired
	 */
	@Override
	public boolean isMasterTokenExpired (CMasterToken token)
	{
		return isTokenExpired(token, masterTokenExpireLimit);
	}

	/**
	 * Checks if is master token inactivity reached.
	 *
	 * @param token the token
	 * @return true, if is master token inactivity reached
	 */
	@Override
	public boolean isMasterTokenInactivityReached (CMasterToken token)
	{
		return isTokenInactivityReached(token, masterTokenChangeLimit);
	}

	/**
	 * Checks if is authentication token expired.
	 *
	 * @param token the token
	 * @return true, if is authentication token expired
	 */
	@Override
	public boolean isAuthenticationTokenExpired (CAuthenticationToken token)
	{
		return isTokenExpired(token, authenticationTokenExpireLimit);
	}

	/**
	 * Checks if is authentication token inactivity reached.
	 *
	 * @param token the token
	 * @return true, if is authentication token inactivity reached
	 */
	@Override
	public boolean isAuthenticationTokenInactivityReached (CAuthenticationToken token)
	{
		return isTokenInactivityReached(token, authenticationTokenChangeLimit);
	}

	/**
	 * Checks if is token expired.
	 *
	 * @param token the token
	 * @param expireLimit the expire limit
	 * @return true, if is token expired
	 */
	private boolean isTokenExpired (CSecurityToken token, Integer expireLimit)
	{
		if (expireLimit == null)
		{
			return false;
		}
		else
		{
			OffsetDateTime expDate = token.getLastAccessDate().plusHours(expireLimit);
			if (expDate.isBefore(OffsetDateTime.now()))
			{
				LOGGER.warn("The token expiration time: {}", expDate);
				return true;
			}
			else
			{
				return false;
			}
		}
	}

	/**
	 * Checks if is token inactivity reached.
	 *
	 * @param token the token
	 * @param changeLimit the change limit
	 * @return true, if is token inactivity reached
	 */
	private boolean isTokenInactivityReached (CSecurityToken token, Integer changeLimit)
	{
		if (changeLimit == null)
		{
			return false;
		}
		else
		{
			OffsetDateTime changeDate = token.getCreateDate().plusHours(changeLimit);
			if (changeLimit > 0 && changeDate.isBefore(OffsetDateTime.now()))
			{
				LOGGER.warn("The token inactivity expiration time: {}", changeDate);
				return true;
			}
			else
			{
				return false;
			}
		}
	}
}
