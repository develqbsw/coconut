package sk.qbsw.security.oauth.db.service.facade;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.security.oauth.base.model.*;
import sk.qbsw.security.oauth.service.OAuthService;
import sk.qbsw.security.oauth.base.service.facade.BaseOAuthServiceCacheFacade;
import sk.qbsw.security.oauth.service.facade.OAuthServiceFacade;
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.oauth.model.AuthenticationData;
import sk.qbsw.security.oauth.model.ExpiredTokenData;
import sk.qbsw.security.oauth.model.GeneratedTokenData;
import sk.qbsw.security.oauth.model.VerificationData;

import java.util.List;

/**
 * The oauth service cache facade implementation.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.2
 */
public class OAuthServiceIpIgnoredCacheFacadeImpl extends BaseOAuthServiceCacheFacade<AccountData> implements OAuthServiceFacade<AccountData>
{
	/**
	 * Instantiates a new O auth service ip ignored facade.
	 *
	 * @param oAuthService the o auth service
	 */
	public OAuthServiceIpIgnoredCacheFacadeImpl (OAuthService<AccountData> oAuthService)
	{
		super(oAuthService);
	}

	@Override
	@Caching (evict = { //
		@CacheEvict (value = {OAuthCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#result.masterTokenData.invalidatedToken, #deviceId}", condition = "#result.masterTokenData.invalidatedToken != null"), //
		@CacheEvict (value = {OAuthCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#result.authenticationTokenData.invalidatedToken, #deviceId}", condition = "#result.authenticationTokenData.invalidatedToken != null") //
	})
	public AuthenticationData<AccountData> authenticate (String login, String password, String deviceId, String ip) throws CBusinessException
	{
		return authenticateBase(login, password, deviceId, ip);
	}

	@Override
	@Caching (evict = { //
		@CacheEvict (value = {OAuthCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#result.invalidatedToken, #deviceId}", condition = "#result.invalidatedToken != null") //
	})
	public GeneratedTokenData reauthenticate (String masterToken, String deviceId, String ip) throws CBusinessException
	{
		return reauthenticateBase(masterToken, deviceId, ip);
	}

	@Override
	@Caching (evict = { //
		@CacheEvict (value = {OAuthCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#masterToken, #deviceId}"), //
		@CacheEvict (value = {OAuthCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#authenticationToken, #deviceId}") //
	})
	public void invalidate (String masterToken, String authenticationToken, String deviceId, String ip) throws CBusinessException
	{
		invalidateBase(masterToken, authenticationToken, deviceId, ip);
	}

	@Override
	@Cacheable (value = {OAuthCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#token, #deviceId}")
	public VerificationData<AccountData> verify (String token, String deviceId, String ip) throws CBusinessException
	{
		return verifyBase(token, deviceId, ip);
	}

	@Override
	public List<ExpiredTokenData> removeExpiredTokens ()
	{
		return removeExpiredTokensBase();
	}

	@Override
	@CacheEvict (value = {OAuthCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#expiredToken.token, #expiredToken.deviceId}")
	public ExpiredTokenData removeExpiredToken (ExpiredTokenData expiredToken)
	{
		return removeExpiredTokenBase(expiredToken);
	}
}
