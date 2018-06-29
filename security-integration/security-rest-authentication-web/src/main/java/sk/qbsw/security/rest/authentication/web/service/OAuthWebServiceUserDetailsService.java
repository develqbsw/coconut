package sk.qbsw.security.rest.authentication.web.service;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import sk.qbsw.security.authentication.spring.model.Organization;
import sk.qbsw.security.authentication.spring.preauth.model.OAuthData;
import sk.qbsw.security.authentication.spring.preauth.model.OAuthLoggedUser;
import sk.qbsw.security.authentication.spring.preauth.model.OAuthWebAuthenticationDetails;
import sk.qbsw.security.authentication.spring.preauth.service.BaseOAuthUserDetailsService;
import sk.qbsw.security.rest.authentication.client.AuthenticationClient;
import sk.qbsw.security.rest.authentication.client.model.CSAccountData;
import sk.qbsw.security.rest.authentication.client.model.request.VerifyRequestBody;
import sk.qbsw.security.rest.authentication.client.model.response.VerificationResponseBody;

/**
 * The oauth pre authenticated user details service.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.0
 */
public class OAuthWebServiceUserDetailsService extends BaseOAuthUserDetailsService
{
	private final AuthenticationClient authenticationClient;

	/**
	 * Instantiates a new O auth pre authenticated user details service.
	 *
	 * @param authenticationClient the authentication client
	 */
	public OAuthWebServiceUserDetailsService (AuthenticationClient authenticationClient)
	{
		super();
		this.authenticationClient = authenticationClient;
	}

	protected UserDetails createUserDetails (Authentication token)
	{
		String deviceId = ((OAuthWebAuthenticationDetails) token.getDetails()).getDeviceId();
		String ip = ((OAuthWebAuthenticationDetails) token.getDetails()).getIp();

		CSAccountData accountData = verify((String) token.getPrincipal(), deviceId, ip).getAccountData();

		Organization organization = new Organization(accountData.getOrganization().getId(), accountData.getOrganization().getName(), accountData.getOrganization().getCode());
		OAuthData oAuthData = new OAuthData((String) token.getPrincipal(), deviceId, ip);

		return new OAuthLoggedUser(accountData.getId(), accountData.getLogin(), "N/A", convertRolesToAuthorities(accountData.getRoles()), organization, oAuthData, accountData.getAdditionalInformation());
	}

	private VerificationResponseBody verify (String token, String deviceId, String ip)
	{
		try
		{
			return authenticationClient.verify(VerifyRequestBody.builder().token(token).deviceId(deviceId).ip(ip).build());
		}
		catch (Exception ex)
		{
			LOGGER.error("Error by fetching user with user token {} and device id {} not found", token, deviceId, ex);
			throw new AuthenticationServiceException("Error by fetching user with user token " + token + " and device id " + deviceId + " not found", ex);
		}
	}
}
