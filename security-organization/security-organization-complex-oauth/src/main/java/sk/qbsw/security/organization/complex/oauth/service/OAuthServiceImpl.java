package sk.qbsw.security.organization.complex.oauth.service;

import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.organization.complex.core.model.domain.Organization;
import sk.qbsw.organization.complex.core.model.domain.Unit;
import sk.qbsw.security.authentication.base.service.AuthenticationService;
import sk.qbsw.security.oauth.base.model.AuthenticationData;
import sk.qbsw.security.oauth.base.model.ExpiredTokenData;
import sk.qbsw.security.oauth.base.model.GeneratedTokenData;
import sk.qbsw.security.oauth.base.model.VerificationData;
import sk.qbsw.security.oauth.base.service.AuthenticationTokenService;
import sk.qbsw.security.oauth.base.service.MasterTokenService;
import sk.qbsw.security.oauth.base.service.OAuthService;
import sk.qbsw.security.oauth.base.service.OAuthServiceBase;
import sk.qbsw.security.organization.complex.core.model.domain.UserAccount;
import sk.qbsw.security.organization.complex.oauth.model.ComplexOrganizationAccountData;
import sk.qbsw.security.organization.complex.oauth.model.OrganizationData;
import sk.qbsw.security.organization.complex.oauth.model.UnitData;
import sk.qbsw.security.organization.complex.oauth.model.domain.AuthenticationToken;
import sk.qbsw.security.organization.complex.oauth.model.domain.MasterToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The complex organization oauth service implementation.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class OAuthServiceImpl extends OAuthServiceBase<ComplexOrganizationAccountData, UserAccount, AuthenticationToken, MasterToken> implements OAuthService<ComplexOrganizationAccountData>
{
	/**
	 * Instantiates a new O auth service.
	 *
	 * @param masterTokenService the master token service
	 * @param authenticationTokenService the authentication token service
	 * @param authenticationService the authentication service
	 */
	public OAuthServiceImpl (MasterTokenService<UserAccount, MasterToken> masterTokenService, AuthenticationTokenService<UserAccount, AuthenticationToken> authenticationTokenService, AuthenticationService authenticationService)
	{
		super(masterTokenService, authenticationTokenService, authenticationService);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public AuthenticationData<ComplexOrganizationAccountData> authenticate (String login, String password, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException
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
	public VerificationData<ComplexOrganizationAccountData> verify (String token, String deviceId, String ip, boolean isIpIgnored) throws CBusinessException
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
	protected ComplexOrganizationAccountData createAccountData (UserAccount account, Map<String, Object> additionalInformation)
	{
		Map<Organization, List<Unit>> unitsByOrganization = account.getUser().getUnits().stream().collect(Collectors.groupingBy(Unit::getOrganization));
		List<OrganizationData> organizationData = unitsByOrganization.entrySet().stream().map(e -> new OrganizationData(e.getKey().getId(), e.getKey().getName(), e.getKey().getCode(), e.getValue().stream().map(u -> new UnitData(u.getId(), u.getName(), u.getCode())).collect(Collectors.toList()))).collect(Collectors.toList());

		if (additionalInformation != null)
		{
			return new ComplexOrganizationAccountData(account.getId(), account.getLogin(), account.getEmail(), account.exportRoles(), organizationData, additionalInformation);
		}
		else
		{
			return new ComplexOrganizationAccountData(account.getId(), account.getLogin(), account.getEmail(), account.exportRoles(), organizationData, new HashMap<>());
		}
	}

	@Override
	protected Map<String, Object> createAdditionalInformation (Long accountId)
	{
		return new HashMap<>();
	}
}
