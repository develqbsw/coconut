/*
 * Copyright 2002-2016 the original author or authors. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package sk.qbsw.security.oauth.web;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import sk.qbsw.security.authentication.spring.model.Organization;
import sk.qbsw.security.authentication.spring.preauth.OAuthWebAuthenticationDetails;
import sk.qbsw.security.authentication.spring.preauth.model.OAuthData;
import sk.qbsw.security.authentication.spring.preauth.model.OAuthLoggedUser;
import sk.qbsw.security.authentication.spring.preauth.service.BaseOAuthUserDetailsService;
import sk.qbsw.security.oauth.model.AccountData;
import sk.qbsw.security.oauth.model.VerificationData;
import sk.qbsw.security.oauth.service.OAuthService;

/**
 * The oauth pre authenticated user details service.
 *
 * @author Tomas Lauro
 * @version 1.18.0
 * @since 1.18.0
 */
public class OAuthServiceUserDetailsService extends BaseOAuthUserDetailsService
{
	private final OAuthService oauthService;

	/**
	 * Instantiates a new O auth service user details service.
	 *
	 * @param oauthService the oauth service
	 */
	public OAuthServiceUserDetailsService (OAuthService oauthService)
	{
		super();
		this.oauthService = oauthService;
	}

	protected UserDetails createUserDetails (Authentication token)
	{
		String deviceId = ((OAuthWebAuthenticationDetails) token.getDetails()).getDeviceId();
		String ip = ((OAuthWebAuthenticationDetails) token.getDetails()).getIp();

		AccountData accountData = verify((String) token.getPrincipal(), deviceId, ip).getAccountData();

		Organization organization = Organization.newBuilder() //
			.id(accountData.getUser().getOrganization().getId()) //
			.name(accountData.getUser().getOrganization().getName()) //
			.code(accountData.getUser().getOrganization().getCode()) //
			.build();

		OAuthData oAuthData = OAuthData.newBuilder() //
			.token((String) token.getPrincipal()) //
			.deviceId(deviceId) //
			.ip(ip) //
			.build();

		return new OAuthLoggedUser(accountData.getUser().getId(), accountData.getUser().getLogin(), "N/A", convertRolesToAuthorities(accountData.getUser().exportRoles()), organization, oAuthData, accountData.getAdditionalInformation());
	}

	private VerificationData verify (String token, String deviceId, String ip)
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
