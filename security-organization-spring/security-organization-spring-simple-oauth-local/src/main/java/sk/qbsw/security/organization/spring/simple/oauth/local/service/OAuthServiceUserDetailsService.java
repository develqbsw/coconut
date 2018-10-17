package sk.qbsw.security.organization.spring.simple.oauth.local.service;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.oauth.model.VerificationData;
import sk.qbsw.security.oauth.service.facade.OAuthServiceFacade;
import sk.qbsw.security.organization.simple.base.model.SPOOrganizationOutputData;
import sk.qbsw.security.organization.simple.base.model.SPOUserOutputData;
import sk.qbsw.security.organization.spring.simple.base.model.SimpleOrganization;
import sk.qbsw.security.organization.spring.simple.oauth.base.model.OAuthLoggedUser;
import sk.qbsw.security.spring.oauth.common.model.OAuthData;
import sk.qbsw.security.spring.oauth.common.model.OAuthWebAuthenticationDetails;
import sk.qbsw.security.spring.oauth.common.service.BaseOAuthUserDetailsService;

/**
 * The oauth pre authenticated user details service.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.0
 */
public class OAuthServiceUserDetailsService extends BaseOAuthUserDetailsService
{
	private final OAuthServiceFacade<AccountData> oauthService;

	/**
	 * Instantiates a new O auth service user details service.
	 *
	 * @param oauthService the oauth service
	 */
	public OAuthServiceUserDetailsService (OAuthServiceFacade<AccountData> oauthService)
	{
		super();
		this.oauthService = oauthService;
	}

	protected UserDetails createUserDetails (Authentication token)
	{
		String deviceId = ((OAuthWebAuthenticationDetails) token.getDetails()).getDeviceId();
		String ip = ((OAuthWebAuthenticationDetails) token.getDetails()).getIp();

		AccountData accountData = verify((String) token.getPrincipal(), deviceId, ip).getAccountData();
		SPOOrganizationOutputData organizationOutputData = ((SPOUserOutputData) accountData.getUser()).getOrganization();

		SimpleOrganization organization = new SimpleOrganization(organizationOutputData.getId(), organizationOutputData.getName(), organizationOutputData.getCode());
		OAuthData oAuthData = new OAuthData((String) token.getPrincipal(), deviceId, ip);

		return new OAuthLoggedUser(accountData.getId(), accountData.getLogin(), "N/A", convertRolesToAuthorities(accountData.getRoles()), organization, oAuthData, accountData.getAdditionalInformation());
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
