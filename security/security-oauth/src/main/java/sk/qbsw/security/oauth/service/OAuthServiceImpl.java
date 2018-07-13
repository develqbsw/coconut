package sk.qbsw.security.oauth.service;

import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.security.authentication.base.service.AuthenticationService;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.oauth.base.model.*;
import sk.qbsw.security.oauth.base.service.AuthenticationTokenService;
import sk.qbsw.security.oauth.base.service.MasterTokenService;
import sk.qbsw.security.oauth.base.service.OAuthService;
import sk.qbsw.security.oauth.base.service.OAuthServiceBase;
import sk.qbsw.security.oauth.model.domain.AuthenticationToken;
import sk.qbsw.security.oauth.model.domain.MasterToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The oauth service implementation.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.1
 */
public class OAuthServiceImpl extends OAuthServiceBase<AccountData, Account, AuthenticationToken, MasterToken> implements OAuthService<AccountData>
{
	/**
	 * Instantiates a new O auth service.
	 *
	 * @param masterTokenService the master token service
	 * @param authenticationTokenService the authentication token service
	 * @param authenticationService the authentication service
	 */
	public OAuthServiceImpl (MasterTokenService<Account, MasterToken> masterTokenService, AuthenticationTokenService<Account, AuthenticationToken> authenticationTokenService, AuthenticationService authenticationService)
	{
		super(masterTokenService, authenticationTokenService, authenticationService);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public AuthenticationData<AccountData> authenticate (String login, String password, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException
	{
		return super.authenticateBase(login, password, deviceId, ip, isIpIgnored);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public GeneratedTokenData reauthenticate (String masterToken, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException
	{
		return super.reauthenticateBase(masterToken, deviceId, ip, isIpIgnored);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void invalidate (String masterToken, String authenticationToken, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException
	{
		super.invalidateBase(masterToken, authenticationToken, deviceId, ip, isIpIgnored);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public VerificationData<AccountData> verify (String token, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException
	{
		return verifyBase(token, deviceId, ip, isIpIgnored);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<ExpiredTokenData> removeExpiredTokens ()
	{
		return removeExpiredTokensBase();
	}

	@Override
	protected AccountData createAccountData (Account account, Map<String, Object> additionalInformation)
	{
		if (additionalInformation != null)
		{
			return new AccountData(account.getId(), account.getLogin(), account.getEmail(), account.exportRoles(), additionalInformation);
		}
		else
		{
			return new AccountData(account.getId(), account.getLogin(), account.getEmail(), account.exportRoles(), new HashMap<>());
		}
	}

	@Override
	protected Map<String, Object> createAdditionalInformation (Long accountId)
	{
		return new HashMap<>();
	}
}
