/*
 * Copyright 2002-2016 the original author or authors. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package sk.qbsw.security.integration.authentication.web.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.Assert;
import sk.qbsw.security.api.authentication.client.model.CSAccountData;
import sk.qbsw.security.api.authentication.client.model.request.VerifyRequestBody;
import sk.qbsw.security.authentication.spring.model.Organization;
import sk.qbsw.security.authentication.spring.preauth.OAuthPreAuthenticatedWebAuthenticationDetails;
import sk.qbsw.security.authentication.spring.preauth.model.OAuthData;
import sk.qbsw.security.authentication.spring.preauth.model.OAuthLoggedUser;
import sk.qbsw.security.integration.authentication.client.AuthenticationClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The oauth pre authenticated user details service.
 *
 * @author Tomas Lauro
 * @version 1.18.0
 * @since 1.18.0
 */
public class OAuthPreAuthenticatedUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken>
{
	private final AuthenticationClient authenticationClient;

	/**
	 * Instantiates a new O auth pre authenticated user details service.
	 *
	 * @param authenticationClient the authentication client
	 */
	public OAuthPreAuthenticatedUserDetailsService (AuthenticationClient authenticationClient)
	{
		this.authenticationClient = authenticationClient;
	}

	/**
	 * Get a UserDetails object based on the user name contained in the given token.
	 */
	public final UserDetails loadUserDetails (PreAuthenticatedAuthenticationToken token) throws AuthenticationException
	{
		Assert.notNull(token.getDetails());
		Assert.isInstanceOf(OAuthPreAuthenticatedWebAuthenticationDetails.class, token.getDetails());

		return createUserDetails(token);
	}

	/**
	 * Create user details user details.
	 *
	 * @param token the token
	 * @return the user details
	 */
	protected UserDetails createUserDetails (Authentication token)
	{
		String deviceId = ((OAuthPreAuthenticatedWebAuthenticationDetails) token.getDetails()).getDeviceId();
		String ip = ((OAuthPreAuthenticatedWebAuthenticationDetails) token.getDetails()).getIp();

		CSAccountData accountData;
		try
		{
			accountData = authenticationClient.verify(VerifyRequestBody.newBuilder().token((String) token.getPrincipal()).deviceId(deviceId).ip(ip).build());
		}
		catch (Exception ex)
		{
			throw new BadCredentialsException("Loading user details failed", ex);
		}

		Organization organization = Organization.newBuilder() //
			.id(accountData.getOrganization().getId()) //
			.name(accountData.getOrganization().getName()) //
			.code(accountData.getOrganization().getCode()) //
			.build();

		OAuthData oAuthData = OAuthData.newBuilder() //
			.deviceId(deviceId) //
			.ip(ip) //
			.build();

		return new OAuthLoggedUser(accountData.getId(), accountData.getLogin(), null, convertRolesToAuthorities(accountData.getRoles()), organization, oAuthData);
	}

	/**
	 * Convert roles to authorities collection.
	 *
	 * @param roles the roles
	 * @return the collection
	 */
	protected Collection<GrantedAuthority> convertRolesToAuthorities (List<String> roles)
	{
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

		if (roles != null)
		{
			for (String role : roles)
			{
				grantedAuthorities.add(new SimpleGrantedAuthority(role));
			}
		}

		return grantedAuthorities;
	}
}
