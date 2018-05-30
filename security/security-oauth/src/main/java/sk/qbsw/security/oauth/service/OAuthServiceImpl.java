package sk.qbsw.security.oauth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.security.base.exception.AccessDeniedException;
import sk.qbsw.core.security.base.exception.AuthenticationException;
import sk.qbsw.security.authentication.base.service.AuthenticationService;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.oauth.model.*;
import sk.qbsw.security.oauth.model.domain.AuthenticationToken;
import sk.qbsw.security.oauth.model.domain.MasterToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

	/**
	 * Instantiates a new O auth service.
	 *
	 * @param masterTokenService the master token service
	 * @param authenticationTokenService the authentication token service
	 * @param authenticationService the authentication service
	 */
	public OAuthServiceImpl (MasterTokenService masterTokenService, AuthenticationTokenService authenticationTokenService, AuthenticationService authenticationService)
	{
		this.masterTokenService = masterTokenService;
		this.authenticationTokenService = authenticationTokenService;
		this.authenticationService = authenticationService;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public AuthenticationData authenticate (String login, String password, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException
	{
		Account account = authenticationService.login(login, password);

		if (account == null)
		{
			throw new CSecurityException(ECoreErrorResponse.ACCOUNT_NOT_FOUND);
		}

		// generate tokens
		GeneratedTokenData masterTokenData = masterTokenService.generateMasterToken(account.getId(), deviceId, ip);
		GeneratedTokenData authenticationTokenData = authenticationTokenService.generateAuthenticationToken(account.getId(), masterTokenData.getGeneratedToken(), deviceId, ip, isIpIgnored);

		// create response
		return new AuthenticationData(masterTokenData, authenticationTokenData, AccountData.build(account, createAdditionalInformation(account.getId())));
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public GeneratedTokenData reauthenticate (String masterToken, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException
	{
		Account account = masterTokenService.getAccountByMasterToken(masterToken, deviceId, ip, isIpIgnored);

		if (account == null)
		{
			throw new CSecurityException(ECoreErrorResponse.ACCOUNT_NOT_FOUND);
		}

		return authenticationTokenService.generateAuthenticationToken(account.getId(), masterToken, deviceId, ip, isIpIgnored);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void invalidate (String masterToken, String authenticationToken, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException
	{
		Account account = masterTokenService.getAccountByMasterToken(masterToken, deviceId, ip, isIpIgnored);

		if (account == null)
		{
			throw new CSecurityException(ECoreErrorResponse.ACCOUNT_NOT_FOUND);
		}

		masterTokenService.revokeMasterToken(account.getId(), masterToken);
		authenticationTokenService.revokeAuthenticationToken(account.getId(), authenticationToken);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public VerificationData verify (String token, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException
	{
		try
		{
			Account accountByMasterToken = masterTokenService.getAccountByMasterToken(token, deviceId, ip, isIpIgnored);
			Account accountByAuthenticationToken = authenticationTokenService.getAccountByAuthenticationToken(token, deviceId, ip, isIpIgnored);

			if (accountByMasterToken != null)
			{
				return new VerificationData(AccountData.build(accountByMasterToken, createAdditionalInformation(accountByMasterToken.getId())), VerificationTypes.MASTER_TOKEN_VERIFICATION);
			}
			else if (accountByAuthenticationToken != null)
			{
				return new VerificationData(AccountData.build(accountByAuthenticationToken, createAdditionalInformation(accountByAuthenticationToken.getId())), VerificationTypes.AUTHENTICATION_TOKEN_VERIFICATION);
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

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<ExpiredTokenData> removeExpiredTokens ()
	{
		List<MasterToken> expiredMasterTokens = masterTokenService.findExpiredMasterTokens();
		List<AuthenticationToken> expiredAuthenticationTokens = authenticationTokenService.findExpiredAuthenticationTokens();

		List<ExpiredTokenData> expiredTokenData = convertToExpiredTokenData(expiredMasterTokens, expiredAuthenticationTokens);

		masterTokenService.removeMasterTokens(expiredMasterTokens.stream().map(MasterToken::getId).collect(Collectors.toList()));
		authenticationTokenService.removeAuthenticationTokens(expiredAuthenticationTokens.stream().map(AuthenticationToken::getId).collect(Collectors.toList()));

		return expiredTokenData;
	}

	private List<ExpiredTokenData> convertToExpiredTokenData (List<MasterToken> expiredMasterTokens, List<AuthenticationToken> expiredAuthenticationTokens)
	{
		List<ExpiredTokenData> expiredTokensData = new ArrayList<>();
		expiredTokensData.addAll(expiredMasterTokens.stream().map(t -> new ExpiredTokenData(t.getToken(), t.getDeviceId(), t.getIp())).collect(Collectors.toList()));
		expiredTokensData.addAll(expiredAuthenticationTokens.stream().map(t -> new ExpiredTokenData(t.getToken(), t.getDeviceId(), t.getIp())).collect(Collectors.toList()));

		return expiredTokensData;
	}

	/**
	 * Create additional information map.
	 *
	 * @param accountId the account id
	 * @return the map
	 */
	protected Map<String, Object> createAdditionalInformation (Long accountId)
	{
		return new HashMap<>();
	}
}