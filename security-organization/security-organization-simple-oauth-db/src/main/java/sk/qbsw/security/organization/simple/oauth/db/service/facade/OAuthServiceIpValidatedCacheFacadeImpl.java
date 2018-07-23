package sk.qbsw.security.organization.simple.oauth.db.service.facade;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.security.oauth.base.model.OAuthCacheNames;
import sk.qbsw.security.oauth.base.service.facade.BaseOAuthServiceCacheFacade;
import sk.qbsw.security.oauth.model.AuthenticationData;
import sk.qbsw.security.oauth.model.ExpiredTokenData;
import sk.qbsw.security.oauth.model.GeneratedTokenData;
import sk.qbsw.security.oauth.model.VerificationData;
import sk.qbsw.security.oauth.service.OAuthService;
import sk.qbsw.security.organization.simple.oauth.model.SimpleOrganizationAccountData;
import sk.qbsw.security.organization.simple.oauth.service.facade.SimpleOrganizationOAuthServiceFacade;

import java.util.List;

/**
 * The simple organization oauth service cache facade implementation.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class OAuthServiceIpValidatedCacheFacadeImpl extends BaseOAuthServiceCacheFacade<SimpleOrganizationAccountData> implements SimpleOrganizationOAuthServiceFacade<SimpleOrganizationAccountData>
{
	/**
	 * Instantiates a new O auth service ip validated cache facade.
	 *
	 * @param oAuthService the o auth service
	 */
	public OAuthServiceIpValidatedCacheFacadeImpl (OAuthService<SimpleOrganizationAccountData> oAuthService)
	{
		super(oAuthService);
	}

	@Override
	@Caching (evict = { //
		@CacheEvict (value = {OAuthCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#result.masterTokenData.invalidatedToken, #deviceId, #ip}", condition = "#result.masterTokenData.invalidatedToken != null"), //
		@CacheEvict (value = {OAuthCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#result.authenticationTokenData.invalidatedToken, #deviceId, #ip}", condition = "#result.authenticationTokenData.invalidatedToken != null") //
	})
	public AuthenticationData<SimpleOrganizationAccountData> authenticate (String login, String password, String deviceId, String ip) throws CBusinessException
	{
		return authenticateBase(login, password, deviceId, ip);
	}

	@Override
	@Caching (evict = { //
		@CacheEvict (value = {OAuthCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#result.invalidatedToken, #deviceId, #ip}", condition = "#result.invalidatedToken != null") //
	})
	public GeneratedTokenData reauthenticate (String masterToken, String deviceId, String ip) throws CBusinessException
	{
		return reauthenticateBase(masterToken, deviceId, ip);
	}

	@Override
	@Caching (evict = { //
		@CacheEvict (value = {OAuthCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#masterToken, #deviceId, #ip}"), //
		@CacheEvict (value = {OAuthCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#authenticationToken, #deviceId, #ip}") //
	})
	public void invalidate (String masterToken, String authenticationToken, String deviceId, String ip) throws CBusinessException
	{
		invalidateBase(masterToken, authenticationToken, deviceId, ip);
	}

	@Override
	@Cacheable (value = {OAuthCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#token, #deviceId, #ip}")
	public VerificationData<SimpleOrganizationAccountData> verify (String token, String deviceId, String ip) throws CBusinessException
	{
		return verifyBase(token, deviceId, ip);
	}

	@Override
	public List<ExpiredTokenData> removeExpiredTokens ()
	{
		return removeExpiredTokensBase();
	}

	@Override
	@CacheEvict (value = {OAuthCacheNames.SEC_OAUTH_TOKEN_CACHE_NAME}, key = "{#expiredToken.token, #expiredToken.deviceId, #expiredToken.ip}")
	public ExpiredTokenData removeExpiredToken (ExpiredTokenData expiredToken)
	{
		return removeExpiredTokenBase(expiredToken);
	}
}
