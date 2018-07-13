package sk.qbsw.security.oauth.base.service.facade;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.security.oauth.base.model.*;
import sk.qbsw.security.oauth.base.service.OAuthService;

import java.util.Collections;
import java.util.List;

/**
 * The base oauth service cache facade implementation.
 *
 * @param <D> the type parameter
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.2
 */
public abstract class BaseOAuthServiceCacheFacade<D extends AccountData>
{
	/**
	 * The O auth service.
	 */
	protected final OAuthService<D> oAuthService;

	/**
	 * Instantiates a new O auth service cache facade.
	 *
	 * @param oAuthService the o auth service
	 */
	public BaseOAuthServiceCacheFacade (OAuthService<D> oAuthService)
	{
		this.oAuthService = oAuthService;
	}

	/**
	 * Base authenticate authentication data.
	 *
	 * @param login the login
	 * @param password the password
	 * @param deviceId the device id
	 * @param ip the ip
	 * @return the authentication data
	 * @throws CBusinessException the c business exception
	 */
	protected AuthenticationData<D> authenticateBase (String login, String password, String deviceId, String ip) throws CBusinessException
	{
		AuthenticationData<D> authenticationData = oAuthService.authenticate(login, password, deviceId, ip, true);
		if (authenticationData.getMasterTokenData().getInvalidatedToken() != null)
		{
			evictCache(authenticationData.getMasterTokenData().getInvalidatedToken(), deviceId, ip);
		}
		if (authenticationData.getAuthenticationTokenData().getInvalidatedToken() != null)
		{
			evictCache(authenticationData.getAuthenticationTokenData().getInvalidatedToken(), deviceId, ip);
		}

		return authenticationData;
	}

	/**
	 * Base reauthenticate generated token data.
	 *
	 * @param masterToken the master token
	 * @param deviceId the device id
	 * @param ip the ip
	 * @return the generated token data
	 * @throws CBusinessException the c business exception
	 */
	protected GeneratedTokenData reauthenticateBase (String masterToken, String deviceId, String ip) throws CBusinessException
	{
		GeneratedTokenData generationResult = oAuthService.reauthenticate(masterToken, deviceId, ip, true);
		if (generationResult.getInvalidatedToken() != null)
		{
			evictCache(generationResult.getInvalidatedToken(), deviceId, ip);
		}

		return generationResult;
	}

	/**
	 * Base invalidate.
	 *
	 * @param masterToken the master token
	 * @param authenticationToken the authentication token
	 * @param deviceId the device id
	 * @param ip the ip
	 * @throws CBusinessException the c business exception
	 */
	protected void invalidateBase (String masterToken, String authenticationToken, String deviceId, String ip) throws CBusinessException
	{
		oAuthService.invalidate(masterToken, authenticationToken, deviceId, ip, true);
		evictCache(masterToken, deviceId, ip);
		evictCache(authenticationToken, deviceId, ip);
	}

	/**
	 * Base verify verification data.
	 *
	 * @param token the token
	 * @param deviceId the device id
	 * @param ip the ip
	 * @return the verification data
	 * @throws CBusinessException the c business exception
	 */
	protected VerificationData<D> verifyBase (String token, String deviceId, String ip) throws CBusinessException
	{
		return oAuthService.verify(token, deviceId, ip, true);
	}

	/**
	 * Base delete expired tokens list.
	 *
	 * @return the list
	 */
	protected List<ExpiredTokenData> removeExpiredTokensBase ()
	{
		List<ExpiredTokenData> expiredTokens = oAuthService.removeExpiredTokens();
		evictCache(expiredTokens);

		return expiredTokens;
	}

	/**
	 * Base delete expired token expired token data.
	 *
	 * @param expiredToken the expired token
	 * @return the expired token data
	 */
	protected ExpiredTokenData removeExpiredTokenBase (ExpiredTokenData expiredToken)
	{
		// just evict cache and return
		return expiredToken;
	}

	private void evictCache (String token, String deviceId, String ip)
	{
		evictCache(Collections.singletonList(new ExpiredTokenData(token, deviceId, ip)));
	}

	/**
	 * Evict cache.
	 *
	 * @param expiredTokens the expired tokens
	 */
	protected void evictCache (List<ExpiredTokenData> expiredTokens)
	{
		// override to evict cache
	}
}
