/*
 * Copyright 2002-2016 the original author or authors. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package sk.qbsw.security.oauth.web;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import sk.qbsw.security.authentication.spring.model.Organization;
import sk.qbsw.security.authentication.spring.preauth.OAuthWebAuthenticationDetails;
import sk.qbsw.security.authentication.spring.preauth.model.OAuthData;
import sk.qbsw.security.authentication.spring.preauth.model.OAuthLoggedUser;
import sk.qbsw.security.authentication.spring.preauth.service.BaseOAuthUserDetailsService;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.oauth.service.AuthenticationTokenService;
import sk.qbsw.security.oauth.service.MasterTokenService;

/**
 * The oauth pre authenticated user details service.
 *
 * @author Tomas Lauro
 * @version 1.18.0
 * @since 1.18.0
 */
public class OAuthServiceUserDetailsService extends BaseOAuthUserDetailsService
{
	private final MasterTokenService masterTokenService;

	private final AuthenticationTokenService authenticationTokenService;

	/**
	 * Instantiates a new O auth service user details service.
	 *
	 * @param masterTokenService the master token service
	 * @param authenticationTokenService the authentication token service
	 */
	public OAuthServiceUserDetailsService (MasterTokenService masterTokenService, AuthenticationTokenService authenticationTokenService)
	{
		super();
		this.masterTokenService = masterTokenService;
		this.authenticationTokenService = authenticationTokenService;
	}

	protected UserDetails createUserDetails (Authentication token)
	{
		String deviceId = ((OAuthWebAuthenticationDetails) token.getDetails()).getDeviceId();
		String ip = ((OAuthWebAuthenticationDetails) token.getDetails()).getIp();

		User userData = findUserData((String) token.getPrincipal(), deviceId, ip);

		Organization organization = Organization.newBuilder() //
			.id(userData.getOrganization().getId()) //
			.name(userData.getOrganization().getName()) //
			.code(userData.getOrganization().getCode()) //
			.build();

		OAuthData oAuthData = OAuthData.newBuilder() //
			.token((String) token.getPrincipal()) //
			.deviceId(deviceId) //
			.ip(ip) //
			.build();

		return new OAuthLoggedUser(userData.getId(), userData.getLogin(), "N/A", convertRolesToAuthorities(userData.exportRoles()), organization, oAuthData);
	}

	private User findUserData (String token, String deviceId, String ip)
	{
		try
		{
			User userByMasterToken = masterTokenService.getUserByMasterToken(token, deviceId, ip);
			User userByAuthenticationToken = authenticationTokenService.getUserByAuthenticationToken(token, deviceId, ip);

			if (userByMasterToken != null)
			{
				return userByMasterToken;
			}
			else if (userByAuthenticationToken != null)
			{
				return userByAuthenticationToken;
			}
			else
			{
				LOGGER.error("User for user token {} and device id {} not found", token, deviceId);
				throw new UsernameNotFoundException("User for user token " + token + " and device id " + deviceId + " not found");
			}
		}
		catch (Exception ex)
		{
			LOGGER.error("Error by fetching user with user token {} and device id {} not found", token, deviceId, ex);
			throw new AuthenticationServiceException("Error by fetching user with user token " + token + " and device id " + deviceId + " not found", ex);
		}
	}
}
