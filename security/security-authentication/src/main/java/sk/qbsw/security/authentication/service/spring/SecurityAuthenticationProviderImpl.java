package sk.qbsw.security.authentication.service.spring;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import sk.qbsw.security.authentication.model.spring.UsernamePasswordUnitAuthentication;
import sk.qbsw.security.core.model.domain.User;

/**
 * Provider for spring security.
 * 
 * @author Dalibor Rak
 * @author Tomas lauro
 * 
 * @version 1.13.4
 * @since 1.6.0
 * 
 */
@Component ("customAuthenticationProvider")
public class SecurityAuthenticationProviderImpl extends BaseSecurityAuthenticationProvider
{
	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports (Class<? extends Object> authentication)
	{
		return UsernamePasswordUnitAuthentication.class.isAssignableFrom(authentication);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.spring.ASecurityAuthenticationProvider#populateAuthentication(org.springframework.security.core.Authentication, sk.qbsw.security.core.core.model.domain.CUser, java.util.List)
	 */
	@Override
	protected Authentication populateAuthentication (Authentication authentication, User authenticatedUser, List<GrantedAuthority> authorities)
	{
		//get supported authentication
		UsernamePasswordUnitAuthentication usernameAuthentication = (UsernamePasswordUnitAuthentication) authentication;

		//create fully populated authentication
		UsernamePasswordUnitAuthentication populatedAuthentication = new UsernamePasswordUnitAuthentication(usernameAuthentication.getName(), usernameAuthentication.getCredentials(), authorities, usernameAuthentication.getUnit());
		populatedAuthentication.setDetails(authenticatedUser);

		//return value
		return populatedAuthentication;
	}

}
