package sk.qbsw.security.organization.simple.oauth.db.service;

import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.security.authentication.service.AuthenticationService;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.AccountOutputDataMapper;
import sk.qbsw.security.oauth.base.service.OAuthServiceBase;
import sk.qbsw.security.oauth.model.AuthenticationData;
import sk.qbsw.security.oauth.model.ExpiredTokenData;
import sk.qbsw.security.oauth.model.GeneratedTokenData;
import sk.qbsw.security.oauth.model.VerificationData;
import sk.qbsw.security.oauth.service.AuthenticationTokenService;
import sk.qbsw.security.oauth.service.MasterTokenService;
import sk.qbsw.security.organization.simple.base.model.SimpleOrganizationAccountData;
import sk.qbsw.security.organization.simple.oauth.model.AuthenticationTokenData;
import sk.qbsw.security.organization.simple.oauth.model.MasterTokenData;
import sk.qbsw.security.organization.simple.oauth.service.SimpleOrganizationOAuthService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The simple organization oauth service implementation.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class OAuthServiceImpl extends OAuthServiceBase<Account, SimpleOrganizationAccountData, AuthenticationTokenData, MasterTokenData> implements SimpleOrganizationOAuthService<SimpleOrganizationAccountData>
{
	/**
	 * Instantiates a new O auth service.
	 *
	 * @param masterTokenService the master token service
	 * @param authenticationTokenService the authentication token service
	 * @param authenticationService the authentication service
	 * @param simpleOrganizationAccountMapperImpl the simple organization account mapper
	 */
	public OAuthServiceImpl (MasterTokenService<SimpleOrganizationAccountData, MasterTokenData> masterTokenService, AuthenticationTokenService<SimpleOrganizationAccountData, AuthenticationTokenData> authenticationTokenService, AuthenticationService authenticationService, AccountOutputDataMapper<SimpleOrganizationAccountData, Account> simpleOrganizationAccountMapperImpl)
	{
		super(masterTokenService, authenticationTokenService, authenticationService, simpleOrganizationAccountMapperImpl);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public AuthenticationData<SimpleOrganizationAccountData> authenticate (String login, String password, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException
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
	public VerificationData<SimpleOrganizationAccountData> verify (String token, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException
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
	protected SimpleOrganizationAccountData createAccountData (Account account, Map<String, Object> additionalInformation)
	{
		if (additionalInformation != null)
		{
			SimpleOrganizationAccountData accountData = accountOutputDataMapper.mapToAccountOutputData(account);
			accountData.setAdditionalInformation(additionalInformation);

			return accountData;
		}
		else
		{
			return accountOutputDataMapper.mapToAccountOutputData(account);
		}
	}

	@Override
	protected Map<String, Object> createAdditionalInformation (Long accountId)
	{
		return new HashMap<>();
	}
}
