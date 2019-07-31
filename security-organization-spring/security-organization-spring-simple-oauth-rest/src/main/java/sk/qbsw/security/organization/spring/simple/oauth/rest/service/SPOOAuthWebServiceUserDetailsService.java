package sk.qbsw.security.organization.spring.simple.oauth.rest.service;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import sk.qbsw.security.organization.rest.oauth.simple.client.model.CSSPOAccountData;
import sk.qbsw.security.organization.rest.oauth.simple.client.model.CSSPOUserData;
import sk.qbsw.security.organization.spring.simple.common.model.SPOUserData;
import sk.qbsw.security.organization.spring.simple.oauth.common.model.SPOOAuthLoggedAccount;
import sk.qbsw.security.rest.oauth.client.base.AuthenticationClient;
import sk.qbsw.security.rest.oauth.client.model.CSAccountData;
import sk.qbsw.security.rest.oauth.client.model.CSAccountDataStates;
import sk.qbsw.security.rest.oauth.client.model.request.VerifyRequestBody;
import sk.qbsw.security.rest.oauth.client.model.response.VerificationResponseBody;
import sk.qbsw.security.spring.base.mapper.UserDataMapper;
import sk.qbsw.security.spring.oauth.common.model.OAuthData;
import sk.qbsw.security.spring.oauth.common.model.OAuthWebAuthenticationDetails;
import sk.qbsw.security.spring.oauth.common.service.BaseOAuthUserDetailsService;

/**
 * The oauth pre authenticated user details service.
 *
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 1.18.0
 */
public class SPOOAuthWebServiceUserDetailsService extends BaseOAuthUserDetailsService
{
	private final AuthenticationClient<CSSPOAccountData> authenticationClient;

	private final UserDataMapper<CSSPOUserData> userDataMapper;

	/**
	 * Instantiates a new O auth pre authenticated user details service.
	 *
	 * @param authenticationClient the authentication client
	 * @param userDataMapper the user data mapper
	 */
	public SPOOAuthWebServiceUserDetailsService (AuthenticationClient<CSSPOAccountData> authenticationClient, UserDataMapper<CSSPOUserData> userDataMapper)
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

		SPOUserData userData = (SPOUserData) userDataMapper.mapToUserData((CSSPOUserData) accountData.getUser());
		OAuthData oAuthData = new OAuthData((String) token.getPrincipal(), deviceId, ip);
		return new SPOOAuthLoggedAccount(accountData.getId(), accountData.getLogin(), "N/A", accountData.getState().equals(CSAccountDataStates.ACTIVE), //
			userData, convertRolesToAuthorities(accountData.getRoles()), oAuthData, accountData.getAdditionalInformation());
	}

	private VerificationResponseBody<CSAccountData> verify (String token, String deviceId, String ip)
	{
		try
		{
			// convert response with CSSPOAccountData to response with CSAccountData - to unify interface of spring integration
			VerificationResponseBody<CSSPOAccountData> response = authenticationClient.verify(new VerifyRequestBody(token, deviceId, ip));
			return new VerificationResponseBody<>( //
				new CSAccountData(response.getAccountData().getId(), response.getAccountData().getUid(), response.getAccountData().getLogin(), response.getAccountData().getEmail(), response.getAccountData().getState(), //
					response.getAccountData().getRoles(), response.getAccountData().getUser(), response.getAccountData().getAdditionalInformation()), //
				response.getVerificationType() //
			);
		}
		catch (Exception ex)
		{
			LOGGER.error("Error by fetching user with user token {} and device id {} not found", token, deviceId, ex);
			throw new AuthenticationServiceException("Error by fetching user with user token " + token + " and device id " + deviceId + " not found", ex);
		}
	}
}
