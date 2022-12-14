package sk.qbsw.security.spring.oauth.rest.service;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import sk.qbsw.security.rest.oauth.client.base.AuthenticationClient;
import sk.qbsw.security.rest.oauth.client.model.CSAccountData;
import sk.qbsw.security.rest.oauth.client.model.CSAccountDataStates;
import sk.qbsw.security.rest.oauth.client.model.CSUserData;
import sk.qbsw.security.rest.oauth.client.model.request.VerifyRequestBody;
import sk.qbsw.security.rest.oauth.client.model.response.VerificationResponseBody;
import sk.qbsw.security.spring.base.mapper.UserDataMapper;
import sk.qbsw.security.spring.oauth.common.model.OAuthData;
import sk.qbsw.security.spring.oauth.common.model.OAuthLoggedAccount;
import sk.qbsw.security.spring.oauth.common.model.OAuthWebAuthenticationDetails;
import sk.qbsw.security.spring.oauth.common.service.BaseOAuthUserDetailsService;

/**
 * The oauth pre authenticated user details service.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.18.0
 */
public class OAuthWebServiceUserDetailsService extends BaseOAuthUserDetailsService
{
	private final AuthenticationClient<CSAccountData> authenticationClient;

	private final UserDataMapper<CSUserData> userDataMapper;

	/**
	 * Instantiates a new O auth pre authenticated user details service.
	 *
	 * @param authenticationClient the authentication client
	 * @param userDataMapper the user data mapper
	 */
	public OAuthWebServiceUserDetailsService (AuthenticationClient<CSAccountData> authenticationClient, UserDataMapper<CSUserData> userDataMapper)
	{
		super();
		this.authenticationClient = authenticationClient;
		this.userDataMapper = userDataMapper;
	}

	protected UserDetails createUserDetails (Authentication token)
	{
		String deviceId = ((OAuthWebAuthenticationDetails) token.getDetails()).getDeviceId();
		String ip = ((OAuthWebAuthenticationDetails) token.getDetails()).getIp();

		CSAccountData accountData = verify((String) token.getPrincipal(), deviceId, ip).getAccountData();
		OAuthData oAuthData = new OAuthData((String) token.getPrincipal(), deviceId, ip);

		return new OAuthLoggedAccount(accountData.getId(), accountData.getLogin(), "N/A", accountData.getState().equals(CSAccountDataStates.ACTIVE), //
			userDataMapper.mapToUserData(accountData.getUser()), convertRolesToAuthorities(accountData.getRoles()), oAuthData, accountData.getAdditionalInformation());
	}

	private VerificationResponseBody verify (String token, String deviceId, String ip)
	{
		try
		{
			return authenticationClient.verify(new VerifyRequestBody(token, deviceId, ip));
		}
		catch (Exception ex)
		{
			LOGGER.error("Error by fetching user with user token {} and device id {} not found", token, deviceId, ex);
			throw new AuthenticationServiceException("Error by fetching user with user token " + token + " and device id " + deviceId + " not found", ex);
		}
	}
}
