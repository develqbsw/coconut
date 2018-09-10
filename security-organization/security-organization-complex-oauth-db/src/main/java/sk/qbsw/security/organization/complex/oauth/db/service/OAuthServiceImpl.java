package sk.qbsw.security.organization.complex.oauth.db.service;

import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.security.authentication.service.AuthenticationService;
import sk.qbsw.security.core.service.mapper.AccountOutputDataMapper;
import sk.qbsw.security.oauth.base.service.OAuthServiceBase;
import sk.qbsw.security.oauth.model.AuthenticationData;
import sk.qbsw.security.oauth.model.ExpiredTokenData;
import sk.qbsw.security.oauth.model.GeneratedTokenData;
import sk.qbsw.security.oauth.model.VerificationData;
import sk.qbsw.security.oauth.service.AuthenticationTokenService;
import sk.qbsw.security.oauth.service.MasterTokenService;
import sk.qbsw.security.organization.complex.base.model.ComplexOrganizationAccountData;
import sk.qbsw.security.organization.complex.core.model.domain.UserAccount;
import sk.qbsw.security.organization.complex.oauth.model.AuthenticationTokenData;
import sk.qbsw.security.organization.complex.oauth.model.MasterTokenData;
import sk.qbsw.security.organization.complex.oauth.service.ComplexOrganizationOAuthService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The complex organization oauth service implementation.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class OAuthServiceImpl extends OAuthServiceBase<UserAccount, ComplexOrganizationAccountData, AuthenticationTokenData, MasterTokenData> implements ComplexOrganizationOAuthService<ComplexOrganizationAccountData>
{
	/**
	 * Instantiates a new O auth service.
	 *
	 * @param masterTokenService the master token service
	 * @param authenticationTokenService the authentication token service
	 * @param authenticationService the authentication service
	 * @param accountOutputDataMapper the account output data mapper
	 */
	public OAuthServiceImpl (MasterTokenService<ComplexOrganizationAccountData, MasterTokenData> masterTokenService, AuthenticationTokenService<ComplexOrganizationAccountData, AuthenticationTokenData> authenticationTokenService, AuthenticationService authenticationService, AccountOutputDataMapper<ComplexOrganizationAccountData, UserAccount> accountOutputDataMapper)
	{
		super(masterTokenService, authenticationTokenService, authenticationService, accountOutputDataMapper);
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
		if (additionalInformation != null)
		{
			ComplexOrganizationAccountData accountData = accountOutputDataMapper.mapToAccountOutputData(account);
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
