package sk.qbsw.security.authentication.provider;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.base.logging.annotation.CNotLogged;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.security.authentication.service.AuthenticationSecurityService;
import sk.qbsw.security.core.model.domain.Account;

/**
 * Abstract provider for spring security.
 * 
 * @author Dalibor Rak
 * @author Tomas lauro
 * 
 * @version 1.13.4
 * @since 1.6.0
 * 
 */
public abstract class BaseAuthenticationProvider extends AService implements AuthenticationProvider
{
	private AuthenticationSecurityService authenticationSecurityService;
	
	public BaseAuthenticationProvider (AuthenticationSecurityService authenticationSecurityService)
	{
		this.authenticationSecurityService = authenticationSecurityService;
	}

	@Override
	public Authentication authenticate (@CNotLogged @CNotAuditLogged Authentication authentication) throws AuthenticationException
	{
		Authentication retVal = null;
		try
		{
			if (this.supports(authentication.getClass()) && authenticationSecurityService.supports(authentication.getClass()))
			{
				Account authenticatedUser = authenticationSecurityService.login(authentication);
				retVal = populateAuthentication(authentication, authenticatedUser, transformUserRolesToAuthorities(authenticatedUser.exportRoles()));
			}
		}
		catch (CSecurityException e)
		{
			throw new AuthenticationServiceException("User not authenticated", e);
		}

		return retVal;
	}

	/**
	 * Populate authentication token with security data. The result should be the fully populated authentication to be used in security context.
	 *
	 * @param authentication the input authentication token
	 * @param authenticatedUser the authenticated user from security service
	 * @param authorities the authorities list of authorities
	 * @return the fully populated authentication token 
	 */
	protected abstract Authentication populateAuthentication (@CNotLogged @CNotAuditLogged Authentication authentication, @CNotLogged @CNotAuditLogged Account authenticatedUser, @CNotLogged @CNotAuditLogged List<GrantedAuthority> authorities);

	/**
	 * Transform user roles to spring authorities.
	 *
	 * @param userRoles the user roles
	 * @return the list of spring authorities
	 */
	private List<GrantedAuthority> transformUserRolesToAuthorities (List<String> userRoles)
	{
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String role : userRoles)
		{
			authorities.add(new SimpleGrantedAuthority(role));
		}

		return authorities;
	}
}
