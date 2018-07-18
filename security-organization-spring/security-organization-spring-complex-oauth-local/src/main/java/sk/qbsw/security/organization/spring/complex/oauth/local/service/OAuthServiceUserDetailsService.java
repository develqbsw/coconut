package sk.qbsw.security.organization.spring.complex.oauth.local.service;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import sk.qbsw.security.oauth.model.VerificationData;
import sk.qbsw.security.oauth.service.facade.OAuthServiceFacade;
import sk.qbsw.security.organization.complex.oauth.model.ComplexOrganizationAccountData;
import sk.qbsw.security.organization.spring.complex.base.model.ComplexOrganization;
import sk.qbsw.security.organization.spring.complex.base.model.ComplexOrganizationUnit;
import sk.qbsw.security.organization.spring.complex.oauth.base.model.OAuthLoggedUser;
import sk.qbsw.security.spring.oauth.common.model.OAuthData;
import sk.qbsw.security.spring.oauth.common.model.OAuthWebAuthenticationDetails;
import sk.qbsw.security.spring.oauth.common.service.BaseOAuthUserDetailsService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The oauth pre authenticated user details service.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.0
 */
public class OAuthServiceUserDetailsService extends BaseOAuthUserDetailsService
{
	private final OAuthServiceFacade<ComplexOrganizationAccountData> oauthService;

	/**
	 * Instantiates a new O auth service user details service.
	 *
	 * @param oauthService the oauth service
	 */
	public OAuthServiceUserDetailsService (OAuthServiceFacade<ComplexOrganizationAccountData> oauthService)
	{
		super();
		this.oauthService = oauthService;
	}

	protected UserDetails createUserDetails (Authentication token)
	{
		String deviceId = ((OAuthWebAuthenticationDetails) token.getDetails()).getDeviceId();
		String ip = ((OAuthWebAuthenticationDetails) token.getDetails()).getIp();

		ComplexOrganizationAccountData accountData = verify((String) token.getPrincipal(), deviceId, ip).getAccountData();

		List<ComplexOrganization> organizations = accountData.getOrganizations().stream().map(o -> new ComplexOrganization(o.getId(), o.getName(), o.getCode(), o.getUnits().stream().map(u -> new ComplexOrganizationUnit(u.getId(), u.getName(), u.getCode())).collect(Collectors.toList()))).collect(Collectors.toList());
		OAuthData oAuthData = new OAuthData((String) token.getPrincipal(), deviceId, ip);

		return new OAuthLoggedUser(accountData.getId(), accountData.getLogin(), "N/A", convertRolesToAuthorities(accountData.getRoles()), organizations, oAuthData, accountData.getAdditionalInformation());
	}

	private VerificationData<ComplexOrganizationAccountData> verify (String token, String deviceId, String ip)
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
