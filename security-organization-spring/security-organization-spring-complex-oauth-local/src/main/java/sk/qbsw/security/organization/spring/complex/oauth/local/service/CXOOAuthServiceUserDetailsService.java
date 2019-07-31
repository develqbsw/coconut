package sk.qbsw.security.organization.spring.complex.oauth.local.service;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.core.security.base.model.AccountDataStates;
import sk.qbsw.security.oauth.model.VerificationData;
import sk.qbsw.security.oauth.service.facade.OAuthServiceFacade;
import sk.qbsw.security.organization.complex.base.model.CXOUserOutputData;
import sk.qbsw.security.organization.spring.complex.common.model.CXOUserData;
import sk.qbsw.security.organization.spring.complex.oauth.common.model.CXOOAuthLoggedAccount;
import sk.qbsw.security.spring.base.mapper.UserDataMapper;
import sk.qbsw.security.spring.oauth.common.model.OAuthData;
import sk.qbsw.security.spring.oauth.common.model.OAuthWebAuthenticationDetails;
import sk.qbsw.security.spring.oauth.common.service.BaseOAuthUserDetailsService;

/**
 * The oauth pre authenticated user details service.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 1.18.0
 */
public class CXOOAuthServiceUserDetailsService extends BaseOAuthUserDetailsService
{
	private final OAuthServiceFacade<AccountData> oauthService;

	private final UserDataMapper<CXOUserOutputData> userDataMapper;

	/**
	 * Instantiates a new O auth service user details service.
	 *
	 * @param oauthService the oauth service
	 * @param userDataMapper the user data mapper
	 */
	public CXOOAuthServiceUserDetailsService (OAuthServiceFacade<AccountData> oauthService, UserDataMapper<CXOUserOutputData> userDataMapper)
	{
		super();
		this.oauthService = oauthService;
		this.userDataMapper = userDataMapper;
	}

	protected UserDetails createUserDetails (Authentication token)
	{
		String deviceId = ((OAuthWebAuthenticationDetails) token.getDetails()).getDeviceId();
		String ip = ((OAuthWebAuthenticationDetails) token.getDetails()).getIp();

		AccountData accountData = verify((String) token.getPrincipal(), deviceId, ip).getAccountData();

		CXOUserData userData = (CXOUserData) userDataMapper.mapToUserData((CXOUserOutputData) accountData.getUser());
		OAuthData oAuthData = new OAuthData((String) token.getPrincipal(), deviceId, ip);
		return new CXOOAuthLoggedAccount(accountData.getId(), accountData.getLogin(), "N/A", accountData.getState().equals(AccountDataStates.ACTIVE), //
			userData, convertRolesToAuthorities(accountData.getRoles()), oAuthData, accountData.getAdditionalInformation());
	}

	private VerificationData<AccountData> verify (String token, String deviceId, String ip)
	{
		try
		{
			return oauthService.verify(token, deviceId, ip);
		}
		catch (Exception ex)
		{
			LOGGER.error("Error by fetching user with user token {} and device id {} not found", token, deviceId, ex);
			throw new AuthenticationServiceException("Error by fetching user with user token " + token + " and device id " + deviceId + " not found", ex);
		}
	}
}
