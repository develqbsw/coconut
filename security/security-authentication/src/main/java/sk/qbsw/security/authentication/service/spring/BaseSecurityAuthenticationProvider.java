package sk.qbsw.security.authentication.service.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import sk.qbsw.security.core.model.domain.CUser;

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
public abstract class BaseSecurityAuthenticationProvider extends AService implements AuthenticationProvider
{
	/** The spring authentication service. */
	private SpringAuthenticationService springAuthenticationService;

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate (@CNotLogged @CNotAuditLogged Authentication authentication) throws AuthenticationException
	{
		Authentication retVal = null;
		try
		{
			if (this.supports(authentication.getClass()) == true && springAuthenticationService.supports(authentication.getClass()) == true)
			{
				CUser authenticatedUser = springAuthenticationService.login(authentication);
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
	protected abstract Authentication populateAuthentication (@CNotLogged @CNotAuditLogged Authentication authentication, @CNotLogged @CNotAuditLogged CUser authenticatedUser, @CNotLogged @CNotAuditLogged List<GrantedAuthority> authorities);

	/**
	 * Transform user roles to spring authorities.
	 *
	 * @param userRoles the user roles
	 * @return the list of spring authorities
	 */
	private List<GrantedAuthority> transformUserRolesToAuthorities (List<String> userRoles)
	{
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : userRoles)
		{
			authorities.add(new SimpleGrantedAuthority(role));
		}

		return authorities;
	}

	/**
	 * Sets the spring authentication service.
	 *
	 * @param springAuthenticationService the new spring authentication service
	 */
	@Autowired
	public void setSpringAuthenticationService (SpringAuthenticationService springAuthenticationService)
	{
		this.springAuthenticationService = springAuthenticationService;
	}
}
