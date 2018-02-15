package sk.qbsw.security.oauth.service.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.security.oauth.model.AuthenticationData;
import sk.qbsw.security.oauth.model.GeneratedTokenData;
import sk.qbsw.security.oauth.model.OAuthCacheNames;
import sk.qbsw.security.oauth.model.VerificationData;
import sk.qbsw.security.oauth.service.OAuthService;
import sk.qbsw.security.oauth.service.OAuthServiceCacheFacade;

/**
 * The oauth service cache facade implementation.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.18.2
 */
public class OAuthServiceCacheFacadeImpl implements OAuthServiceCacheFacade
{
	private final OAuthService oAuthService;

	public OAuthServiceCacheFacadeImpl (OAuthService oAuthService)
	{
		this.oAuthService = oAuthService;
	}

	@Override
	@Caching (evict = { //
		@CacheEvict (value = {OAuthCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#result.masterTokenData.invalidatedToken, #deviceId, #ip}", condition = "#result.masterTokenData.invalidatedToken != null"), //
		@CacheEvict (value = {OAuthCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#result.authenticationTokenData.invalidatedToken, #deviceId, #ip}", condition = "#result.authenticationTokenData.invalidatedToken != null") //
	})
	public AuthenticationData authenticate (String login, String password, String deviceId, String ip) throws CBusinessException
	{
		AuthenticationData authenticationData = oAuthService.authenticate(login, password, deviceId, ip);
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

	@Override
	@Caching (evict = { //
		@CacheEvict (value = {OAuthCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#result.invalidatedToken, #deviceId, #ip}", condition = "#result.invalidatedToken != null") //
	})
	public GeneratedTokenData reauthenticate (String masterToken, String deviceId, String ip) throws CBusinessException
	{
		GeneratedTokenData generationResult = oAuthService.reauthenticate(masterToken, deviceId, ip);
		if (generationResult.getInvalidatedToken() != null)
		{
			evictCache(generationResult.getInvalidatedToken(), deviceId, ip);
		}

		return generationResult;
	}

	@Override
	@Caching (evict = { //
		@CacheEvict (value = {OAuthCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#masterToken, #deviceId, #ip}"), //
		@CacheEvict (value = {OAuthCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#authenticationToken, #deviceId, #ip}") //
	})
	public void invalidate (String masterToken, String authenticationToken, String deviceId, String ip) throws CBusinessException
	{
		oAuthService.invalidate(masterToken, authenticationToken, deviceId, ip);
		evictCache(masterToken, deviceId, ip);
		evictCache(authenticationToken, deviceId, ip);
	}

	@Override
	@Cacheable (value = {OAuthCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#token, #deviceId, #ip}")
	public VerificationData verify (String token, String deviceId, String ip) throws CBusinessException
	{
		return oAuthService.verify(token, deviceId, ip);
	}

	protected void evictCache (String authenticationToken, String deviceId, String ip)
	{
		// override to evict cache
	}
}
