package sk.qbsw.security.oauth.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.security.oauth.model.*;

import java.util.List;

/**
 * The oauth service cache facade implementation.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.18.2
 */
public class OAuthServiceIpIgnoredCacheFacadeImpl extends BaseOAuthServiceCacheFacade implements OAuthServiceFacade
{
	/**
	 * Instantiates a new O auth service ip ignored facade.
	 *
	 * @param oAuthService the o auth service
	 */
	public OAuthServiceIpIgnoredCacheFacadeImpl (OAuthService oAuthService)
	{
		super(oAuthService);
	}

	@Override
	@Caching (evict = { //
		@CacheEvict (value = {OAuthCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#result.masterTokenData.invalidatedToken, #deviceId}", condition = "#result.masterTokenData.invalidatedToken != null"), //
		@CacheEvict (value = {OAuthCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#result.authenticationTokenData.invalidatedToken, #deviceId}", condition = "#result.authenticationTokenData.invalidatedToken != null") //
	})
	public AuthenticationData authenticate (String login, String password, String deviceId, String ip) throws CBusinessException
	{
		return baseAuthenticate(login, password, deviceId, ip);
	}

	@Override
	@Caching (evict = { //
		@CacheEvict (value = {OAuthCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#result.invalidatedToken, #deviceId}", condition = "#result.invalidatedToken != null") //
	})
	public GeneratedTokenData reauthenticate (String masterToken, String deviceId, String ip) throws CBusinessException
	{
		return baseReauthenticate(masterToken, deviceId, ip);
	}

	@Override
	@Caching (evict = { //
		@CacheEvict (value = {OAuthCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#masterToken, #deviceId}"), //
		@CacheEvict (value = {OAuthCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#authenticationToken, #deviceId}") //
	})
	public void invalidate (String masterToken, String authenticationToken, String deviceId, String ip) throws CBusinessException
	{
		baseInvalidate(masterToken, authenticationToken, deviceId, ip);
	}

	@Override
	@Cacheable (value = {OAuthCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#token, #deviceId}")
	public VerificationData verify (String token, String deviceId, String ip) throws CBusinessException
	{
		return baseVerify(token, deviceId, ip);
	}

	@Override
	public List<ExpiredTokenData> removeExpiredTokens ()
	{
		return baseRemoveExpiredTokens();
	}

	@Override
	@CacheEvict (value = {OAuthCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#expiredToken.token, #expiredToken.deviceId}")
	public ExpiredTokenData removeExpiredToken (ExpiredTokenData expiredToken)
	{
		return baseRemoveExpiredToken(expiredToken);
	}
}
