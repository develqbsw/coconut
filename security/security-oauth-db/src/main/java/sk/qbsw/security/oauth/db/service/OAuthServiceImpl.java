package sk.qbsw.security.oauth.db.service;

import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.authentication.service.AuthenticationService;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.oauth.base.service.OAuthServiceBase;
import sk.qbsw.security.oauth.db.model.AuthenticationTokenData;
import sk.qbsw.security.oauth.db.model.MasterTokenData;
import sk.qbsw.security.oauth.model.AuthenticationData;
import sk.qbsw.security.oauth.model.ExpiredTokenData;
import sk.qbsw.security.oauth.model.GeneratedTokenData;
import sk.qbsw.security.oauth.model.VerificationData;
import sk.qbsw.security.oauth.service.AuthenticationTokenService;
import sk.qbsw.security.oauth.service.MasterTokenService;
import sk.qbsw.security.oauth.service.OAuthService;

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
public class OAuthServiceImpl extends OAuthServiceBase<Account, AccountData, AuthenticationTokenData, MasterTokenData> implements OAuthService<AccountData>
{
	/**
	 * Instantiates a new O auth service.
	 *
	 * @param masterTokenService the master token service
	 * @param authenticationTokenService the authentication token service
	 * @param authenticationService the authentication service
	 */
	public OAuthServiceImpl (MasterTokenService<AccountData, MasterTokenData> masterTokenService, AuthenticationTokenService<AccountData, AuthenticationTokenData> authenticationTokenService, AuthenticationService authenticationService)
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
			return new AccountData(account.getId(), account.getLogin(), account.getEmail(), account.exportGroups(), account.exportRoles(), additionalInformation);
		}
		else
		{
			return new AccountData(account.getId(), account.getLogin(), account.getEmail(), account.exportGroups(), account.exportRoles(), new HashMap<>());
		}
	}

	@Override
	protected Map<String, Object> createAdditionalInformation (Long accountId)
	{
		return new HashMap<>();
	}
}
