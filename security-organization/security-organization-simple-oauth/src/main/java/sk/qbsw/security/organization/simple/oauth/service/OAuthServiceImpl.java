package sk.qbsw.security.organization.simple.oauth.service;

import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.security.authentication.service.AuthenticationService;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.oauth.base.service.OAuthServiceBase;
import sk.qbsw.security.oauth.model.AuthenticationData;
import sk.qbsw.security.oauth.model.ExpiredTokenData;
import sk.qbsw.security.oauth.model.GeneratedTokenData;
import sk.qbsw.security.oauth.model.VerificationData;
import sk.qbsw.security.oauth.service.AuthenticationTokenService;
import sk.qbsw.security.oauth.service.MasterTokenService;
import sk.qbsw.security.oauth.service.OAuthService;
import sk.qbsw.security.organization.simple.oauth.model.AuthenticationTokenData;
import sk.qbsw.security.organization.simple.oauth.model.MasterTokenData;
import sk.qbsw.security.organization.simple.oauth.model.OrganizationData;
import sk.qbsw.security.organization.simple.oauth.model.SimpleOrganizationAccountData;

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
public class OAuthServiceImpl extends OAuthServiceBase<Account, SimpleOrganizationAccountData, AuthenticationTokenData, MasterTokenData> implements OAuthService<SimpleOrganizationAccountData>
{
	/**
	 * Instantiates a new O auth service.
	 *
	 * @param masterTokenService the master token service
	 * @param authenticationTokenService the authentication token service
	 * @param authenticationService the authentication service
	 */
	public OAuthServiceImpl (MasterTokenService<SimpleOrganizationAccountData, MasterTokenData> masterTokenService, AuthenticationTokenService<SimpleOrganizationAccountData, AuthenticationTokenData> authenticationTokenService, AuthenticationService authenticationService)
	{
		super(masterTokenService, authenticationTokenService, authenticationService);
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
		OrganizationData organization = new OrganizationData(account.getOrganization().getId(), account.getOrganization().getName(), account.getOrganization().getCode());

		if (additionalInformation != null)
		{
			return new SimpleOrganizationAccountData(account.getId(), account.getLogin(), account.getEmail(), account.exportGroups(), account.exportRoles(), organization, additionalInformation);
		}
		else
		{
			return new SimpleOrganizationAccountData(account.getId(), account.getLogin(), account.getEmail(), account.exportGroups(), account.exportRoles(), organization, new HashMap<>());
		}
	}

	@Override
	protected Map<String, Object> createAdditionalInformation (Long accountId)
	{
		return new HashMap<>();
	}
}
